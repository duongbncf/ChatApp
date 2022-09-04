package com.dungnd.mvvm.ui.detail.user

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dungnd.mvvm.R
import com.dungnd.mvvm.data.remote.model.Chat
import com.dungnd.mvvm.data.remote.model.Message
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.ui.detail.chat.ChatActivity
import com.squareup.picasso.Picasso


class MessageAdapter(val context: Context, val messageList: ArrayList<User>) :
    RecyclerView.Adapter<MessageAdapter.MessageVH>() {
    //    private lateinit var context: Context
    class MessageVH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val vAvatar: ImageView = view.findViewById(R.id.vAvatar)
        val tvmessage :TextView = view.findViewById(R.id.tvmessage)
//        fun setData(user: User) {
//            tvName.text = user.name
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVH {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false)
        view.resources
        return MessageVH(view)

    }

    override fun onBindViewHolder(holder: MessageVH, position: Int) {
        val currentMessage = messageList[position]
        holder.tvName.text = currentMessage.name
        if(currentMessage.lastMessage == null){
            holder.tvmessage.text = ""

        }else{
            holder.tvmessage.text = currentMessage.lastMessage

        }
        Picasso.get().load(currentMessage.profileImage).fit().into(holder.vAvatar)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("image",currentMessage.profileImage)
            intent.putExtra("name", currentMessage.name)
            intent.putExtra("uid", currentMessage.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = messageList?.size ?: 0
}