package it.polito.g26.server.ticketing.device

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class DeviceServiceImpl(
    private val deviceRepository: DeviceRepository
) : DeviceService {
    override fun getAll(): List<DeviceDTO> {
        return deviceRepository.findAll().map { it.toDTO() }
    }

    override fun getDevice(ean: Long): DeviceDTO? {
        return deviceRepository.findByIdOrNull(ean)?.toDTO()
    }

    override fun insertDevice(device: Device) {
        if (device.ean != null && deviceRepository.existsById(device.ean!!)) {
            throw Exception("Product already inserted")
        }
        else {
            deviceRepository.save(device)
        }
    }

    override fun updateDevice(device: Device) {
        if (deviceRepository.existsById(device.ean!!)) {
            val retrievedDevice = deviceRepository.findById(device.ean!!).get()

            retrievedDevice.name = device.name
            retrievedDevice.brand = device.brand
            retrievedDevice.category = device.category
            retrievedDevice.price = device.price

            deviceRepository.save(retrievedDevice)
        }
        else {
            throw Exception("Product not found")
        }
    }
}