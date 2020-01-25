package sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters

import android.bluetooth.le.ScanResult

/**
 * A class to compose different filters (classes that implements IPassFilter) by an OR logic operation
 */

/*
 * If constructor is invoked with no parameter then filters is initialized to an empty mutable list
 */
class CompositeFilterOR (var filters : MutableList<IPassFilter> = mutableListOf<IPassFilter>()):
        IPassFilter {

    public override fun passFilter(scanResult: ScanResult) : Boolean {
        for (filter in filters){
            if (filter.passFilter(scanResult)){
                return true
            }
        }
        return false
    }

}