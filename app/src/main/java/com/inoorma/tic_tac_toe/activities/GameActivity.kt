package com.inoorma.tic_tac_toe.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import com.inoorma.tic_tac_toe.players.EasyAI
import com.inoorma.tic_tac_toe.players.HardAI
import com.inoorma.tic_tac_toe.players.HumanPlayer
import com.inoorma.tic_tac_toe.players.MediumAI
import com.inoorma.tic_tac_toe.R
import com.inoorma.tic_tac_toe.models.Board
import com.inoorma.tic_tac_toe.players.Player
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), View.OnClickListener {

    private var board = Board(HEIGHT, WIDTH)

    private var crossScore = 0
    private var naughtScore = 0

    private var aiGame = false
    private var crossStart = true
    private var turn = Board.CROSS

    private lateinit var naughtPlayer: Player

    private lateinit var gridButtons: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameType = intent.extras["type"] as Player.Difficulty
        gridButtons = arrayOf(game_iv_topLeft, game_iv_topCentre, game_iv_topRight, game_iv_middleLeft, game_iv_middleCentre, game_iv_middleRight, game_iv_bottomLeft, game_iv_bottomCentre, game_iv_bottomRight)

        initListeners()
        initGameSettings(gameType)
        initUI()
    }

    /**
     * Initialise onClickListeners
     */
    private fun initListeners() {
        for (gridButton in gridButtons) {
            gridButton.setOnClickListener(this)
        }

        game_tv_playAgain.setOnClickListener {
            resetGame()
        }

        game_tv_mainMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    /**
     * Initialise opposition player
     */

    private fun initGameSettings(gameType: Player.Difficulty) {
        if (gameType != Player.Difficulty.HUMAN) {
            aiGame = true
        }
        naughtPlayer = when (gameType) {
            Player.Difficulty.HUMAN -> HumanPlayer(Board.NAUGHT, Board.CROSS)
            Player.Difficulty.EASY -> EasyAI(Board.NAUGHT, Board.CROSS)
            Player.Difficulty.MEDIUM -> MediumAI(Board.NAUGHT, Board.CROSS)
            Player.Difficulty.HARD -> HardAI(Board.NAUGHT, Board.CROSS)
        }
    }

    /**
     * Initial update for UI
     */

    private fun initUI() {
        game_tv_crossScore.text = crossScore.toString()
        game_tv_naughtPlayer.text = naughtPlayer.name
        game_tv_naughtScore.text = naughtScore.toString()
    }

    /**
     * Listener for board buttons
     */

    override fun onClick(v: View?) {
        if ((turn == Board.CROSS || !aiGame) && board.isPlaying()) {
            val imageView = v as ImageView
            val tag = Integer.parseInt(imageView.tag.toString())
            val pos = tagToPos(tag)
            val x = pos[0]
            val y = pos[1]
            if (board.isEmpty(x, y)) {
                board.setTile(x, y, turn)
                updateUI(x, y, turn)
                if (board.isPlaying() && aiGame) {
                    chooseAIMove()
                }
            }
        }
    }

    /**
     * Converts 1d value to 2d grid coordinates
     */

    private fun tagToPos(tag: Int): IntArray {
        val x = tag / 3
        val y = tag % 3
        return intArrayOf(x, y)
    }

    /**
     * turn is taken; update which player takes the next turn
     */

    private fun turnTaken() {
        if (board.isPlaying()) {
            when (turn) {
                Board.CROSS -> turn = Board.NAUGHT
                Board.NAUGHT -> turn = Board.CROSS
            }
        }
    }

    /**
     * calculate the AI move and update the board
     */

    private fun chooseAIMove() {
        game_tv_gameStatus.text = resources.getString(R.string.turn_ai)
        val choice = naughtPlayer.chooseMove(board)
        board.setTile(choice[0], choice[1], Board.NAUGHT)
        updateUI(choice[0], choice[1], Board.NAUGHT)
    }

    /**
     * Update UI when a turn is taken
     */

    private fun updateUI(x: Int, y: Int, turn: Int) {
        updateBoardUI(x, y, turn)
        updateTextViews()
        turnTaken()
    }

    /**
     * Update board UI when a turn is taken
     */

    private fun updateBoardUI(x: Int, y: Int, turn: Int) {
        val pos = 3 * x + y
        val crossImage = ContextCompat.getDrawable(this, R.drawable.cross)
        val naughtImage = ContextCompat.getDrawable(this, R.drawable.naught)
        if (turn == Board.CROSS) {
            gridButtons[pos].setImageDrawable(crossImage)
        } else {
            gridButtons[pos].setImageDrawable(naughtImage)
        }
    }

    private fun updateTextViews() {
        when (board.gameState) {
            Board.GameState.PLAYING -> {
                if (turn == Board.CROSS) {
                    game_tv_gameStatus.text = resources.getString(R.string.turn_naught)
                } else {
                    game_tv_gameStatus.text = resources.getString(R.string.turn_cross)
                }
            }
            Board.GameState.CROSS_WIN -> {
                game_tv_gameStatus.text = resources.getString(R.string.win_cross)
                game_tv_playAgain.visibility = View.VISIBLE
                crossScore++
                updateScores()
            }
            Board.GameState.NAUGHT_WIN -> {
                game_tv_gameStatus.text = resources.getString(R.string.win_naught)
                game_tv_playAgain.visibility = View.VISIBLE
                naughtScore++
                updateScores()
            }
            Board.GameState.DRAW -> {
                game_tv_gameStatus.text = resources.getString(R.string.draw)
                game_tv_playAgain.visibility = View.VISIBLE
            }
        }
    }

    private fun resetGame() {
        resetBoard()
        updateFirstTurn()
        updateTextViews()
    }

    private fun resetBoard() {
        board = Board(HEIGHT, WIDTH)
        for (gridButton in gridButtons) {
            gridButton.setImageDrawable(null)
        }
    }

    private fun updateScores() {
        game_tv_crossScore.text = crossScore.toString()
        game_tv_naughtScore.text = naughtScore.toString()
    }

    /**
     * Consider which player starts
     */

    private fun updateFirstTurn() {
        if (crossStart) {
            turn = Board.NAUGHT
            if (aiGame) {
                chooseAIMove()
            }
        } else {
            turn = Board.CROSS
        }
        crossStart = !crossStart

    }

    companion object {
        const val HEIGHT = 3
        const val WIDTH = 3
    }
}