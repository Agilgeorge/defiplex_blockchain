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
import sanchez.sanchez.sergio.brownie.ble.scan.strat.impl.ScannerGattFirst

/**
 * Connect Auto First
 */
class ConnectAutoFirst (
    bleFilter : BleFilter,
    var connectListener : OnFailConnectionListener? = null) : IConnectAuto, IScanListener,
        OnSuccessConnectionListener, CoroutineScope by MainScope() {

    private val TAG = "CONNECT_AUTO"

    /** ATTRIBUTES **/
    lateinit var context : Context

    /**
     * Scanner
     */
    private val scanner by lazy {
        ScannerGattFirst(bleFilter)
    }

    private var lastConnectTrialSuccess : Boolean = false

    /************************************************
     * IConnectAuto methods
     ************************************************/

    override fun scanAndConnect(context : Context, scanPeriodInMillis : Long){
        this.context = context
        // Launch Scan
        scanner.scan(this, scanPeriodInMillis)
    }

    override fun cancel() {
        scanner.stop()
    }

    /************************************************
     * IScanListener methods
     ************************************************/

    override fun onFinishScan(bleFilter: IBleFilter){
        val bleDevicesToConnectTo = bleFilter.findFirstBleDeviceForEachService()
        if (bleDevicesToConnectTo.isNotEmpty())
            tryConnectToBleDevice(bleDevicesToConnectTo.first())
    }

    override fun onFinishScanNoResult(){
        connectListener?.onFinishScanNoResults()
    }

    override fun onErrorScan(error : Int){
        //TODO
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
        bleDevice.connect(context, this@ConnectAutoFirst)
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