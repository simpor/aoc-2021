package year2021

import AoCUtils
import AoCUtils.test
import java.util.*

class Day16 {

    private fun getNumber(input: String): Long {
        return input.windowed(5, 5, false).joinToString(separator = "") { it.drop(1) }.toLong(radix = 2)
    }

    private data class Operator(
        val type: Int,
        val version: Int,
        var operators: List<Operator> = listOf(),
        val values: MutableList<Long> = mutableListOf(),
        var count: Int = 0,
        var lengthType: Int = -1,
        var lengthTypeValue: Int = -1,
        var length: Int = -1,
        var binaryString: String = "",
        val id: String = UUID.randomUUID().toString(),
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
            val operator = Operator(packetId, packetVersion)
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
                    operator.count = operator.count + returnVal.second.size
                    operator.binaryString = header + binaryString
                    operator.length = binaryString.length - returnVal.first.length
                    binaryString = returnVal.first
                } else {
                    val subPackageLength = binaryString.take(15).toInt(radix = 2)
                    binaryString = binaryString.drop(15)
                    operator.lengthTypeValue = subPackageLength
                    val toParse = binaryString.take(subPackageLength)
                    operator.binaryString = header + toParse
                    val returnVal = parseString(toParse)
                    operator.count = operator.count + returnVal.second.size
                    operator.operators = operator.operators + returnVal.second
                    binaryString = binaryString.drop(subPackageLength)
                }
                val numbers = operator.operators.map { it.values }.flatten()
                val newNum = when (operator.type) {
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
        return operators.sumOf { it.count }.toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val binaryString = input.toList().map { it.toString().toLong(radix = 16).toString(2) }
            .joinToString(separator = "") { if (it.length == 1) "000$it" else if (it.length == 2) "00$it" else if (it.length == 3) "0$it" else it }

        println("BinaryString: $binaryString")


        var (_, operators) = parseString(binaryString)

        println("operators")
        operators.forEach { println("type: ${it.type}, version: ${it.version}, values: ${it.values}") }

        // reduce it...
        var ops = operators.map { it.copy() }

        var reduceIndex = 0
        while (reduceIndex < ops.size) {
            val op = ops[reduceIndex]
            if (op.lengthType == 1) {
                val ops2 = ops.drop(reduceIndex + 1).take(op.lengthTypeValue)
                op.values.addAll(ops2.map { it.values }.flatten())
                ops = ops.filter { a -> !ops2.any { b -> a.id == b.id } }
            }
            reduceIndex++
        }

        println("ops")
        ops.forEach { println(it) }


//        var counter = 0
//        while (counter) {
//
//        }


        var lastOperator = operators.last()
        while (operators.isNotEmpty()) {
            lastOperator = operators.last()
            val intNumbers = lastOperator.values
            val newNum = when (lastOperator.type) {
                4 -> intNumbers
                else -> listOf(when (lastOperator.type) {
                    0 -> intNumbers.sum()
                    1 -> intNumbers.fold(1L) { acc, next -> next * acc }
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

        println("end")
        operators.forEach { println(it) }
        return lastOperator.values.first()
    }

    fun main() {
        val input = AoCUtils.readText("year2021/Day16.txt")
        part1(input) test Pair(957L, "part 1")
        part2(input) test Pair(0L, "part 2")
    }
}

