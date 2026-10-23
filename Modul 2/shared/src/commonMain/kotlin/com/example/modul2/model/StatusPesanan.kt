package org.example.project.model

sealed class StatusPesanan {
    data object Diterima : StatusPesanan()

    data class Diproses(
        val penjual: String
    ) : StatusPesanan()

    data class SiapDiantar(
        val kurir: String
    ) : StatusPesanan()
}