# LaporKampus

Aplikasi pelaporan kerusakan fasilitas kampus, dibangun dengan **Kotlin Compose Multiplatform** (Android dan Desktop) untuk mata kuliah IF25-22017 Pengembangan Aplikasi Mobile, Teknik Informatika ITERA.

## Anggota Kelompok

| No | Nama | NIM |
|----|------|-----|
| 1 | Muhammad Faiz Ashfaq | 1241404144 |
| 2 | Nama Anggota 2 | 12xxxxxxx |
| 3 | Nama Anggota 3 | 12xxxxxxx |

## Checkpoint Proyek Minggu 3

| No | Target | Status | Bukti di kode |
|----|--------|--------|---------------|
| 1 | **Layar daftar**: LazyColumn berisi kartu entitas utama dan label status dari sealed class minggu lalu | ✅ Tuntas | `ui/DaftarLaporan.kt`, `ui/KartuLaporan.kt`, `ui/LabelStatus.kt` |
| 2 | **Olah sebelum tampil**: minimal satu filter atau urutan dengan fungsi koleksi | ✅ Tuntas | `ui/LayarDaftar.kt` memakai `sortedByDescending`, `filter`, dan `count` |
| 3 | **Satu aksi utama**: FAB tambah data (belum berfungsi) dan tangkapan layar di README | ✅ Tuntas | FAB "Lapor" di `ui/LayarDaftar.kt` |
| 4 | **Data dummy** minimal 10 item dari data class minggu lalu | ✅ Tuntas (12 item) | `data/DataDummy.kt` |

## Tangkapan Layar

| Android | Desktop |
|---------|---------|
| ![Android](docs/screenshot-android.png) | ![Desktop](docs/screenshot-desktop.png) |

## Struktur Kode

```
composeApp/src/commonMain/kotlin/org/example/project/
├── App.kt                  # titik masuk, memanggil LayarDaftar
├── model/
│   ├── Laporan.kt          # data class entitas utama
│   └── StatusLaporan.kt    # sealed class status + fungsi label()
├── data/
│   └── DataDummy.kt        # 12 data contoh
└── ui/
    ├── LayarDaftar.kt      # Scaffold: judul, jumlah aktif, daftar, FAB
    ├── DaftarLaporan.kt    # LazyColumn dengan key = id
    ├── KartuLaporan.kt     # Card > Row > Column + LabelStatus
    └── LabelStatus.kt      # label status berbentuk pil
```

## Penjelasan Singkat

- **Kartu entitas (`KartuLaporan`)**: `Card` berisi `Row`. Di kiri ada `Column` dengan `Modifier.weight(1f)` untuk judul dan lokasi, di kanan ada `LabelStatus`. Parameter `ringkas` dan `onKlik` membuat kartu bisa dipakai ulang.
- **Label status (`LabelStatus`)**: warna dipilih dengan `when` atas sealed class `StatusLaporan`, sehingga compiler memastikan semua status tertangani. Urutan modifier `background` lalu `padding` membuat warna ikut melebar.
- **Daftar (`DaftarLaporan`)**: `LazyColumn` hanya menggambar kartu yang terlihat. `key = { it.id }` membantu Compose mengenali item yang sama walaupun urutannya berubah.
- **Olah sebelum tampil (`LayarDaftar`)**:
  - `sortedByDescending { it.id }` menaruh laporan terbaru di atas.
  - `filter { it.status !is StatusLaporan.Ditolak }` menyembunyikan laporan yang ditolak.
  - `count { ... }` menghitung laporan yang masih aktif (Baru atau Diproses).
- **Aksi utama**: `ExtendedFloatingActionButton` "Lapor" di pojok kanan bawah. Fungsinya menyusul di minggu 4 (state dan form).

## Cara Menjalankan

**Android**: buka proyek di Android Studio, pilih konfigurasi `composeApp`, lalu Run ke emulator atau HP.

**Desktop**:

```bash
./gradlew :composeApp:run
```

## Pembagian Kerja Minggu 3

| Anggota | Tugas |
|---------|-------|
| Nama Anggota 1 | `KartuLaporan` dan `LabelStatus` |
| Nama Anggota 2 | `DaftarLaporan` (LazyColumn) dan data dummy |
| Nama Anggota 3 | `LayarDaftar`: filter, urutan, dan FAB |
| Nama Anggota 4 | Uji di Android dan desktop, tangkapan layar, README |

## Rencana Minggu 4

Form laporan baru dengan `TextField`, state, dan MVVM. FAB "Lapor" akan membuka form tersebut.