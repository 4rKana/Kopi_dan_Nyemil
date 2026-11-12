# Kopi dan Nyemil ☕🍩

**Kopi dan Nyemil** adalah aplikasi purwarupa (prototype) berbasis Android untuk pemesanan makanan dan minuman kopi. Aplikasi ini mensimulasikan alur pengguna yang lengkap, mulai dari autentikasi hingga proses konfirmasi pengiriman pesanan, dengan antarmuka yang modern, responsif, dan dinamis.

Proyek ini dibangun untuk memenuhi tugas pengembangan aplikasi Android modern menggunakan arsitektur antarmuka deklaratif.

---

## 📖 Deskripsi Aplikasi

Aplikasi "Kopi dan Nyemil" dirancang khusus dengan alur pengguna (*user flow*) yang kohesif. Meskipun menggunakan *in-memory data* (tanpa database), aplikasi ini mampu menyimpan state nama pengguna secara dinamis saat melakukan Login atau Registrasi. Nama pengguna tersebut akan diteruskan secara konsisten dan digunakan sebagai sapaan personal ("Halo [Nama],") di berbagai layar seperti Home, Profile, dan Konfirmasi Pesanan.

## ✨ Fitur Utama

Aplikasi ini mencakup **8 layar (screen) utama**, yaitu:

1. **Splash / Welcome Screen**: Layar pembuka yang elegan untuk menyambut pengguna sebelum mulai berinteraksi dengan aplikasi.
2. **Sign Up Landing Screen**: Halaman arahan yang memberikan pengguna pilihan untuk mendaftar (Register) atau masuk (Login).
3. **Login Screen**: Formulir untuk pengguna yang sudah memiliki akun (menyimpan *username* secara dinamis).
4. **Register Screen**: Formulir pendaftaran untuk pengguna baru (menyimpan *nama lengkap* secara dinamis).
5. **Home Screen**: Halaman utama yang menampilkan sapaan dinamis pengguna dan katalog daftar menu yang terdiri dari 5 menu statis (Kopi Susu Gula Aren, Donat Cokelat, Sourdough Loaf, Cappuccino, dan Matcha Latte) dengan gambar menarik.
6. **Profile Screen**: Layar profil yang menampilkan nama aktif saat ini dan dilengkapi fitur untuk mengubah (*update*) nama pengguna yang ditampilkan.
7. **Alamat Pengiriman (Order Address)**: Formulir pengisian detail alamat pengiriman untuk proses pemesanan.
8. **Konfirmasi Pesanan**: Halaman terakhir yang memberitahukan pengguna bahwa pesanan telah berhasil dibuat dan siap diantarkan.

Aplikasi juga didukung oleh **Bottom Navigation Bar** untuk kemudahan navigasi antara halaman Home, Order, dan Profile.

---

## 🛠️ Teknologi yang Digunakan

- **IDE:** Android Studio
- **Bahasa Pemrograman:** Kotlin
- **UI Toolkit:** Jetpack Compose (Modern Declarative UI)
- **Navigasi:** Jetpack Navigation Compose
- **Desain & Tema:** Material Design 3 (M3)
- **Penyimpanan Data:** *In-Memory Dummy Data* (State & ViewModel / `rememberSaveable`)

---

## 🚀 Cara Menjalankan Aplikasi

Ikuti langkah-langkah di bawah ini untuk menjalankan proyek ini di mesin lokal Anda:

1. **Clone Repository**
   Buka terminal atau command prompt, lalu jalankan perintah berikut:
   ```bash
   git clone https://github.com/username-anda/KopiDanNyemil.git
   ```
2. **Buka Proyek di Android Studio**
   - Buka aplikasi **Android Studio**.
   - Pilih **File > Open**, lalu arahkan ke direktori folder proyek hasil clone.
3. **Sinkronisasi Gradle**
   Tunggu hingga proses sinkronisasi *Gradle build* selesai. Jika gagal, klik ikon **Sync Project with Gradle Files** di bagian atas toolbar Android Studio.
4. **Jalankan Aplikasi**
   - Sambungkan perangkat fisik (menggunakan kabel USB dengan opsi *USB Debugging* aktif) atau jalankan Emulator Android (AVD).
   - Klik tombol **Run 'app'** (ikon panah hijau ▶) di toolbar atas.

---

## 📸 Screenshot Tampilan

*(Tambahkan path gambar lokal atau URL gambar di bagian yang kosong pada tabel di bawah ini)*

| Splash / Welcome | Sign Up Landing | Login Screen | Register Screen |
| :---: | :---: | :---: | :---: |
| <img src="screenshots/1_splash.png" width="200" alt="Splash Screen"> | <img src="screenshots/2_landing.png" width="200" alt="Landing Screen"> | <img src="screenshots/3_login.png" width="200" alt="Login Screen"> | <img src="screenshots/4_register.png" width="200" alt="Register Screen"> |

| Home Screen | Profile Screen | Order Address | Konfirmasi Pesanan |
| :---: | :---: | :---: | :---: |
| <img src="screenshots/5_home.png" width="200" alt="Home Screen"> | <img src="screenshots/6_profile.png" width="200" alt="Profile Screen"> | <img src="screenshots/7_order.png" width="200" alt="Order Address"> | <img src="screenshots/8_confirm.png" width="200" alt="Konfirmasi Pesanan"> |

---

<p align="center">Dibuat dengan ❤️ untuk Tugas Aplikasi Android Kopi dan Nyemil</p>