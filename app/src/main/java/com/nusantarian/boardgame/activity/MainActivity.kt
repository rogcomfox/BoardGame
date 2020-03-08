package com.nusantarian.boardgame.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.nusantarian.boardgame.R
import com.nusantarian.boardgame.fragment.MainFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set frame layout for fragment
        val frameLayout = FrameLayout(this)
        frameLayout.id = R.id.frame_container
        setContentView(
            frameLayout, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        if (savedInstanceState == null) {
            getMainFragment()
        }
        //listen for changes in back stack
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    private fun getMainFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, MainFragment())
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack() else super.onBackPressed()
    }

    override fun onBackStackChanged() {

    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}
