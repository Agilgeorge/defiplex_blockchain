package sanchez.sanchez.sergio.brownie.ble.models.devices

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import sanchez.sanchez.sergio.brownie.ble.models.gatt.GattService
import sanchez.sanchez.sergio.brownie.ble.protocol.UNKOWN_VERSION

/**
    Model that represents a BLE Device
 **/
data class BleDevice(
        val address : String,
        val name : String,
        val device : BluetoothDevice,
        val rssi : Int,
        val gattService: GattService,
        val hasProtocolVersion : Boolean = false,
        val protocolVersion: String = UNKOWN_VERSION,
        var gatt : BluetoothGatt? = null
)