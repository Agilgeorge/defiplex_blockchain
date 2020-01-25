package sanchez.sanchez.sergio.brownie.ble.connect.strat.impl

import android.content.Context
import sanchez.sanchez.sergio.brownie.ble.ConnectCallback
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.scan.listener.IScanListener
import sanchez.sanchez.sergio.brownie.ble.scan.strat.impl.ScannerGattTime
import sanchez.sanchez.sergio.brownie.ble.scan.filters.BleFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnFailConnectionListener
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnSuccessConnectionListener
import sanchez.sanchez.sergio.brownie.ble.connect.strat.IConnectWaitUser
import sanchez.sanchez.sergio.brownie.ble.ext.connect

/**
 * Connect Wait User
 */
class ConnectWaitUser (
    val bleFilter : BleFilter
) : IConnectWaitUser, IScanListener, OnSuccessConnectionListener, CoroutineScope by MainScope() {

    /** ATTRIBUTES **/
    lateinit var context : Context

    lateinit var connectListener : OnFailConnectionListener

    var scanner : ScannerGattTime = ScannerGattTime(bleFilter)

    var bleDevicesToConnectTo : MutableList<BleDevice> = mutableListOf<BleDevice>()

    var lastConnectTrialSuccess : Boolean = false


    fun cancel() {
        scanner.stop()
    }

    /************************************************
     * IConnecWaitUser methods
     ************************************************/

    override fun scan(context : Context, connectListener : OnFailConnectionListener, scanPeriodInMillis : Long){
        this.context = context
        this.connectListener = connectListener
        scanner.scan(this, scanPeriodInMillis)
    }

    override fun connect(bleDevice : BleDevice){
        bleDevicesToConnectTo.add(bleDevice)

        val callback = ConnectCallback(bleDevice, this)

        launch {

            lastConnectTrialSuccess = false

            bleDevice.connect(context, callback)

            delay (MAX_TIME_TO_WAIT_FOR_A_CONNECTION)

            if (!lastConnectTrialSuccess){
                connectListener?.onFailConnection(bleDevice)
            }
        }
    }


    /************************************************
     * IScanListener methods
     ************************************************/

    override fun onFinishScan(bleFilter: BleFilter){
        this.connectListener.onFinishScan(bleFilter)
    }

    override fun onFinishScanNoResult(){
        this.connectListener.onFinishScanNoResults()
    }
    override fun onErrorScan(error : Int){
        //this.connectListener.onFinishScanNoResults()
    }


    /************************************************
     * OnConnectionSuccessListener methods
     ************************************************/

    override fun onConnection(bleDevice: BleDevice){
        lastConnectTrialSuccess = true
    }


    /** COMPANION OBJECT **/

    companion object {
        const val MAX_TIME_TO_WAIT_FOR_A_CONNECTION : Long = 8000
    }

}