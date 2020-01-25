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
class ScannerGattTime(private val filter: BleFilter) : IScanner, ScanCallback() {

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
            .build(),
            scanPeriodInMillis,
            scanCallback = this)


        scanJob.invokeOnCompletion {

            try {
                if (filter.hasAtLeastOneMatchInAnyService()) {
                    listener.onFinishScan(filter)
                } else {
                    listener.onFinishScanNoResult()
                }
            } catch (e: Exception){
                e.printStackTrace()
                listener.onErrorScan(0)
            }
        }

    }


    /************************************************
     * ScanCallback methods
     ************************************************/

    override fun onScanResult(callbackType: Int, result: ScanResult) {
        filter.passFilter(result)
    }


    override fun onScanFailed(errorCode: Int) {
        Log.d("LOGBLE", "Scanner Error -> $errorCode")
        BleScannerManager.stopScan(this@ScannerGattTime)
        scanResultsListener.onErrorScan(errorCode)
    }


    override fun onBatchScanResults(results: List<ScanResult>) {

    }


    fun stop() {
        BleScannerManager.stopScan(this)
    }
}