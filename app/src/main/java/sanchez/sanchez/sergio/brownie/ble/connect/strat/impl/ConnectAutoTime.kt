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
import sanchez.sanchez.sergio.brownie.ble.connect.strat.IConnectAuto
import sanchez.sanchez.sergio.brownie.ble.ext.connect

/**
 * Connect Auto Time
 */
class ConnectAutoTime (
        val bleFilter : BleFilter,
        var connectListener : OnFailConnectionListener? = null,
        var isCancelled : Boolean = false
) : IConnectAuto, IScanListener, OnSuccessConnectionListener, CoroutineScope by MainScope() {

    /** ATTRIBUTES **/
    lateinit var context : Context
    var scanner : ScannerGattTime = ScannerGattTime(bleFilter)

    lateinit var bleDevicesToConnectTo : MutableList<BleDevice>

    var lastConnectTrialSuccess : Boolean = false

    /** OVERRIDE METHODS **/

    /************************************************
     * IConnectAuto methods
     ************************************************/

    override fun scanAndConnect(context : Context, scanPeriodInMillis : Long){
        this.context = context
        scanner.scan(this, scanPeriodInMillis)
    }

    /************************************************
     * IScanListener methods
     ************************************************/

    override fun onFinishScan(bleFilter: BleFilter){
        println("LOGBLE - ConnectAutoTime.onFinishScan passed?: " + bleFilter.hasAtLeastOneMatchInAnyService())
        connectListener?.onFinishScan(bleFilter)

        bleDevicesToConnectTo = bleFilter.findFirstBleDeviceForEachService()

        if (bleDevicesToConnectTo.size > 0){
            val bleDevice = bleDevicesToConnectTo.get(0)
            println("LOGBLE - ConnectAutoTime.onFinishScan bledevice: " + bleDevice.address)

            val callback = ConnectCallback(bleDevice, this)

            launch {

                lastConnectTrialSuccess = false

                if (!isCancelled) {

                    bleDevice.connect(context, callback)

                    delay(MAX_TIME_TO_WAIT_FOR_A_CONNECTION)

                    if (!lastConnectTrialSuccess) {
                        connectListener?.onFailConnection(bleDevice)
                    }
                }
            }
        }
    }

    override fun onFinishScanNoResult(){
        println("LOGBLE - ConnectAutoTime.onFinishScanNoResult")
        connectListener?.onFinishScanNoResults()
    }

    override fun onErrorScan(error : Int){
        println("LOGBLE - ConnectAutoTime.onErrorScan")
        //connectListener?.onFinishScanNoResults()
    }

    /************************************************
     * OnConnectionSuccessListener methods
     ************************************************/

    override fun onConnection(bleDevice: BleDevice){
        lastConnectTrialSuccess = true
    }

    fun cancel() {
        isCancelled = true
        scanner.stop()
    }


    /** COMPANION OBJECT **/

    companion object {
        const val MAX_TIME_TO_WAIT_FOR_A_CONNECTION : Long = 8000
    }

}