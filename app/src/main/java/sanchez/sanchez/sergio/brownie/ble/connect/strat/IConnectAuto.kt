package sanchez.sanchez.sergio.brownie.ble.connect.strat

import android.content.Context

/**
 * Connect Auto Interface
 */
interface IConnectAuto {

    /**
     * Scan and Connect
     * @param context
     * @param scanPeriodInMillis
     */
    fun scanAndConnect(context : Context, scanPeriodInMillis : Long)

    fun cancel()
}