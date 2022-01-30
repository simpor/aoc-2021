package year2021

import AoCUtils
import AoCUtils.test

class Day18 {

    data class Node(val parent: Node?, val left: Node?, val right: Node?, val value: Long = -1) {
        fun depth(level: Int = 0): Int = if (parent == null) level else depth(level + 1)

//        fun depth() {
//            var depth = 1
//            var prev = parent
//            while (prev != null) {
//                prev = prev.parent
//                depth++
//            }
//        }

        fun asString(): String = if (value == -1L) "$value" else "[$left, $right]"

    }

    data class Model(val input: String, val left: Node, val right: Node) {
        fun asString(): String = "[$left, $right]"
    }

    fun parse(input: String): Model {


        return Model(input)
    }

    fun magnitude(model: Model): Long {
        TODO("Not yet implemented")
    }

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