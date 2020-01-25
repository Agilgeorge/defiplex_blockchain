package sanchez.sanchez.sergio.brownie.ble.scan.filters

import android.bluetooth.le.ScanResult
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.models.gatt.GattService
import sanchez.sanchez.sergio.brownie.ble.scan.IBleFilter

/**
 * Ble Filter
 */
class BleFilter (
    private val gattServiceFilters : MutableList<GattServiceFilter> = mutableListOf()
): IBleFilter {

    // Cache for scan results passed
    private var scanResultsPassed : MutableList<ScanResult> = mutableListOf()

    /**
     * Pass Filter
     * @param scanResult
     */
    override fun passFilter(scanResult: ScanResult) : Boolean =
            if(!isAlreadyPassed(scanResult))
                gattServiceFilters.filter {
                    it.passFilter(scanResult)
                }.let { filterPassedList ->

                    val passAlmostOneFilter = filterPassedList.isNotEmpty()

                    if (passAlmostOneFilter)
                        scanResultsPassed.add(scanResult)

                    passAlmostOneFilter
                }
            else
                true

    /**
     * Post Scan Filter
     */
    override fun postScanFilter(){
        for (filter in gattServiceFilters) {
            filter.postScanFilter()
        }
    }

    /**
     * Find Ble Devices For Gatt Service
     * @param gattService
     */
    fun findBleDevicesForGattService(gattService : GattService) : MutableList<BleDevice>{
        var result : MutableList<BleDevice> = mutableListOf()

        for (gattServiceFilter in gattServiceFilters) {
            if (gattServiceFilter.gattService.uuid == gattService.uuid) {
                result = gattServiceFilter.bleScanResultsPassed
                break;
            }
        }
        return result
    }

    /**
     * Find First Ble Device For Each Service
     */
    fun findFirstBleDeviceForEachService() : MutableList<BleDevice> {
        val result : MutableList<BleDevice> = mutableListOf()

        for (gattServiceFilter in gattServiceFilters) {
            if (gattServiceFilter.bleScanResultsPassed.size > 0){
                result.add(gattServiceFilter.bleScanResultsPassed[0])
            }
        }
        return result
    }

    /**
     * Has At Least One Match In Any Service
     */
    fun hasAtLeastOneMatchInAnyService() : Boolean {
        var result : Boolean = false
        for (gattServiceFilter in gattServiceFilters){
            if (gattServiceFilter.bleScanResultsPassed.size > 0){
                result = true
                break
            }
        }
        return result
    }


    /**
     * Private Methods
     */

    private fun isAlreadyPassed(scanResult: ScanResult) : Boolean{
        var result : Boolean = false
        for (scanResultPassed in scanResultsPassed){
            if (scanResult.device.address == scanResultPassed.device.address){
                result = true
                break
            }
        }
        return result
    }
}