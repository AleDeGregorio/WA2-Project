package it.polito.g26.server.ticketing.device

interface DeviceService {
    fun getAll() : List<DeviceDTO>

    fun getDevice(ean: Long) : DeviceDTO?

    fun insertDevice(device: Device)

    fun updateDevice(device: Device)
}