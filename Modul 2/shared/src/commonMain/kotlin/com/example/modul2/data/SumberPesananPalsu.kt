package org.example.project.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.model.Pesanan
import org.example.project.model.StatusPesanan

class SumberPesananPalsu : Sumber<Pesanan> {
    override suspend fun ambil(id: Int): Pesanan {
        require(id > 0) { "id harus positif" }
        delay(1_000)
        return contohPesanan(id)
    }

    suspend fun ambilRiwayat(id: Int): List<String> {
        require(id > 0) { "id harus positif" }
        delay(800)
        return listOf("Dipesan 12.05", "Diterima kantin 12.06")
    }

    override fun pantau(id: Int): Flow<Pesanan> = flow {
        val pesanan = contohPesanan(id)
        emit(pesanan)

        delay(1_500)
        emit(pesanan.copy(status = StatusPesanan.Diproses(pesanan.menu.kantin)))

        delay(2_000)
        emit(pesanan.copy(status = StatusPesanan.SiapDiantar("Kurir Budi")))
    }
}
