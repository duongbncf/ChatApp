package com.dungnd.mvvm.ui.detail.user

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import com.dungnd.mvvm.R
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.databinding.FragmentUserBinding
import com.dungnd.mvvm.ui.base.BaseFragment
import com.dungnd.mvvm.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class UserFragment : BaseFragment<FragmentUserBinding, UserViewModel>() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter : UserAdapter
    private lateinit var userAdapterOnline: UserAdapterOnline
    protected var dialog: AlertDialog? = null
    override fun layoutRes(): Int {
        return R.layout.fragment_user
    }

    override fun viewModelClass(): Class<UserViewModel> = UserViewModel::class.java

    override fun initView() {
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        userList = ArrayList()
        userAdapter = context?.let { UserAdapter(it, userList) }!!
        if(binding.rcvData.adapter == userAdapter){
            binding.progressUser.visibility = View.GONE

        }else{
            binding.rcvData.visibility = View.VISIBLE
        }
        binding.rcvData.adapter = userAdapter
        binding.pullToRefresh.setOnRefreshListener {
            loadData()
            binding.pullToRefresh.isRefreshing = false
        }
        loadData()
        menu()

    }
    fun loadData(){
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currenUser = postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currenUser?.id) {
                        userList.add(currenUser!!)
                    }
                }
                userAdapter.notifyDataSetChanged()
                binding.rcvData.visibility = View.VISIBLE
                binding.progressUser.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                (Toast.makeText(context, "loi", Toast.LENGTH_SHORT))
            }

        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            mAuth.signOut()
            findNavController().navigate(
                R.id.action_userFragment_to_loginFragment
            )
            return true
        }
        return true
    }

    override fun getLoaderManager(): LoaderManager {
        return super.getLoaderManager()
    }

    private fun menu(){

        binding.menuProfile.setOnClickListener {
            findNavController().navigate(
                R.id.action_userFragment_to_profileFragment
            )
        }
        binding.menuMessage.setOnClickListener {
            findNavController().navigate(
                R.id.action_userFragment_to_messageFragment
            )
        }
    }


}