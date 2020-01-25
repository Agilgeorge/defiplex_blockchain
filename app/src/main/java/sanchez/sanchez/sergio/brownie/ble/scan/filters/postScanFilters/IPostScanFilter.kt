package sanchez.sanchez.sergio.brownie.ble.scan.filters.postScanFilters

import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice

/**
 * Post Scan Filter Interface
 */
interface IPostScanFilter {

    /**
     * Post Filter
     * @param scanResults
     */
    fun postFilter(scanResults : MutableList<BleDevice>)
}