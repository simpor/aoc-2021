import AoCUtils.test
import java.io.File

fun main() {

    fun part1(input: String, debug: Boolean = false): Int {
        var polymer = input.lines().take(1).first()
        val template = input.lines().drop(2).map { it.split(" -> ") }.associate { it[0] to it[1] }

        for (steps in 1..10) {
            val match = template.filter { polymer.contains(it.key) }

            val newPolymer = polymer.windowed(2, 1, false) { list ->
                val pair = list.toString()
                if (match.contains(pair)) match[pair] + list[1].toString()
                else pair
            }.joinToString(separator = "")

            polymer = polymer[0] + newPolymer
        }

        val counter = polymer.toList().groupBy { it }

        val maxOf = counter.values.map { it.size }.maxOf { it }
        val minOf = counter.values.map { it.size }.minOf { it }
        return maxOf - minOf
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val polymer = input.lines().take(1).first()
        val template = input.lines().drop(2).map { it.split(" -> ") }.associate { it[0] to it[1][0] }

        val countMap = template.keys.associateWith { 0L }.toMutableMap()

        polymer.windowed(2) { win -> countMap.increaseOrAdd(win.toString(), 1) }

        val letterCounter = polymer.toList()
            .groupBy { it }
            .map { it.key to it.value.size.toLong() }.toMap().toMutableMap()

        for (step in 1..40) {
            countMap
                .filter { it.value > 0 }
                .filter { template[it.key] != null }
                .forEach { pair ->
                    val l = template[pair.key]!!
                    val old = pair.key
                    val new1 = old[0] + l.toString()
                    val new2 = l.toString() + old[1]
                    countMap.increaseOrAdd(old, -pair.value)
                    countMap.increaseOrAdd(new1, pair.value)
                    countMap.increaseOrAdd(new2, pair.value)

                    letterCounter.increaseOrAdd(l, pair.value)
                }
        }
        val maxOf = letterCounter.map { it.value }.maxOrNull()!!
        val minOf = letterCounter.map { it.value }.minOrNull()!!
        return maxOf - minOf
    }


    val testInput = "NNCB\n" +
            "\n" +
            "CH -> B\n" +
            "HH -> N\n" +
            "CB -> H\n" +
            "NH -> C\n" +
            "HB -> C\n" +
            "HC -> B\n" +
            "HN -> C\n" +
            "NN -> C\n" +
            "BH -> H\n" +
            "NC -> B\n" +
            "NB -> B\n" +
            "BN -> B\n" +
            "BB -> N\n" +
            "BC -> B\n" +
            "CC -> N\n" +
            "CN -> C"

    val input = File("src", "Day14.txt").readText()

    part1(testInput) test Pair(1588, "test 1 part 1")
    part1(input) test Pair(2345, "part 1")

    part2(testInput, true) test Pair(2188189693529L, "test 2 part 2")
    part2(input) test Pair(2432786807053L, "part 2")


}

