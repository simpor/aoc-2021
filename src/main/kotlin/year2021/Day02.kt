package year2021

import AoCUtils
import AoCUtils.test

fun main() {
    data class Order(val order: String, val x: Int)

    fun part1(input: String): Int {
        val orders = input.lines().map { s -> s.split(" ") }.map { a -> Order(a[0], a[1].toInt()) }

        var position = 0
        var depth = 0
        for (order in orders) {
            when (order.order) {
                "forward" -> position += order.x
                "down" -> depth += order.x
                "up" -> depth -= order.x
            }
        }

        return position * depth

    }

    fun part2(input: String): Int {
        val orders = input.lines().map { s -> s.split(" ") }.map { a -> Order(a[0], a[1].toInt()) }

        var position = 0
        var depth = 0
        var aim = 0
        for (order in orders) {
            when (order.order) {
                "forward" -> {
                    position += order.x
                    depth += aim * order.x
                }
                "down" -> aim += order.x
                "up" -> aim -= order.x
            }
        }

        return position * depth
    }


    val testInput = "forward 5\n" +
            "down 5\n" +
            "forward 8\n" +
            "up 3\n" +
            "down 8\n" +
            "forward 2"

    val input = AoCUtils.readText("year2021/Day02.txt")

    part1(testInput) test Pair(150, "test 1 part 1")
    part1(input) test Pair(1714950, "part 1")

    part2(testInput) test Pair(900, "test 2 part 2")
    part2(input) test Pair(1281977850, "part 2")


}



