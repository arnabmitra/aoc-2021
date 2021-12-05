import java.lang.Math.abs

fun main() {

    val input: List<String> = readInput("Day05")

    println(part1(input))
    println(part2(input))
}

private fun part1(inputs: List<String>) {

    val linesForOverlap = mutableListOf<Line>()
    val pointsForOverlap = mutableListOf<Position>()
    for (input in inputs) {
        val lines = parseInputLine(input)
        linesForOverlap.add(lines)
    }

    for (line in linesForOverlap) {
        //vertical line
        if (line.start.row == line.end.row) {
            val rowVal = line.start.row
            if (line.start.col < line.end.col) {
                for (foo in line.start.col..line.end.col) {
                    determinePointOverlap(pointsForOverlap, foo, rowVal)
                }
            } else if (line.start.col >= line.end.col) {
                for (foo in line.end.col..line.start.col) {
                    determinePointOverlap(pointsForOverlap, foo, rowVal)
                }
            }
        }

        //horizontal line
        if (line.start.col == line.end.col) {
            val columnVal = line.start.col
            if (line.start.row < line.end.row) {
                for (foo in line.start.row..line.end.row) {
                    determinePointOverlap(pointsForOverlap, columnVal, foo)
                }
            } else if (line.start.row >= line.end.row) {
                for (foo in line.end.row..line.start.row) {
                    determinePointOverlap(pointsForOverlap, columnVal, foo)

                }
            }
        }

    }

    println(pointsForOverlap.filter { it.overlap > 1 }.size)
}

private fun determinePointOverlap(pointsForOverlap: MutableList<Position>, columnVal: Int, rowValue: Int) {
    val getPoint = pointsForOverlap.firstOrNull { it.col == columnVal && it.row == rowValue }
    if (getPoint == null) {
        pointsForOverlap.add(Position(rowValue, columnVal, 1))
    } else {
        pointsForOverlap.remove(getPoint)
        pointsForOverlap.add(Position(rowValue, columnVal, getPoint.overlap + 1))
    }
}

private fun part2(inputs: List<String>) {

    val linesForOverlap = mutableListOf<Line>()
    val pointsForOverlap = mutableListOf<Position>()
    for (input in inputs) {
        val lines = parseInputLine(input)
        linesForOverlap.add(lines)
    }

    for (line in linesForOverlap) {
        //vertical line
        if (line.start.row == line.end.row) {
            val rowVal = line.start.row
            if (line.start.col < line.end.col) {
                for (foo in line.start.col..line.end.col) {
                    determinePointOverlap(pointsForOverlap, foo, rowVal)
                }
            } else if (line.start.col >= line.end.col) {
                for (foo in line.end.col..line.start.col) {
                    determinePointOverlap(pointsForOverlap, foo, rowVal)
                }
            }
        }

        //horizontal line
        if (line.start.col == line.end.col) {
            val columnVal = line.start.col
            if (line.start.row < line.end.row) {
                for (foo in line.start.row..line.end.row) {
                    determinePointOverlap(pointsForOverlap, columnVal, foo)
                }
            } else if (line.start.row >= line.end.row) {
                for (foo in line.end.row..line.start.row) {
                    determinePointOverlap(pointsForOverlap, columnVal, foo)

                }
            }
        }

        //diagonal line
        if ((line.start.row - line.end.row) != 0 && kotlin.math.abs((line.start.col - line.end.col) / (line.start.row - line.end.row)) == 1) {
            val delta = kotlin.math.abs(line.start.col - line.end.col) - 1

            determinePointOverlap(pointsForOverlap, line.start.col, line.start.row)
            determinePointOverlap(pointsForOverlap, line.end.col, line.end.row)
            val rowValues = mutableListOf<Int>()
            val colValues = mutableListOf<Int>()
            if (line.start.row < line.end.row) {
                var s = line.start.row
                for(i in 0 until delta ){
                    s += 1
                    rowValues.add(s)
                }
            }
            if (line.start.row > line.end.row) {
                var s = line.start.row

                for(i in 0 until delta ){
                    s -= 1
                    rowValues.add(s)
                }
            }

            if (line.start.col < line.end.col) {
                var s = line.start.col
                for(i in 0 until delta ){
                    s += 1
                    colValues.add(s)
                }
            }
            if (line.start.col > line.end.col) {
                var s = line.start.col

                for(i in 0 until delta ){
                    s -= 1
                    colValues.add(s)
                }
            }

            if(colValues.size != rowValues.size){
                throw IllegalStateException("Bad input")
            }

            for( index in 0 until rowValues.size){
                determinePointOverlap(pointsForOverlap, colValues[index], rowValues[index])
            }
        }
    }


println(pointsForOverlap.filter { it.overlap > 1 }.size)
}


data class Position(val row: Int, val col: Int, val overlap: Int = 0)

data class Line(val start: Position, val end: Position)


fun parseInputLine(s: String): Line {
    val regex = "(\\d+),(\\d+)\\s+->\\s+(\\d+),(\\d+)".toRegex()
    val groups = regex.find(s)?.groupValues!!
    val (startRow, startCol, endRow, endCol) = groups.drop(1).map { it.toInt() }
    return Line(Position(startRow, startCol), Position(endRow, endCol))
}
