package year2021

import AoCUtils
import AoCUtils.test

class Day16 {

    private fun getNumber(input: String): Long {
        return input.windowed(5, 5, false).joinToString(separator = "") { it.drop(1) }.toLong(radix = 2)
    }

    private data class Operator(
        val typeId: Int,
        val version: Int,
        var operators: List<Operator> = listOf(),
        val values: MutableList<Long> = mutableListOf(),
        var versions: List<Int> = listOf(version),
        var lengthType: Int = -1,
        var lengthTypeValue: Int = -1,
        var length: Int = -1,
        var binaryString: String = "",
    )

    private fun parseString(input: String): Pair<String, List<Operator>> {
        var binaryString = input
        val operators = mutableListOf<Operator>()
        while (binaryString.isNotEmpty()) {
            if (binaryString.dropWhile { it == '0' }.isEmpty()) break

            val header = binaryString.take(6)
            binaryString = binaryString.drop(6)

            val packetVersion = header.take(3).toInt(radix = 2)
            val packetId = header.drop(3).take(3).toInt(radix = 2)
            val operator = Operator(typeId = packetId, version = packetVersion)
            operators.add(operator)

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
                binaryString = binaryString.drop(end)

                val element = getNumber(toCheck)
                operator.values.add(element)
                operator.length = end + 6
                operator.binaryString = header + toCheck
                return Pair(binaryString, operators)
            } else {
                val lengthType = binaryString.take(1)
                operator.lengthType = lengthType.toInt()
                binaryString = binaryString.drop(1)
                if (lengthType == "1") {
                    val numberOfSubPackages = binaryString.take(11).toInt(radix = 2)
                    operator.lengthTypeValue = numberOfSubPackages
                    binaryString = binaryString.drop(11)
                    val subOperators = (1..numberOfSubPackages).map { _ ->
                        val returnVal = parseString(binaryString)
                        binaryString = returnVal.first
                        returnVal.second
                    }.flatten()

                    val returnVal = parseString(binaryString)
                    operator.operators = operator.operators + subOperators
                    operator.versions = operator.versions + subOperators.map { it.versions }.flatten()
                    operator.binaryString = header + binaryString
                    operator.length = binaryString.length - returnVal.first.length
                    binaryString = returnVal.first
                } else {
                    val subPackageLength = binaryString.take(15).toInt(radix = 2)
                    binaryString = binaryString.drop(15)
                    operator.lengthTypeValue = subPackageLength
                    var toParse = binaryString.take(subPackageLength)
                    operator.binaryString = header + toParse

                    while (toParse.isNotEmpty()) {
                        val returnVal = parseString(toParse)
                        operator.versions = operator.versions + returnVal.second.map { it.versions }.flatten()
                        operator.operators = operator.operators + returnVal.second
                        toParse = returnVal.first
                    }

                    binaryString = binaryString.drop(subPackageLength)
                }
                val numbers = operator.operators.map { it.values }.flatten()
                val newNum = when (operator.typeId) {
                    0 -> numbers.sum()
                    1 -> numbers.fold(1L) { acc, next -> next * acc }
                    2 -> numbers.minOrNull()!!
                    3 -> numbers.maxOrNull()!!
                    5 -> if (numbers[0] > numbers[1]) 1 else 0
                    6 -> if (numbers[0] < numbers[1]) 1 else 0
                    7 -> if (numbers[0] == numbers[1]) 1 else 0
                    else -> TODO()
                }
                operator.values.add(newNum)
                return Pair(binaryString, operators)

            }

        }
        return Pair(binaryString, operators)
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val binaryString = input.toList().map { it.toString().toLong(radix = 16).toString(2) }
            .joinToString(separator = "") { if (it.length == 1) "000$it" else if (it.length == 2) "00$it" else if (it.length == 3) "0$it" else it }

        println("BinaryString: $binaryString")
        val (_, operators) = parseString(binaryString)
        println("operators $operators and sum: ${operators.size}")
        return operators.first().versions.sum().toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val binaryString = input.toList().map { it.toString().toLong(radix = 16).toString(2) }
            .joinToString(separator = "") { if (it.length == 1) "000$it" else if (it.length == 2) "00$it" else if (it.length == 3) "0$it" else it }

        println("BinaryString: $binaryString")


        var (_, operators) = parseString(binaryString)

        println("operators")
        operators.forEach { println("type: ${it.typeId}, version: ${it.version}, values: ${it.values}") }
        return operators.first().values.first()
    }

    fun main() {
        val input = AoCUtils.readText("year2021/Day16.txt")
        part1(input) test Pair(957L, "part 1")
        part2(input) test Pair(0L, "part 2")
    }
}

