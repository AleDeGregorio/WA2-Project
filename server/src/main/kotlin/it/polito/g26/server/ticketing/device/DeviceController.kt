package it.polito.g26.server.ticketing.device

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class DeviceController(
    private val deviceService: DeviceService
) {
    private fun deviceDTOToEntity(deviceDTO: DeviceDTO, ean: Long?) : Device {
        val device = Device()

        if (ean != null) {
            device.ean = ean
        }

        device.name = deviceDTO.name
        device.category = deviceDTO.category
        device.brand = deviceDTO.brand
        device.price = deviceDTO.price

        return device
    }

    @GetMapping("/API/devices")
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<DeviceDTO> {
        return deviceService.getAll()
    }

    @GetMapping("/API/device/{ean}")
    @ResponseStatus(HttpStatus.OK)
    fun getDevice(@PathVariable ean: Long) : DeviceDTO? {
        return deviceService.getDevice(ean) ?: throw Exception("Product not found")
    }

    @PostMapping("/API/device")
    @ResponseStatus(HttpStatus.CREATED)
    fun insertDevice(@RequestBody deviceDTO: DeviceDTO?) {
        if (deviceDTO != null) {
            val insertDevice = deviceDTOToEntity(deviceDTO, null)

            deviceService.insertDevice(insertDevice)
        }
        else {
            throw Exception("Empty device body")
        }
    }

    @PutMapping("/API/device/{ean}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun updateDevice(@RequestBody deviceDTO: DeviceDTO?, @PathVariable ean: Long) {
        if (deviceDTO != null) {
            val updateDevice = deviceDTOToEntity(deviceDTO, ean)

            deviceService.updateDevice(updateDevice)
        }
        else {
            throw Exception("Empty device body")
        }
    }
}