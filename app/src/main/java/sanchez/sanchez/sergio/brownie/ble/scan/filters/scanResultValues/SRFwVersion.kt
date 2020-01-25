package sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues

import android.bluetooth.le.ScanResult
import sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues.IScanResultValue

/**
 * Objective of the document:
 */
object SRFwVersion : IScanResultValue {

    public override fun getValue(scanResult : ScanResult) : String{
        return "2.3.78"
    }
}