import AoCUtils.test
import java.io.File

enum class LineStatus { COMPLETE, CORRUPT, INCOMPLETE }

fun main() {

    fun testValid(line: String): Pair<LineStatus, String> {
        var toTest = line
        val test = """\(\)|\[]|\{}|<>""".toRegex()
        var lastLength = Int.MAX_VALUE
        while (toTest.length != lastLength) {
            lastLength = toTest.length
            toTest = toTest.replace(test, "")
        }

        if (toTest.isEmpty()) return Pair(LineStatus.COMPLETE, "")

        val removeLeading = toTest.replace("""[(\[{<]""".toRegex(), "")
        if (removeLeading.isEmpty()) return Pair(LineStatus.INCOMPLETE, toTest)

        return Pair(LineStatus.CORRUPT, removeLeading);
    }

    fun part1(input: String): Int {
        val points = mapOf(
            '"' to 0,
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )

        return input.lines()
            .map { testValid(line = it) }
            .filter { it.first == LineStatus.CORRUPT }
            .map { it.second.first() }
            .sumOf { c -> points[c]!! }
    }

    fun part2(input: String): Long {
        val points = mapOf(
            '"' to 0,
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4
        )

        val list = input.lines()
            .map { testValid(line = it) }
            .filter { it.first == LineStatus.INCOMPLETE }
            .map { it.second.reversed() }
            .map { it.map { points[it]!!.toLong() }.reduce { acc, num -> acc * 5 + num } }

        return if (list.size == 1) list[0]
        else
            list.sorted()[list.size / 2]


    }

    val testInput = "[({(<(())[]>[[{[]{<()<>>\n" +
            "[(()[<>])]({[<{<<[]>>(\n" +
            "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "(((({<>}<{<{<>}{[]{[]{}\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "{<[[]]>}<{[{[{[]{()[[[]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{\n" +
            "<{([{{}}[<[[[<>{}]]]>[]]\n"

    val input = File("src", "Day10.txt").readText()

    part1("{([(<{}[<>[]}>{[]{[(<()>") test Pair(1197, "test 1 part 1")
    part1("[[<[([]))<([[{}[[()]]]") test Pair(3, "test 1 part 1")
    part1("[{[{({}]{}}([{[{{{}}([]") test Pair(57, "test 1 part 1")
    part1("[<(<(<(<{}))><([]([]()") test Pair(3, "test 1 part 1")
    part1("<{([([[(<>()){}]>(<<{{") test Pair(25137, "test 1 part 1")

    part1(testInput) test Pair(26397, "test 1 part 1")
    part1(input) test Pair(294195, "part 1")

    part2("[({(<(())[]>[[{[]{<()<>>") test Pair(288957, "test 2 part 2")
    part2("[(()[<>])]({[<{<<[]>>(") test Pair(5566, "test 2 part 2")
    part2("(((({<>}<{<{<>}{[]{[]{}") test Pair(1480781, "test 2 part 2")
    part2("{<[[]]>}<{[{[{[]{()[[[]") test Pair(995444, "test 2 part 2")
    part2("<{([{{}}[<[[[<>{}]]]>[]]") test Pair(294, "test 2 part 2")
    part2(testInput) test Pair(288957, "test 2 part 2")
    part2(input) test Pair(3490802734L, "part 2")


}