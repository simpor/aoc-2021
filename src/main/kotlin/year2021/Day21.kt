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

        data class Game(val pos: Long, var score: Long, var moves: Long = 0, var wins: Long = 0)
        data class GameState(val p1: Game, val p2: Game, val p1Turn: Boolean)
        data class WinnerCount(val p1: Long, val p2: Long) {
            operator fun plus(other: WinnerCount): WinnerCount = WinnerCount(p1 + other.p1, p2 + other.p2)
            operator fun times(other: Long): WinnerCount = WinnerCount(p1 * other, p2 * other)
            fun max(): Long = maxOf(p1, p2)
        }

        var player1Wins = 0L
        var player2Wins = 0L
        val cache = mutableMapOf<GameState, WinnerCount>()
        val dieFrequency: Map<Int, Long> = mapOf(3 to 1, 4 to 3, 5 to 6, 6 to 7, 7 to 6, 8 to 3, 9 to 1)

        fun playGame(gameState: GameState): WinnerCount {
            if (!gameState.p1Turn) {
                val newPos = if (gameState.p1.pos % 10L == 0L) 10 else gameState.p1.pos % 10L
                val currentScore = gameState.p1.score
                val newScore = currentScore + newPos
                gameState.p1.score = newScore
            } else {
                val newPos = if (gameState.p2.pos % 10L == 0L) 10 else gameState.p2.pos % 10L
                val currentScore = gameState.p2.score
                val newScore = currentScore + newPos
                gameState.p2.score = newScore
            }
            //println(gameState)
            if (gameState.p1.score >= 21) {
                player1Wins += gameState.p1.moves
                return WinnerCount(1, 0)
            }
            if (gameState.p2.score >= 21) {
                player2Wins += gameState.p2.moves
                return WinnerCount(0, 1)
            }

            if (cache.containsKey(gameState)) return cache.getValue(gameState)
            //.also { println("returning from cache: $it") }

            return if (gameState.p1Turn) {
                dieFrequency.map { (sum, freq) ->
                    playGame(
                        gameState.copy(
                            p1 = gameState.p1.copy(pos = gameState.p1.pos + sum),
                            p1Turn = !gameState.p1Turn
                        )
                    ) * freq
                }.reduce { acc, winnerCount -> acc + winnerCount }.also { cache[gameState] = it }
            } else {
                dieFrequency.map { (sum, freq) ->
                    playGame(
                        gameState.copy(
                            p2 = gameState.p1.copy(pos = gameState.p1.pos + sum),
                            p1Turn = !gameState.p1Turn
                        )
                    ) * freq
                }.reduce { acc, winnerCount -> acc + winnerCount }.also { cache[gameState] = it }
            }
        }


        val p1 = Game(players[0], 0)
        val p2 = Game(players[1], 0)

        val gameState = playGame(GameState(p1, p2, true))
        println("End of game: $player1Wins, $player2Wins")
        println("End of game: $gameState")

        return if (gameState.p1 < gameState.p2) gameState.p2 else gameState.p1
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