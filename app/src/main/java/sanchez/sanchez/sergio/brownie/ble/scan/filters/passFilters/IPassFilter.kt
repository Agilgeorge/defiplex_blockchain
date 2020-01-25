package sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters

import android.bluetooth.le.ScanResult

/**
 * All filters will implement this interface to know if a scanResult passes it
 */
interface IPassFilter {

    fun passFilter(scanResult: ScanResult):Boolean
}