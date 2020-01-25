package sanchez.sanchez.sergio.brownie.ble.scan.listener

import sanchez.sanchez.sergio.brownie.ble.scan.IBleFilter


/**
 * This interface should be used if you expect multiple bluetooth scan results.
 */


interface IScanListener {

    /**
     * Events that notifies the scan finished
     * @param bleFilter
     */
    fun onFinishScan(bleFilter: IBleFilter)


    /**
     * On Finish Scan No Result
     */
    fun onFinishScanNoResult()

    /**
     * Events that notifies the errors occur during the scan, it returns the error code
     * @param error
     */
    fun onErrorScan(error : Int)



}