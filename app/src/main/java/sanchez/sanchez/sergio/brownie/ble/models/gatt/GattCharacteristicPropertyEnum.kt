package sanchez.sanchez.sergio.brownie.ble.models.gatt

/**
    Gatt Characteristic Property Enum
 **/
enum class GattCharacteristicPropertyEnum {
    READ, WRITE, WRITE_WITHOUT_RESPONSE, SIGNED_WRITE, RELIABLE_WRITE,
    NOTIFY, INDICATE, WRITABLE_AUXILIARIES, BROADCAST
}