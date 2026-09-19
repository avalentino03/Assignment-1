package com.addo.feli_system_order

class Order(val pelanggan: String) {

    private val daftarItem = ArrayList<MenuItem>()
    private val daftarJumlah = ArrayList<Int>()

    fun tambah(item: MenuItem, jumlah: Int) {
        daftarItem.add(item)
        daftarJumlah.add(jumlah)
    }

    fun apakahKosong(): Boolean = daftarItem.isEmpty()

    fun hitungTotal(): Int {
        var total = 0
        daftarItem.forEachIndexed { i, item ->
            total += item.harga * daftarJumlah[i]
        }
        return total
    }

    fun cetak() {
        println("PESANAN ${pelanggan.uppercase()}")
        daftarItem.forEachIndexed { i, item ->
            val subtotal = item.harga * daftarJumlah[i]
            println("${i + 1}. ${item.nama} x${daftarJumlah[i]}  Rp$subtotal")
        }
        println()
        println("TOTAL  Rp${hitungTotal()}")
    }
}
