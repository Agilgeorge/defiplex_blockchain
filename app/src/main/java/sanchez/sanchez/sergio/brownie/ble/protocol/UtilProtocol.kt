package sanchez.sanchez.sergio.brownie.ble.protocol

import java.nio.ByteBuffer
import java.nio.charset.Charset

/**
 * Objective of the document:
 */
class UtilProtocol {

    companion object {

        fun readUnsignedByte(b : Byte) : Int{
            if (b < 0){
                return 256 + b;
            }
            else {
                return b.toInt()
            }
        }

        fun read16BitValueAsInt(array : ByteArray, indexFrom : Int) : Int{
            return ((array[indexFrom].toInt() and 0x000000FF) shl 8) or (array[indexFrom+1].toInt() and 0x000000FF)
        }

        fun read32BitValueAsInt(array : ByteArray, indexFrom : Int) : Int{
            var bytes : ByteArray = byteArrayOf(array[indexFrom], array[indexFrom+1], array[indexFrom+2], array[indexFrom+3])
            return ByteBuffer.wrap(bytes).int
        }

        fun readSubArrayAsString(array : ByteArray, indexFrom : Int, indexTo : Int) : String {
            val subArray = array.copyOfRange(indexFrom,indexTo)
            return String(subArray, Charset.forName("UTF-8"))
        }

    }

}