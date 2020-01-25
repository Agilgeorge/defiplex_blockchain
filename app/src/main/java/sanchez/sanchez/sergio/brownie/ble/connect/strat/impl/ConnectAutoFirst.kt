package sanchez.sanchez.sergio.brownie.ble.connect.strat.impl

import android.content.Context
import sanchez.sanchez.sergio.brownie.ble.ConnectCallback
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.scan.listener.IScanListener
import sanchez.sanchez.sergio.brownie.ble.scan.strat.impl.ScannerGattFirst
import sanchez.sanchez.sergio.brownie.ble.scan.filters.BleFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnSuccessConnectionListener
import sanchez.sanchez.sergio.brownie.ble.connect.strat.IConnectAuto
import sanchez.sanchez.sergio.brownie.ble.ext.connect

/**
 * Connect Auto First
 */
class ConnectAutoFirst (bleFilter : BleFilter) : IConnectAuto, IScanListener,
        OnSuccessConnectionListener, CoroutineScope by MainScope() {

    /** ATTRIBUTES **/
    lateinit var context : Context
    lateinit var bleDevicesToConnectTo : MutableList<BleDevice>

    private val scanner by lazy {
        ScannerGattFirst(bleFilter)
    }

    private var lastConnectTrialSuccess : Boolean = false

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

        bleDevicesToConnectTo = bleFilter.findFirstBleDeviceForEachService()

        if (bleDevicesToConnectTo.size > 0){
            val bleDevice = bleDevicesToConnectTo[0]
            val callback = ConnectCallback(bleDevice, this)

            launch {

                lastConnectTrialSuccess = false

                bleDevice.connect(context, callback)

                delay (2000)

                if (!lastConnectTrialSuccess){
                    //TODO
                }
            }

        }
    }

    override fun onFinishScanNoResult(){
        //TODO
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
}