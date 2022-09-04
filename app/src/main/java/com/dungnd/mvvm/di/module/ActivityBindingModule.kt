package com.dungnd.mvvm.di.module

import com.dungnd.mvvm.ui.detail.DetailFragment
import com.dungnd.mvvm.ui.detail.chat.ChatFragment
import com.dungnd.mvvm.ui.detail.message.MessageFragment
import com.dungnd.mvvm.ui.detail.people.PeopleFragment
import com.dungnd.mvvm.ui.detail.profile.ProfileFragment
import com.dungnd.mvvm.ui.detail.signup.SignUpFragment
import com.dungnd.mvvm.ui.detail.user.UserFragment
import com.dungnd.mvvm.ui.home.HomeFragment
import com.dungnd.mvvm.ui.login.LoginFragment
import com.dungnd.mvvm.ui.main.MainActivity
import com.dungnd.mvvm.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun bindSignUpFragment(): SignUpFragment
    @ContributesAndroidInjector
    abstract fun bindUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun bindPeopleFragment(): PeopleFragment

    @ContributesAndroidInjector
    abstract fun bindChatFragment(): ChatFragment

    @ContributesAndroidInjector
    abstract fun bindProfileFragment(): ProfileFragment
    @ContributesAndroidInjector
    abstract fun bindMessageFragment(): MessageFragment

}