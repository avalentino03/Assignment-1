package com.addo.feli_system_order

class MenuItem(var nama: String, var harga: Int) {

    fun tampilkan(nomor: Int) {
        println("[$nomor] $nama  -  Rp$harga")
    }
}
