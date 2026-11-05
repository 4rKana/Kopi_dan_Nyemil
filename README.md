# ☕ Kopi dan Nyemil - Mobile Ordering App

![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-Studio-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![UI](https://img.shields.io/badge/UI-Material%20Design%203-F5CBA7?style=for-the-badge)

**Kopi dan Nyemil** adalah aplikasi mobile Android untuk pemesanan kopi dan camilan (*pastry/snack*) dengan antarmuka yang modern, bersih, dan ramah pengguna. Proyek ini dibangun berdasarkan spesifikasi desain *wireframe* dengan alur lengkap mulai dari pengenalan aplikasi, autentikasi pengguna, penelusuran katalog menu, pengelolaan profil, hingga konfirmasi pengiriman pesanan.

---

## ✨ Fitur Utama

Aplikasi ini mengimplementasikan 8 tampilan utama yang saling terintegrasi tanpa memerlukan basis data eksternal (*menggunakan in-memory/static dummy data*):

1. **Splash / Welcome Screen**: Halaman pembuka yang menampilkan identitas visual (logo), nama aplikasi, *tagline* *"Get a taste of your wish"*, dan tombol aksi untuk memulai.
2. **Sign Up (Auth Landing)**: Halaman sambutan bagi pengguna untuk memilih masuk ke akun yang sudah ada (*Login*) atau mendaftar sebagai pengguna baru (*Register*).
3. **Login User**: Formulir masuk menggunakan *username* dan *password*.
4. **Register**: Formulir pendaftaran pengguna baru yang mencakup *nama lengkap*, *username*, dan *password*.
5. **Home (Katalog Makanan & Minuman)**: Menampilkan sapaan dinamis (`Halo [Nama],`) serta daftar katalog menu makanan dan minuman lengkap dengan foto, komposisi singkat, dan harga.
6. **Profile Saya**: Halaman pengaturan profil untuk melihat dan memperbarui nama pengguna secara *real-time* di dalam sesi aplikasi.
7. **Alamat Pengiriman (Order)**: Formulir detail pengiriman yang memuat input *Nama Lengkap*, *Alamat*, serta *Patokan atau detail lengkap* sebelum pesanan diproses.
8. **Konfirmasi Pesanan**: Halaman status akhir pemesanan yang menginformasikan bahwa pesanan telah diterima dan kurir sedang dalam perjalanan.

---

## 🥐 Daftar Menu (Static Dummy Data)

Sesuai ketentuan aplikasi, halaman **Home** menampilkan 5 item makanan dan minuman statis berikut:

| No | Nama Menu | Deskripsi | Harga |
| :-: | :--- | :--- | :--- |
| 1 | **Kopi Susu Gula Aren** | Espresso, susu, gula aren | Rp 22.000 |
| 2 | **Donat Cokelat** | Donat empuk, glaze cokelat | Rp 12.000 |
| 3 | **Sourdough Loaf** | Roti fermentasi alami | Rp 35.000 |
| 4 | **Cappuccino** | Espresso, foam susu lembut | Rp 25.000 |
| 5 | **Matcha Latte** | Matcha Jepang, susu segar | Rp 27.000 |

---

## 🛠️ Teknologi yang Digunakan

* **Bahasa Pemrograman**: Kotlin
* **UI Toolkit**: Jetpack Compose (Material Design 3) / Android XML Views *(sesuaikan salah satu)*
* **Navigasi**: Jetpack Navigation Compose / Explicit Intent & Bottom Navigation
* **Manajemen Data**: *Static Dummy Data* & *In-Memory State* (tanpa *database*)
* **IDE & Build Tool**: Android Studio & Gradle (Kotlin DSL)

---

## 🚀 Cara Menjalankan Aplikasi

Ikuti langkah-langkah berikut untuk menjalankan proyek ini di perangkat lokal Anda:

1. **Clone repositori ini** ke komputer Anda menggunakan terminal atau Git Bash:
   ```bash
   git clone [https://github.com/4rKana/kopi-dan-nyemil.git](https://github.com/4rKana/kopi-dan-nyemil.git)
   ```
2. Buka **Android Studio**, pilih menu **Open**, lalu arahkan ke folder proyek hasil *clone* tadi.
3. Tunggu hingga proses **Gradle Sync** selesai mengunduh seluruh dependensi yang dibutuhkan.
4. Siapkan **Android Emulator** (AVD) atau hubungkan perangkat Android fisik menggunakan kabel USB (pastikan *USB Debugging* aktif).
5. Klik tombol **Run 'app'** (`Shift + F10`) di bagian atas Android Studio.

---

## 📱 Screenshot Tampilan Aplikasi

> *Catatan: Simpan gambar screenshot aplikasi Anda di dalam folder `screenshots/` pada repositori ini dengan nama file yang sesuai di bawah ini.*

| 1. Splash / Welcome | 2. Sign Up | 3. Login User | 4. Register |
| :---: | :---: | :---: | :---: |
| <img src="screenshots/1_splash.png" width="200" alt="Splash Screen"/> | <img src="screenshots/2_signup.png" width="200" alt="Sign Up Screen"/> | <img src="screenshots/3_login.png" width="200" alt="Login Screen"/> | <img src="screenshots/4_register.png" width="200" alt="Register Screen"/> |

| 5. Home | 6. Profile | 7. Alamat Pengiriman | 8. Konfirmasi Pesanan |
| :---: | :---: | :---: | :---: |
| <img src="screenshots/5_home.png" width="200" alt="Home Screen"/> | <img src="screenshots/6_profile.png" width="200" alt="Profile Screen"/> | <img src="screenshots/7_alamat.png" width="200" alt="Alamat Screen"/> | <img src="screenshots/8_konfirmasi.png" width="200" alt="Konfirmasi Screen"/> |

---

## 👤 Pengembang

* **Nama**: Arya Fabio Ismail
* **NIM**: 1247050080
* **Program Studi**: Teknik Informatika — UIN Sunan Gunung Djati Bandung
* **GitHub**: [@4rKana](https://github.com/4rKana)
