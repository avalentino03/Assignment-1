package com.addo.feli_system_order

fun main() {
    val daftarMenu = ArrayList<MenuItem>().apply {
        add(MenuItem("Nasi Goreng", 150))
        add(MenuItem("Mie Ayam", 120))
        add(MenuItem("Es Teh", 30))
        add(MenuItem("Ayam Bakar", 200))
    }

    val daftarPesanan = ArrayList<Order>()

    var jalan = true
    while (jalan) {
        println()
        println("ORDER SYSTEM")
        println("1. Make Order")
        println("2. View Orders")
        println("3. View Menu")
        println("4. Add Menu")
        println("5. Edit Menu")
        println("6. Delete Menu")
        println("7. Exit")
        print("Pilih: ")

        when (readln().trim()) {
            "1" -> buatPesanan(daftarMenu, daftarPesanan)
            "2" -> lihatPesanan(daftarPesanan)
            "3" -> lihatMenu(daftarMenu)
            "4" -> tambahMenu(daftarMenu)
            "5" -> ubahMenu(daftarMenu)
            "6" -> hapusMenu(daftarMenu)
            "7" -> {
                println("Terima kasih, sampai jumpa!")
                jalan = false
            }
            else -> println("Pilihan tidak valid. Coba lagi.")
        }
    }
}

fun buatPesanan(daftarMenu: ArrayList<MenuItem>, daftarPesanan: ArrayList<Order>) {
    if (daftarMenu.isEmpty()) {
        println("Menu masih kosong. Silakan tambah menu terlebih dahulu.")
        return
    }

    print("Nama pelanggan: ")
    val nama = readln().trim()
    if (nama.isEmpty()) {
        println("Nama tidak boleh kosong.")
        return
    }

    val pesanan = Order(nama)

    var selesai = false
    while (!selesai) {
        println()
        daftarMenu.forEachIndexed { i, item -> item.tampilkan(i + 1) }
        println("[0] Selesai")
        print("Pilih nomor menu: ")

        val input = readln().trim().toIntOrNull()
        when {
            input == null -> println("Input harus berupa angka.")
            input == 0 -> selesai = true
            input < 1 || input > daftarMenu.size ->
                println("Nomor menu tidak tersedia.")
            else -> {
                val item = daftarMenu[input - 1]
                print("Jumlah: ")
                val jumlah = readln().trim().toIntOrNull()
                when {
                    jumlah == null -> println("Jumlah harus berupa angka.")
                    jumlah <= 0 -> println("Jumlah minimal 1.")
                    else -> {
                        pesanan.tambah(item, jumlah)
                        println("Berhasil ditambahkan.")
                    }
                }
            }
        }
    }

    if (pesanan.apakahKosong()) {
        println("Pesanan dibatalkan karena kosong.")
    } else {
        daftarPesanan.add(pesanan)
        println()
        println(">>>>>> PESANAN DIKONFIRMASI <<<<<<")
        pesanan.cetak()
    }
}

fun lihatPesanan(daftarPesanan: ArrayList<Order>) {
    if (daftarPesanan.isEmpty()) {
        println("Belum ada pesanan.")
        return
    }
    daftarPesanan.forEach { pesanan ->
        println("--------------------------------")
        pesanan.cetak()
    }
    println("--------------------------------")
}

fun lihatMenu(daftarMenu: ArrayList<MenuItem>) {
    if (daftarMenu.isEmpty()) {
        println("Menu kosong.")
        return
    }
    println()
    println("--- DAFTAR MENU ---")
    daftarMenu.forEachIndexed { i, item -> item.tampilkan(i + 1) }
}

fun tambahMenu(daftarMenu: ArrayList<MenuItem>) {
    print("Nama makanan: ")
    val nama = readln().trim()
    if (nama.isEmpty()) {
        println("Nama tidak boleh kosong.")
        return
    }

    print("Harga: ")
    val harga = readln().trim().toIntOrNull()
    if (harga == null || harga <= 0) {
        println("Harga harus berupa angka positif.")
        return
    }

    daftarMenu.add(MenuItem(nama, harga))
    println("Menu berhasil ditambahkan.")
}

fun ubahMenu(daftarMenu: ArrayList<MenuItem>) {
    if (daftarMenu.isEmpty()) {
        println("Menu kosong.")
        return
    }
    daftarMenu.forEachIndexed { i, item -> item.tampilkan(i + 1) }
    print("Nomor menu yang ingin diubah: ")

    val nomor = readln().trim().toIntOrNull()
    if (nomor == null || nomor < 1 || nomor > daftarMenu.size) {
        println("Nomor menu tidak valid.")
        return
    }
    val item = daftarMenu[nomor - 1]

    print("Nama baru (${item.nama}): ")
    val namaBaru = readln().trim()
    if (namaBaru.isEmpty()) {
        println("Nama tidak boleh kosong.")
        return
    }

    print("Harga baru (${item.harga}): ")
    val hargaBaru = readln().trim().toIntOrNull()
    if (hargaBaru == null || hargaBaru <= 0) {
        println("Harga harus berupa angka positif.")
        return
    }

    item.nama = namaBaru
    item.harga = hargaBaru
    println("Menu berhasil diubah.")
}

fun hapusMenu(daftarMenu: ArrayList<MenuItem>) {
    if (daftarMenu.isEmpty()) {
        println("Menu kosong.")
        return
    }
    daftarMenu.forEachIndexed { i, item -> item.tampilkan(i + 1) }
    print("Nomor menu yang ingin dihapus: ")

    val nomor = readln().trim().toIntOrNull()
    if (nomor == null || nomor < 1 || nomor > daftarMenu.size) {
        println("Nomor menu tidak valid.")
        return
    }

    println("Menu '${daftarMenu[nomor - 1].nama}' dihapus.")
    daftarMenu.removeAt(nomor - 1)
}
