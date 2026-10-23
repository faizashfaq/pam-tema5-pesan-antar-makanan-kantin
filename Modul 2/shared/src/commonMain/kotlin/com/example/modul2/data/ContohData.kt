package org.example.project.data

import org.example.project.model.MenuKantin
import org.example.project.model.Pesanan

val contohMenu = MenuKantin(
    id = 1,
    nama = "Nasi Ayam Geprek",
    kantin = "Kantin Bu Siti",
    harga = 15_000,
)

fun contohPesanan(id: Int = 7): Pesanan {
    require(id > 0) { "id harus positif" }

    return Pesanan(
        id = id,
        pemesan = "Rani Putri",
        lokasiAntar = "GKU 1 ruang 201",
        menu = contohMenu,
        jumlah = 2,
        catatan = null,
    )
}
