package sanchez.sanchez.sergio.brownie.ble.scan.filters

import android.bluetooth.le.ScanResult
import sanchez.sanchez.sergio.brownie.ble.mappers.mapperScanResultToBleDevice
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.models.gatt.GattService
import sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters.BelongsToFilter
import sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters.CompositeFilterAND
import sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters.IPassFilter
import sanchez.sanchez.sergio.brownie.ble.scan.filters.postScanFilters.IPostScanFilter
import sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues.SRServiceUuids

/**
 * Objective of the document:
 */
class GattServiceFilter (

        val gattService : GattService,
        var postScanFilter : IPostScanFilter? = null
) {

    var filterAND : CompositeFilterAND
    var bleScanResultsPassed : MutableList<BleDevice> = mutableListOf<BleDevice>()

    init {
        val filterService = BelongsToFilter(
            gattService.uuid.toString(),
            SRServiceUuids
        )
        filterAND = CompositeFilterAND()
        filterAND.filters.add(filterService)
    }

    fun addFilter(filter : IPassFilter){
        filterAND.filters.add(filter)
    }

    fun passFilter(scanResult: ScanResult) : Boolean {
        if (filterAND.passFilter(scanResult)) {
            bleScanResultsPassed.add(mapperScanResultToBleDevice(scanResult, gattService))
            return true
        }
        else {
            return false
        }
    }

    fun postScanFilter(){
        postScanFilter?.let{
            it.postFilter(bleScanResultsPassed)
        }
    }

}