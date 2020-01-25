package sanchez.sanchez.sergio.brownie.ble.connect.listener

import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice

/**
 * Success Connection Listener
 */
interface OnSuccessConnectionListener {

    /**
     * On Connection
     * @param bleDevice
     */
    fun onConnection(bleDevice: BleDevice)
}