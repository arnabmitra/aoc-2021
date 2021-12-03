fun main() {

    val input:List<String> = readInput("Day03")

    println(part1(input))
    println(part2(input))
}

private fun part1(input:List<String>): Long {
  val qourum = input.size/2
  val byteArrayCounter = IntArray(input[0].length).toMutableList()

  for (i in input)
  {
      for(v in i.indices){
//          println(v)
          if(i[v] == '1'){
              byteArrayCounter[v]++
          }
      }
  }

    var gammaRate=""
    var epsilonRate=""

    for ( b in byteArrayCounter){
        if(b > qourum){
            gammaRate += "1"
            epsilonRate+="0"
        }else
        {
            gammaRate+="0"
            epsilonRate+="1"
        }
    }

    return gammaRate.toLong(2)*epsilonRate.toLong(2)

}



private fun getOxygenRating(input:List<String>): Long {

    var oxygenGeneratorRating = input.toMutableList()
    val length = input[0].length
    for (i in 0 until length){
        val zerosAndones = getMostCommon(oxygenGeneratorRating,i)
        if (zerosAndones.first >= zerosAndones.second) {
            oxygenGeneratorRating = oxygenGeneratorRating.filter { it[i] == '1' }.toMutableList()
        } else {
            oxygenGeneratorRating = oxygenGeneratorRating.filter { it[i] == '0' }.toMutableList()
        }
        if(oxygenGeneratorRating.size == 1) break
    }

    return oxygenGeneratorRating[0].toLong(2)

}

private fun getC02Rating(input:List<String>): Long {

    var co2rating = input.toMutableList()
    val length = input[0].length
    for (i in 0 until length){
        val zerosAndones = getMostCommon(co2rating,i)
        if (zerosAndones.second <= zerosAndones.first) {
            co2rating = co2rating.filter { it[i] == '0' }.toMutableList()
        } else {
            co2rating = co2rating.filter { it[i] == '1' }.toMutableList()
        }
        if(co2rating.size == 1) break
    }

    return co2rating[0].toLong(2)

}

private fun part2(input:List<String>): Long {
    println(getOxygenRating(input))
    println(getC02Rating(input))
    return getOxygenRating(input)* getC02Rating(input)
}
fun getMostCommon(input:MutableList<String>, counter:Int): Pair<Int,Int> {
    var ones = 0
    var zeroes = 0

    for (i in input) {
        if (i[counter] == '1') {
            ones += 1
        } else {
            zeroes += 1
        }

    }

   return Pair(ones,zeroes)
}

