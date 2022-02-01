package year2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day18Test : FunSpec({

    listOf(
        "[1,2]",
        "[[1,2],3]",
        "[9,[8,7]]",
        "[[1,9],[8,5]]",
        "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]",
        "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]",
        "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]",
    ).forEach {
        test("Parse $it") {
            val day = Day18()
            val model = day.parse(it)
            model.asString() shouldBe it
        }
    }

    listOf(
        Pair("[[1,2],[[3,4],5]]", 143L),
        Pair("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", 1384L),
        Pair("[[[[1,1],[2,2]],[3,3]],[4,4]]", 445L),
        Pair("[[[[3,0],[5,3]],[4,4]],[5,5]]", 791L),
        Pair("[[[[5,0],[7,4]],[5,5]],[6,6]]", 1137L),
        Pair("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", 3488L),
    ).forEach {
        test("Magnitude $it.first should have a magnitude of ${it.second}") {
            val input = it.first
            val day = Day18()
            val model = day.parse(input)
            val magnitude = day.magnitude(model)
            magnitude shouldBe it.second
        }
    }

    listOf(
        Pair("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]"),
        Pair("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]"),
        Pair("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]"),
        Pair("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"),
        Pair("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
    ).forEach {
        test("Explode $it.first after exploding should be ${it.second}") {
            val input = it.first
            val day = Day18()
            val model = day.parse(input)
            val explode = day.explode(model)
            explode.asString() shouldBe it.second
        }
    }

    test("Reduce [[[[4,3],4],4],[7,[[8,4],9]]] + [1,1]") {
        val input = "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]\n" +
                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]\n" +
                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]\n" +
                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]\n" +
                "[7,[5,[[3,8],[1,4]]]]\n" +
                "[[2,[2,2]],[8,[8,1]]]\n" +
                "[2,9]\n" +
                "[1,[[[9,3],9],[[9,0],[0,7]]]]\n" +
                "[[[5,[7,4]],7],1]\n" +
                "[[[[4,2],2],6],[8,7]]"
        val day = Day18()
        val model = day.parse(input)
        val reduced = day.reduce(model)
        reduced.asString() shouldBe "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
    }

    listOf(
        Pair("[[[[4,3],4],4],[7,[[8,4],9]]]\n[1,1]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
        Pair("[1,1]\n[2,2]\n[3,3]\n[4,4]", "[[[[1,1],[2,2]],[3,3]],[4,4]]"),
        Pair("[1,1]\n[2,2]\n[3,3]\n[4,4]\n[5,5]", "[[[[3,0],[5,3]],[4,4]],[5,5]]"),
        Pair("[1,2]\n[[3,4],5]", "[[1,2],[[3,4],5]]"),
        Pair("[1,1]\n[2,2]\n[3,3]\n[4,4]\n[5,5]\n[6,6]", "[[[[5,0],[7,4]],[5,5]],[6,6]]")
    ).forEach {
        test("Reduce ${it.first.replace('\n', '-')} to ${it.second}") {
            val input = it.first
            val day = Day18()
            val model = day.parse(input)
            val reduced = day.reduce(model)
            reduced.asString() shouldBe it.second
        }
    }


    test("Example 1") {
        val input = "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]\n" +
                "[[[5,[2,8]],4],[5,[[9,9],0]]]\n" +
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]\n" +
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]\n" +
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]\n" +
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]\n" +
                "[[[[5,4],[7,7]],8],[[8,3],8]]\n" +
                "[[9,3],[[9,9],[6,[4,9]]]]\n" +
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]\n" +
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        val day = Day18()
        val model = day.parse(input)
        val answer = day.solve(model)
        answer shouldBe 4140L
    }

})

