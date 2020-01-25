package sanchez.sanchez.sergio.brownie.ble.scan.filters.scanResultValues

import android.bluetooth.le.ScanResult

/**
 * Interface to get values from a scanResult
 * For example SRServiceUuids returns the list of serviceUUids of a device scanned
 */
interface IScanResultValues {

    public fun getValues(scanResult : ScanResult) : List<String>
}