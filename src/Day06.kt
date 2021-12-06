import AoCUtils.test
import java.io.File

fun main() {
    data class Fish(var days: Int, var count: Long = 1)

    fun part1(input: String): Long {
        val fishes = input.split(",").map { it.trim().toInt() }.map { Fish(it) }.toMutableList()
        for (day in 1..80) {
            var newborn = 0L
            fishes.forEach { fish ->
                fish.days = fish.days - 1
                if (fish.days == -1) {
                    newborn += fish.count
                    fish.days = 6
                }
            }

            if (newborn != 0L) fishes.add(Fish(8, newborn))
        }


        return fishes.map { it.count }.sum()
    }

    fun part2(input: String): Long {
        val fishes = input.split(",").map { it.trim().toInt() }.map { Fish(it) }.toMutableList()
        for (day in 1..256) {
            var newborn = 0L
            fishes.forEach { fish ->
                fish.days = fish.days - 1
                if (fish.days == -1) {
                    newborn += fish.count
                    fish.days = 6
                }
            }

            if (newborn != 0L) fishes.add(Fish(8, newborn))
        }


        return fishes.map { it.count }.sum()
    }


    val testInput = "3,4,3,1,2"

    val input = File("src", "Day06.txt").readText()

    part1(testInput) test Pair(5934, "test 1 part 1")
    part1(input) test Pair(362666, "part 1")

    part2(testInput) test Pair(26984457539, "test 2 part 2")
    part2(input) test Pair(1640526601595, "part 2")


}



