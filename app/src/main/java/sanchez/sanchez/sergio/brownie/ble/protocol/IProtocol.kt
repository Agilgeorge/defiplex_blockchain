package sanchez.sanchez.sergio.brownie.ble.protocol

/**
 * Protocol
 */
interface IProtocol {

    fun newMessage(message : ByteArray)
}


const val UNKOWN_VERSION : String = ""