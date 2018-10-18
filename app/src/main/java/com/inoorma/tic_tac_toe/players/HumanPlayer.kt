package com.inoorma.tic_tac_toe.players

import com.inoorma.tic_tac_toe.models.Board
import com.inoorma.tic_tac_toe.models.Player

class HumanPlayer(id: Int, oppID: Int) : Player(id, oppID, "Human") {

    override fun chooseMove(board: Board): IntArray {
        TODO("not implemented")
    }

}