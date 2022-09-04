package com.dungnd.mvvm.ui.detail.message

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import com.dungnd.mvvm.R
import com.dungnd.mvvm.data.remote.model.Chat
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.databinding.FragmentMessageBinding
import com.dungnd.mvvm.ui.base.BaseFragment
import com.dungnd.mvvm.ui.detail.user.MessageAdapter
import com.dungnd.mvvm.ui.detail.user.UserAdapterOnline
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageFragment : BaseFragment<FragmentMessageBinding, MessageViewModel>() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    private lateinit var userList: ArrayList<User>
    private lateinit var chatList: ArrayList<Chat>
    private lateinit var userListNotMessage: ArrayList<User>
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var receiveRoom : String
    protected var dialog: AlertDialog? = null
    override fun layoutRes(): Int {
        return R.layout.fragment_message
    }

    override fun viewModelClass(): Class<MessageViewModel> = MessageViewModel::class.java

    override fun initView() {
        userList = ArrayList()
        binding.pullToRefresh.setOnRefreshListener {
            loadData()
            binding.pullToRefresh.isRefreshing = false
        }
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        menu()
        loadData()

    }
var countUser = 0
    fun loadData(){
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList = ArrayList()
                chatList = ArrayList()
                for (postSnapshot in snapshot.children) {
                    countUser = snapshot.children.count()
                    val currenMessage = postSnapshot.getValue(User::class.java)
                    if (mAuth.currentUser?.uid != currenMessage?.id) {
                        userList.add(currenMessage!!)
                    }
                }
                userListNotMessage = ArrayList()
                messageAdapter = context?.let { MessageAdapter(it, userList) }!!
                binding.rcvData.adapter = messageAdapter
                var count = 0
                if(userList.size >= countUser - 1){
                    for (i in 0..userList.size - 1) {
                        count = 0;
                        receiveRoom = FirebaseAuth.getInstance().currentUser?.uid + userList[i].id!!
                        mDbRef.child("chats").child(receiveRoom).child("messages").addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                chatList.clear()
                                for (postSnapshot in snapshot.children) {
                                    val chat = postSnapshot.getValue(Chat::class.java)
                                    chatList.add(chat!!)
                                }
                                if(chatList.size <=0 ){
                                    count++
                                    if(userList.size>= countUser - 1)
                                    userListNotMessage.add(userList[i])
//                               messageAdapter.notifyDataSetChanged()
                                }else{
                                    count++
                                    if(userList.size>= countUser - 1)
                                    userList[i].lastMessage = chatList[chatList.size-1].message
                                }
                                if(count == userList.size){
                                    for (userNotMessage in userListNotMessage ){
                                        userList.remove(userNotMessage)
                                    }
                                    binding.rcvData.visibility = View.VISIBLE
                                    binding.progressLogin.visibility = View.GONE
                                    messageAdapter.notifyDataSetChanged()

                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

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


    private fun menu() {

        binding.menuProfile.setOnClickListener {
            findNavController().navigate(
                R.id.action_messageFragment_to_profileFragment
            )
        }
        binding.menuPeople.setOnClickListener {
            findNavController().navigate(
                R.id.action_messageFragment_to_userFragment
            )
        }

    }
}