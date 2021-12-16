import AoCUtils.test
import java.io.File
import java.util.*

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

    fun Point.neighbours() = listOf(
        Point(x, y - 1),
        Point(x, y + 1),
        Point(x + 1, y),
        Point(x - 1, y)
    )

    fun part2(input: String, debug: Boolean = false): Long {
        data class Node(val x: Int, val y: Int, val cost: Long = 0) {
            var sum: Long? = null

            fun neighbours(completeMap: MutableMap<Point, Node>) = Point(x, y).neighbours().mapNotNull { completeMap[it] }
        }

        val map = input.lines()
            .mapIndexed { y, row ->
                row.toList().mapIndexed { x, v -> Point(x, y) to Node(x, y, v.toString().toLong()) }
            }.flatten()
            .toMap()
        val mapSize = map.keys.maxByOrNull { it.x + it.y }!!

        val completeMap = mutableMapOf<Point, Node>()
        // y....
        for (x in 0..mapSize.x) {
            for (y in 0 until ((mapSize.y + 1) * 5)) {
                if (y <= mapSize.y) {
                    completeMap[Point(x, y)] = map[Point(x, y)]!!
                } else {
                    val prevY = y - mapSize.y - 1
                    val prev = Point(x, prevY)
                    val current = Point(x, y)
                    val cost = if (completeMap[prev]!!.cost == 9L) 1L else completeMap[prev]!!.cost + 1
                    completeMap[current] = Node(x, y, cost)
                }
            }
        }

        // x...
        for (y in 0 until ((mapSize.y + 1) * 5)) {
            for (x in (mapSize.x + 1) until ((mapSize.x + 1) * 5)) {
                val prevX = x - mapSize.x - 1
                val prev = Point(prevX, y)
                val current = Point(x, y)
                val cost = if (completeMap[prev]!!.cost == 9L) 1L else completeMap[prev]!!.cost + 1
                completeMap[current] = Node(x, y, cost)
            }
        }

        val start = Node(0, 0)
        start.sum = 0
        val goalPoint = completeMap.keys.maxByOrNull { it.x + it.y }!!
        val goal = completeMap[goalPoint]!!
        println("start: $start, goal: $goal")

        val visiting = PriorityQueue(compareBy(Node::sum))
        visiting.add(start)
        while (true) {
            val node = visiting.poll()
            if (node === goal)
                return goal.sum!!
            for (neighbour in node.neighbours(completeMap)) {
                if (neighbour.sum == null) {
                    neighbour.sum = neighbour.cost + node.sum!!
                    visiting.offer(neighbour)
                }
            }
        }
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

    val test2 = "199111\n" +
            "199191\n" +
            "111191\n" +
            "999991"

    val input = File("src", "Day15.txt").readText()

    part1(testInput) test Pair(40L, "test 1 part 1")
    part1(input) test Pair(673L, "part 1")

    part2(testInput) test Pair(315L, "test 2 part 2")
    part2(test2) test Pair(188L, "test 2 part 2")
    part2(input) test Pair(2893L, "part 2")  // to high: 2908


}