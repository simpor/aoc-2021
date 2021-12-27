package year2021

import AoCUtils
import AoCUtils.test

fun main() {
    data class Order(val order: String, val x: Int)

    fun part1(input: String): Int {
        val nums = input.lines().get(0).length
        var gamma = ""
        var epsilon = ""

        for (i in 0 until nums) {
            val count = input.lines().filter { line -> line[i] == '1' }.size
            val other = input.lines().size - count
            if (count > other) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: String): Int {
        val nums = input.lines().get(0).length

        var lines = input.lines()
        for (i in 0 until nums) {
            val count = lines.filter { line -> line[i] == '1' }.size
            val filter = if (count >= lines.size - count) '1' else '0'
            lines = lines.filter { l -> l[i] == filter }
            if (lines.size == 1) break
        }
        val first = lines[0].toInt(2)


        lines = input.lines()
        for (i in 0 until nums) {
            val count = lines.filter { line -> line[i] == '0' }.size
            val filter = if (count <= lines.size - count) '0' else '1'
            lines = lines.filter { l -> l[i] == filter }
            if (lines.size == 1) break
        }
        val second = lines[0].toInt(2)
        return first * second
    }


    val testInput = "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010"

    val input = AoCUtils.readText("year2021/Day03.txt")

    part1(testInput) test Pair(198, "test 1 part 1")
    part1(input) test Pair(4147524, "part 1")

    part2(testInput) test Pair(230, "test 2 part 2")
    part2(input) test Pair(3570354, "part 2")


}



