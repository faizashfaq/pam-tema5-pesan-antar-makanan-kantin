package org.example.project.data

import kotlinx.coroutines.CancellationException
import org.example.project.model.Pesanan
import org.example.project.model.ringkas

suspend fun Sumber<Pesanan>.muatAman(id: Int): String = try {
    ambil(id).ringkas()
} catch (batal: CancellationException) {
    throw batal
} catch (error: Exception) {
    "Gagal memuat pesanan #$id: ${error.message ?: "kesalahan tidak diketahui"}"
}
