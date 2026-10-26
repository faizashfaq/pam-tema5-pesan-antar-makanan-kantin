package com.example.modul4

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform