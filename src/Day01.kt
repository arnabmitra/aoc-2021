fun main() {

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")


    val input = readInput("Day01")
    println(partOne(input.map { x-> x.toInt() }))
    println(partTwo(input.map { x-> x.toInt() }))

}

private fun partOne(input: List<Int>): Int {
    var prev = 0

    var prev1 = prev
    var increased = 0

    for ((index, value) in input.withIndex()) {
        if (index != 0 && value > prev1) {
            increased++
        }
        prev1 = value
    }
    return increased
}

fun partTwo(input : List<Int>): Int {
    val listOfWindows = mutableListOf<Int>()
    for (i in 0..(input.size - 3)) {
        listOfWindows.add(input[i] + input[i + 1] + input[i + 2])
    }
    return partOne(listOfWindows)

}
