package sanchez.sanchez.sergio.brownie.ble.ext

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.getDefaultAdapter
import android.bluetooth.le.BluetoothLeScanner


/**
 * Is Adapter Enable
 */
fun BluetoothAdapter.isAdapterEnable() =
       getDefaultAdapter().isEnabled

/**
 * Get Scanner
 */
fun BluetoothAdapter.getScanner(): BluetoothLeScanner =
        getDefaultAdapter().bluetoothLeScanner