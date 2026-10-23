# Pesan Antar Makanan Kantin

Proyek kelompok mata kuliah **Pengembangan Aplikasi Mobile (IF25-22017)**, Teknik Informatika ITERA, dibuat dengan **Kotlin Multiplatform + Compose Multiplatform**.

## Tema
**Pesan Antar Makanan Kantin** – aplikasi untuk memesan makanan dari kantin kampus dan mengantarkannya ke pemesan.
Fitur platform yang dipakai: **Notifikasi** (pemberitahuan pesanan diterima, diproses, dan siap diantar).

## Anggota Kelompok
| No | Nama | NIM |
|----|------|-----|
| 1 | Muhammad Faiz Ashfaq | 124140144 |
| 2 | (isi nama anggota 2) | (isi NIM) |
| 3 | (isi nama anggota 3) | (isi NIM) |

## Progres
### Minggu 1
- [x] Proyek dibuat dari template Compose Multiplatform dan di-push ke repository kelompok
- [x] Judul aplikasi diganti menjadi nama tema, nama platform ikut tampil
- [x] Setiap anggota menambahkan nama dan NIM di layar Tentang (satu commit per anggota)

### Minggu 2: Model Data, Coroutines, dan Flow
- [x] **Model data tema**: `Pesanan`, `MenuKantin`, dan sealed class `StatusPesanan`
- [x] **Sumber data palsu**: `SumberPesananPalsu` dengan `delay` dan Flow perubahan status
- [x] **Bukti berjalan**: `jalankanDemo()` mencetak hasil ke konsol

### Minggu 3: Dasar Compose Multiplatform
- [x] **Layar daftar**: `LazyColumn` berisi `KartuPesanan` dan `LabelStatus` dari sealed class `StatusPesanan`
- [x] **Daftar dummy**: 12 data contoh dari data class Minggu 2
- [x] **Olah sebelum tampil**: urutan terbaru di atas, filter status, dan ringkasan jumlah per status dengan fungsi koleksi
- [x] **Satu aksi utama**: FAB **+ Pesan** di pojok kanan bawah (belum berfungsi)

## Tangkapan Layar
| Layar Daftar (Android) | Layar Daftar (Desktop) |
|---------|---------|
| ![Daftar Android](docs/daftar-android.png) | ![Daftar Desktop](docs/daftar-desktop.png) |

## Layar Daftar (Minggu 3)

Semua kode ada di `commonMain`, jadi dipakai bersama oleh Android dan Desktop.

| Composable | Isi | Fungsinya |
|------------|-----|-----------|
| `LayarDaftarPesanan` | `Scaffold` + `Column` | Judul, ringkasan status, daftar pesanan, dan FAB **+ Pesan** |
| `DaftarPesanan` | `LazyColumn` + `items(key = { it.id })` | Menampilkan kartu pesanan satu per satu |
| `KartuPesanan` | `Card` > `Row` > `Column(weight(1f))` + `LabelStatus` | Menu dan jumlah, nomor dan pemesan, lokasi antar, total dan catatan |
| `LabelStatus` | `Text` + `background` + `padding` | Label pil **Baru**, **Diproses**, **Siap diantar** dengan warna berbeda |

Keputusan desain:
- **`LazyColumn`, bukan `Column` + `forEach`**. Daftar pesanan bisa panjang; `LazyColumn` hanya menggambar kartu yang terlihat di layar. `key = { it.id }` membuat Compose tetap mengenali pesanan yang sama walaupun urutannya berubah.
- **`Row` + `weight(1f)`** pada kolom teks, sehingga label status selalu terdorong ke ujung kanan kartu.
- **Urutan modifier label**: `background` dulu, baru `padding`, jadi warna ikut melebar membentuk pil.
- **Warna dan teks label ditentukan dengan `when` pada sealed class**. Kalau ada status baru, compiler langsung menandai bagian yang belum menanganinya.
- **Kartu bisa dipakai ulang lewat parameter**: `ringkas = true` untuk versi pendek (nanti untuk notifikasi), `onKlik` dipanggil saat kartu diketuk dan pemanggilnya yang menentukan aksinya.

## Olah Sebelum Tampil (Minggu 3)

Pengolahan daftar ditulis di `OlahDaftar.kt` sebagai fungsi biasa:

```kotlin
fun List<Pesanan>.terbaruDulu() = sortedByDescending { it.id }                     // urutan
fun List<Pesanan>.hanyaDiproses() = filter { it.status is StatusPesanan.Diproses } // filter
fun List<Pesanan>.ringkasanStatus(): String                                        // count per status
```

Dipakai di `LayarDaftarPesanan` sebelum daftar ditampilkan:
```kotlin
val terbaru = semua.terbaruDulu()
val ringkasan = semua.ringkasanStatus()
```

## Contoh Hasil

Hasil fungsi pengolahan terhadap 12 data contoh:

```
urutan id terbaru dulu : [12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
hanya diproses         : [7, 5, 9, 6]
ringkasan              : 12 pesanan · 4 baru · 4 diproses · 4 siap diantar
```

Cara membaca hasilnya:
- Data contoh sengaja disusun dengan id acak (3, 7, 1, 12, ...). Kartu paling atas di layar adalah **#12**, artinya `terbaruDulu()` bekerja.
- Teks ringkasan di bawah judul layar berasal dari `count`, jadi ikut berubah kalau datanya berubah.

## Struktur Berkas (tambahan Minggu 3)

```
composeApp/src/commonMain/kotlin/org/example/project/
├── App.kt                    layar utama sekarang LayarDaftarPesanan
├── ContohDaftarPesanan.kt    12 data dummy
├── OlahDaftar.kt             terbaruDulu, hanyaDiproses, ringkasanStatus
├── LabelStatus.kt            label pil per status
├── KartuPesanan.kt           kartu entitas utama
├── DaftarPesanan.kt          LazyColumn
└── LayarDaftarPesanan.kt     judul, ringkasan, daftar, FAB + Pesan
```

## Pembagian Tugas

| Anggota | Bagian yang dikerjakan | Berkas |
|---------|------------------------|--------|
| (isi nama anggota 1) | Kartu entitas dan label status | `KartuPesanan.kt`, `LabelStatus.kt` |
| (isi nama anggota 2) | Data dummy dan olah sebelum tampil | `ContohDaftarPesanan.kt`, `OlahDaftar.kt` |
| (isi nama anggota 3) | Layar daftar, FAB, tangkapan layar, README | `DaftarPesanan.kt`, `LayarDaftarPesanan.kt`, `App.kt`, `README.md` |

<!-- Isi nama anggota dan sesuaikan pembagian tugas dengan kontribusi sebenarnya sebelum push. -->

## Cara Menjalankan
- **Android**: buka proyek di Android Studio, pilih konfigurasi `composeApp`, lalu Run. Layar pertama adalah daftar pesanan; tombol **Tentang** ada di kanan atas.
- **Desktop**: `./gradlew :composeApp:run` (Windows: `gradlew.bat :composeApp:run`).

Semua berkas memakai `package org.example.project` (bawaan template). Kalau proyek kelompok memakai package lain, ganti dengan Find and Replace di seluruh proyek.