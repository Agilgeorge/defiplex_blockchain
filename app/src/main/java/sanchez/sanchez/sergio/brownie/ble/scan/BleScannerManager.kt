package sanchez.sanchez.sergio.brownie.ble.scan

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sanchez.sanchez.sergio.brownie.ble.ext.getScanner

/**
 * Ble Scanner Manager
 */
object BleScannerManager: CoroutineScope by MainScope() {

    private var scanning : Boolean = false

    const val MAX_SCAN_PERIOD_IN_MILLIS = 2500L

    /**
     * Scan Devices Until
     * @param settings
     * @param periodInMillis
     * @param scanCallback
     *
     */
    fun scanDevicesUntil(
            settings: ScanSettings,
            periodInMillis: Long = MAX_SCAN_PERIOD_IN_MILLIS,
            scanCallback: ScanCallback) = launch {

        scanning = true

        // Start Scan
        BluetoothAdapter.getDefaultAdapter().getScanner()
                .startScan(mutableListOf(), settings, scanCallback)
        delay(periodInMillis) // non-blocking delay
        // Stop Scan
        stopScan(scanCallback)
    }

    /**
     * Stop Scan
     * @param scanCallback
     */
    fun stopScan(scanCallback: ScanCallback) {
        try {
            BluetoothAdapter.getDefaultAdapter()
                    .getScanner().stopScan(scanCallback)
        } catch (e: Exception){
            //catch exception in case BluetoothLeScanner try stopping an already stopped scan
        } finally {
            scanning = false
        }
    }
}