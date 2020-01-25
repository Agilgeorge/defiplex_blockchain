package sanchez.sanchez.sergio.brownie.ble.protocol

/**
 * Message Parser
 */
interface IMessageParser {

    /**
     * Parse Message
     */
    fun parseMessageAndUpdateModel(message : ByteArray)
}