fun main() {

    val input:List<String> = readInput("Day02")
    val mapOfDirs = input.map { it.split("\\s".toRegex()).first() to it.split("\\s".toRegex()).last() }
    println(mapOfDirs)
    part1(mapOfDirs)
    part2(mapOfDirs)
}

private fun part1(mapOfDirs: List<Pair<String, String>>) {
    var x = 0
    var y = 0
    for ((k, v) in mapOfDirs) {
        when (k) {
            "forward" -> x += v.toInt()
            "backward" -> x -= v.toInt()
            "up" -> y -= v.toInt()
            "down" -> y += v.toInt()
        }
    }
    println(x * y)
}

private fun part2(mapOfDirs: List<Pair<String, String>>) {
    var x = 0
    var y = 0
    var aim = 0
    for ((k, v) in mapOfDirs) {
        when (k) {
            "forward" -> {
                x += v.toInt()
                if(aim!=0){
                    y += (v.toInt() * aim)
                }
            }
            "up" -> aim -= v.toInt()
            "down" -> {
                aim +=v.toInt()
            }
        }
    }
    println(x * y)
}



