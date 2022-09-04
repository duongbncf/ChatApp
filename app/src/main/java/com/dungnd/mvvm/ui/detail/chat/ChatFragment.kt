package com.dungnd.mvvm.ui.detail.chat

import android.R
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.dungnd.mvvm.data.remote.model.Chat
import com.dungnd.mvvm.databinding.FragmentChatBinding
import com.dungnd.mvvm.ui.base.BaseFragment
import com.dungnd.mvvm.ui.detail.user.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class ChatFragment : BaseFragment<FragmentChatBinding, ChatViewModel>() {
    var receiveRoom: String? = null
    var senderRoom: String? = null
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatList: ArrayList<Chat>
    private lateinit var mDbref: DatabaseReference
    override fun layoutRes(): Int {
       return com.dungnd.mvvm.R.layout.fragment_chat
    }

    override fun viewModelClass(): Class<ChatViewModel> = ChatViewModel::class.java

    override fun initView() {
        mDbref = FirebaseDatabase.getInstance().getReference()
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val name = requireActivity().intent.getStringExtra("name")
        val receiveruid = requireActivity().intent.getStringExtra("uid")
        receiveRoom = senderUid + receiveruid
        senderRoom = receiveruid  + senderUid
        (activity as AppCompatActivity?)!!.supportActionBar?.title = name
        chatList = ArrayList()
        chatAdapter = context?.let { ChatAdapter(it,chatList) }!!
       mDbref.child("chats").child(senderRoom!!).child("message").addValueEventListener(object : ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               chatList.clear()
               for (postSnapshot in snapshot.children) {
                   val chat = postSnapshot.getValue(Chat::class.java)
                   chatList.add(chat!!)
               }
               chatAdapter.notifyDataSetChanged()
           }
           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       })
        binding.vsend.setOnClickListener{
           val message =  binding.messageBox.text.toString()
            val messageObject = Chat(message,senderUid)
            mDbref.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbref.child("chats").child(receiveRoom!!).child("messages").push().setValue(messageObject)
                }
            binding.messageBox.setText("")
            }
        }
    }


