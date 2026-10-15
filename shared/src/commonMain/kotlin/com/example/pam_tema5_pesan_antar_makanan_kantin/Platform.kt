package com.example.pam_tema5_pesan_antar_makanan_kantin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform