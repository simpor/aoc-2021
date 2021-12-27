package year2021

import AoCUtils
import AoCUtils.test

fun main() {
    fun part1(input: String): Int {
        val list = input.split("\n").map { s -> s.toInt() }
        var counter = 0
        for (i in 1 until list.size) {
            if (list[i - 1] < list[i]) counter++
        }

        return counter
    }

    fun part2(input: String): Int {
        val list = input.split("\n").map { s -> s.toInt() }
        var counter = 0
        for (i in 1 until (list.size - 2)) {
            val w1 = list[i - 1] + list[i] + list[i + 1]
            val w2 = list[i] + list[i + 1] + list[i + 2]
            if (w1 < w2) counter++
        }

        return counter
    }


    val testInput = "199\n" +
            "200\n" +
            "208\n" +
            "210\n" +
            "200\n" +
            "207\n" +
            "240\n" +
            "269\n" +
            "260\n" +
            "263"
    val input = AoCUtils.readText("year2021/Day01.txt")

    part1(testInput) test Pair(7, "test 1 part 1 should be 0")
    part1(input) test Pair(1139, "part 1 should be 0")

    part2(testInput) test Pair(5, "test 2 part 2 should be 0")
    part2(input) test Pair(1103, "part 2 should be 0")


}



