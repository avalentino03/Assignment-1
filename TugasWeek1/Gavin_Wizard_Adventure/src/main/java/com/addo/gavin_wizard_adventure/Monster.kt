package com.addo.gavin_wizard_adventure

import kotlin.random.Random

class Monster {
    var elemen: Element
    var nama: String
    var maxHp = 40
    var hp = 40

    init {
        elemen = Element.values().random()
        nama = elemen.namaMonster()
    }

    fun serang(pemain: Pemain) {
        pemain.hp = (pemain.hp - 10).coerceAtLeast(0)
    }
}
