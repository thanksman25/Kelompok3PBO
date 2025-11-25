# Kelompok 5 PBO

Anggota:
1. Abdan Syakura bin Yasir 2408107010014
2. Nayla Nabila Syahel 2408107010005
3. Raisa Salsa Nabila 2408107010007

# HealthMart - Aplikasi Penjualan Obat

Aplikasi desktop berbasis Java Swing untuk manajemen penjualan obat dan kesehatan dengan fitur admin dan customer.

## Struktur Program

```
healthmart4/HealthMart/
├── src/
│   └── healthmart/
│       ├── model/              # Model/Entity classes
│       │   ├── Akun.java
│       │   ├── Admin.java
│       │   ├── Customer.java
│       │   ├── Obat.java
│       │   ├── ListObat.java
│       │   ├── Keranjang.java
│       │   ├── ItemKeranjang.java
│       │   ├── ItemTransaksi.java
│       │   ├── Transaksi.java
│       │   ├── Invoice.java
│       │   └── ValidationUtil.java
│       ├── view/               # UI/View classes (Swing panels)
│       │   ├── HealthMartApp.java         # Main frame dengan CardLayout
│       │   ├── LoginPanel.java            # Panel login
│       │   ├── RegisterPanel.java         # Panel registrasi
│       │   ├── AdminDashboard.java        # Dashboard admin
│       │   └── CustomerDashboard.java     # Dashboard customer
│       ├── controller/         # Controller/Driver classes
│       │   ├── Driver.java
│       │   ├── AdminDriver.java
│       │   └── CustomerDriver.java
│       └── util/               # Utility classes
│           ├── DataManager.java           # Load/Save ke text files
│           └── AuthenticationManager.java # Login/Register logic
├── bin/                        # Compiled .class files (auto-generated)
├── data/                       # Data files (text-based persistence)
│   ├── admin.txt              # Admin accounts
│   ├── customer.txt           # Customer accounts
│   ├── obat.txt               # Product data
│   └── transaksi.txt          # Transaction records
└── README.md
```

## Fitur Utama

### Admin
- Kelola produk (tambah, edit, hapus)
- Lihat semua produk dalam laporan
- Terima transaksi (konfirmasi/tolak)
- Kurangi stok saat transaksi dikonfirmasi

### Customer
- Lihat semua produk
- Tambah ke keranjang belanja
- Checkout dengan 3 metode pembayaran:
  - QRIS (dengan kode QR)
  - Bank Transfer (BNI)
  - Cash on Delivery (COD)
- Lihat riwayat transaksi
- Update profil alamat & nomor telepon

## Compile dan Jalankan

### macOS/Linux

**Compile:**
```bash
cd /Users/macbookpro/Mac/kuliah/healthmart4/HealthMart
javac -d bin $(find src -name "*.java")
```

**Jalankan:**
```bash
java -cp bin healthmart.view.HealthMartApp
```

### Windows (PowerShell)

**Compile:**
```powershell
cd C:\Users\YourUsername\...\healthmart4\HealthMart
javac -d bin $(Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object {$_.FullName})
```

**Jalankan:**
```bash
java -cp bin healthmart.view.HealthMartApp
```

### Windows (Command Prompt - Alternatif)

**Compile:** Compile folder per folder
```bash
javac -d bin src\healthmart\model\*.java
javac -d bin src\healthmart\controller\*.java
javac -d bin src\healthmart\util\*.java
javac -d bin src\healthmart\view\*.java
```

**Jalankan:**
```bash
java -cp bin healthmart.view.HealthMartApp
```

## Akun Default

### Admin
- **Username:** `admin`
- **Password:** `admin123`

### Customer
- **Username:** `user1`
- **Password:** `pass123`
- **Username:** `user2`
- **Password:** `pass456`

## Format Data Files

### admin.txt
```
username|password|nama|email
```

### customer.txt
```
username|password|nama|email|alamat|noTelepon
```

### obat.txt
```
id|nama|deskripsi|harga|stok|kategori
```

### transaksi.txt
```
idTransaksi|customerId|namaCustomer|metodePayment|status|tanggalTransaksi|tanggalKonfirmasi|totalHarga|items
```
(items format: idObat:jumlah;idObat:jumlah)

## Teknologi

- **Language:** Java 21
- **GUI:** Swing (JFrame, JPanel, JTable, JTabbedPane)
- **Layout:** BorderLayout, GridBagLayout, FlowLayout, CardLayout
- **Persistence:** Text files dengan pipe-delimited format
- **Data Types:** 
  - Harga obat: `int` (Rupiah)
  - Total transaksi: `double` (Rupiah)

## Catatan Penting

1. **Data Persistence:** Semua data disimpan ke text files di folder `data/`
2. **Escape Characters:** Special characters (|, ;, :, \) akan di-escape saat disimpan
3. **Login Flow:** Setiap login membaca data terbaru dari files
4. **Logout:** Menyimpan semua perubahan sebelum kembali ke login
5. **Stock Management:** Stok obat berkurang saat admin mengkonfirmasi transaksi
6. **Transaction History:** Customer dapat melihat riwayat transaksi mereka setelah login

## Troubleshooting

### "Cannot find symbol"
Pastikan semua file `.java` dikompilasi dengan benar. Compile ulang:
```bash
javac -d bin $(find src -name "*.java")
```

### Data tidak tersimpan
Pastikan folder `data/` ada di direktori yang sama dengan `bin/` folder. Aplikasi akan auto-create jika belum ada.

### Path issues di Windows
Gunakan double backslash `\\` atau forward slash `/` di command.
