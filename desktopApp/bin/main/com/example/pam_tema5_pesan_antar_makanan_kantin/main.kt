package com.example.pam_tema5_pesan_antar_makanan_kantin

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Pamtema5pesanantarmakanankantin",
    ) {
        App()
    }
}