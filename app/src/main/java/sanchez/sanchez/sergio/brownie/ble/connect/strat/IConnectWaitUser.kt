package sanchez.sanchez.sergio.brownie.ble.connect.strat

import android.content.Context
import sanchez.sanchez.sergio.brownie.ble.connect.listener.OnFailConnectionListener
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice


/**
 * Connect Wait User Interface
 */
interface IConnectWaitUser {

    /**
     * Scan
     * @param context
     * @param connectListener
     * @param scanPeriodInMillis
     */
    fun scan(
            context : Context,
            connectListener : OnFailConnectionListener,
            scanPeriodInMillis : Long
    )

    /**
     * Connect
     * @param bleDevice
     */
    fun connect(bleDevice : BleDevice)


}