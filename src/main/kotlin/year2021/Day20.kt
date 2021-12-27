package year2021

import AoCUtils
import AoCUtils.test
import Point

fun main() {

    fun printMap(debug: Boolean, map: Map<Point, String>) {
        if (debug) {
            val minX = map.map { it.key.x }.minByOrNull { it }!!
            val maxX = map.map { it.key.x }.maxByOrNull { it }!!
            val minY = map.map { it.key.y }.minByOrNull { it }!!
            val maxY = map.map { it.key.y }.maxByOrNull { it }!!

            println("Printing map")
            for (y in minY..maxY) {
                println()
                for (x in minX..maxX) {
                    print(map[Point(x, y)])
                }
            }

            println()
        }
    }

    fun pixelForImage(input: String, imageEnhancement: String): String {
        val pos = input.replace('.', '0').replace('#', '1').toInt(radix = 2)
        val pixel = imageEnhancement[pos].toString()
//        println("$pos to $pixel")
        return pixel
    }

    fun surroundingPixels(pos: Point, map: Map<Point, String>, defaultPixel: String): Map<Point, String> {
        val surrounding = listOf(
            Point(pos.x - 1, pos.y - 1),
            Point(pos.x - 1, pos.y + 1),
            Point(pos.x - 1, pos.y),
            Point(pos.x, pos.y - 1),
            Point(pos.x, pos.y + 1),
            Point(pos.x, pos.y),
            Point(pos.x + 1, pos.y - 1),
            Point(pos.x + 1, pos.y + 1),
            Point(pos.x + 1, pos.y)
        )

        return surrounding.map { it to map.getOrDefault(it, defaultPixel) }.toMap()
    }

    fun mapToString(pixels: Map<Point, String>): String {
        val minX = pixels.map { it.key.x }.minByOrNull { it }!!
        val maxX = pixels.map { it.key.x }.maxByOrNull { it }!!
        val minY = pixels.map { it.key.y }.minByOrNull { it }!!
        val maxY = pixels.map { it.key.y }.maxByOrNull { it }!!

        var returnString = ""
        for (y in (minY)..maxY) {
            for (x in minX..maxX) {
                returnString += pixels[Point(x, y)]
            }
        }
        return returnString
    }

    fun createNewImage(
        inputImageMap: MutableMap<Point, String>,
        imageEnhancement: String,
        defaultPixel: String
    ): MutableMap<Point, String> {
        val newMap1 = mutableMapOf<Point, String>()
        val newSize = if (defaultPixel == ".") 1 else 2
        val minX = inputImageMap.map { it.key.x }.minByOrNull { it }!! - newSize
        val maxX = inputImageMap.map { it.key.x }.maxByOrNull { it }!! + newSize
        val minY = inputImageMap.map { it.key.y }.minByOrNull { it }!! - newSize
        val maxY = inputImageMap.map { it.key.y }.maxByOrNull { it }!! + newSize

        //       println("Creating new image [$minX, $minY] to [$maxX, $maxY]")

        val newInputImage = inputImageMap.toMap().toMutableMap()
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val current = Point(x, y)
                if (!inputImageMap.containsKey(current)) {
                    newInputImage[current] = defaultPixel
                }
            }
        }

        for (y in minY..maxY) {
            for (x in minX..maxX) {
                val current = Point(x, y)
                val surrounding = surroundingPixels(current, newInputImage, defaultPixel)
                val s = mapToString(surrounding)
                val pixel = pixelForImage(s, imageEnhancement)
                newMap1[current] = pixel
            }
        }
        return newMap1
    }

    fun part1(input: String, debug: Boolean = false): Long {
        val imageEnhancement = input.lines().first();
        val inputImage = input.lines().drop(2)

        val emptyPixel = pixelForImage(".........", imageEnhancement)
        val fullPixel = pixelForImage("#########", imageEnhancement)
        val inputImageMap =
            inputImage.mapIndexed { y, row -> row.mapIndexed { x, pixel -> Point(x, y) to pixel.toString() } }.flatten()
                .toMap()
                .toMutableMap()


        printMap(debug, inputImageMap)
        val newMap = createNewImage(inputImageMap, imageEnhancement, ".")
        printMap(debug, newMap)
        val newMap2 = createNewImage(newMap, imageEnhancement, emptyPixel)
        printMap(debug, newMap2)


        return newMap2.map { it.value }.filter { it == "#" }.count().toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val imageEnhancement = input.lines().first();
        val inputImage = input.lines().drop(2)

        val emptyPixel = pixelForImage(".........", imageEnhancement)
        val fullPixel = pixelForImage("#########", imageEnhancement)
        val inputImageMap =
            inputImage.mapIndexed { y, row -> row.mapIndexed { x, pixel -> Point(x, y) to pixel.toString() } }.flatten()
                .toMap()
                .toMutableMap()
        var finalImage = inputImageMap.toMutableMap()
        for (i in 0 until 25) {
            printMap(debug, finalImage)
            val newMap = createNewImage(finalImage, imageEnhancement, ".")
            printMap(debug, newMap)
            finalImage = createNewImage(newMap, imageEnhancement, emptyPixel)
            printMap(debug, finalImage)
        }


        return finalImage.map { it.value }.filter { it == "#" }.count().toLong()
    }


    val testInput = "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##" +
            "#..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###" +
            ".######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#." +
            ".#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#....." +
            ".#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.." +
            "...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#....." +
            "..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#\n" +
            "\n" +
            "#..#.\n" +
            "#....\n" +
            "##..#\n" +
            "..#..\n" +
            "..###\n"

    val input = AoCUtils.readText("year2021/Day20.txt")

    part1(testInput, true) test Pair(35, "test 1 part 1")
    part1(input) test Pair(4917, "part 1, not 4924, 4837, (4885 to low)")

    part2(testInput) test Pair(3351, "test 2 part 2")
    part2(input) test Pair(16389, "part 2")


}