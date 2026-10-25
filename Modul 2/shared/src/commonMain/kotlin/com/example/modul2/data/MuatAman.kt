package com.example.modul2.data

import kotlinx.coroutines.CancellationException
import com.example.modul2.model.Pesanan
import com.example.modul2.model.ringkas

suspend fun Sumber<Pesanan>.muatAman(id: Int): String = try {
    ambil(id).ringkas()
} catch (batal: CancellationException) {
    throw batal
} catch (error: Exception) {
    "Gagal memuat pesanan #$id: ${error.message ?: "kesalahan tidak diketahui"}"
}
