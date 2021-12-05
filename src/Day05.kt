import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String): Int {
        data class Line(val start: Point, val end: Point)

        val lines = input.lines().map { line ->
            line.trim().split(" -> ")
                .map { it.split(",") }.windowed(2) { t ->
                    Line(
                        Point(t[0][0].toInt(), t[0][1].toInt()),
                        Point(t[1][0].toInt(), t[1][1].toInt())
                    )
                }
        }.flatten()
        val map = mutableMapOf<Point, Int>()

        lines.forEach { line ->
            // horizontal
            if (line.start.x == line.end.x) {
                val start = if (line.start.y < line.end.y) line.start.y else line.end.y
                val end = if (line.start.y > line.end.y) line.start.y else line.end.y
                for (p in start..end) {
                    val point = Point(line.start.x, p)
                    map[point] = map.getOrDefault(point, 0) + 1
                }
            }
            // verticalt
            if (line.start.y == line.end.y) {
                val start = if (line.start.x < line.end.x) line.start.x else line.end.x
                val end = if (line.start.x > line.end.x) line.start.x else line.end.x
                for (p in start..end) {
                    val point = Point(p, line.start.y)
                    map[point] = map.getOrDefault(point, 0) + 1
                }
            }
        }

        return map.values.count { it > 1 }
    }

    fun part2(input: String): Int {

        data class Line(val start: Point, val end: Point)

        val lines = input.lines().map { line ->
            line.trim().split(" -> ")
                .map { it.split(",") }.windowed(2) { t ->
                    Line(
                        Point(t[0][0].toInt(), t[0][1].toInt()),
                        Point(t[1][0].toInt(), t[1][1].toInt())
                    )
                }
        }.flatten()
        val map = mutableMapOf<Point, Int>()

        lines.forEach { line ->
            // horizontal
            if (line.start.x == line.end.x) {
                val start = if (line.start.y < line.end.y) line.start.y else line.end.y
                val end = if (line.start.y > line.end.y) line.start.y else line.end.y
                for (p in start..end) {
                    val point = Point(line.start.x, p)
                    map[point] = map.getOrDefault(point, 0) + 1
                }
            }
            // verticalt
            else if (line.start.y == line.end.y) {
                val start = if (line.start.x < line.end.x) line.start.x else line.end.x
                val end = if (line.start.x > line.end.x) line.start.x else line.end.x
                for (p in start..end) {
                    val point = Point(p, line.start.y)
                    map[point] = map.getOrDefault(point, 0) + 1
                }
            }

            // diagonal
            else {
                val xIncrease = line.start.x < line.end.x
                val yIncrease = line.start.y < line.end.y

                var drawPoint = line.start

                map[drawPoint] = map.getOrDefault(drawPoint, 0) + 1
                while (drawPoint != line.end) {
                    drawPoint = Point(
                        if (xIncrease) drawPoint.x + 1 else drawPoint.x - 1,
                        if (yIncrease) drawPoint.y + 1 else drawPoint.y - 1
                    )
                    map[drawPoint] = map.getOrDefault(drawPoint, 0) + 1
                }
            }


        }


        return map.values.count { it > 1 }
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

    val input = File("src", "Day05.txt").readText()

    part1(testInput) test Pair(5, "test 1 part 1")
    part1(input) test Pair(5632, "part 1")

    part2(testInput) test Pair(12, "test 2 part 2")
    part2(input) test Pair(0, "part 2")


}



