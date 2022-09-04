package com.dungnd.mvvm.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dungnd.mvvm.R
import com.dungnd.mvvm.databinding.ActivityMainBinding
import com.dungnd.mvvm.di.module.ActivityBindingModule_BindSplashFragment
import com.dungnd.mvvm.ui.detail.user.UserFragment
import com.dungnd.mvvm.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var userFragment: UserFragment
    private lateinit var LoginFragment: LoginFragment

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        binding.bottomNav.setOnItemSelectedListener{
//            when(it.itemId){
//                R.id.message -> {
//                    replaceFragment(userFragment)
//                }
//            }
//            true
//        }
//
//    }
//    private fun replaceFragment(fragment: Fragment){
//        if(fragment != null){
//            val fragmentTransaction = supportFragmentManager.beginTransaction().apply {
//                replace(R.id.frame,fragment)
//            }
//            fragmentTransaction.commit()
//        }
//
//    }
    }
}