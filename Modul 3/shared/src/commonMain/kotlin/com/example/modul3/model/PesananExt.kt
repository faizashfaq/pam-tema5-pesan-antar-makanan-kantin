package com.example.modul2.model

fun StatusPesanan.label(): String = when (this) {
    StatusPesanan.Diterima -> "Pesanan diterima, menunggu kantin"
    is StatusPesanan.Diproses -> "Sedang disiapkan oleh $penjual"
    is StatusPesanan.SiapDiantar -> "Siap diantar oleh $kurir"
}

fun Pesanan.total(): Int = menu.harga * jumlah

fun Pesanan.ringkas(): String = "#$id ${menu.nama} x$jumlah untuk $pemesan, antar ke $lokasiAntar"

fun Pesanan.catatanAtauDefault(): String = catatan ?: "Tanpa catatan"

fun Int.rupiah(): String = "Rp" + toString().reversed().chunked(3).joinToString(".").reversed()