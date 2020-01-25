package sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters

import android.bluetooth.le.ScanResult
import sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues.IScanResultValues

/**
 * Objective of the document:
 */
class BelongsToFilter (val value : String, val scanResultValues : IScanResultValues):
        IPassFilter {

    override fun passFilter(scanResult: ScanResult) : Boolean {
        return scanResultValues.getValues(scanResult).contains(value)
    }

}