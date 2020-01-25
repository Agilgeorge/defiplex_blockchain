package sanchez.sanchez.sergio.brownie.ble.models.gatt

import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.protocol.IProtocol
import java.util.*

/**
 * Gatt Profile Service
 **/
abstract class GattService{

    /**
     * Properties for defining GattService
     */

    // UUID
    abstract val uuid: UUID
    // Name
    abstract val name: String
    // Type
    abstract val type: String
    // Summary
    abstract val summary: String
    // Assigned Number
    abstract val assignedNumber: String
    // Specification
    abstract val specification: String
    // Characteristics
    abstract val characteristics: Array<GattCharacteristic>

    abstract fun getCharacteristicsReadNotifyOrdered () : List<GattCharacteristic>
    abstract fun getCharacteristicsWriteOrdered () : List<GattCharacteristic>
    abstract fun getProtocol(fwVersion : String) : IProtocol

}