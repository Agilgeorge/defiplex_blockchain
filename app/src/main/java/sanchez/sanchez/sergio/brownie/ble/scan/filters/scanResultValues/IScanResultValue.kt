package sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues

import android.bluetooth.le.ScanResult

/**
 * Interface to get values from a scanResult
 * For example SRMacAddress returns a mac address of the scanned device (scanResult)
 */
interface IScanResultValue {

    public fun getValue(scanResult : ScanResult) : String
}