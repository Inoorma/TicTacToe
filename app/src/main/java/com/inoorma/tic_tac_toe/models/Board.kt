package com.inoorma.tic_tac_toe.models

import kotlin.math.min

class Board(val height: Int, val width: Int) {

    var board: Array<IntArray> = Array(height) { IntArray(width) { DEFAULT } }

    companion object {
        const val DEFAULT = 0
        const val NAUGHT = 1
        const val CROSS = 10
    }

    var gameState = GameState.PLAYING

    val NAUGHT_WIN_SCORE = NAUGHT * min(width, height)
    val CROSS_WIN_SCORE = CROSS * min(width, height)

    var emptyTiles = height * width
    var lastMove = IntArray(2)

    fun setTile(x: Int, y: Int, value: Int) {
        if (isEmpty(x, y)) {
            board[x][y] = value
            lastMove = intArrayOf(x, y)
            emptyTiles--
            updateGameState(x, y)
        }
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return board[x][y] == 0
    }

    fun removeLastTile() {
        board[lastMove[0]][lastMove[1]] = DEFAULT
        emptyTiles++
    }

    fun getValidMoves(): Array<IntArray> {
        var index = 0
        val validMoves = Array(emptyTiles) { IntArray(2) }
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (board[i][j] == DEFAULT) {
                    validMoves[index][0] = i
                    validMoves[index][1] = j
                    index++
                }
            }
        }
        return validMoves
    }

    private fun updateGameState(x: Int, y: Int) {
        checkRow(x)
        checkCol(y)
        if (x == y || x == 0 && y == 2 || x == 2 && y == 0) {
            checkDiag()
        }
        if (emptyTiles == 0 && gameState == GameState.PLAYING) {
            gameState = GameState.DRAW
        }
    }

    private fun checkRow(x: Int): Int {
        var score = 0
        for (y in 0 until width) {
            score += board[x][y]
        }
        isWinner(score)
        return score
    }

    private fun checkCol(y: Int): Int{
        var score = 0
        for (x in 0 until height) {
            score += board[x][y]
        }
        isWinner(score)
        return score
    }

     private fun checkDiag(): IntArray {
        var scoreMain = 0
        var scoreAnti = 0
        for (i in 0 until width) {
            scoreMain += board[i][i]
            scoreAnti += board[i][width - i - 1]
        }
        isWinner(scoreMain)
        isWinner(scoreAnti)
        return intArrayOf(scoreMain, scoreAnti)
    }

    private fun isWinner(score: Int) {
        if (score == CROSS_WIN_SCORE) {
            gameState = GameState.CROSS_WIN
        } else if (score == NAUGHT_WIN_SCORE) {
            gameState = GameState.NAUGHT_WIN
        }
    }

    fun isPlaying(): Boolean {
        return gameState == GameState.PLAYING
    }

    fun isDrawn(): Boolean {
        return gameState == GameState.DRAW
    }

    fun copy() : Board {
        val boardCopy = Board(height, width)
        boardCopy.board = board
        boardCopy.gameState = gameState
        boardCopy.emptyTiles = emptyTiles
        return boardCopy
    }

    enum class GameState {
        PLAYING, CROSS_WIN, NAUGHT_WIN, DRAW
    }
}