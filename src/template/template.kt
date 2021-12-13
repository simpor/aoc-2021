package template

import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String, debug: Boolean = false): Int {

        return 0
    }

    fun part2(input: String, debug: Boolean = false): Int {


        return 0

    }


    val testInput = ""

    val input = File("src", "Day05.txt").readText()

    part1(testInput) test Pair(0, "test 1 part 1")
    part1(input) test Pair(0, "part 1")

    part2(testInput) test Pair(0, "test 2 part 2")
    part2(input) test Pair(0, "part 2")


}