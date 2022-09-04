package com.dungnd.mvvm.ui.detail.people

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dungnd.mvvm.R
import com.dungnd.mvvm.databinding.FragmentPeopleBinding
import com.dungnd.mvvm.ui.base.BaseFragment

class PeopleFragment : BaseFragment<FragmentPeopleBinding,PeopleViewModel>() {
    override fun layoutRes(): Int {
        TODO("Not yet implemented")
    }

    override fun viewModelClass(): Class<PeopleViewModel> = PeopleViewModel::class.java

    override fun initView() {
        TODO("Not yet implemented")
    }


}