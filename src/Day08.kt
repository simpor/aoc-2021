import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String): Int {
        val digits = input.lines().map { it.split(" | ") }.map { it[1] }
        val list = digits.map { it.split(" ") }.map { it.map { it.length } }
        return list.sumOf { it.count { it == 2 || it == 4 || it == 3 || it == 7 } }
    }

    fun String.alphabetized() = String(toCharArray().apply { sort() })

    fun numberMatcher(encoded: List<String>): Map<String, Int> {
        val numList = encoded.map { it.alphabetized() }
        val mapped = mutableMapOf<Int, String>()
        mapped[1] = numList.single { it.length == 2 }
        mapped[4] = numList.single { it.length == 4 }
        mapped[7] = numList.single { it.length == 3 }
        mapped[8] = numList.single { it.length == 7 }

        val digit069 = numList.filter { it.length == 6 }
        val digit235 = numList.filter { it.length == 5 }

        val diff4and1 = mapped[4]!!.toList().filter { !mapped[1]!!.contains(it) }

        mapped[5] = digit235.single { it.toList().containsAll(diff4and1) }
        mapped[3] = digit235.single { it.toList().containsAll(mapped[1]!!.toList()) }
        mapped[2] = digit235.single { it != mapped[5] && it != mapped[3] }

        mapped[6] = digit069.single { !it.toList().containsAll(mapped[1]!!.toList()) }
        mapped[9] = digit069.single { it != mapped[6] && it.toList().containsAll(diff4and1) }
        mapped[0] = digit069.single { it != mapped[9] && it != mapped[6] }

        return mapped.map { it }.associate { e -> e.value to e.key }
    }

    fun part2(input: String): Int {
        data class Decoder(val input: List<String>, val output: List<String>)

        val list = input.lines().map { it.split(" | ") }.map { Decoder(it[0].split(" "), it[1].split(" ")) }

        val sum = list.sumOf { decoder ->
            val matcher = numberMatcher(decoder.input)
            val nums = decoder.output.map { matcher[it.alphabetized()] }
            val string = nums.joinToString("") { it.toString() }
            string.toInt()
        }
        return sum
    }


    val testInput = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\n" +
            "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\n" +
            "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\n" +
            "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\n" +
            "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\n" +
            "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\n" +
            "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\n" +
            "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\n" +
            "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\n" +
            "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"

    val input = File("src", "Day08.txt").readText()

    part1(testInput) test Pair(26, "test 1 part 1")
    part1(input) test Pair(539, "part 1")

    part2("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf") test Pair(
        5353,
        "test 2 part 2"
    )
    part2(testInput) test Pair(61229, "test 2 part 2")
    part2(input) test Pair(1084606, "part 2")


}