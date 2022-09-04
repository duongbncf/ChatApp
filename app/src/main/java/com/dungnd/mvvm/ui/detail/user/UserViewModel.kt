package com.dungnd.mvvm.ui.detail.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.ui.base.BaseViewModel
import javax.inject.Inject

class UserViewModel @Inject constructor() : BaseViewModel() {
    var userList = MutableLiveData<List<User>>()
}