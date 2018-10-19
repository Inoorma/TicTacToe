package com.inoorma.tic_tac_toe.ai

import android.util.Log
import com.inoorma.tic_tac_toe.activities.GameActivity
import com.inoorma.tic_tac_toe.models.Board
import com.inoorma.tic_tac_toe.players.Player
import java.util.*
import kotlin.collections.ArrayList

/**
 * Player choose moves based on a MinMax algorithm
 */
class MinMax (private val player: Player, difficulty: Player.Difficulty) {

    companion object {
        private const val MEDIUM_DEPTH = 2
    }

    /**
     * Medium difficulty: Takes into consideration the next two moves
     * Hard difficulty: Takes into consideration all possible moves
     */

    private val maxDepth: Int = if (difficulty == Player.Difficulty.MEDIUM) {
        MEDIUM_DEPTH
    } else {
        GameActivity.WIDTH * GameActivity.HEIGHT
    }

    fun chooseMove(board: Board): IntArray {
        val validMoves = board.getValidMoves()
        var score = Int.MIN_VALUE
        var choice = IntArray(validMoves[0].size)
        val depth = 1

        //Starting case; make the first move
        if (board.emptyTiles >= board.width * board.height - 1) {
            choice = if (board.isEmpty(board.width / 2, board.height / 2)) {
                intArrayOf(board.width / 2, board.height / 2)
            } else {
                val topLeft = Random().nextBoolean()
                if (topLeft) {
                    intArrayOf(0, 0)
                } else {
                    intArrayOf(board.width - 1, board.height - 1)
                }
            }
        //Otherwise, apply Min-Max Theorem
        } else {
            for (validMove in validMoves) {
                board.setTile(validMove[0], validMove[1], player.playerID)
                 if (!board.isPlaying() && !board.isDrawn()) {
                    choice = validMove
                    board.removeLastTile()
                    break
                }

                //create a new list for valid moves
                val newMoveList = ArrayList<IntArray>()
                newMoveList.addAll(validMoves)
                newMoveList.remove(validMove)

                val curScore = minimiseScore(newMoveList, board.copy(), depth + 1)
                Log.d(validMove[0].toString() + validMove[1].toString(), curScore.toString())
                if (curScore > score) {
                    score = curScore
                    choice = validMove
                }
                //Undo move
                board.removeLastTile()
            }
        }
        return choice
    }

    /**
     * Minimise the possibility of the worst case loss by evaluating moves based on a score.
     * To achieve a winning move sooner, branch nodes are given a higher score than left nodes
     * @param validMoveList list of empty tiles
     * @param board copy of the board
     * @param depth the number of moves already consider
     * @returns score evaluated based on valid moves
     */
    private fun minimiseScore(validMoveList: List<IntArray>, board: Board, depth: Int): Int {
        var score = Int.MAX_VALUE
        if (validMoveList.size == 1) {
            val move = validMoveList[0]
            board.setTile(move[0], move[1], player.opponentID)
            score = if (board.isDrawn()) {
                0
            } else {
                -20
            }
            board.removeLastTile()
        } else {
            for (validMove in validMoveList) {
                board.setTile(validMove[0], validMove[1], player.opponentID)
                if (!board.isPlaying() && board.isDrawn()) {
                    score = 0
                    board.removeLastTile()
                    break
                }  else if (!board.isPlaying()) {
                    score = -10
                    board.removeLastTile()
                    break
                }

                if (depth != maxDepth) {
                    val newMoveList = ArrayList<IntArray>()
                    newMoveList.addAll(validMoveList)
                    newMoveList.remove(validMove)

                    val curScore = maximiseScore(newMoveList, board.copy(), depth + 1)
                    if (curScore < score) {
                        score = curScore
                    }
                }
                board.removeLastTile()
            }
        }
        return score
    }

    /**
     * Maximise the possibility of the best case scenario by evaluating moves baed on a score
     * @param validMoveList list of empty tiles
     * @param board copy of the board
     * @param depth the number of moves already consider
     * @return score evaluated based on valid moves
     */

    private fun maximiseScore(validMoveList: List<IntArray>, board: Board, depth: Int): Int {
        var score = Int.MIN_VALUE
        Log.d("C Point", "Called")
        if (validMoveList.size == 1) {
            val move = validMoveList[0]
            board.setTile(move[0], move[1], player.playerID)
            score = if (board.isDrawn()) {
                0
            } else {
                10
            }
            board.removeLastTile()
        } else {
            for (validMove in validMoveList) {
                board.setTile(validMove[0], validMove[1], player.playerID)
                if (!board.isPlaying() && board.isDrawn()) {
                    score = 0
                    board.removeLastTile()
                    break
                } else if (!board.isPlaying()) {
                    score = 20
                    board.removeLastTile()
                    break
                }

                if (depth != maxDepth) {
                    val newMoveList = ArrayList<IntArray>()
                    newMoveList.addAll(validMoveList)
                    newMoveList.remove(validMove)

                    val curScore = minimiseScore(newMoveList, board.copy(), depth + 1)
                    if (curScore > score) {
                        score = curScore
                    }
                }

                board.removeLastTile()
            }
        }
        return score
    }
}