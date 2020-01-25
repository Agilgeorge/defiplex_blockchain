package sanchez.sanchez.sergio.brownie.ble.scan

import android.bluetooth.le.ScanResult

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
}