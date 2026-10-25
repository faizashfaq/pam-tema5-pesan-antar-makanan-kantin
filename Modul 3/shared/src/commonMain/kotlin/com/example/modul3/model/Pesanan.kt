package com.example.modul2.model

data class Pesanan(
    val id: Int,
    val pemesan: String,
    val lokasiAntar: String,
    val menu: MenuKantin,
    val jumlah: Int,
    val catatan: String?,
    val status: StatusPesanan = StatusPesanan.Diterima,
)