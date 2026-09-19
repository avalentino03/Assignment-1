package com.addo.gavin_wizard_adventure

class Pemain(var nama: String) {
    var maxHp = 50
    var hp = 50
    var maxMp = 30
    var mp = 30
    var ramuanHp = 5
    var ramuanMp = 5
    var jumlahBunuh = 0
    var sudahKuat = false
    var lifesteal = 1

    fun minumRamuanHp(): String = when {
        ramuanHp <= 0 -> "No Health Potions left."
        hp >= maxHp -> "HP is already full."
        else -> {
            ramuanHp--
            hp = (hp + 25).coerceAtMost(maxHp)
            "Drank Health Potion. HP = $hp/$maxHp"
        }
    }

    fun minumRamuanMp(): String = when {
        ramuanMp <= 0 -> "No Mana Potions left."
        mp >= maxMp -> "MP is already full."
        else -> {
            ramuanMp--
            mp = (mp + 15).coerceAtMost(maxMp)
            "Drank Mana Potion. MP = $mp/$maxMp"
        }
    }

    fun catatPembunuhan(): Boolean {
        jumlahBunuh++
        if (jumlahBunuh >= 5 && !sudahKuat) {
            sudahKuat = true
            maxHp = (maxHp * 1.5).toInt()
            maxMp = (maxMp * 1.5).toInt()
            hp = maxHp
            mp = maxMp
            lifesteal = 1
            return true
        }
        if (sudahKuat) lifesteal++
        return false
    }

    fun pengaliSerangan(): Double = if (sudahKuat) 1.5 else 1.0
}
