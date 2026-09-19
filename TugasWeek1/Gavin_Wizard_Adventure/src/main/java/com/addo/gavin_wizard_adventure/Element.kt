package com.addo.gavin_wizard_adventure

enum class Element {
    FIRE, WATER, GRASS;

    fun mengalahkan(): Element = when (this) {
        FIRE -> GRASS
        WATER -> FIRE
        GRASS -> WATER
    }

    fun namaMonster(): String = when (this) {
        FIRE -> "Firemon"
        WATER -> "Watermon"
        GRASS -> "Grassmon"
    }
}
