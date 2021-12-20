import AoCUtils.test
import java.io.File

fun main() {

    fun pixelForImage(input: String, imageEnhancement: String): String {
        val pos = input.replace('.', '0').replace('#', '1').toInt(radix = 2)
        return imageEnhancement[pos].toString()
    }

    fun surroundingPixels(pos: Point, map: Map<Point, String>): Map<Point, String> {
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

        return surrounding.map { it to map.getOrDefault(it, ".").toString() }.toMap()
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

    fun createNewImage(inputImageMap: MutableMap<Point, String>, imageEnhancement: String): MutableMap<Point, String> {
        val newMap1 = mutableMapOf<Point, String>()
        val minX = inputImageMap.map { it.key.x }.minByOrNull { it }!!
        val maxX = inputImageMap.map { it.key.x }.maxByOrNull { it }!!
        val minY = inputImageMap.map { it.key.y }.minByOrNull { it }!!
        val maxY = inputImageMap.map { it.key.y }.maxByOrNull { it }!!

        for (y in (minY - 1)..(maxY+1)) {
            for (x in (minX - 1)..(maxX+1)) {
                val current = Point(x, y)
                val surrounding = surroundingPixels(current, inputImageMap)
                val s = mapToString(surrounding)
                val pixel = pixelForImage(s, imageEnhancement)
                newMap1[current] = pixel
            }
        }
        return newMap1
    }

    fun printMap(map: Map<Point, String>) {
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

    fun part1(input: String, debug: Boolean = false): Long {
        val imageEnhancement = input.lines().first();
        val inputImage = input.lines().drop(2)

        val inputImageMap =
            inputImage.mapIndexed { y, row -> row.mapIndexed { x, pixel -> Point(x, y) to pixel.toString() } }.flatten().toMap()
                .toMutableMap()


        printMap(inputImageMap)
        val newMap = createNewImage(inputImageMap, imageEnhancement)
        printMap(newMap)
        val newMap2 = createNewImage(newMap, imageEnhancement)
        printMap(newMap2)


        return newMap2.map { it.value }.filter { it == "#" }.count().toLong()
    }

    fun part2(input: String, debug: Boolean = false): Long {


        return 0

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

    val input = File("src", "Day20.txt").readText()

    part1(testInput) test Pair(35, "test 1 part 1")
    part1(input) test Pair(0L, "part 1, not 4924")

    part2(testInput) test Pair(0L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")


}