package com.example.modul2.demo

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import com.example.modul2.data.SumberPesananPalsu
import com.example.modul2.data.contohPesanan
import com.example.modul2.data.muatAman
import com.example.modul2.model.StatusPesanan
import com.example.modul2.model.catatanAtauDefault
import com.example.modul2.model.label
import com.example.modul2.model.ringkas
import com.example.modul2.model.rupiah
import com.example.modul2.model.total
import kotlin.time.TimeSource
import kotlin.time.measureTime

/** Bukti berjalan checkpoint minggu 2: mencetak model, coroutines, dan perubahan status. */
suspend fun jalankanDemo(cetak: (String) -> Unit = { println(it) }) {
    val sumber = SumberPesananPalsu()

    // 1. Model data: data class, sealed class, nullable, copy
    cetak("=== 1. Model data ===")
    val a = contohPesanan(7)
    val diproses = a.copy(status = StatusPesanan.Diproses("Kantin Bu Siti"))
    cetak("Pesanan : ${a.ringkas()}")
    cetak("Total   : ${a.total().rupiah()}")
    cetak("Catatan : ${a.catatanAtauDefault()}")
    cetak("a == diproses  : ${a == diproses}")
    cetak("status a       : ${a.status.label()}")
    cetak("status diproses: ${diproses.status.label()}")

    // 2. Coroutines: dua proses menunggu, berurutan vs bersamaan
    cetak("")
    cetak("=== 2. Coroutines: dua proses bersamaan ===")
    val berurutan = measureTime {
        sumber.ambil(7)
        sumber.ambilRiwayat(7)
    }
    cetak("Berurutan (tanpa async)  : ${berurutan.inWholeMilliseconds} ms")
    val bersamaan = measureTime {
        coroutineScope {
            val pesanan = async { sumber.ambil(7) }
            val riwayat = async { sumber.ambilRiwayat(7) }
            cetak("Hasil: ${pesanan.await().ringkas()} | riwayat: ${riwayat.await()}")
        }
    }
    cetak("Bersamaan (dengan async) : ${bersamaan.inWholeMilliseconds} ms")

    // 3. Flow: status berubah, lewat onEach, filter, map, collect
    cetak("")
    cetak("=== 3. Flow: status pesanan berubah ===")
    val mulai = TimeSource.Monotonic.markNow()
    fun waktu() = "[+${mulai.elapsedNow().inWholeMilliseconds.toString().padStart(4)} ms]"
    cetak("Sebelum collect")
    sumber.pantau(7)
        .map { it.status }
        .onEach { cetak("${waktu()} status berubah: ${it.label()}") }
        .filter { it !is StatusPesanan.Diterima }
        .map { "Notifikasi: pesanan #7 - ${it.label()}" }
        .collect { cetak("${waktu()} $it") }
    cetak("Sesudah collect")

    // 4. Error: try/catch di suspend function
    cetak("")
    cetak("=== 4. Menangani error ===")
    cetak("Id  7: ${sumber.muatAman(7)}")
    cetak("Id -1: ${sumber.muatAman(-1)}")
}