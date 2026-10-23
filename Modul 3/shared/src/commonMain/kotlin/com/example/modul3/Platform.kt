package com.example.modul3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform