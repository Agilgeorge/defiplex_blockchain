package sanchez.sanchez.sergio.brownie.ble.connect.strat.impl

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnFailConnectionListener
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnSuccessConnectionListener
import sanchez.sanchez.sergio.brownie.ble.connect.strat.IConnectAuto
import sanchez.sanchez.sergio.brownie.ble.ext.connect
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.scan.IBleFilter
import sanchez.sanchez.sergio.brownie.ble.scan.filters.BleFilter
import sanchez.sanchez.sergio.brownie.ble.scan.listener.IScanListener
import sanchez.sanchez.sergio.brownie.ble.scan.strat.impl.ScannerGattTime

/**
 * Connect Auto Time
 */
class ConnectAutoTime (
    private val bleFilter : BleFilter,
    var connectListener : OnFailConnectionListener? = null,
    var isCancelled : Boolean = false
) : IConnectAuto, IScanListener, OnSuccessConnectionListener, CoroutineScope by MainScope() {

    private val TAG = "CONNECT_AUTO"


    /** ATTRIBUTES **/
    lateinit var context : Context

    // Scanner
    private val scanner : ScannerGattTime by lazy {
        ScannerGattTime(bleFilter)
    }

    private var lastConnectTrialSuccess : Boolean = false

    /** OVERRIDE METHODS **/

    /************************************************
     * IConnectAuto methods
     ************************************************/

    override fun scanAndConnect(context : Context, scanPeriodInMillis : Long){
        this.context = context
        // Start Scan
        scanner.scan(this, scanPeriodInMillis)
    }

    override fun cancel() {
        isCancelled = true
        scanner.stop()
    }

    /************************************************
     * IScanListener methods
     ************************************************/

    override fun onFinishScan(bleFilter: IBleFilter){
        Log.d(TAG, "ConnectAutoTime.onFinishScan passed?: " + bleFilter.hasAtLeastOneMatchInAnyService())
        connectListener?.onFinishScan(bleFilter)

        if (!isCancelled) {

            val bleDevicesToConnectTo = bleFilter.findFirstBleDeviceForEachService()
            if (bleDevicesToConnectTo.isNotEmpty())
                tryConnectToBleDevice(bleDevicesToConnectTo.first())
        }
    }

    override fun onFinishScanNoResult(){
        Log.d(TAG, "ConnectAutoTime.onFinishScanNoResult")
        connectListener?.onFinishScanNoResults()
    }

    override fun onErrorScan(error : Int){
        Log.d(TAG, "ConnectAutoTime.onErrorScan")
    }

    /************************************************
     * OnConnectionSuccessListener methods
     ************************************************/

    override fun onConnection(bleDevice: BleDevice){
        lastConnectTrialSuccess = true
    }
    /**
     * Private Methods
     */

    /**
     * Try Connect To Ble Device
     * @param bleDevice
     */
    private fun tryConnectToBleDevice(bleDevice: BleDevice) = launch {

        lastConnectTrialSuccess = false

        // Connect To BLE Device
        bleDevice.connect(context, this@ConnectAutoTime)
        // Wait for a connection
        delay(MAX_TIME_TO_WAIT_FOR_A_CONNECTION)
        if (!lastConnectTrialSuccess) {
            Log.d(TAG, "Notify Last Connect fail")
            connectListener?.onFailConnection(bleDevice)
        }
    }


    /** COMPANION OBJECT **/

    companion object {
        const val MAX_TIME_TO_WAIT_FOR_A_CONNECTION : Long = 8000
    }

}