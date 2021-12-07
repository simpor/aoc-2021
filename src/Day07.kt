import AoCUtils.test
import java.io.File
import kotlin.math.abs
import kotlin.math.min

fun main() {

    fun part1(input: String): Long {
        val crabs = input.split(",").map { it.toLong() }
        val min = crabs.minOrNull()!!
        val max = crabs.maxOrNull()!!

        var minFuel = Long.MAX_VALUE
        for (pos in min..max) {
            val fuel = crabs.map { abs(it - pos) }.sum()
            minFuel = min(fuel, minFuel)
        }

        return minFuel
    }

    fun part2(input: String): Long {
        val crabs = input.split(",").map { it.toLong() }
        val min = crabs.minOrNull()!!
        val max = crabs.maxOrNull()!!

        var minFuel = Long.MAX_VALUE
        for (pos in min..max) {

            var totalFuel = 0L
            crabs.forEach { start ->
                val endPos = abs(start - pos)
                totalFuel += endPos * (endPos + 1) / 2
            }
            minFuel = min(totalFuel, minFuel)
        }
        return minFuel
    }


    val testInput = "16,1,2,0,4,2,7,1,2,14"

    val input = File("src", "Day07.txt").readText()

    part1(testInput) test Pair(37, "test 1 part 1")
    part1(input) test Pair(343441, "part 1")

    part2(testInput) test Pair(168, "test 2 part 2")
    part2(input) test Pair(98925151, "part 2")


}