package sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues

import android.bluetooth.le.ScanResult

/**
 * Objective of the document:
 */
object SRMacAddress : IScanResultValue {

    public override fun getValue(scanResult : ScanResult) : String{
        return scanResult.device.address
    }
}