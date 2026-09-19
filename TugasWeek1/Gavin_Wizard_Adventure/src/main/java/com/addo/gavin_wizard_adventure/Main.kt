package com.addo.gavin_wizard_adventure

fun main() {
    println("--- WIZARD ADVENTURE ---")

    print("What's your name? ")
    var nama = readln().trim()
    while (nama.isEmpty()) {
        println("Name cannot be empty.")
        print("What's your name? ")
        nama = readln().trim()
    }

    val pemain = Pemain(nama)
    println("Good luck, $nama! You're gonna need it!")

    var jalan = true
    while (jalan) {
        println()
        println("What're you going to do?")
        println("1. View Stats")
        println("2. Enter battle")
        println("3. Exit")
        print("Choose: ")

        when (readln().trim()) {
            "1" -> menuStatistik(pemain)
            "2" -> masukPertarungan(pemain)
            "3" -> {
                println("Farewell, ${pemain.nama}!")
                jalan = false
            }
            else -> println("Invalid choice.")
        }
    }
}

fun menuStatistik(pemain: Pemain) {
    var kembali = false
    while (!kembali) {
        println()
        println("--- ${pemain.nama}'s STATS ---")
        println("HP: ${pemain.hp}/ ${pemain.maxHp}")
        println("Mana: ${pemain.mp}/ ${pemain.maxMp}")
        println("Kills needed to evolve: ${pemain.jumlahBunuh}/ 5")
        println("Mana Potions held: ${pemain.ramuanMp}")
        println("Health Potions held: ${pemain.ramuanHp}")
        if (pemain.sudahKuat) {
            println("Lifesteal: ${pemain.lifesteal}")
        }
        println("-----------------")

        println("a. Drink Mana Potion")
        println("b. Drink Health Potion")
        println("c. Rename self")
        println("d. Back")
        print("Choose: ")

        when (readln().trim().lowercase()) {
            "a" -> println(pemain.minumRamuanMp())
            "b" -> println(pemain.minumRamuanHp())
            "c" -> {
                print("New name: ")
                val baru = readln().trim()
                when {
                    baru.isEmpty() -> println("Name cannot be empty.")
                    else -> {
                        pemain.nama = baru
                        println("Renamed to ${pemain.nama}.")
                    }
                }
            }
            "d" -> kembali = true
            else -> println("Invalid choice.")
        }
    }
}

fun masukPertarungan(pemain: Pemain) {
    val monster = Monster()
    var hasil = ""

    while (hasil == "") {
        println()
        println("--- BATTLE ---")
        println("${pemain.nama}")
        println("HP: ${pemain.hp}/ ${pemain.maxHp}")
        println("Mana: ${pemain.mp}/ ${pemain.maxMp}")
        println("HP Potions: ${pemain.ramuanHp}")
        println("MP Potions: ${pemain.ramuanMp}")
        println("${monster.nama}")
        println("HP: ${monster.hp}/ ${monster.maxHp}")
        println("Type: ${monster.elemen.name.lowercase()}")
        println("----------")

        println()
        println("a. Fire Attack")
        println("b. Water Attack")
        println("c. Grass Attack")
        println("d. Drink potion")
        println("e. Run")
        print("Choose: ")

        val aksi = readln().trim().lowercase()

        var mantra: Element? = when (aksi) {
            "a" -> Element.FIRE
            "b" -> Element.WATER
            "c" -> Element.GRASS
            else -> null
        }

        when {
            mantra != null -> {
                if (pemain.mp < 10) {
                    println("Not enough mana. (need 10)")
                    continue
                }
                pemain.mp -= 10

                var damage = 10
                if (mantra.mengalahkan() == monster.elemen) {
                    damage *= 2
                    println("It's super effective!")
                }
                damage = (damage * pemain.pengaliSerangan()).toInt()

                monster.hp = (monster.hp - damage).coerceAtLeast(0)
                println("${pemain.nama} casts ${mantra.name} for $damage damage.")

                if (pemain.sudahKuat) {
                    pemain.hp = (pemain.hp + pemain.lifesteal).coerceAtMost(pemain.maxHp)
                    println("Lifesteal heals ${pemain.lifesteal} HP.")
                }

                if (monster.hp <= 0) {
                    hasil = "menang"
                } else {
                    monster.serang(pemain)
                    println("${monster.nama} attacks for 10.")
                    if (pemain.hp <= 0) hasil = "kalah"
                }
            }

            aksi == "d" -> {
                var diMenuRamuan = true
                while (diMenuRamuan) {
                    println()
                    println("--- POTION MENU ---")
                    println("HP: ${pemain.hp}/ ${pemain.maxHp}")
                    println("Mana: ${pemain.mp}/ ${pemain.maxMp}")
                    println("HP Potions: ${pemain.ramuanHp}")
                    println("MP Potions: ${pemain.ramuanMp}")
                    println("----------")
                    println("1. Drink HP Potion")
                    println("2. Drink MP Potion")
                    println("3. Back")
                    print("Choose: ")

                    when (readln().trim()) {
                        "1" -> {
                            println(pemain.minumRamuanHp())
                            if (monster.hp > 0) {
                                monster.serang(pemain)
                                println("${monster.nama} attacks for 10.")
                                if (pemain.hp <= 0) hasil = "kalah"
                            }
                            diMenuRamuan = false
                        }
                        "2" -> {
                            println(pemain.minumRamuanMp())
                            if (monster.hp > 0) {
                                monster.serang(pemain)
                                println("${monster.nama} attacks for 10.")
                                if (pemain.hp <= 0) hasil = "kalah"
                            }
                            diMenuRamuan = false
                        }
                        "3" -> {
                            diMenuRamuan = false
                            println("Back to battle.")
                        }
                        else -> println("Invalid choice.")
                    }
                }
            }

            aksi == "e" -> {
                hasil = "kabur"
                println("You ran away!")
            }

            else -> println("Invalid choice.")
        }
    }

    if (hasil == "menang") {
        println()
        println("${monster.nama} defeated!")
        val berevolusi = pemain.catatPembunuhan()
        println("Kills: ${pemain.jumlahBunuh}/ 5")
        if (berevolusi) {
            println("You evolved into a STRONG WIZARD!")
            println("HP/MP scaled 1.5x. Lifesteal unlocked.")
        } else if (pemain.sudahKuat) {
            println("Lifesteal increased to ${pemain.lifesteal}.")
        }
    } else if (hasil == "kalah") {
        println()
        println("You died! Restarting from the beginning...")
        pemain.maxHp = 50
        pemain.hp = 50
        pemain.maxMp = 30
        pemain.mp = 30
        pemain.ramuanHp = 5
        pemain.ramuanMp = 5
        pemain.jumlahBunuh = 0
        pemain.sudahKuat = false
        pemain.lifesteal = 1
    }
}
