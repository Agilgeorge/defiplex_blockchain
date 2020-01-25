package sanchez.sanchez.sergio.brownie.ble.mappers

import android.bluetooth.le.ScanResult
import sanchez.sanchez.sergio.brownie.ble.models.devices.BleDevice
import sanchez.sanchez.sergio.brownie.ble.models.gatt.GattService


/**
 * Mapper of the classes
 */


/**
 * Mapper scanResult class to BleDevice class
 * @param scanResult
 * @return BleDevice
 */
fun mapperScanResultToBleDevice(scanResult: ScanResult, gattService : GattService): BleDevice =
    BleDevice(
        address = scanResult.device.address,
        name = scanResult.device.name?.let { it}?: scanResult.device.address,
        device = scanResult.device,
        rssi = scanResult.rssi,
        gattService = gattService
    )

