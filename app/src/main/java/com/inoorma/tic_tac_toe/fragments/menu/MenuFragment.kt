package com.inoorma.tic_tac_toe.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inoorma.tic_tac_toe.R
import com.inoorma.tic_tac_toe.players.Player
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : BaseMenuFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        menu_b_single.setOnClickListener(this)
        menu_b_multi.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.menu_b_single -> {
                val singlePlayerMenuFragment = SinglePlayerMenuFragment()
                changeFragment(singlePlayerMenuFragment)
            }

            R.id.menu_b_multi -> {
                goToGameArea(Player.Difficulty.HUMAN)
            }
        }
    }
}