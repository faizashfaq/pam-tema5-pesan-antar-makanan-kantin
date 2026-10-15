package com.example.pam_tema5_pesan_antar_makanan_kantin

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}