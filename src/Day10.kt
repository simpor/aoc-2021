import AoCUtils.test
import java.io.File

enum class LineStatus { COMPLETE, CORRUPT, INCOMPLETE }

fun main() {

    fun testValid(line: String): LineStatus {
        val parentheses = mutableListOf(0, 0, 0, 0)

        line.toList().forEach { p ->
            when (p) {
                '(' -> parentheses[0]++
                ')' -> parentheses[0]--
                '[' -> parentheses[1]++
                ']' -> parentheses[1]--
                '{' -> parentheses[2]++
                '}' -> parentheses[2]--
                '<' -> parentheses[3]++
                '>' -> parentheses[3]--
                else -> throw RuntimeException("Invalid char: $p")
            }
            if (parentheses.any { it < 0 }) return LineStatus.CORRUPT
        }
        if (parentheses.any { it < 0 }) return LineStatus.INCOMPLETE
        return LineStatus.COMPLETE
    }

    fun part1(input: String): Int {
        val points = mapOf(
            '"' to 0,
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )

        return input.lines().filter { testValid(it) == LineStatus.CORRUPT }
            .map { line ->
                val parentheses = mutableListOf(0, 0, 0, 0)

                line.toList().forEach { p ->
                    when (p) {
                        '(' -> parentheses[0]++
                        ')' -> parentheses[0]--
                        '[' -> parentheses[1]++
                        ']' -> parentheses[1]--
                        '{' -> parentheses[2]++
                        '}' -> parentheses[2]--
                        '<' -> parentheses[3]++
                        '>' -> parentheses[3]--
                        else -> throw RuntimeException("Invalid char: $p")
                    }
                    if (parentheses.any { it < 0 }) return@map p
                }
                'x'
            }.sumOf { c -> points[c]!! }

    }

    fun part2(input: String): Int {


        return 0

    }

    val testValid = "([])\n" +
            "{()()()}\n" +
            "<([{}])>\n" +
            "[<>({}){}[([])<>]]\n" +
            "(((((((((())))))))))\n"

    testValid.lines().forEach { println(it + " -> " + testValid(it)) }

    val testCorrupted = "(]\n" +
            "{()()()>\n" +
            "(((()))}\n" +
            "<([]){()}[{}])\n"
    testCorrupted.lines().forEach { println(it + " -> " + testValid(it)) }

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

    println("[[<[([]))<([[{}[[()]]] -> " + testValid("[[<[([]))<([[{}[[()]]]"))

    part1("{([(<{}[<>[]}>{[]{[(<()>") test Pair(1197, "test 1 part 1")
    part1("[[<[([]))<([[{}[[()]]]") test Pair(3, "test 1 part 1")
    part1("[{[{({}]{}}([{[{{{}}([]") test Pair(57, "test 1 part 1")
    part1("[<(<(<(<{}))><([]([]()") test Pair(3, "test 1 part 1")
    part1("<{([([[(<>()){}]>(<<{{") test Pair(25137, "test 1 part 1")
//    part1(testInput) test Pair(26397, "test 1 part 1")
//    part1(input) test Pair(0, "part 1")

    part2(testInput) test Pair(0, "test 2 part 2")
    part2(input) test Pair(0, "part 2")


}