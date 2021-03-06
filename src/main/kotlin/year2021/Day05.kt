package year2021

import AoCUtils
import AoCUtils.test
import Point

fun main() {

    data class Line(val start: Point, val end: Point)

    fun parseInput(input: String) = input.lines().map { line ->
        line.trim().split(" -> ")
            .map { it.split(",") }.windowed(2) { t ->
                Line(
                    Point(t[0][0].toInt(), t[0][1].toInt()),
                    Point(t[1][0].toInt(), t[1][1].toInt())
                )
            }
    }.flatten()

    fun solution(lines: List<Line>): Int {
        val map = mutableMapOf<Point, Int>()
        lines.forEach { line ->
            val xStill = line.start.x == line.end.x
            val yStill = line.start.y == line.end.y
            val xIncrease = line.start.x < line.end.x
            val yIncrease = line.start.y < line.end.y

            var drawPoint = line.start
            map[drawPoint] = map.getOrDefault(drawPoint, 0) + 1
            while (drawPoint != line.end) {
                drawPoint = Point(
                    if (xStill) drawPoint.x else if (xIncrease) drawPoint.x + 1 else drawPoint.x - 1,
                    if (yStill) drawPoint.y else if (yIncrease) drawPoint.y + 1 else drawPoint.y - 1
                )
                map[drawPoint] = map.getOrDefault(drawPoint, 0) + 1
            }
        }

        return map.values.count { it > 1 }
    }

    fun part1(input: String): Int {
        val lines = parseInput(input).filter { it.start.x == it.end.x || it.end.y == it.start.y }
        return solution(lines)
    }

    fun part2(input: String): Int {
        val lines = parseInput(input)
        return solution(lines)
    }


    val testInput = "0,9 -> 5,9\n" +
            "8,0 -> 0,8\n" +
            "9,4 -> 3,4\n" +
            "2,2 -> 2,1\n" +
            "7,0 -> 7,4\n" +
            "6,4 -> 2,0\n" +
            "0,9 -> 2,9\n" +
            "3,4 -> 1,4\n" +
            "0,0 -> 8,8\n" +
            "5,5 -> 8,2"

    val input = AoCUtils.readText("year2021/Day05.txt")

    part1(testInput) test Pair(5, "test 1 part 1")
    part1(input) test Pair(5632, "part 1")

    part2(testInput) test Pair(12, "test 2 part 2")
    part2(input) test Pair(22213, "part 2")


}



