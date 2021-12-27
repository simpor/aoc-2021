import com.github.mm.coloredconsole.colored
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest


/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = AoCUtils.readText("$name.txt")

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

data class Point(val x: Int, val y: Int)
data class Point3(var x: Int, var y: Int, var z: Int)

operator fun Point3.plus(other: Point3): Point3 {
    return Point3(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

fun <K> MutableMap<K, Long>.increaseOrAdd(key: K, value: Long) {
    if (this[key] != null) {
        this[key] = this[key]!! + value
    } else this[key] = value
}

enum class Map2dDirection { N, NE, E, SE, S, SW, W, NW, CENTER }

typealias Map2d<T> = MutableMap<Point, T>

fun <T> parseMap(input: String, mapper: (char: Char) -> T): Map2d<T> =
    input.lines().mapIndexed { y, row ->
        row.mapIndexed { x, c -> Point(x, y) to mapper(c) }
    }.flatten().toMap().toMutableMap()

fun <T> Map2d<T>.around(
    point: Point,
    dirs: List<Map2dDirection>,
    wrap: Boolean = false,
    defaultValue: T? = null
): Map<Point, T> = mapOf(
    Map2dDirection.N to Point(point.x, point.y - 1),
    Map2dDirection.NE to Point(point.x + 1, point.y - 1),
    Map2dDirection.E to Point(point.x + 1, point.y),
    Map2dDirection.SE to Point(point.x + 1, point.y + 1),
    Map2dDirection.S to Point(point.x, point.y + 1),
    Map2dDirection.SW to Point(point.x - 1, point.y + 1),
    Map2dDirection.W to Point(point.x - 1, point.y),
    Map2dDirection.NW to Point(point.x - 1, point.y - 1),
    Map2dDirection.CENTER to Point(point.x, point.y)
).filter { dirs.contains(it.key) }
    .values
    .map {
        if (wrap) {
            if (!this.containsKey(it)) {
                val maxX = this.keys.maxOf { m -> m.x }
                val maxY = this.keys.maxOf { m -> m.y }
                if (it.x == 1 && it.y == 9) {
                    println("hej hej")
                }
                if (it.y < 0) it.copy(y = maxY)
                else if (it.y > maxY) it.copy(y = 0)
                else if (it.x < 0) it.copy(x = maxX)
                else if (it.x > maxX) it.copy(x = 0)
                else TODO()
            } else it
        } else it
    }
    .associateWith { if (defaultValue == null) this[it]!! else this.getOrDefault(it, defaultValue) }

fun <T> Map2d<T>.around(
    point: Point,
    dirs: List<Map2dDirection>,
    wrap: Boolean = false
): Map<Point, T> = this.around(point, dirs, wrap, null)

fun <T> Map2d<T>.loopRightDown(action: (point: Point) -> Unit) {
    val maxX = this.maxOf { it.key.x }
    val maxY = this.maxOf { it.key.y }
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            action(Point(x, y))
        }
    }
}

fun <T> Map2d<T>.loopDownRight(action: (point: Point) -> Unit) {
    val maxX = this.maxOf { it.key.x }
    val maxY = this.maxOf { it.key.y }
    for (x in 0..maxX) {
        for (y in 0..maxY) {
            action(Point(x, y))
        }
    }
}

object AoCUtils {

    fun readText(source: String): String =
        File(ClassLoader.getSystemResource(source).file).readText().replace("\r\n", "\n")

    fun readLines(source: String): List<String> = readText(source).lines()

    infix fun <T> T.test(pair: Pair<T, String>) =
        if (this == pair.first)
            colored {
                println("PASS - ${this@test} is correct - ${pair.second}".cyan.bold)
            }
        else
            colored {
                println("FAIL - ${this@test} != ${pair.first} - ${pair.second}".red.bold)
            }


    fun <T> uniqueNumber(input: List<T>): List<List<T>> {
        val returnList = mutableListOf<List<T>>()
        val allCombos = allCombos(input, 5)
        allCombos.forEach {
            val str = it
            var add = true
            str.forEachIndexed { i1, c1 ->
                str.forEachIndexed { i2, c2 ->
                    if (i1 != i2 && c1 == c2) {
                        add = false
                    }
                }
            }
            if (add) returnList.add(str)
        }
        return returnList
    }

    fun <T> allCombos(set: List<T>, k: Int): List<List<T>> {
        val n = set.size
        val list = mutableListOf<MutableList<T>>()
        allCombosRec(set, mutableListOf(), n, k, list);
        return list
    }

    fun <T> allCombosRec(
        set: List<T>,
        prefix: MutableList<T>,
        n: Int, k: Int, list: MutableList<MutableList<T>>,
    ) {
        if (k == 0) {
            list.add(prefix)
            return
        }
        for (i in 0 until n) {
            val newPrefix = prefix + set[i]
            allCombosRec(set, newPrefix.toMutableList(), n, k - 1, list)
        }
    }
}

// Driver Code
fun main() {
    val set1 = listOf("a", "b")
    val k = 3
    println("First test " + AoCUtils.allCombos(set1, k))

    val set2 = listOf("a", "b", "c", "d")
    val k2 = 1
    println("Second test " + AoCUtils.allCombos(set2, k2))

    val set3 = listOf("0", "1", "2", "3", "4")
    val k3 = 5
    println("Third test: " + AoCUtils.allCombos(set3, k3))

    println("Unique test: " + AoCUtils.uniqueNumber(set3))


    listOf("", "").mapIndexed { index, s -> Pair(index, s) }
        .toMap()


}