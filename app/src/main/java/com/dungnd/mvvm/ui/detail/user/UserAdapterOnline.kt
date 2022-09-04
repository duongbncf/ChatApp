package com.dungnd.mvvm.ui.detail.user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dungnd.mvvm.R
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.ui.detail.chat.ChatActivity
import com.squareup.picasso.Picasso

class UserAdapterOnline(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapterOnline.UserOnlineVH>() {
    //    private lateinit var context: Context
    class UserOnlineVH(view: View) : RecyclerView.ViewHolder(view) {
        val vAvatarOnline: ImageView = view.findViewById(R.id.vAvatarOnline)

//        fun setData(user: User) {
//            tvName.text = user.name
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserOnlineVH {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_user_online, parent, false)
        view.resources
        return UserOnlineVH(view)

    }

    override fun onBindViewHolder(holder: UserOnlineVH, position: Int) {
        val currentUser = userList[position]
        Picasso.get().load(currentUser.profileImage).fit().into(holder.vAvatarOnline)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("image",currentUser.profileImage)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = userList?.size ?: 0

}