package com.example.modul3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.modul3.model.Pesanan

@Composable
fun DaftarPesanan(
    daftar: List<Pesanan>,
    modifier: Modifier = Modifier,
    onKlik: (Pesanan) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(items = daftar, key = { it.id }) { pesanan ->
            KartuPesanan(pesanan = pesanan, onKlik = { onKlik(pesanan) })
        }
    }
}
