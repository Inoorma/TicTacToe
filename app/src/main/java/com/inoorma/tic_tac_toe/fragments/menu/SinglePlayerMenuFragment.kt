package com.inoorma.tic_tac_toe.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inoorma.tic_tac_toe.R
import com.inoorma.tic_tac_toe.activities.GameActivity
import com.inoorma.tic_tac_toe.models.Player
import kotterknife.bindView
import mehdi.sakout.fancybuttons.FancyButton

class SinglePlayerMenuFragment : BaseMenuFragment(), View.OnClickListener {

    private val bEasy by bindView<FancyButton>(R.id.single_b_easy)
    private val bMedium by bindView<FancyButton>(R.id.single_b_medium)
    private val bHard by bindView<FancyButton>(R.id.single_b_hard)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_single_player_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners() {
        bEasy.setOnClickListener(this)
        bMedium.setOnClickListener(this)
        bHard.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.single_b_easy -> {
                goToGameArea(Player.Difficulty.EASY)
            }

            R.id.single_b_medium -> {
                goToGameArea(Player.Difficulty.MEDIUM)
            }

            R.id.single_b_hard -> {
                goToGameArea(Player.Difficulty.HARD)
            }
        }
    }

}