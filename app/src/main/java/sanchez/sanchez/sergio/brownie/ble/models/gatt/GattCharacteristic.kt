package sanchez.sanchez.sergio.brownie.ble.models.gatt

import java.util.*

/**
    Gatt Profile Service Characteristic
 **/
data class GattCharacteristic(
        // UUID
        val uuid: UUID,
        // Name
        val name: String,
        // Type
        val type: String? = null,
        // Properties
        val properties: List<GattCharacteristicPropertyEnum>,
        // Descriptors
        var descriptors : List<GattDescriptor> = listOf()
)