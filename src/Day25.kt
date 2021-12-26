import AoCUtils.test
import java.io.File

enum class Day25 { Right, Down, Empty }

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {
        val map2d = parseMap(input) { c ->
            when (c) {
                '>' -> Day25.Right
                'v' -> Day25.Down
                '.' -> Day25.Empty
                else -> TODO()
            }
        }

        val checkRight = listOf(Map2dDirection.E)
        val checkDown = listOf(Map2dDirection.S)

        var movements = true
        var counter = 0L
        while (movements) {

            if (debug) {
                val maxX = map2d.maxOf { it.key.x }
                val maxY = map2d.maxOf { it.key.y }
                println()
                println()
                println(counter)
                for (y in 0..maxY) {
                    println()
                    for (x in 0..maxX) {
                        print(
                            when (map2d[Point(x, y)]) {
                                Day25.Right -> ">"
                                Day25.Down -> "v"
                                Day25.Empty -> "."
                                null -> TODO()
                            }
                        )
                    }
                }
            }

            counter++
            movements = false
            val rightMovements = mutableMapOf<Point, Point>()
            map2d.loopRightDown { pos ->
                if (map2d[pos] == Day25.Right) {
                    val around = map2d.around(pos, checkRight, wrap = true)
                    if (around.values.first() == Day25.Empty) {
                        movements = true
                        rightMovements[pos] = around.keys.first()
                    }
                }
            }
            rightMovements.forEach { (from, to) ->
                map2d[to] = map2d[from]!!
                map2d[from] = Day25.Empty
            }
            val downMovements = mutableMapOf<Point, Point>()
            map2d.loopDownRight { pos ->
                if (map2d[pos] == Day25.Down) {
                    val around = map2d.around(pos, checkDown, wrap = true)
                    if (around.values.first() == Day25.Empty) {
                        movements = true
                        downMovements[pos] = around.keys.first()
                    }
                }
            }
            downMovements.forEach { (from, to) ->
                map2d[to] = map2d[from]!!
                map2d[from] = Day25.Empty
            }

        }






        return counter
    }

    fun part2(input: String, debug: Boolean = false): Long {


        return 0

    }

    val testInput = "v...>>.vv>\n" +
            ".vv>>.vv..\n" +
            ">>.>v>...v\n" +
            ">>v>>.>.v.\n" +
            "v>v.vv.v..\n" +
            ">.>>..v...\n" +
            ".vv..>.>v.\n" +
            "v.v..>>v.v\n" +
            "....v..v.>"

    val input = File("src", "Day25.txt").readText()


    part1(testInput, debug = true) test Pair(58L, "test 1 part 1")
    part1(input) test Pair(453L, "part 1")

    part2(testInput) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")


}