package com.inoorma.tic_tac_toe.models

abstract class Player(var playerID: Int, var opponentID: Int, var name: String) {

    abstract fun chooseMove(board: Board): IntArray

    enum class Difficulty {
        EASY, MEDIUM, HARD, HUMAN
    }
}