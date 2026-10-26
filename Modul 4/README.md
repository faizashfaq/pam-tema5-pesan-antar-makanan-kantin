# Pesan Antar Makanan Kantin

Proyek kelompok mata kuliah **Pengembangan Aplikasi Mobile (IF25-22017)**, Teknik Informatika ITERA, dibuat dengan **Kotlin Multiplatform + Compose Multiplatform**.

## Tema
**Pesan Antar Makanan Kantin**: aplikasi untuk memesan makanan dari kantin kampus dan mengantarkannya ke pemesan.
Fitur platform yang dipakai: **Notifikasi** (pemberitahuan pesanan diterima, diproses, dan siap diantar).

## Anggota Kelompok
| No | Nama | NIM |
|----|------|-----|
| 1 | Muhammad Faiz Ashfaq | 124140144 |
| 2 |  Aryo Fajar Pratomo  | 124140012 |
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
- [x] **Satu aksi utama**: FAB **+ Pesan** di pojok kanan bawah

### Minggu 4: State dan MVVM
- [x] **Form tambah**: `LayarFormPesanan` untuk membuat pesanan baru, semua kolom isian memakai `KolomTeks` tanpa state
- [x] **UiState form**: `FormPesananUiState` berisi kolom pesanan dan `bisaSimpan` yang dihitung dari nilai lain
- [x] **ViewModel**: `FormPesananViewModel` dengan `MutableStateFlow` private dan `StateFlow` publik; data hanya disimpan kalau valid
- [x] **Daftar ikut bertambah**: `SumberDaftarPesanan` menyimpan daftar dalam `MutableStateFlow`; pesanan baru langsung muncul paling atas di layar daftar
- [x] FAB **+ Pesan** sekarang membuka form

## Tangkapan Layar
| Form Tambah Pesanan | Daftar Setelah Data Ditambah |
|---------|---------|
| ![Form tambah](docs/form-tambah.png) | ![Daftar setelah tambah](docs/daftar-setelah-tambah.png) |

Tangkapan layar Minggu 3: [daftar Android](docs/daftar-android.png) · [daftar Desktop](docs/daftar-desktop.png)

## Form Tambah (Minggu 4)

Semua kode ada di `commonMain`, jadi dipakai bersama oleh Android dan Desktop. Polanya MVVM:

```
LayarFormPesanan (View)        membaca uiState, meneruskan aksi pengguna
        │ event ▼   ▲ state
FormPesananViewModel           menyimpan uiState, memvalidasi, memanggil sumber data
        │ meminta ▼ ▲ data
SumberDaftarPesanan (Model)    menyimpan daftar pesanan di MutableStateFlow
```

| Bagian | Isi | Fungsinya |
|--------|-----|-----------|
| `KolomTeks` | `OutlinedTextField` tanpa state | Menerima `value` dari luar dan melaporkan ketikan lewat `onValueChange`. Dipakai untuk nama pemesan, lokasi antar, jumlah, dan catatan |
| `PilihanMenu` | `RadioButton` + `selectable` | Memilih satu menu kantin; juga tanpa state |
| `LayarFormPesanan` | `Column` + `verticalScroll` | Menampilkan form, total harga, tombol **Batal** dan **Simpan** |
| `FormPesananUiState` | data class | Semua isi form di satu tempat, plus nilai turunan `bisaSimpan`, `errorJumlah`, `total` |
| `FormPesananViewModel` | `ViewModel` + `StateFlow` | Mengubah state dengan `update { it.copy(...) }` dan menyimpan pesanan di `viewModelScope` |
| `SumberDaftarPesanan` | `object` + `MutableStateFlow<List<Pesanan>>` | Sumber palsu yang dibaca layar daftar dan ditambah oleh form |

Keputusan desain:
- **State turun, event naik.** `KolomTeks` tidak menyimpan apa pun. Nilainya datang dari `uiState`, dan setiap ketikan dikirim ke ViewModel (`vm::ubahPemesan`, `vm::ubahLokasi`, ...). Pemilik state hanya `FormPesananViewModel`.
- **ViewModel, bukan `remember`.** Isi form tetap utuh saat layar diputar, karena ViewModel tidak ikut dibuat ulang seperti Activity.
- **Hanya pemilik yang mengubah.** `_uiState` bertipe `MutableStateFlow` dan `private`; layar hanya melihat `uiState` bertipe `StateFlow`.
- **`bisaSimpan` dihitung, tidak disimpan.** Tombol **Simpan** tidak mungkin aktif selama nama atau lokasi kosong, menu belum dipilih, atau jumlah di luar 1 sampai 20.
- **Tidak tersimpan dua kali.** `sedangMenyimpan` langsung bernilai `true` saat **Simpan** ditekan, sehingga `bisaSimpan` menjadi `false` dan tekanan kedua diabaikan.
- **Tanpa efek samping di composable.** Penyimpanan berjalan di ViewModel. Layar hanya memakai `LaunchedEffect` untuk kembali ke daftar setelah `idTersimpan` terisi.

## ViewModel dan Validasi (Minggu 4)

Aturan validasi ada di `FormPesananUiState`, terpisah dari tampilan:

```kotlin
val jumlahAngka: Int? get() = jumlah.toIntOrNull()?.takeIf { it in 1..JUMLAH_MAKS }
val bisaSimpan: Boolean get() = pemesan.isNotBlank() && lokasiAntar.isNotBlank() &&
    menu != null && jumlahAngka != null && !sedangMenyimpan
```

ViewModel menyimpan hanya kalau valid, lalu sumber palsu menambahkan pesanan ke daftar:

```kotlin
fun simpan() {
    val s = _uiState.value
    if (!s.bisaSimpan) return
    _uiState.update { it.copy(sedangMenyimpan = true) }
    viewModelScope.launch {
        val baru = sumber.tambah(...)                 // _daftar.update { lama -> lama + baru }
        _uiState.update { it.copy(sedangMenyimpan = false, idTersimpan = baru.id) }
    }
}
```

Layar daftar membaca sumber yang sama, jadi pesanan baru tampil tanpa kode tambahan:

```kotlin
val semua by SumberDaftarPesanan.daftar.collectAsState()
```

## Contoh Hasil

Hasil menjalankan logika UiState, ViewModel, dan sumber palsu (urutan isian seperti di form):

```
jumlah awal di daftar : 12
form kosong                        bisaSimpan=false errorJumlah=null total=0
isi pemesan                        bisaSimpan=false errorJumlah=null total=0
isi lokasi                         bisaSimpan=false errorJumlah=null total=0
pilih Nasi Ayam Geprek             bisaSimpan=true  errorJumlah=null total=15000
jumlah 25                          bisaSimpan=false errorJumlah=Jumlah harus 1 sampai 20 total=0
ketik '2a' tersimpan sebagai: '2'
jumlah 2                           bisaSimpan=true  errorJumlah=null total=30000
simpan ditekan 2x, sedangMenyimpan=true
idTersimpan = 13
jumlah di daftar sekarang : 13
kartu paling atas : #13 Nasi Ayam Geprek x2 · Aryo · GKU 2 ruang 305 · catatan=null · Diterima
ringkasan : 13 pesanan · 5 baru · 4 diproses · 4 siap diantar
```

Cara membaca hasilnya:
- `bisaSimpan` baru `true` setelah nama, lokasi, dan menu terisi. Jumlah 25 langsung ditolak dengan pesan error.
- **Simpan** ditekan dua kali, tetapi daftar hanya bertambah satu (12 menjadi 13).
- Pesanan baru mendapat id **#13**, status **Baru**, dan muncul paling atas karena `terbaruDulu()` dari Minggu 3. Ringkasan di bawah judul ikut berubah menjadi `5 baru`.

## Struktur Berkas (tambahan Minggu 4)

```
composeApp/src/commonMain/kotlin/org/example/project/
├── App.kt                     navigasi daftar / form / tentang, daftar dibaca dari SumberDaftarPesanan
├── ContohDaftarPesanan.kt     + daftarMenuKantin untuk pilihan menu di form
├── LayarDaftarPesanan.kt      FAB + Pesan memanggil onTambah
├── SumberDaftarPesanan.kt     (baru) sumber palsu dengan MutableStateFlow
├── FormPesananUiState.kt      (baru) data class form + bisaSimpan
├── FormPesananViewModel.kt    (baru) ViewModel dengan StateFlow
├── KolomTeks.kt               (baru) kolom isian tanpa state
└── LayarFormPesanan.kt        (baru) layar form tambah pesanan
```

Dependency tambahan di `commonMain`: `org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose` (untuk `ViewModel`, `viewModelScope`, dan `viewModel { }`).

## Pembagian Tugas

| Anggota | Bagian yang dikerjakan | Berkas |
|---------|------------------------|--------|
| (isi nama anggota 1) | UiState dan ViewModel form | `FormPesananUiState.kt`, `FormPesananViewModel.kt` |
| (isi nama anggota 2) | Sumber palsu dan daftar ikut bertambah | `SumberDaftarPesanan.kt`, `ContohDaftarPesanan.kt`, `App.kt` |
| (isi nama anggota 3) | Layar form, KolomTeks, tangkapan layar, README | `KolomTeks.kt`, `LayarFormPesanan.kt`, `LayarDaftarPesanan.kt`, `README.md` |

<!-- Isi nama anggota dan sesuaikan pembagian tugas dengan kontribusi sebenarnya sebelum push. -->

## Cara Menjalankan
- **Android**: buka proyek di Android Studio, pilih konfigurasi `composeApp`, lalu Run. Tekan **+ Pesan**, isi form, lalu **Simpan**; aplikasi kembali ke daftar dan pesanan baru ada paling atas.
- **Desktop**: `./gradlew :composeApp:run` (Windows: `gradlew.bat :composeApp:run`).

Semua berkas memakai `package org.example.project` (bawaan template). Kalau proyek kelompok memakai package lain, ganti dengan Find and Replace di seluruh proyek.

Data masih disimpan di memori, jadi pesanan yang ditambah akan hilang saat aplikasi ditutup. Penyimpanan permanen dibuat di minggu-minggu berikutnya.