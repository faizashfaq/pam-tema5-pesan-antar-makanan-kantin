package org.example.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.model.StatusPesanan

fun StatusPesanan.labelPendek(): String = when (this) {
    StatusPesanan.Diterima -> "Baru"
    is StatusPesanan.Diproses -> "Diproses"
    is StatusPesanan.SiapDiantar -> "Siap diantar"
}

private fun StatusPesanan.warna(): Pair<Color, Color> = when (this) {
    StatusPesanan.Diterima -> Color(0xFFF3E3B0) to Color(0xFF5C4300)
    is StatusPesanan.Diproses -> Color(0xFFDCE7F7) to Color(0xFF1F3F73)
    is StatusPesanan.SiapDiantar -> Color(0xFFDFF0DC) to Color(0xFF2D5F27)
}

@Composable
fun LabelStatus(status: StatusPesanan, modifier: Modifier = Modifier) {
    val (latar, teks) = status.warna()
    Text(
        text = status.labelPendek(),
        color = teks,
        fontWeight = FontWeight.SemiBold,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .background(latar, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp),
    )
}