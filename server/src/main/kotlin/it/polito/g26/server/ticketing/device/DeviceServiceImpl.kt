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
        if (deviceRepository.existsById(device.ean!!)) {
            throw Exception("Product already inserted")
        }
        else {
            deviceRepository.save(device)
        }
    }

    override fun updateDevice(device: Device) {
        if (deviceRepository.existsById(device.ean!!)) {
            deviceRepository.save(device)
        }
        else {
            throw Exception("Product not found")
        }
    }
}