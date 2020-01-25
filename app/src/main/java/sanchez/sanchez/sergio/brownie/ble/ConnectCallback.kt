package sanchez.sanchez.sergio.brownie.ble

import android.bluetooth.*
import android.util.Log
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnSuccessConnectionListener
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice

/**
 * Connect Callback
 */
class ConnectCallback (val bleDevice : BleDevice, var onConnectionListener : OnSuccessConnectionListener
) : BluetoothGattCallback(){


    private val TAG = "BLE_CONNECT_CALL"


    /************************************************
     * BluetoothGattCallback methods
     ************************************************/


    /**
     * On Connection State Change
     * @param gatt
     * @param status
     * @param newState
     */
    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {

        Log.d(TAG, "Device Mac Address -> ${gatt.device.address}")

        when(status) {

            BluetoothGatt.GATT_SUCCESS ->
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d(TAG, "ConnectCallback onConnect with status ok: ${gatt.device.address}")
                    gatt.discoverServices()
                }
                else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d(TAG, "ConnectCallback onDisconnect with status ok: ${gatt.device.address}")
                    //ConnectedBleDevices.disconnectedFrom(gatt.device.address)
                    gatt.close()
                }

            else ->
                if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d(TAG, "ConnectCallback onDisconnect with status not ok : ${gatt.device.address}")
                    //ConnectedBleDevices.disconnected(bleDevice)
                    gatt.close()


                }
                else if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d(TAG, "ConnectCallback onConnect with status not ok : ${gatt.device.address}")
                    //BleManager.disconnect(gatt)
                }
        }

    }

    /**
     * On Services Discovered
     * @param gatt
     * @param status
     */
    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        try {

            if (status == BluetoothGatt.GATT_SUCCESS) {

                if (bleDevice.address == gatt.device.address) {

                    //BluetoothGattService
                    val service = gatt.getService(bleDevice.gattService.uuid)

                    val gattCharacteristic = bleDevice.gattService.getCharacteristicsReadNotifyOrdered()[0]
                    //BluetoothGattCharacteristic
                    val characteristic = service.getCharacteristic(gattCharacteristic.uuid)

                    if (gatt.setCharacteristicNotification(characteristic, true)) {

                        val gattDescriptor = gattCharacteristic.descriptors[0]
                        //BluetoothGattDescriptor
                        val descriptor = characteristic.getDescriptor(gattDescriptor.uuid)
                        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        gatt.writeDescriptor(descriptor)
                    }
                }
            }
        } catch (e : Exception){
            e.printStackTrace()
        }
    }

    /**
     * On Characteristic Changed
     * @param gatt
     * @param characteristic
     */
    override fun onCharacteristicChanged(
            gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic){
        if (bleDevice.address == gatt.device.address) {
            val protocol = bleDevice.gattService.getProtocol(bleDevice.protocolVersion)
            val data = characteristic.value
            protocol.newMessage(data)
        }
    }

    /**
     * On Characteristic Read
     * @param gatt
     * @param characteristic
     * @param status
     */
    override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int){
        Log.d(TAG, "onCharacteristicRead CALLED")
    }

    /**
     * On Characteristic Write
     * @param gatt
     * @param characteristic
     * @param status
     */
    override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int){
        Log.d(TAG, "onCharacteristicRead Write")
    }

    /**
     * On Descriptor Write
     * @param gatt
     * @param descriptor
     * @param status
     */
    override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int){
        Log.d(TAG, "onDescriptorWrite Write")
        if (bleDevice.address == gatt.device.address) {
            bleDevice.gatt = gatt
            //ConnectedBleDevices.add(bleDevice)
            onConnectionListener.onConnection(bleDevice)
            //bleDevice.gattService.notifyModelToConnect(bleDevice, true)
        }

    }
}