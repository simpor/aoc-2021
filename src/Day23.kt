import AoCUtils.test

enum class Day23(val cost: Int) { Empty(0), Wall(0), A(1), B(10), C(100), D(1000) }

fun main() {

    val goal = "#############\n" +
            "#...........#\n" +
            "###A#B#C#D###\n" +
            "  #A#B#C#D#\n" +
            "  #########"


    fun parseToMap(input: String) = input.lines().mapIndexed { y, row ->
        row.mapIndexed { x, c ->
            when (c) {
                '#' -> Point(x, y) to Day23.Wall
                'A' -> Point(x, y) to Day23.A
                'B' -> Point(x, y) to Day23.B
                'C' -> Point(x, y) to Day23.C
                'D' -> Point(x, y) to Day23.D
                '.' -> Point(x, y) to Day23.Empty
                else -> TODO()
            }
        }
    }.flatten().toMap()

    fun part1(input: String, debug: Boolean = false): Long {
        val goalMap = parseToMap(goal)
        val startingMap = parseToMap(input)

        val goalA = goalMap.filter { entry -> entry.value in listOf(Day23.A, Day23.B, Day23.C, Day23.D) }
            .map { entry -> entry.value to entry.key }.groupBy { it.second }


        return 0
    }

    fun part2(input: String, debug: Boolean = false): Long {


        return 0

    }


    val testInput = "#############\n" +
            "#...........#\n" +
            "###B#C#B#D###\n" +
            "  #A#D#C#A#\n" +
            "  #########"

    val input = "#############\n" +
            "#...........#\n" +
            "###D#C#A#B###\n" +
            "  #D#C#B#A#\n" +
            "  #########"

    // not 20278, 20238, 20148
    part1(testInput) test Pair(0L, "test 1 part 1")
    part1(input) test Pair(0L, "part 1")

    part2(testInput) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")


}