package year2021

import AoCUtils
import AoCUtils.test
import Point

fun main() {

    fun part1(input: String): Int {
        val list = input.lines().map { it.toList().map { it.toString().toInt() } }
        val floor = list.mapIndexed { x, li ->
            li.mapIndexed { y, h -> Point(x, y) to h }
        }.flatten().toMap()

        val lowest = floor.filter { p ->
            (p.value < (floor[Point(p.key.x - 1, p.key.y)] ?: 10)
                    && p.value < (floor[Point(p.key.x + 1, p.key.y)] ?: 10)
                    && p.value < (floor[Point(p.key.x, p.key.y - 1)] ?: 10)
                    && p.value < (floor[Point(p.key.x, p.key.y + 1)] ?: 10))
        }

        return lowest.map { it.value + 1 }.sum()
    }

    fun basin(floor: Map<Point, Int>, basins: List<Point>, point: Point): List<Point> {
        val list = mutableListOf<Point>()
        list.addAll(basins)
        val level = floor[point]!!
        listOf(
            Point(point.x + 1, point.y),
            Point(point.x - 1, point.y),
            Point(point.x, point.y + 1),
            Point(point.x, point.y - 1)
        )
            .map { Pair(it, floor[it] ?: -1) }
            .forEach { p ->
                if (level < p.second && p.second != 9) {
                    list.add(p.first)
                    list.addAll(basin(floor, basins, p.first))
                }
            }

        return list.distinct()

    }

    fun part2(input: String): Int {
        val list = input.lines().map { it.toList().map { it.toString().toInt() } }
        val floor = list.mapIndexed { x, li ->
            li.mapIndexed { y, h -> Point(x, y) to h }
        }.flatten().toMap()

        val lowest = floor.filter { p ->
            (p.value < (floor[Point(p.key.x - 1, p.key.y)] ?: 10)
                    && p.value < (floor[Point(p.key.x + 1, p.key.y)] ?: 10)
                    && p.value < (floor[Point(p.key.x, p.key.y - 1)] ?: 10)
                    && p.value < (floor[Point(p.key.x, p.key.y + 1)] ?: 10))
        }
        val map = lowest.map { basin(floor, listOf(it.key), it.key).size }
        return map.sorted().takeLast(3).fold(1) { acc, i -> acc * i }

    }


    val testInput = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678\n"

    val input = AoCUtils.readText("year2021/Day09.txt")

    part1(testInput) test Pair(15, "test 1 part 1")
    part1(input) test Pair(502, "part 1")

    part2(testInput) test Pair(1134, "test 2 part 2")
    part2(input) test Pair(1330560, "part 2")


}