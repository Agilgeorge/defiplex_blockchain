package sanchez.sanchez.sergio.brownie.ble.scan

import android.bluetooth.le.ScanResult
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.models.gatt.GattService

interface IBleFilter {

    /**
     * Pass Filter
     * @param scanResult
     */
    fun passFilter(scanResult: ScanResult) : Boolean

    /**
     * Post Scan Filter
     */
    fun postScanFilter()

    /**
     * Has at least one match in any service
     */
    fun hasAtLeastOneMatchInAnyService() : Boolean

    /**
     * Find First BLE Device For Each Service
     */
    fun findFirstBleDeviceForEachService(): MutableList<BleDevice>

    /**
     * Find BLE Devices For Gatt Service
     */
    fun findBleDevicesForGattService(gattService : GattService): MutableList<BleDevice>
}