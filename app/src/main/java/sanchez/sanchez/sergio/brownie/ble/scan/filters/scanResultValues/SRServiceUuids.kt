package sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues

import android.bluetooth.le.ScanResult

/**
 * Objective of the document:
 */
object SRServiceUuids : IScanResultValues {

    public override fun getValues(scanResult : ScanResult) : List<String>{
        scanResult.scanRecord?.serviceUuids?.let {
            return it.map { it.toString() }.toList()
        }?: return listOf()
    }
}