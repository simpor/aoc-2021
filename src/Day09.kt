import AoCUtils.test
import java.io.File

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
        val p1 = Point(point.x + 1, point.y)
        val p2 = Point(point.x - 1, point.y)
        val p3 = Point(point.x, point.y + 1)
        val p4 = Point(point.x, point.y - 1)
        val f1 = floor[p1] ?: -1
        val f2 = floor[p2] ?: -1
        val f3 = floor[p3] ?: -1
        val f4 = floor[p4] ?: -1
        if (level < f1 && f1 != 9) {
            list.add(p1)
            list.addAll(basin(floor, basins, p1))
        }
        if (level < f2 && f2 != 9) {
            list.add(p2)
            list.addAll(basin(floor, basins, p2))
        }
        if (level < f3 && f3 != 9) {
            list.add(p3)
            list.addAll(basin(floor, basins, p3))
        }
        if (level < f4 && f4 != 9) {
            list.add(p4)
            list.addAll(basin(floor, basins, p4))
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
        return map.sorted().reversed().take(3).fold(1) { acc, i -> acc * i }

    }


    val testInput = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678\n"

    val input = File("src", "Day09.txt").readText()

    part1(testInput) test Pair(15, "test 1 part 1")
    part1(input) test Pair(502, "part 1")

    part2(testInput) test Pair(1134, "test 2 part 2")
    part2(input) test Pair(1330560, "part 2")


}