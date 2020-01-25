package sanchez.sanchez.sergio.brownie.ble.scan.filters.passFilters

import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.scan.filters.postScanFilters.IPostScanFilter

/**
 * Objective of the document:
 */
class HighestRssiFilter (): IPostScanFilter {

    var highestRssi : Int = Int.MIN_VALUE

    override fun postFilter(bleDevices : MutableList<BleDevice>){

        var scanResultPassed : BleDevice? = null

        for (bleDevice in bleDevices){
            if (bleDevice.rssi > highestRssi){
                highestRssi = bleDevice.rssi
                scanResultPassed = bleDevice
            }
        }

        bleDevices.clear()
        scanResultPassed?.let { bleDevices.add(it)}
    }

}