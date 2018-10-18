package com.inoorma.tic_tac_toe.players

import com.inoorma.tic_tac_toe.ai.MinMax
import com.inoorma.tic_tac_toe.models.Board
import com.inoorma.tic_tac_toe.models.Player

class HardAI(id: Int, oppID: Int) : Player(id, oppID, "HardAI") {

    private var minMax: MinMax = MinMax(this, Difficulty.HARD)

    override fun chooseMove(board: Board): IntArray {
        return minMax.chooseMove(board)
    }
}