package sanchez.sanchez.sergio.brownie.ble.ext

import android.bluetooth.BluetoothGattCallback
import android.content.Context
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice

/**
 * Connect to Ble Device
 */
fun BleDevice.connect(context : Context, callback : BluetoothGattCallback) {
    device.connectGatt(context, false, callback)
}

/**
 * Disconnect from Ble Device
 */
fun BleDevice.disconnect(){
    try {
        gatt?.disconnect()
    } catch (e : Exception){
        e.printStackTrace()
    }
}

/**
 * Send Command to Ble Device
 * @param commandFragments
 */
@Throws(Exception::class)
fun BleDevice.sendCommand(commandFragments: Array<ByteArray>) {
    try {
        gatt?.let {bleGatt ->
                val service = bleGatt.getService(gattService.uuid)
                val gattCharacteristic = gattService.getCharacteristicsWriteOrdered()[0]
                //BluetoothGattCharacteristic
                val characteristic = service?.getCharacteristic(gattCharacteristic.uuid)
                for (commandFragment in commandFragments) {
                    characteristic?.value = commandFragment
                    bleGatt.writeCharacteristic(characteristic)
                }
            }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
