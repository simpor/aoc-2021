import AoCUtils.test
import java.io.File

fun main() {
    fun debugMap(debug: Boolean,  foldedMap: List<Point>, maxX: Int, maxY: Int,letter: Boolean=false) {
        if (debug) {
            val map = foldedMap.associateWith { "#" }

            println("map: " + foldedMap.size)
            for (y in 0..maxY) {
                println()
                for (x in 0..maxX) {
                    if (letter && x % 5 == 0) print("      ")
                    val mark = map[Point(x, y)]
                    if (mark != null)
                        print(mark)
                    else print(".")
                }
            }
            println()
        }
    }

    fun part1(input: String, debug: Boolean = false): Int {


        val map = input.lines().filter { !it.startsWith("fold") }
            .filter { it.isNotEmpty() }
            .map { it.split(",") }
            .map { Point(it[0].toInt(), it[1].toInt()) }

        val fold =
            input.lines().asSequence().filter { it.startsWith("fold") }
                .map { it.replace("fold along ", "") }.map { it.split("=") }
                .map { split ->
                    when {
                        split[0] == "y" -> Point(-1, split[1].toInt())
                        split[0] == "x" -> Point(split[1].toInt(), -1)
                        else -> {
                            throw RuntimeException("nope")
                        }
                    }
                }.toList()

        var foldedMap = map
        var maxY = foldedMap.maxOf { it.y }
        var maxX = foldedMap.maxOf { it.x }
        println("Before Dots: " + foldedMap.size)
        debugMap(debug, foldedMap, maxX, maxY)
        for (toFold in fold) {
            if (toFold.x == -1) {
                val newMap = foldedMap.filter { it.y < toFold.y }.toMutableList()
                val toAdd = foldedMap.filter { it.y > toFold.y }.map { it.copy(y = maxY - it.y) }
                newMap.addAll(toAdd)
                foldedMap = newMap.distinct()
                maxY = (maxY / 2) - 1
            }
            if (toFold.y == -1) {
                val newMap = foldedMap.filter { it.x < toFold.x }.toMutableList()
                val toAdd = foldedMap.filter { it.x > toFold.x }.map { it.copy(x = maxX - it.x) }
                newMap.addAll(toAdd)
                foldedMap = newMap.distinct()
                maxX = (maxX / 2) - 1
            }

            println("Dots: " + foldedMap.size)
            debugMap(debug, foldedMap, maxX, maxY)
            // break
        }
//        foldedMap.sortedBy { it.x }.forEach { println(it) }
        debugMap(true, foldedMap, maxX, maxY, true)

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

    part1(testInput, true) test Pair(17, "test 1 part 1")
    part1(input) test Pair(0, "part 1") // not 897

    part2(testInput) test Pair(0, "test 2 part 2")
    part2(input) test Pair(0, "part 2")


}