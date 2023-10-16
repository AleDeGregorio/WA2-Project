package it.polito.g26.server.ticketing.attachment

import it.polito.g26.server.ticketing.messages.MessageDTO
import it.polito.g26.server.ticketing.messages.toDTO
import it.polito.g26.server.ticketing.messages.toEntity
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater

data class AttachmentDTO (
    val id: Long?,
    val message: MessageDTO?,
    val name: String,
    val type: String,
    val imageData: ByteArray
)

fun Attachment.toDTO() : AttachmentDTO {
    return AttachmentDTO(id, message!!.toDTO(), name, type, decompressImage(imageData))//decompressImage(imageData)
}

fun AttachmentDTO.toEntity() : Attachment {
    return Attachment(id, message!!.toEntity(), name,  type, compressImage(imageData))//compressImage(imageData)
}



fun compressImage(data: ByteArray): ByteArray {
    val deflater = Deflater()
    deflater.setLevel(Deflater.BEST_COMPRESSION)
    deflater.setInput(data)
    deflater.finish()
    val outputStream = ByteArrayOutputStream(data.size)
    val tmp = ByteArray(4 * 1024)
    while (!deflater.finished()) {
        val size = deflater.deflate(tmp)
        outputStream.write(tmp, 0, size)
    }
    try {
        outputStream.close()
    } catch (ignored: Exception) {
    }
    return outputStream.toByteArray()
}

fun decompressImage(data: ByteArray): ByteArray {
    val inflater = Inflater()
    inflater.setInput(data)
    val outputStream = ByteArrayOutputStream(data.size)
    val tmp = ByteArray(4 * 1024)
    try {
        while (!inflater.finished()) {
            val count = inflater.inflate(tmp)
            outputStream.write(tmp, 0, count)
        }
        outputStream.close()
    } catch (ignored: Exception) {
    }
    return outputStream.toByteArray()
}
