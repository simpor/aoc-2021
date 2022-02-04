package year2021

import AoCUtils
import AoCUtils.test

class Day18 {

    data class Node(val parent: Node? = null, val left: Node? = null, val right: Node? = null, val value: Long = -1) {
        fun depth(level: Int = 0): Int = if (parent == null) level else depth(level + 1)

        fun magnitude(): Long {
            val leftValue = if (left!!.value != -1L) left.value else left.magnitude()
            val rightValue = if (right!!.value != -1L) right.value else right.magnitude()

            return leftValue * 3 + rightValue * 2
        }

        fun asString(): String = if (value != -1L) "$value" else "[${left!!.asString()},${right!!.asString()}]"

    }

    data class Model(val input: String, val left: Node = Node(), val right: Node = Node()) {
        fun asString(): String = "[${left.asString()},${right.asString()}]"
    }

    fun parse(input: String): Model {
        val model = Model(input)

        fun internalParse(start: Int, end: Int): Node {
            if (input[start].isDigit()) return Node(value = input[start].toString().toLong())
            var count = 0
            var index = start + 1
            while (index < end) {
                when (input[index]) {
                    ',' -> {
                        if (count == 0) {
                            return Node(left = internalParse(start + 1, index), right = internalParse(index + 1, end))
                        }
                    }
                    '[' -> count++
                    ']' -> count--
                }
                index++
            }
            throw RuntimeException()
        }


        val start = internalParse(0, input.length)
        println(start)

        return Model(input, start.left!!, start.right!!)
    }

    fun magnitude(model: Model): Long = model.left.magnitude() * 3 + model.right.magnitude() * 2

    fun explode(model: Model): Model {
        TODO("Not yet implemented")
    }

    fun reduce(model: Model): Model {
        TODO("Not yet implemented")
    }

    fun solve(model: Model): Long {
        TODO("Not yet implemented")
    }


}

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {

        return 0
    }

    fun part2(input: String, debug: Boolean = false): Long {


        return 0

    }


    val testInput = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]\n" +
            "[[[5,[2,8]],4],[5,[[9,9],0]]]\n" +
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]\n" +
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]\n" +
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]\n" +
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]\n" +
            "[[[[5,4],[7,7]],8],[[8,3],8]]\n" +
            "[[9,3],[[9,9],[6,[4,9]]]]\n" +
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]\n" +
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"

    val input = AoCUtils.readText("year2021/Day18.txt")

    part1(testInput) test Pair(4140L, "test 1 part 1")
    part1(input) test Pair(0L, "part 1")

    part2(testInput) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")


}