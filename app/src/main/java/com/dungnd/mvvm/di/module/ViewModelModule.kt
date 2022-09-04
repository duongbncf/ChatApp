package com.dungnd.mvvm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dungnd.mvvm.di.viewmodel.ViewModelFactory
import com.dungnd.mvvm.di.viewmodel.ViewModelKey
import com.dungnd.mvvm.ui.detail.DetailViewModel
import com.dungnd.mvvm.ui.detail.chat.ChatViewModel
import com.dungnd.mvvm.ui.detail.message.MessageViewModel
import com.dungnd.mvvm.ui.detail.people.PeopleViewModel
import com.dungnd.mvvm.ui.detail.profile.ProfileViewModel
import com.dungnd.mvvm.ui.detail.signup.SignUpViewModel
import com.dungnd.mvvm.ui.detail.user.UserFragment
import com.dungnd.mvvm.ui.detail.user.UserViewModel
import com.dungnd.mvvm.ui.home.HomeViewModel
import com.dungnd.mvvm.ui.login.LoginViewModel
import com.dungnd.mvvm.ui.main.MainViewModel
import com.dungnd.mvvm.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    internal abstract fun signUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    internal abstract fun userViewModel(viewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatViewModel::class)
    internal abstract fun chatViewModel(viewModel: ChatViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PeopleViewModel::class)
    internal abstract fun peopleViewModel(viewModel: PeopleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    internal abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    internal abstract fun messageViewModel(viewModel: MessageViewModel): ViewModel
}