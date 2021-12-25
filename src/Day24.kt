import AoCUtils.test
import java.io.File

enum class InstructionType { inp, add, mul, div, mod, eql }

fun main() {

    data class Instruction(val type: InstructionType, val variable: Char, val value: String)

    fun runComputer(
        instructions: List<Instruction>,
        number: String,
        testCase: Boolean = false
    ): MutableMap<Char, Long> {
        val variables = mutableMapOf<Char, Long>('w' to 0, 'x' to 0, 'y' to 0, 'z' to 0)
        var currentNum = 0
        instructions.forEach { instruction ->
            val value = if (instruction.type != InstructionType.inp) {
                if (instruction.value.contains("[0-9]+".toRegex())) {
                    instruction.value.toLong()
                } else {
                    variables[instruction.value[0]]!!
                }
            } else -1000

            val currentValue = variables[instruction.variable]!!
            variables[instruction.variable] = when (instruction.type) {
                InstructionType.inp -> {
                    val l = number.drop(currentNum).take(1).toLong()
                    currentNum++
                    if (testCase)
                        number.toLong()
                    else l
//                    if (currentNum <= 3)
                    //                   println(number + " -> " + instruction.variable + " -> " + l + " at pos " + currentNum + " -> " + variables)
                }
                InstructionType.add -> currentValue + value
                InstructionType.mul -> currentValue * value
                InstructionType.div -> currentValue / value
                InstructionType.mod -> currentValue % value
                InstructionType.eql -> if (currentValue == value) 1 else 0
            }
        }
        return variables
    }

    fun parse(input: String): List<Instruction> {
        val instructions = input.lines().map { row ->
            val i = row.take(3)
            val v1 = row.drop(4).take(1)[0]
            val v2 = row.drop(6)
            when (i) {
                "inp" -> Instruction(InstructionType.inp, v1, v2)
                "add" -> Instruction(InstructionType.add, v1, v2)
                "mul" -> Instruction(InstructionType.mul, v1, v2)
                "div" -> Instruction(InstructionType.div, v1, v2)
                "mod" -> Instruction(InstructionType.mod, v1, v2)
                "eql" -> Instruction(InstructionType.eql, v1, v2)
                else -> TODO()
            }
        }
        return instructions
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val instructions = parse(input)


//        instructions.forEach { println(it) }

        val start = "12345678912345".toLong()
        val end = "621121111111111".toLong()
        var current = start
        while (current < end) {
            val num = current.toString()
            if (num.contains("0")) {
                current++
                break
//                continue
            }
            val result = runComputer(instructions, num)
            if (result['z'] == 0L) {
                println("$num = $result")
            }
            current = ((num.take(1).toInt() + 1).toString() + num.drop(1)).toLong()
        }





        return 0
    }

    fun part2(input: String, debug: Boolean = false): Long {


        return 0

    }


    val testInput = "inp w\n" +
            "add z w\n" +
            "mod z 2\n" +
            "div w 2\n" +
            "add y w\n" +
            "mod y 2\n" +
            "div w 2\n" +
            "add x w\n" +
            "mod x 2\n" +
            "div w 2\n" +
            "mod w 2"

    val input = File("src", "Day24.txt").readText()



    for (test in 1..15) {
        runComputer(parse(testInput), "$test", true).let {
            println(it)
            "${it['w']}${it['x']}${it['y']}${it['z']}".toInt(2)
        } test Pair(test, "test $test")
    }


//    part1(testInput) test Pair(0L, "test 1 part 1")
//    part1(input) test Pair(0L, "part 1")
//
//    part2(testInput) test Pair(0L, "test 2 part 2")
//    part2(input) test Pair(0L, "part 2")


}