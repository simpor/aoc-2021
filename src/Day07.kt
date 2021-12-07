import AoCUtils.test
import java.io.File
import kotlin.math.abs

fun main() {

    fun part1(input: String): Long {
        data class Crab(var pos: Long, var fuel: Long = 0)

        val crabs = input.split(",").map { it.toLong() }

        val average = crabs.sum() / crabs.size


        val min = crabs.minOrNull()!!
        val max = crabs.maxOrNull()!!

        val test = mutableListOf<Crab>()
        for (pos in min..max) {
            test.add(Crab(pos, crabs.map { abs(it - pos) }.sum()))
        }

        val best = test.sortedBy { it.fuel }[0]


        return best.fuel
    }

    fun part2(input: String): Long {
        data class Crab(var pos: Long, var fuel: Long = 0)

        val crabs = input.split(",").map { it.toLong() }

        val min = crabs.minOrNull()!!
        val max = crabs.maxOrNull()!!

        val test = mutableListOf<Crab>()
        for (pos in min..max) {

            var totalFuel = 0L
            crabs.forEach { start ->
                val endPos = abs(start - pos)
                for (i in 0..endPos) {
                    totalFuel = totalFuel + i
                }
            }
            test.add(Crab(pos, totalFuel))
        }

        val best = test.sortedBy { it.fuel }[0]


        return best.fuel


    }


    val testInput = "16,1,2,0,4,2,7,1,2,14"

    val input = File("src", "Day07.txt").readText()

    part1(testInput) test Pair(37, "test 1 part 1")
    part1(input) test Pair(343441, "part 1")

    part2(testInput) test Pair(168, "test 2 part 2")
    part2(input) test Pair(98925151, "part 2")


}