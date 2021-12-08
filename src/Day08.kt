import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String): Int {
        val digits = input.lines().map { it.split(" | ") }.map { it[1] }
        val list = digits.map { it.split(" ") }.map { it.map { it.length } }
        return list.map { it.filter { it == 2 || it == 4 || it == 3 || it == 7 }.count() }.sum()
    }

    fun numberMatcher(encoded: List<String>): Map<String, Int> {
        val one = encoded.filter { it.length == 2 }.single()
        val four = encoded.filter { it.length == 4 }.single()
        val seven = encoded.filter { it.length == 3 }.single()
        val eight = encoded.filter { it.length == 7 }.single()

        val digit069 = encoded.filter { it.length == 6 }
        val digit235 = encoded.filter { it.length == 5 }

        val diff4and1 = four.toList().filter { !one.contains(it) }

        val five = digit235.filter { it.toList().containsAll(diff4and1) }.single()
        val three = digit235.filter { it.toList().containsAll(one.toList()) }.single()
        val two = digit235.filter { it != five && it != three }.single()

        val six = digit069.filter { !it.toList().containsAll(one.toList()) }.single()
        val nine = digit069.filter { it != six && it.toList().containsAll(diff4and1) }.single()
        val zero = digit069.filter { it != nine && it != six }.single()

        return mutableMapOf(
            Pair(zero, 0),
            Pair(one, 1),
            Pair(two, 2),
            Pair(three, 3),
            Pair(four, 4),
            Pair(five, 5),
            Pair(six, 6),
            Pair(seven, 7),
            Pair(eight, 8),
            Pair(nine, 9),
        );

    }

    fun part2(input: String): Int {
        data class Decoder(val input: List<String>, val output: List<String>)

        val list = input.lines().map { it.split(" | ") }.map { Decoder(it[0].split(" "), it[1].split(" ")) }

        val sum = list.map { decoder ->
            val matcher = numberMatcher(decoder.input).map { entry -> Pair(entry.key.toList().sorted().toString(), entry.value) }.toMap()
            val nums = decoder.output.map { matcher[it.toList().sorted().toString()] }
            val string = nums.map { it.toString() }.joinToString { it }
            if (string.contains("null")) println(decoder)
            string.replace(", ", "").toLong()
        }.sum()
        return sum.toInt()
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

    part2("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf") test Pair(5353, "test 2 part 2")
    part2(testInput) test Pair(61229, "test 2 part 2")
    part2(input) test Pair(1084606, "part 2")


}