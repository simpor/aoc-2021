import AoCUtils.test
import java.io.File

fun main() {
    data class Check(var mark: Boolean = false, val num: Int)
    data class Board(var rows: List<List<Check>>, var winner: Boolean = false, var order: Int = 0, var sum: Int = 0) {
        fun mark(number: Int) =
            rows.forEach { row ->
                row.forEach { cell ->
                    if (cell.num == number) cell.mark = true
                }
            }


        fun checkWinning(number: Int) {
            for (i in 0..4) {
                if (rows[i].filter { it.mark }.size == 5) {
                    winner = true
                }
                var counter = 0
                for (j in 0..4) {
                    if (rows[j][i].mark) counter++
                }
                if (counter == 5) winner = true
            }
            if (winner) {
                sum = rows.sumOf { it -> it.filter { !it.mark }.sumOf { it.num } } * number
            }
        }

    }

    fun createBoards(input: String) = input.lines().drop(1).windowed(6, 6)
        .map { board ->
            board.drop(1)
                .map { s ->
                    s.split(" ")
                        .filter { it.isNotEmpty() }
                        .map { Check(false, it.trim().toInt()) }
                }
        }.map { b -> Board(b) }

    fun part1(input: String): Int {
        val numbers = input.lines()[0].split(",").map { s -> s.toInt() }
        val boards = createBoards(input)

        for (number in numbers) {
            boards.forEach { board ->
                board.mark(number)
                board.checkWinning(number)
            }

            val winner = boards.find { it.winner }
            if (winner != null) {
                return winner.sum
            }
        }

        return 0
    }

    fun part2(input: String): Int {
        val numbers = input.lines()[0].split(",").map { s -> s.toInt() }
        val boards = createBoards(input)
        var winOrder = 1

        for (number in numbers) {
            boards.forEach { board ->
                if (!board.winner) {
                    board.mark(number)
                    board.checkWinning(number)
                    if (board.winner && board.order == 0) {
                        board.order = winOrder
                        winOrder++
                    }
                }
            }
        }


        return boards.filter { board -> board.winner }.sortedBy { board -> board.order }.reversed().first().sum

    }


    val testInput = "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1\n" +
            "\n" +
            "22 13 17 11  0\n" +
            " 8  2 23  4 24\n" +
            "21  9 14 16  7\n" +
            " 6 10  3 18  5\n" +
            " 1 12 20 15 19\n" +
            "\n" +
            " 3 15  0  2 22\n" +
            " 9 18 13 17  5\n" +
            "19  8  7 25 23\n" +
            "20 11 10 24  4\n" +
            "14 21 16 12  6\n" +
            "\n" +
            "14 21 17 24  4\n" +
            "10 16 15  9 19\n" +
            "18  8 23 26 20\n" +
            "22 11 13  6  5\n" +
            " 2  0 12  3  7"

    val input = File("src", "Day04.txt").readText()

    part1(testInput) test Pair(4512, "test 1 part 1")
    part1(input) test Pair(45031, "part 1")

    part2(testInput) test Pair(1924, "test 2 part 2")
    part2(input) test Pair(2568, "part 2")


}



