package com.inoorma.tic_tac_toe.players

import com.inoorma.tic_tac_toe.ai.MinMax
import com.inoorma.tic_tac_toe.models.Board

class MediumAI(val id: Int, private val oppID: Int) : Player(id, oppID,"MediumAI") {

    private var minMax: MinMax = MinMax(this, Difficulty.MEDIUM)

    override fun chooseMove(board: Board): IntArray {
        return minMax.chooseMove(board)
    }
}

