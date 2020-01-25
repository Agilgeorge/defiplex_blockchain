package sanchez.sanchez.sergio.brownie.ble.scan.strat.impl

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.util.Log
import sanchez.sanchez.sergio.brownie.ble.scan.filters.BleFilter
import kotlinx.coroutines.Job
import sanchez.sanchez.sergio.brownie.ble.scan.BleScannerManager
import sanchez.sanchez.sergio.brownie.ble.scan.listener.IScanListener
import sanchez.sanchez.sergio.brownie.ble.scan.strat.IScanner


/**
 * Scan strategy associated with multiple results of scan.
 * The filters apply in start Scan.
 */
class ScannerGattFirst(private val filter: BleFilter) : IScanner, ScanCallback() {

    /** ATTRIBUTES **/
    private lateinit var scanResultsListener: IScanListener
    private lateinit var scanJob : Job

    /** PUBLIC METHOD **/

    /************************************************
     * IScanMultipleResults methods
     ************************************************/

    override fun scan(listener: IScanListener, scanPeriodInMillis : Long) {

        this.scanResultsListener = listener

        scanJob = BleScannerManager.scanDevicesUntil(ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build(), scanCallback = this)

        scanJob.invokeOnCompletion {

            try {
                if (filter.hasAtLeastOneMatchInAnyService()) {
                    listener.onFinishScan(filter)
                } else {
                    listener.onFinishScanNoResult()
                }
            } catch (e: Exception){
                Log.d("LOGBLE", "Scanner Error -> ${e.message}")
                listener.onErrorScan(0)
            }
        }


    }


    /************************************************
     * ScanCallback methods
     ************************************************/

    override fun onScanResult(callbackType: Int, result: ScanResult) {

        if (filter.passFilter(result)){
            scanJob.cancel()
            BleScannerManager.stopScan(this)
        }
    }


    override fun onScanFailed(errorCode: Int) {

        BleScannerManager.stopScan(this@ScannerGattFirst)
        scanResultsListener.onErrorScan(errorCode)
    }


    override fun onBatchScanResults(results: List<ScanResult>) {

    }
}