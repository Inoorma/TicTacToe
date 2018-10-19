package com.inoorma.tic_tac_toe.fragments.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.inoorma.tic_tac_toe.R
import com.inoorma.tic_tac_toe.activities.GameActivity
import com.inoorma.tic_tac_toe.players.Player

abstract class BaseMenuFragment : Fragment() {

    private lateinit var activity: Activity

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is Activity) {
            activity = context
        }
    }

    fun changeFragment(newFragment : Fragment) {
        val manager = this.fragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.menu_area_frame, newFragment)
        transaction?.addToBackStack(this.tag)
        transaction?.commit()
    }

    fun goToGameArea(type: Player.Difficulty) {
        val intent = Intent(this.activity, GameActivity::class.java).apply {
            putExtra("type", type)
        }
        startActivity(intent)
    }
}