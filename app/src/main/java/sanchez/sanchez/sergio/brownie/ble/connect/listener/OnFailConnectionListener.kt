package sanchez.sanchez.sergio.brownie.ble.connect.listener

import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.scan.filters.BleFilter

/**
 * Fail Connection Listener
 */
interface OnFailConnectionListener {

    /**
     * on finish scan no results
     */
    fun onFinishScanNoResults()

    /**
     * on finish scan
     * @param bleFilter
     */
    fun onFinishScan(bleFilter: BleFilter)

    /**
     * on fail connection
     * @param bleDevice
     */
    fun onFailConnection(bleDevice: BleDevice)
}