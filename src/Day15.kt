import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {
        val map = input.lines()
            .mapIndexed { y, row -> row.toList().mapIndexed { x, v -> Point(x, y) to v.toString().toLong() } }.flatten()
            .toMap()

        val start = Point(0, 0)
        val goal = map.keys.maxByOrNull { it.x + it.y }!!
        println("start: $start, goal: $goal")


        val costMap = mutableMapOf<Point, Long>()

        for (x in 0..goal.x) {
            for (y in 0..goal.y) {
                val current = Point(x, y)
                val up = Point(x, y - 1)
                val left = Point(x - 1, y)

                val upCost = costMap[up]
                val leftCost = costMap[left]

                if (upCost == null && leftCost == null) {
                    costMap[current] = 0
                } else if (upCost == null && leftCost != null) {
                    costMap[current] = leftCost + map[current]!!
                } else if (upCost != null && leftCost == null) {
                    costMap[current] = upCost + map[current]!!
                } else {
                    val cheapest = if (leftCost!! < upCost!!) leftCost else upCost
                    costMap[current] = cheapest + map[current]!!
                }
            }
        }

        return costMap[goal]!!
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val map = input.lines()
            .mapIndexed { y, row -> row.toList().mapIndexed { x, v -> Point(x, y) to v.toString().toLong() } }.flatten()
            .toMap()
        val mapSize = map.keys.maxByOrNull { it.x + it.y }!!

        val completeMap = mutableMapOf<Point, Long>()
        for (y in 0..9) {
            println()
            for (x in 0..9) {
                print(map[Point(x, y)])
            }
        }
        println()

        // increase input
        // y....
        for (x in 0..mapSize.x) {
            for (y in 0..((mapSize.y + 1) * 5)) {
                if (y <= mapSize.y) {
                    completeMap[Point(x, y)] = map[Point(x, y)]!!
                } else {
                    val prevY = y - mapSize.y - 1
                    val prev = Point(x, prevY)
                    val current = Point(x, y)
                    val cost = if (completeMap[prev]!! == 9L) 1L else completeMap[prev]!! + 1
                    completeMap[current] = cost
                }

            }
        }

        for (y in 0..49) {
            println()
            for (x in 0..9) {
                print(completeMap[Point(x, y)])
            }
        }
        println()

        // x...
        for (y in 0..((mapSize.y + 1) * 5)) {
            for (x in (mapSize.x + 1)..((mapSize.x + 1) * 5)) {
                val prevX = x - mapSize.x - 1
                val prev = Point(prevX, y)
                val current = Point(x, y)
                val cost = if (completeMap[prev]!! == 9L) 1L else completeMap[prev]!! + 1
                completeMap[current] = cost
            }
        }


        for (y in 0..49) {
            println()
            for (x in 0..49) {
                print(completeMap[Point(x, y)])
            }
        }
        println()

        val start = Point(0, 0)
        val goal = completeMap.keys.maxByOrNull { it.x + it.y }!!
        println("start: $start, goal: $goal")


        val costMap = mutableMapOf<Point, Long>()

        for (y in 0..goal.y) {
            println()
            for (x in 0..goal.x) {
//                print(completeMap[Point(x, y)])
                val current = Point(x, y)
                val up = Point(x, y - 1)
                val left = Point(x - 1, y)

                val upCost = costMap[up]
                val leftCost = costMap[left]

                if (upCost == null && leftCost == null) {
                    costMap[current] = 0
                } else if (upCost == null && leftCost != null) {
                    costMap[current] = leftCost + completeMap[current]!!
                } else if (upCost != null && leftCost == null) {
                    costMap[current] = upCost + completeMap[current]!!
                } else {
                    val cheapest = if (leftCost!! < upCost!!) leftCost else upCost
                    costMap[current] = cheapest + completeMap[current]!!
                }
            }
        }
        println()

        return costMap[goal]!!
    }


    val testInput =
        "1163751742\n" +
                "1381373672\n" +
                "2136511328\n" +
                "3694931569\n" +
                "7463417111\n" +
                "1319128137\n" +
                "1359912421\n" +
                "3125421639\n" +
                "1293138521\n" +
                "2311944581"

    val input = File("src", "Day15.txt").readText()

    part1(testInput) test Pair(40L, "test 1 part 1")
    part1(input) test Pair(673L, "part 1")

    part2(testInput) test Pair(315L, "test 2 part 2")
//    part2(input) test Pair(0L, "part 2")


}