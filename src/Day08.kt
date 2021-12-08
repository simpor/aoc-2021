import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String): Int {
        val digits = input.lines().map { it.split(" | ") }.map { it[1] }
        val list = digits.map { it.split(" ") }.map { it.map { it.length } }
        return list.map { it.filter { it == 2 || it == 4 || it == 3 || it == 7 }.count() }.sum()
    }


    fun numberMatcher(encoded: List<String>): Map<String, Int> {
        val numbers = mutableMapOf<String, Int>()

        val one = encoded.filter { it.length == 2 }[0]
        val four = encoded.filter { it.length == 4 }[0]
        val seven = encoded.filter { it.length == 3 }[0]
        val eight = encoded.filter { it.length == 7 }[0]

        var sixList = encoded.filter { it.length == 6 }
        var zeroList = encoded.filter { it.length == 6 }
        var nineList = encoded.filter { it.length == 6 }

        var threeList = encoded.filter { it.length == 5 }
        var twoList = encoded.filter { it.length == 5 }
        var fiveList = encoded.filter { it.length == 5 }


        data class Digit(val digit: Int, val pattern: String, val scrambled: String = "")

        val digits = mutableMapOf<Int, Digit>();

        digits[0] = Digit(0, "qcfgeb")
        digits[1] = Digit(1, "cf", one)
        digits[2] = Digit(2, "acdeg")
        digits[3] = Digit(3, "acdfg")
        digits[4] = Digit(4, "bcdf", four)
        digits[5] = Digit(5, "abdfg")
        digits[6] = Digit(6, "bdefg")
        digits[7] = Digit(7, "acf", seven)
        digits[8] = Digit(8, "abcdefg", eight)


        val mapping = mutableMapOf<String, List<String>>()
        mapping["a"] = listOf("a", "b", "c", "d", "e", "f", "g")
        mapping["b"] = listOf("a", "b", "c", "d", "e", "f", "g")
        mapping["c"] = listOf("a", "b", "c", "d", "e", "f", "g")
        mapping["d"] = listOf("a", "b", "c", "d", "e", "f", "g")
        mapping["e"] = listOf("a", "b", "c", "d", "e", "f", "g")
        mapping["f"] = listOf("a", "b", "c", "d", "e", "f", "g")
        mapping["g"] = listOf("a", "b", "c", "d", "e", "f", "g")

        // start reducing
        // one...
        mapping["c"] = mapping["c"]!!.filter { one.toList().map { it.toString() }.contains(it) }
        mapping["f"] = mapping["f"]!!.filter { one.toList().map { it.toString() }.contains(it) }

        mapping["a"] = mapping["a"]!!.filter { !one.toList().map { it.toString() }.contains(it) }
        mapping["b"] = mapping["a"]!!.filter { !one.toList().map { it.toString() }.contains(it) }
        mapping["d"] = mapping["a"]!!.filter { !one.toList().map { it.toString() }.contains(it) }
        mapping["e"] = mapping["a"]!!.filter { !one.toList().map { it.toString() }.contains(it) }
        mapping["g"] = mapping["a"]!!.filter { !one.toList().map { it.toString() }.contains(it) }


        // four
        mapping["b"] = mapping["b"]!!.filter { four.toList().map { it.toString() }.contains(it) }
        mapping["c"] = mapping["c"]!!.filter { four.toList().map { it.toString() }.contains(it) }
        mapping["d"] = mapping["d"]!!.filter { four.toList().map { it.toString() }.contains(it) }
        mapping["f"] = mapping["f"]!!.filter { four.toList().map { it.toString() }.contains(it) }

        mapping["a"] = mapping["a"]!!.filter { !four.toList().map { it.toString() }.contains(it) }
        mapping["e"] = mapping["e"]!!.filter { !four.toList().map { it.toString() }.contains(it) }
        mapping["g"] = mapping["g"]!!.filter { !four.toList().map { it.toString() }.contains(it) }

        // seven
        mapping["a"] = mapping["a"]!!.filter { seven.toList().map { it.toString() }.contains(it) }
        mapping["c"] = mapping["c"]!!.filter { seven.toList().map { it.toString() }.contains(it) }
        mapping["f"] = mapping["f"]!!.filter { seven.toList().map { it.toString() }.contains(it) }

        mapping["b"] = mapping["b"]!!.filter { !seven.toList().map { it.toString() }.contains(it) }
        mapping["d"] = mapping["d"]!!.filter { !seven.toList().map { it.toString() }.contains(it) }
        mapping["e"] = mapping["e"]!!.filter { !seven.toList().map { it.toString() }.contains(it) }
        mapping["g"] = mapping["g"]!!.filter { !seven.toList().map { it.toString() }.contains(it) }

        // locate d
//        val d = zeroList.map { it.toList().map { it.toString() } }.flatten().groupBy { it }
        val g = listOf(zeroList, threeList).flatten().map { it.toList().map { it.toString() } }.flatten().groupBy { it }

        val gMapping = g.filter { it.value.size == 6 }.map { it.key }.filter { it != mapping["a"]!![0] }[0]

        mapping["g"] = listOf(gMapping)

        // remove g from rest
        mapping["a"] = mapping["a"]!!.filter { it != gMapping }
        mapping["b"] = mapping["b"]!!.filter { it != gMapping }
        mapping["c"] = mapping["c"]!!.filter { it != gMapping }
        mapping["d"] = mapping["d"]!!.filter { it != gMapping }
        mapping["e"] = mapping["e"]!!.filter { it != gMapping }
        mapping["f"] = mapping["f"]!!.filter { it != gMapping }

        // locate 9....
        val nine = nineList.filter { !it.contains(mapping["e"]!![0]) }[0]
        val two = fiveList.filter { it.contains(mapping["e"]!![0]) }[0]

        sixList = sixList.filter { it != nine }
        zeroList = zeroList.filter { it != nine }

        threeList = threeList.filter { it != two }
        fiveList = fiveList.filter { it != two }

        // locate 5
        val five = fiveList.filter { (it.contains(one[0]) || it.contains(one[1])) && !(it.contains(one[0]) && it.contains(one[1])) }[0]
        val three = threeList.filter { it != five }[0]

        val six = sixList.filter { (it.contains(one[0]) || it.contains(one[1])) && !(it.contains(one[0]) && it.contains(one[1])) }[0]
        val zero = zeroList.filter { it != six }[0]

//
//
//        println(encoded)
//        println(mapping)
//
//        println(mapping["a"]!! + " -> d mine a")
//        println(mapping["b"]!! + " -> e mine b")
//        println(mapping["c"]!! + " -> a mine c")
//        println(mapping["d"]!! + " -> f mine d")
//        println(mapping["e"]!! + " -> g mine e")
//        println(mapping["f"]!! + " -> b mine f")
//        println(mapping["g"]!! + " -> c mine g")
//


        return mutableMapOf(
            Pair(zero.toList().sorted().toString(), 0),
            Pair(one.toList().sorted().toString(), 1),
            Pair(two.toList().sorted().toString(), 2),
            Pair(three.toList().sorted().toString(), 3),
            Pair(four.toList().sorted().toString(), 4),
            Pair(five.toList().sorted().toString(), 5),
            Pair(six.toList().sorted().toString(), 6),
            Pair(seven.toList().sorted().toString(), 7),
            Pair(eight.toList().sorted().toString(), 8),
            Pair(nine.toList().sorted().toString(), 9),
        );

    }

    /**
     *
    aaaa
    b    c
    b    c
    ddddd
    e    f
    e    f
    gggg

     */

    fun part2(input: String): Int {
        data class Decoder(val input: List<String>, val output: List<String>)

        val list = input.lines().map { it.split(" | ") }.map { Decoder(it[0].split(" "), it[1].split(" ")) }


        val test = numberMatcher("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab".split(" "))

        println("cdfeb fcadb cdfeb cdbaf".split(" ").map { test[it.toList().sorted().toString()] })


        val sum = list.map { decoder ->
            val matcher = numberMatcher(decoder.input)
            val nums = decoder.output.map { matcher[it.toList().sorted().toString()] }
            val string = nums.map { it.toString() }.joinToString { it }
            string.replace(", ", "").toLong()
        }.sum()



//        numberMatcher(list.get(0).input)

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

    part2(testInput) test Pair(61229, "test 2 part 2")
    part2(input) test Pair(0, "part 2")


}