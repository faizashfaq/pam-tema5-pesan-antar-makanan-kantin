package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

private const val NAMA_TEMA = "Pesan Antar Makanan Kantin"

private data class Anggota(val nama: String, val nim: String)

private val daftarAnggota = listOf(
    Anggota("Muhammad Faiz Ashfaq", "124140144"),
    Anggota("Aryo Fajar Pratomo", "124140012"),
    Anggota("Syehabudin", "124140102"),
)

@Composable
fun App() {
    MaterialTheme {
        var tampilkanTentang by remember { mutableStateOf(false) }

        if (tampilkanTentang) {
            TentangScreen(onKembali = { tampilkanTentang = false })
        } else {
            // Minggu 3: layar utama sekarang layar daftar pesanan.
            LayarDaftarPesanan(onBukaTentang = { tampilkanTentang = true })
        }
    }
}

@Composable
private fun TentangScreen(onKembali: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Tentang", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Tema: $NAMA_TEMA", style = MaterialTheme.typography.bodyLarge)

        Text("Berjalan di ${getPlatform().name}", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(16.dp))
        Text("Anggota kelompok", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        daftarAnggota.forEach { a ->
            Text("${a.nama} (${a.nim})", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.height(24.dp))
        OutlinedButton(onClick = onKembali) { Text("Kembali") }
    }
}