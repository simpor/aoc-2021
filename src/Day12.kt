import AoCUtils.test

enum class Day12Type { START, END, BIG_CAVE, SMALL_CAVE }

fun main() {
    data class Node(val name: String, val type: Day12Type)
    data class Link(val from: Node, val to: Node)

    fun generatePaths(nodes: Map<String, Node>, links: List<Link>): List<List<Node>> {
        val start = nodes.values.first { it.type == Day12Type.START }

        fun innerGenerator(path: List<Node>, links: List<Link>, completePaths: List<List<Node>>): List<List<Node>> {
            val current = path.last();
            if (current.type == Day12Type.END) {
                val list = completePaths.toMutableList()
                list.add(path)
                return list
            }
            val possibleLinks = links.filter { it.from == current }
            val returnList = mutableListOf<List<Node>>()
            for (possibleLink in possibleLinks) {
                if (possibleLink.to == start) continue
                if (possibleLink.to.type == Day12Type.SMALL_CAVE && path.any { it == possibleLink.to }) continue

                val newPath = path.toMutableList()
                newPath.add(possibleLink.to)
                returnList.addAll(innerGenerator(newPath, links, completePaths))
            }
            return returnList
        }

        return innerGenerator(listOf(start), links, listOf())

    }

    fun part1(input: String): Int {

        operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

        val nodes = input.lines().map { it.split("-") }.flatten().distinct().map {
            when (it) {
                "start" -> Node(it, Day12Type.START)
                "end" -> Node(it, Day12Type.END)
                in Regex("[a-z]+") -> Node(it, Day12Type.SMALL_CAVE)
                in Regex("[A-Z]+") -> Node(it, Day12Type.BIG_CAVE)
                else -> throw RuntimeException("Unknown node: $it")
            }
        }.associateBy { it.name }
        val links = input.lines().map { it.split("-") }.map { split -> Link(nodes[split[0]]!!, nodes[split[1]]!!) }
            .map { listOf(it, Link(it.to, it.from)) }.flatten()
        val paths: List<List<Node>> = generatePaths(nodes, links)

        return paths.size
    }


    fun generatePaths2(nodes: Map<String, Node>, links: List<Link>): List<List<Node>> {
        val start = nodes.values.first { it.type == Day12Type.START }

        fun innerGenerator(path: List<Node>, links: List<Link>, completePaths: List<List<Node>>): List<List<Node>> {
            val current = path.last();
            if (current.type == Day12Type.END) {
                val list = completePaths.toMutableList()
                list.add(path)
                return list
            }
            val possibleLinks = links.filter { it.from == current }
            val returnList = mutableListOf<List<Node>>()
            for (possibleLink in possibleLinks) {
                if (possibleLink.to == start) continue
                if (possibleLink.to.type == Day12Type.SMALL_CAVE) {
                    val times = path.filter { it == possibleLink.to }.size
                    if (times == 0) {
                        val newPath = path.toMutableList()
                        newPath.add(possibleLink.to)
                        returnList.addAll(innerGenerator(newPath, links, completePaths))
                    } else if (times == 1) {
                        val map = path.filter { it.type == Day12Type.SMALL_CAVE }.groupBy { it.name }
                        if (map.values.any { it.size == 2 }) {
                            continue
                        }
                        val newPath = path.toMutableList()
                        newPath.add(possibleLink.to)
                        returnList.addAll(innerGenerator(newPath, links, completePaths))
                    } else {
                        continue
                    }
                } else {
                    val newPath = path.toMutableList()
                    newPath.add(possibleLink.to)
                    returnList.addAll(innerGenerator(newPath, links, completePaths))
                }

            }
            return returnList
        }

        return innerGenerator(listOf(start), links, listOf())

    }

    fun part2(input: String): Int {
        operator fun Regex.contains(text: CharSequence): Boolean = this.matches(text)

        val nodes = input.lines().map { it.split("-") }.flatten().distinct().map {
            when (it) {
                "start" -> Node(it, Day12Type.START)
                "end" -> Node(it, Day12Type.END)
                in Regex("[a-z]+") -> Node(it, Day12Type.SMALL_CAVE)
                in Regex("[A-Z]+") -> Node(it, Day12Type.BIG_CAVE)
                else -> throw RuntimeException("Unknown node: $it")
            }
        }.associateBy { it.name }
        val links = input.lines().map { it.split("-") }.map { split -> Link(nodes[split[0]]!!, nodes[split[1]]!!) }
            .map { listOf(it, Link(it.to, it.from)) }.flatten()
        val paths: List<List<Node>> = generatePaths2(nodes, links)

        return paths.size
    }


    val testInput1 = "start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end"
    val testInput2 = "dc-end\n" +
            "HN-start\n" +
            "start-kj\n" +
            "dc-start\n" +
            "dc-HN\n" +
            "LN-dc\n" +
            "HN-end\n" +
            "kj-sa\n" +
            "kj-HN\n" +
            "kj-dc"
    val testInput3 = "fs-end\n" +
            "he-DX\n" +
            "fs-he\n" +
            "start-DX\n" +
            "pj-DX\n" +
            "end-zg\n" +
            "zg-sl\n" +
            "zg-pj\n" +
            "pj-he\n" +
            "RW-he\n" +
            "fs-DX\n" +
            "pj-RW\n" +
            "zg-RW\n" +
            "start-pj\n" +
            "he-WI\n" +
            "zg-he\n" +
            "pj-fs\n" +
            "start-RW"

    val input = "fw-ll\n" +
            "end-dy\n" +
            "tx-fw\n" +
            "tx-tr\n" +
            "dy-jb\n" +
            "ZD-dy\n" +
            "dy-BL\n" +
            "dy-tr\n" +
            "dy-KX\n" +
            "KX-start\n" +
            "KX-tx\n" +
            "fw-ZD\n" +
            "tr-end\n" +
            "fw-jb\n" +
            "fw-yi\n" +
            "ZD-nr\n" +
            "start-fw\n" +
            "tx-ll\n" +
            "ll-jb\n" +
            "yi-jb\n" +
            "yi-ll\n" +
            "yi-start\n" +
            "ZD-end\n" +
            "ZD-jb\n" +
            "tx-ZD"

    part1(testInput1) test Pair(10, "test 1 part 1")
    part1(testInput2) test Pair(19, "test 1 part 1")
    part1(testInput3) test Pair(226, "test 1 part 1")
    part1(input) test Pair(4573, "part 1")

    part2(testInput1) test Pair(36, "test 2 part 2")
    part2(testInput2) test Pair(103, "test 2 part 2")
    part2(testInput3) test Pair(3509, "test 2 part 2")
    part2(input) test Pair(117509, "part 2")


}