import AoCUtils.test
import java.io.File

fun main() {
    data class Order(val order: String, val x: Int)

    fun part1(input: String): Int {

        var nums = input.lines().get(0).length

        var a = "";
        var b = "";

        for (i in 0 until nums) {
            var count = 0
            for (line in input.lines()) {
                if (line[i] == '1') count++;
            }
            val other = input.lines().size - count
            if (count > other) {
                a += "1"
                b += "0"
            } else {
                a += "0"
                b += "1"
            }
        }

        var gamma = a.toInt(2)
        var epsilon = b.toInt(2)

        return gamma * epsilon

    }

    fun part2(input: String): Int {
        var nums = input.lines().get(0).length

        var lines = input.lines()
        for (i in 0 until nums) {
            var count = 0
            for (line in lines) {
                if (line[i] == '1') count++;
            }
            val other = lines.size - count
            lines = if (count >= other) {
                lines.filter { l -> l[i] == '1' }
            } else {
                lines.filter { l -> l[i] == '0' }
            }
            if (lines.size == 1) break
        }
        var first = lines.get(0).toInt(2)


        lines = input.lines()
        for (i in 0 until nums) {
            var count = 0
            for (line in lines) {
                if (line[i] == '0') count++;
            }
            val other = lines.size - count
            lines = if (count <=  other) {
                lines.filter { l -> l[i] == '0' }
            } else {
                lines.filter { l -> l[i] == '1' }
            }
            if (lines.size == 1) break
        }
        var second = lines.get(0).toInt(2)
        return first * second
    }


    val testInput = "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010"

    val input = File("src", "Day03.txt").readText()

    part1(testInput) test Pair(198, "test 1 part 1")
    part1(input) test Pair(4147524, "part 1")

    part2(testInput) test Pair(230, "test 2 part 2")
    part2(input) test Pair(3570354, "part 2")


}



