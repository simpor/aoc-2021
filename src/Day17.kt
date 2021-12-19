import AoCUtils.test

fun main() {
    data class Box(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {
        fun isInside(point: Point) = point.x in x1..x2 && point.y in y2..y1
    }

    data class SimulatorResult(var yMax: Int, var boxHit: Boolean, var startSpeed: Point)

    fun simulate(start: Point, speed: Point, box: Box): SimulatorResult {
        var currentPos = start
        var currentSpeed = speed
        val result = SimulatorResult(0, false, speed)
        while (true) {
            val newX = if (currentSpeed.x < 0) currentSpeed.x + 1 else if (currentSpeed.x > 0) currentSpeed.x - 1 else 0
            val newY = currentSpeed.y - 1
            currentPos = Point(currentPos.x + currentSpeed.x, currentPos.y + currentSpeed.y)
            currentSpeed = Point(newX, newY)
            if (result.yMax < currentPos.y) result.yMax = currentPos.y
            if (box.isInside(currentPos)) {
                result.boxHit = true
                break;
            }
            if (currentSpeed.x == 0 && currentPos.x < box.x1) break
            if (currentSpeed.y < box.y2) break;
        }

        return result
    }


    fun part1(input: String, debug: Boolean = false): Int {
        val split = input.replace("target area: ", "").split(", ")
        val first = split.first().replace("x=", "").split("..")
        val second = split.last().replace("y=", "").split("..")
        val box = Box(first[0].toInt(), first[1].toInt(), second[1].toInt(), second[0].toInt())

        var x1 = 0
        var step = 0
        var goal = (box.x1 - 1)
        while (x1 < goal) {
            x1 += step
            step++
        }

        val xSpeedRange = (step - 1)..box.x1
        val ySpeedRange = 1..100

        val results = mutableListOf<SimulatorResult>()
        for (x in xSpeedRange) {
            for (y in ySpeedRange) {
                val startSpeed = Point(x, y)
                val result = simulate(Point(0, 0), startSpeed, box)
                results.add(result)
            }
        }

        return results.filter { it.boxHit }.maxOf { it.yMax }
    }


    fun part2(input: String, debug: Boolean = false): Int {

        val split = input.replace("target area: ", "").split(", ")
        val first = split.first().replace("x=", "").split("..")
        val second = split.last().replace("y=", "").split("..")
        val box = Box(first[0].toInt(), first[1].toInt(), second[1].toInt(), second[0].toInt())

        var x1 = 0
        var step = 0
        var goal = (box.x1 - 1)
        while (x1 < goal) {
            x1 += step
            step++
        }

        val xSpeedRange = (step -1)..box.x2
        val ySpeedRange = box.y2..100

        val results = mutableListOf<SimulatorResult>()
        for (x in xSpeedRange) {
            for (y in ySpeedRange) {
                val startSpeed = Point(x, y)
                val result = simulate(Point(0, 0), startSpeed, box)
                results.add(result)
            }
        }

        val maxOf = results.filter { it.boxHit }.maxOf { it.yMax }
        return results.filter { it.boxHit }.size
    }


    val testInput = "target area: x=20..30, y=-10..-5"

    val input = "target area: x=201..230, y=-99..-65"

    part1(testInput) test Pair(45, "test 1 part 1")
    part1(input) test Pair(4851, "part 1")

    part2(testInput) test Pair(112, "test 2 part 2")
    part2(input) test Pair(1739, "part 2")


}