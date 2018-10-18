package com.inoorma.tic_tac_toe.activities

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.inoorma.tic_tac_toe.fragments.menu.MenuFragment
import com.inoorma.tic_tac_toe.R

class MenuActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        if (savedInstanceState == null) {
            val menuFragment = MenuFragment()
            supportFragmentManager.beginTransaction().add(R.id.menu_area_frame, menuFragment).commit()
        }
    }
}
