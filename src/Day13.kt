import AoCUtils.test
import java.io.File

fun main() {

    fun debugMap(foldedMap: List<Point>) {
        val maxY = foldedMap.maxOf { it.y }
        val maxX = foldedMap.maxOf { it.x }

        val map = foldedMap.associateWith { "#" }

        println("map")
        for (y in 0..maxY) {
            println()
            for (x in 0..maxX) {
                val mark = map[Point(x, y)]
                if (mark != null)
                    print(mark)
                else print(".")
            }
        }
        println()
    }

    fun part1(input: String): Int {
        val map = input.lines().filter { !it.startsWith("fold") }
            .filter { it.isNotEmpty() }
            .map { it.split(",") }
            .map { Point(it[0].toInt(), it[1].toInt()) }

        val fold =
            input.lines().filter { it.startsWith("fold") }
                .filter { it.isNotEmpty() }
                .map { it.replace("fold along ", "") }.map { it.split("=") }
                .map { split ->
                    when {
                        split[0] == "y" -> Point(-1, split[1].toInt())
                        split[0] == "x" -> Point(split[1].toInt(), -1)
                        else -> {
                            throw RuntimeException("nope")
                        }
                    }
                }

        var foldedMap = map
        debugMap(foldedMap)

        for (toFold in fold) {
            if (toFold.x == -1) {
                val maxY = foldedMap.maxOf { it.y }
                val newMap = foldedMap.filter { it.y < toFold.y }.toMutableList()
                val toAdd = foldedMap.filter { it.y > toFold.y }.map { Point(it.x, maxY - it.y) }
                newMap.addAll(toAdd)
                newMap.distinct()
                foldedMap = newMap
            }
            if (toFold.y == -1) {
                val maxX = foldedMap.maxOf { it.x }
                val newMap = foldedMap.filter { it.x < toFold.x }.toMutableList()
                val toAdd = foldedMap.filter { it.x > toFold.x }.map { Point(maxX - it.x, it.y) }
                newMap.addAll(toAdd)
                newMap.distinct()
                foldedMap = newMap
            }

            debugMap(foldedMap)
        }



        return foldedMap.size
    }

    fun part2(input: String): Int {


        return 0

    }


    val testInput = "6,10\n" +
            "0,14\n" +
            "9,10\n" +
            "0,3\n" +
            "10,4\n" +
            "4,11\n" +
            "6,0\n" +
            "6,12\n" +
            "4,1\n" +
            "0,13\n" +
            "10,12\n" +
            "3,4\n" +
            "3,0\n" +
            "8,4\n" +
            "1,10\n" +
            "2,14\n" +
            "8,10\n" +
            "9,0\n" +
            "\n" +
            "fold along y=7\n" +
            "fold along x=5"

    val input = File("src", "Day13.txt").readText()

    part1(testInput) test Pair(17, "test 1 part 1")
//    part1(input) test Pair(0, "part 1")

    part2(testInput) test Pair(0, "test 2 part 2")
    part2(input) test Pair(0, "part 2")


}