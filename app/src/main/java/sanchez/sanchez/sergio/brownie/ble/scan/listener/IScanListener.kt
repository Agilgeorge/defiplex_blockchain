package sanchez.sanchez.sergio.brownie.ble.scan.listener

import sanchez.sanchez.sergio.brownie.ble.scan.filters.BleFilter


/**
 * This interface should be used if you expect multiple bluetooth scan results.
 */


interface IScanListener {

    /**
     * Events that notifies the scan finished
     * @param listBleDevices
     */
    fun onFinishScan(bleFilter: BleFilter)


    fun onFinishScanNoResult()

    /**
     * Events that notifies the errors occur during the scan, it returns the error code
     * @param error
     */
    fun onErrorScan(error : Int)



}