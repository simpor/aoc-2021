import AoCUtils.test
import java.io.File

enum class ParseState { INIT }

fun main() {

    fun getNumber(input: String): Int {
        return input.windowed(5, 5, false).map { it.substring(1) }.joinToString(separator = "").toInt(radix = 2)
    }

    fun parseString(input: String): List<String> {
        var binaryString = input
        println("parsing: " + input)
        val innerResult = mutableListOf<String>()
        while (binaryString.isNotEmpty()) {
            if (binaryString.dropWhile { it == '0' }.isEmpty()) break

            val packetVersion = binaryString.take(3).toInt(radix = 2)
            val packetId = binaryString.drop(3).take(3).toInt(radix = 2)
            binaryString = binaryString.drop(6)

            if (packetId == 4) {

                // locate end of it
                var end = -1
                val windowed = binaryString.windowed(5, 5)
                windowed.forEachIndexed { index, s ->
                    if (s.startsWith("0")) {
                        if (end == -1) end = index
                    }
                }
                end = (end * 5) + 5

                val toCheck = binaryString.take(end)
                val element = getNumber(toCheck).toString()
                innerResult.add(element)
                binaryString = binaryString.drop(end)


            } else {
                val lengthType = binaryString.take(1)
                binaryString = binaryString.drop(1)

                if (lengthType == "1") {
                    val numberOfSubPackages = binaryString.take(11).toInt(radix = 2)
                    binaryString = binaryString.drop(11)
                    for (n in 0 until numberOfSubPackages) {
                        val element = binaryString.take(11)
                        innerResult.addAll(parseString(element))
                        binaryString = binaryString.drop(11)
                    }
                } else {
                    val subPackageLength = binaryString.take(15).toInt(radix = 2)
                    binaryString = binaryString.drop(15)

                    val list = parseString(binaryString.take(subPackageLength))
                    innerResult.addAll(list)
                    binaryString = binaryString.drop(subPackageLength)
                }
            }

        }
        return innerResult;
    }

    fun part1(input: String, debug: Boolean = false): Long {
        var binaryString = input.toList().map { it.toString().toInt(radix = 16).toString(2) }
            .map { if (it.length == 1) "000$it" else if (it.length == 2) "00$it" else if (it.length == 3) "0$it" else it }
            .joinToString(separator = "")

        println("BinaryString: $binaryString")

        val result = parseString(binaryString)
        println(result)
        return 0
    }

    fun part2(input: String, debug: Boolean = false): Long {


        return 0

    }


    val testInput = ""

    val input = File("src", "Day16.txt").readText()

//    part1("D2FE28") test Pair(16L, "test 1 part 1")
//    part1("38006F45291200") test Pair(16L, "test 1 part 1")
    part1("EE00D40C823060") test Pair(16L, "test 1 part 1")
    part1("8A004A801A8002F478") test Pair(16L, "test 1 part 1")
    part1("620080001611562C8802118E34") test Pair(12L, "test 1 part 1")
    part1("C0015000016115A2E0802F182340") test Pair(23L, "test 1 part 1")
    part1("A0016C880162017C3686B18A3D4780") test Pair(31L, "test 1 part 1")
    part1(input) test Pair(0L, "part 1")

    part2(testInput) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")


}