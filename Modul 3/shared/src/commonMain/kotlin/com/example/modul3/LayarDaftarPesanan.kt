package com.example.modul3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun LayarDaftarPesanan(
    onBukaTentang: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val semua = contohDaftarPesanan
    val terbaru = semua.terbaruDulu()
    val ringkasan = semua.ringkasanStatus()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            // Aksi utama: belum berfungsi (dikerjakan di minggu berikutnya).
            ExtendedFloatingActionButton(onClick = { }) {
                Text("+ Pesan")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Pesan Antar Makanan Kantin",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
                TextButton(onClick = onBukaTentang) { Text("Tentang") }
            }
            Text(
                text = ringkasan,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            DaftarPesanan(
                daftar = terbaru,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
