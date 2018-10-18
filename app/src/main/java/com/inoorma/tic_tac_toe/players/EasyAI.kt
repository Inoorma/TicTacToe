package com.inoorma.tic_tac_toe.players

import com.inoorma.tic_tac_toe.models.Board
import com.inoorma.tic_tac_toe.models.Player
import java.util.*

class EasyAI(val id: Int, oppID: Int) : Player(id, oppID, "EasyAI") {

    private var random = Random()

    override fun chooseMove(board: Board): IntArray {
        val validMoves: Array<IntArray> = board.getValidMoves()
        return validMoves[random.nextInt(validMoves.size)]
    }

}