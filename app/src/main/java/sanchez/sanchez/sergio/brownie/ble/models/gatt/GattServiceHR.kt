package sanchez.sanchez.sergio.brownie.ble.models.gatt

import android.util.Log
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.protocol.IProtocol
import java.util.*

/**
 *  Gatt profile
 **/
object GattServiceHR : GattService(){

    private val TAG = "GattServiceHR"


    /**
     * Properties for defining GattService
     */

    override val name = "Heart Rate"
    override val uuid = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")

    override val type: String = "org.bluetooth.service.heart_rate"
    override val summary: String = "The HEART RATE Service exposes heart rate and other data related to a heart rate sensor intended for fitness applications."
    override val assignedNumber: String = "0x180D"
    override val specification: String = "GSS"

    override val characteristics = arrayOf(

        // Heart Rate Measurement
        GattCharacteristic(
            uuid = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb"),
            name = "Heart Rate Measurement",
            type = "org.bluetooth.characteristic.heart_rate_measurement",
            properties = listOf(GattCharacteristicPropertyEnum.NOTIFY),
            descriptors = listOf(GattDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")))
        ),

        // Body Sensor Location
        GattCharacteristic(
            uuid = UUID.fromString("00002a38-0000-1000-8000-00805f9b34fb"),
            name = "Body Sensor Location",
            type = "org.bluetooth.characteristic.body_sensor_location",
            properties = listOf(GattCharacteristicPropertyEnum.READ)
        ),

        // Heart Rate Control Point
        GattCharacteristic(
            uuid = UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb"),
            name = "Heart Rate Control Point",
            type = "org.bluetooth.characteristic.heart_rate_control_point",
            properties = listOf(GattCharacteristicPropertyEnum.WRITE)
        )
    )

    override fun getCharacteristicsReadNotifyOrdered () : List<GattCharacteristic> {
        return listOf(
            characteristics[0]
        )
    }

    override fun getCharacteristicsWriteOrdered () : List<GattCharacteristic> {
        return listOf(
            characteristics[2]
        )
    }

    override fun getProtocol(fwVersion: String): IProtocol {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}