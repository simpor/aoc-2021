package year2021

import AoCUtils
import AoCUtils.test

class Day16 {

    fun getNumber(input: String): Long {
        return input.windowed(5, 5, false).map { it.substring(1) }.joinToString(separator = "").toLong(radix = 2)
    }

    data class Operator(
        val type: Int,
        val version: Int,
        var lengthType: Int = -1,
        var lengthTypeValue: Int = -1,
        val values: MutableList<Long> = mutableListOf()
    )

    private fun parseString(input: String, operators: MutableList<Operator>): String {
        var binaryString = input

        while (binaryString.isNotEmpty()) {
            if (binaryString.dropWhile { it == '0' }.isEmpty()) break

            val packetVersion = binaryString.take(3).toInt(radix = 2)
            val packetId = binaryString.drop(3).take(3).toInt(radix = 2)
            val operator = Operator(packetId, packetVersion)
            operators.add(operator)
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
                val element = getNumber(toCheck)
                operator.values.add(element)
                binaryString = binaryString.drop(end)
            } else {
                val lengthType = binaryString.take(1)
                operator.lengthType = lengthType.toInt()
                binaryString = binaryString.drop(1)
                if (lengthType == "1") {
                    val numberOfSubPackages = binaryString.take(11).toInt(radix = 2)
                    operator.lengthTypeValue = numberOfSubPackages
                    binaryString = binaryString.drop(11)
                    val newBinary = parseString(binaryString, operators)
                    binaryString = newBinary
                } else {
                    val subPackageLength = binaryString.take(15).toInt(radix = 2)
                    binaryString = binaryString.drop(15)
                    operator.lengthTypeValue = subPackageLength
                    parseString(binaryString.take(subPackageLength), operators)
                    binaryString = binaryString.drop(subPackageLength)
                }
            }

        }
        return binaryString;
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val binaryString = input.toList().map { it.toString().toLong(radix = 16).toString(2) }
            .joinToString(separator = "") { if (it.length == 1) "000$it" else if (it.length == 2) "00$it" else if (it.length == 3) "0$it" else it }

        println("BinaryString: $binaryString")
        val operators = mutableListOf<Operator>()

        parseString(binaryString, operators)
        println("operators $operators and sum: ${operators.size}")
        return operators.sumOf { it.version }.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val binaryString = input.toList().map { it.toString().toLong(radix = 16).toString(2) }
            .joinToString(separator = "") { if (it.length == 1) "000$it" else if (it.length == 2) "00$it" else if (it.length == 3) "0$it" else it }

        println("BinaryString: $binaryString")

        var operators = mutableListOf<Operator>()
        parseString(binaryString, operators)

        println("operators")
        operators.forEach { println(it) }

        var lastOperator = operators.last()
        while (operators.size > 0) {
            lastOperator = operators.last()
            val intNumbers = lastOperator.values
            val newNum = when (lastOperator.type) {
                4 -> intNumbers
                else -> listOf(when (lastOperator.type) {
                    0 -> intNumbers.sum()
                    1 -> intNumbers.reduceIndexed { index, acc, num -> if (index == 0) num else num * acc }
                    2 -> intNumbers.minOrNull()!!
                    3 -> intNumbers.maxOrNull()!!
                    5 -> if (intNumbers[0] > intNumbers[1]) 1 else 0
                    6 -> if (intNumbers[0] < intNumbers[1]) 1 else 0
                    7 -> if (intNumbers[0] == intNumbers[1]) 1 else 0
                    else -> TODO()
                })
            }
            if (operators.size > 1) {
                operators = operators.dropLast(1).toMutableList()
                operators.last().values.addAll(newNum)
            } else {
                operators = operators.dropLast(1).toMutableList()
                lastOperator.values.clear()
                lastOperator.values.addAll(newNum)
            }

        }

        return lastOperator.values.first()
    }

    fun main() {
        val input = AoCUtils.readText("year2021/Day16.txt")
        part1(input) test Pair(957L, "part 1")
        part2(input) test Pair(0L, "part 2")
    }
}

