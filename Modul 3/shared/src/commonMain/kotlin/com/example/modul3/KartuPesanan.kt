package org.example.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.modul3.model.Pesanan
import com.example.modul3.model.catatanAtauDefault
import com.example.modul3.model.rupiah
import com.example.modul3.model.total

@Composable
fun KartuPesanan(
    pesanan: Pesanan,
    modifier: Modifier = Modifier,
    ringkas: Boolean = false,
    onKlik: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onKlik() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${pesanan.menu.nama} x${pesanan.jumlah}",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "#${pesanan.id} · ${pesanan.pemesan}",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (!ringkas) {
                    Text(
                        text = "Antar ke ${pesanan.lokasiAntar}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${pesanan.total().rupiah()} · ${pesanan.catatanAtauDefault()}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            LabelStatus(pesanan.status)
        }
    }
}
