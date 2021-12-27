package year2021

import AoCUtils.test

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {
        val players = input.lines().map { it.toLong() }.toMutableList()

        val score = mutableListOf<Long>(0, 0)
        var dice = 0
        var player = 0
        var rolls = 0
        while (score.maxOrNull()!! < 1000) {
            var new = 0
            dice++
            if (dice == 101) dice = 1
            new += dice
            dice++
            if (dice == 101) dice = 1
            new += dice
            dice++
            if (dice == 101) dice = 1
            new += dice
            rolls += 3

            val currentPos = players[player]
            val newPos = if ((currentPos + new) % 10L == 0L) 10 else (currentPos + new) % 10L
            val currentScore = score[player]
            val newScore = currentScore + newPos
            players[player] = newPos
            score[player] = newScore
            player = if (player == 0) 1 else 0
        }
        println(score)
        return score.minOrNull()!! * rolls
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val players = input.lines().map { it.toLong() }.toMutableList()

        data class Game(val pos: Long, var score: Long)

        var player1Wins = 0L
        var player2Wins = 0L

        fun playGame(p1: Game, p2: Game, turn: Boolean) {
            if (!turn && p1.score != 0L) {
                val currentPos = p1.pos
                val newPos = if (p1.pos % 10L == 0L) 10 else p1.pos % 10L
                val currentScore = p1.score
                val newScore = currentScore + newPos
                p1.score = newScore
            } else if (p2.score != 0L) {
                val currentPos = p2.pos
                val newPos = if (p2.pos % 10L == 0L) 10 else p2.pos % 10L
                val currentScore = p2.score
                val newScore = currentScore + newPos
                p2.score = newScore
            }


            if (p1.score >= 21) {
                player1Wins++
                return
            }
            if (p2.score >= 21) {
                player2Wins++
                return
            }
            if (turn) {

                playGame(p1.copy(pos = p1.pos + 1), p2, false)
                playGame(p1.copy(pos = p1.pos + 2), p2, false)
                playGame(p1.copy(pos = p1.pos + 3), p2, false)
            } else {
                playGame(p1, p2.copy(pos = p2.pos + 1), true)
                playGame(p1, p2.copy(pos = p2.pos + 2), true)
                playGame(p1, p2.copy(pos = p2.pos + 3), true)
            }
        }


        val p1 = Game(players[0], 0)
        val p2 = Game(players[1], 0)

        playGame(p1, p2, true)


        return if (player1Wins < player2Wins) player2Wins else player1Wins
    }


    val testInput = "4\n" +
            "8"

    val input = "7\n" +
            "3"

    part1(testInput) test Pair(739785, "test 1 part 1")
    part1(input) test Pair(551901L, "part 1")

    part2(testInput) test Pair(444356092776315L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")


}