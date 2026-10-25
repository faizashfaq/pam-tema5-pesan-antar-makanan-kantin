package com.example.modul2

import kotlinx.coroutines.runBlocking
import com.example.modul2.demo.jalankanDemo

/**
 * Titik masuk terpisah untuk menjalankan demo konsol di desktop (JVM).
 * Dibungkus object supaya tidak bentrok dengan `fun main()` bawaan template.
 */
object DemoMain {
    @JvmStatic
    fun main(args: Array<String>) = runBlocking { jalankanDemo() }
}
