package year2021

import AoCUtils.test
import Point

fun main() {
    data class Octopus(var level: Int, var flashed: Int, var flashes: Int = 0)

    fun flash(map: Map<Point, Octopus>, pos: Point, step: Int) {
        val currentOctopus = map[pos]!!
        if (currentOctopus.flashed != step) currentOctopus.level++
        if (currentOctopus.level <= 9) return

        currentOctopus.flashed = step
        currentOctopus.level = 0
        currentOctopus.flashes++

        listOf(
            Point(pos.x + 1, pos.y - 1),
            Point(pos.x + 1, pos.y + 1),
            Point(pos.x + 1, pos.y),
            Point(pos.x - 1, pos.y - 1),
            Point(pos.x - 1, pos.y + 1),
            Point(pos.x - 1, pos.y),
            Point(pos.x, pos.y + 1),
            Point(pos.x, pos.y - 1)
        ).forEach { p ->
            val octopus = map[p]
            if (octopus != null) {
                flash(map, p, step)
            }
        }
    }

    fun part1(input: String, steps: Int = 100): Int {
        val lines = input.lines()

        val map = lines.mapIndexed { y, row ->
            row.mapIndexed { x, o -> Point(x, y) to Octopus(o.toString().toInt(), 0) }
        }.flatten().toMap()


        for (step in 1..steps) {
            for (y in 0..9) {
                for (x in 0..9) {
                    val pos = Point(x, y)
                    val octopus = map[pos]!!
                    octopus.level++

                }
            }

            for (y in 0..9) {
                for (x in 0..9) {
                    val pos = Point(x, y)
                    if (map[pos]!!.level > 9)
                        flash(map, pos, step)
                }
            }

        }

        return map.values.sumOf { octopus -> octopus.flashes }
    }

    fun part2(input: String): Int {
        val lines = input.lines()

        val map = lines.mapIndexed { y, row ->
            row.mapIndexed { x, o -> Point(x, y) to Octopus(o.toString().toInt(), 0) }
        }.flatten().toMap()


        for (step in 1..100000) {
            for (y in 0..9) {
                for (x in 0..9) {
                    val pos = Point(x, y)
                    val octopus = map[pos]!!
                    octopus.level++
                }
            }

            for (y in 0..9) {
                for (x in 0..9) {
                    val pos = Point(x, y)
                    if (map[pos]!!.level > 9)
                        flash(map, pos, step)
                }
            }
            if (map.values.sumOf { octopus -> octopus.level } == 0) return step

        }
        return map.values.sumOf { octopus -> octopus.flashes }
    }


    val testInput = "5483143223\n" +
            "2745854711\n" +
            "5264556173\n" +
            "6141336146\n" +
            "6357385478\n" +
            "4167524645\n" +
            "2176841721\n" +
            "6882881134\n" +
            "4846848554\n" +
            "5283751526"

    val input = "4738615556\n" +
            "6744423741\n" +
            "2812868827\n" +
            "8844365624\n" +
            "4546674266\n" +
            "4518674278\n" +
            "7457237431\n" +
            "4524873247\n" +
            "3153341314\n" +
            "3721414667"

    part1(testInput, 10) test Pair(204, "test 1 part 1")
    part1(testInput) test Pair(1656, "test 1 part 1")
    part1(input) test Pair(1615, "part 1")

    part2(testInput) test Pair(195, "test 2 part 2")
    part2(input) test Pair(249, "part 2")


}