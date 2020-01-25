package sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters

import android.bluetooth.le.ScanResult

/**
 * A class to compose different filters (classes that implements IPassFilter)
 * by an AND logic operation
 */
class CompositeFilterAND (var filters : MutableList<IPassFilter> = mutableListOf()): IPassFilter {

    override fun passFilter(scanResult: ScanResult) : Boolean = filters.find {
        !it.passFilter(scanResult) } == null

}