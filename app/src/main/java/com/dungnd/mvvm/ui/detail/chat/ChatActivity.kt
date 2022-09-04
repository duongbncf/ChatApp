package com.dungnd.mvvm.ui.detail.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dungnd.mvvm.R
import com.dungnd.mvvm.data.remote.model.Chat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.sql.Date


class ChatActivity : AppCompatActivity() {
    var receiveRoom: String? = null
    var senderRoom: String? = null
    private lateinit var vSent: ImageView
//    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatList: ArrayList<Chat>
    private lateinit var mDbref: DatabaseReference
    private lateinit var messageBox: EditText
    private lateinit var rcvData: RecyclerView
    private lateinit var titleName: TextView
    private lateinit var vBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)
        rcvData = findViewById(R.id.rcvData)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.setStackFromEnd(true);
        rcvData.layoutManager = layoutManager
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")
        titleName = findViewById(R.id.titleName)
        vSent = findViewById(R.id.vsend1)
        vBack = findViewById(R.id.vback)
        messageBox = findViewById(R.id.messageBox)
        mDbref = FirebaseDatabase.getInstance().getReference()
        chatList = ArrayList()
        receiveRoom = senderUid + receiveruid
        senderRoom = receiveruid + senderUid
        vBack.setOnClickListener{
       finish()
        }
        titleName.text = name
        val chatAdapter = ChatAdapter(this, chatList)
        rcvData.adapter = chatAdapter
        mDbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
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
        vSent.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Chat(message, senderUid)
            mDbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbref.child("chats").child(receiveRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")

            rcvData.postDelayed(
                Runnable { rcvData.smoothScrollToPosition(chatList.size-1) },
                100
            )
        }
    }
}