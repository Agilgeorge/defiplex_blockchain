package sanchez.sanchez.sergio.brownie.ble.scan.strat

import sanchez.sanchez.sergio.brownie.ble.scan.listener.IScanListener

/**
 * Scanner Interface
 */
interface IScanner {

    /**
     * Scan Devices
     * @param listener
     * @param scanPeriodInMillis
     */
    fun scan(listener: IScanListener, scanPeriodInMillis : Long)

    /**
     * Stop Scanner
     */
    fun stop()
}