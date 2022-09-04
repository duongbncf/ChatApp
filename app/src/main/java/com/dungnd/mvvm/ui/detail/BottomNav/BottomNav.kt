package com.dungnd.mvvm.ui.detail.BottomNav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dungnd.mvvm.R
import com.dungnd.mvvm.ui.detail.chat.ChatActivity
import com.dungnd.mvvm.ui.detail.signup.SignUpFragment

class BottomNav : AppCompatActivity() {
    private val ChatActivity =  ChatActivity()
    private val signUpFragment = SignUpFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        replaceFragment(signUpFragment)
    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(androidx.navigation.fragment.R.id.nav_host_fragment_container, fragment)
            transaction.commit()
        }
    }
}