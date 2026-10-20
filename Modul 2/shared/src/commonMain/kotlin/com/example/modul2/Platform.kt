package com.example.modul2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform