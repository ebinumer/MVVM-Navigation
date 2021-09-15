package com.whyte.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.whyte.test.R
import com.whyte.test.utils.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mSessionManager=SessionManager(this)
        if (mSessionManager.appOpenStatus){
//            replaceFragment(fragment = CartFragment())
//              val nav = childFragmentManager.findFragmentById(R.id.cartFragment) as NavHostFragment
//        nav.navController.graph =
//            nav.navController.navInflater.inflate(R.navigation.nav_cart).apply {
//                startDestination = if (mSessionManager.loginStatus) {
//                    R.id.fragmentCart
//                } else R.id.nav_account
//            }

        }
    }
}