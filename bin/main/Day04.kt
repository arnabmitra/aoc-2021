fun main() {

    val input: List<String> = readInput("Day04")

    val inputsWithMatrix = input.toString().removePrefix("[").removeSuffix("]").split(", ,")
    println(part1(inputsWithMatrix))
    println(part2(inputsWithMatrix))
}

private fun part1(input: List<String>): Int {
    val bingo = input[0]
    val bingoCards = input.drop(1)
    val mapBingoMap = mutableMapOf<Int, List<String>>()
    val mapBingoMapPairs = mutableMapOf<Int, List<List<Bingo>>>()
    for ((bingoCardNumber, card) in bingoCards.withIndex()) {
        val c = card.split(",").toList()
        mapBingoMap[bingoCardNumber] = c
    }
    for ((key, values) in mapBingoMap) {
        val listT = mutableListOf<List<Bingo>>()
        for (i in values) {
            val t = i.split(Regex("\\s+")).filter { it != "" }.toList().map { Bingo(it.toInt(), false) }.toList()
            listT.add(t)
        }
        mapBingoMapPairs[key] = listT
    }

    var multiplier = 0

    for (drawn in bingo.split(",")) {
        for ((key, values) in mapBingoMapPairs) {
            for (t in values) {
                for (x in t) {
                    if (drawn.toInt() == x.i) {
                        x.b = true
                    }
                }
            }
        }

        var found = false
        // row value check
        for ((key, values) in mapBingoMapPairs) {
            for (t in values) {
                // row will win
                if (t.filter { it.b }.size == 5) {
                    val sum = sumWinningBoard(mapBingoMapPairs, key)
                    multiplier = sum * drawn.toInt()
                    found = true
                    break
                }
            }
            // column win
            for ((key, values) in mapBingoMapPairs) {
                for (g in 0 until 5) {
                    var ret = 0
                    for (h in 0 until 5) {
                        if (values[h][g].b) {
                            ret++
                        }
                    }
                    if (ret == 5) {
                        val sum = sumWinningBoard(mapBingoMapPairs, key)
                        multiplier = drawn.toInt() * sum
                        found = true
                        break
                    }
                }
                if (found) {
                    break
                }
            }
            if (found) {
                break
            }
        }
        if (found) {
            break
        }
    }


    return multiplier
}

private fun part2(input: List<String>): Int {
    val bingo = input[0]
    val bingoCards = input.drop(1)
    val mapBingoMap = mutableMapOf<Int, List<String>>()
    val mapBingoMapPairs = mutableMapOf<BingoBoardWon, List<List<Bingo>>>()
    for ((bingoCardNumber, card) in bingoCards.withIndex()) {
        val c = card.split(",").toList()
        mapBingoMap[bingoCardNumber] = c
    }
    for ((key, values) in mapBingoMap) {
        val listT = mutableListOf<List<Bingo>>()
        for (i in values) {
            val t = i.split(Regex("\\s+")).filter { it != "" }.toList().map { Bingo(it.toInt(), false) }.toList()
            listT.add(t)
        }
        mapBingoMapPairs[BingoBoardWon(key,false)] = listT
    }

    var multiplier = 0

    for (drawn in bingo.split(",")) {
        for ((key, values) in mapBingoMapPairs) {
            for (t in values) {
                for (x in t) {
                    if (drawn.toInt() == x.i) {
                        x.b = true
                    }
                }
            }
        }

        var found = false
        // row value check
        for ((key, values) in mapBingoMapPairs) {
            if(key.won){
                continue
            }
            for (t in values) {
                // row will win
                if (t.filter { it.b }.size == 5) {
                    val sum = sumWinningBoardPart2(mapBingoMapPairs, key)
                    if(sum !=0) {
                        multiplier = drawn.toInt() * sum
                        key.won = true

                    }
                    found = true
                    break
                }
            }
            if(found) continue
        }

        // column win
        for ((key, values) in mapBingoMapPairs) {
            if(key.won){
                continue
            }

            for (g in 0 until 5) {
                var ret = 0
                for (h in 0 until 5) {
                    if (values[h][g].b) {
                        ret++
                    }
                }
                if (ret == 5) {
                    val sum = sumWinningBoardPart2(mapBingoMapPairs, key)
                    if(sum !=0) {
                        multiplier = drawn.toInt() * sum
                        key.won = true
                        break
                    }
                }
            }
        }

    }


    return multiplier
}

private fun sumWinningBoard(mapBingoMapPairs: MutableMap<Int, List<List<Bingo>>>, key: Int) =
        mapBingoMapPairs[key]!!.flatMap { it }.filter { !it.b }.sumOf { it.i }

private fun sumWinningBoardPart2(mapBingoMapPairs: MutableMap<BingoBoardWon, List<List<Bingo>>>, key: BingoBoardWon) =
        mapBingoMapPairs[key]!!.flatMap { it }.filter { !it.b }.sumOf { it.i }


data class Bingo(var i: Int, var b: Boolean)
data class BingoBoardWon(var i: Int, var won: Boolean)



