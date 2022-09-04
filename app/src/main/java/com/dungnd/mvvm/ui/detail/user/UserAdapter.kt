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
import com.dungnd.mvvm.data.remote.model.User
import com.dungnd.mvvm.ui.detail.chat.ChatActivity
import com.squareup.picasso.Picasso


class UserAdapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserVH>() {
//    private lateinit var context: Context
    class UserVH(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val vAvatar: ImageView = view.findViewById(R.id.vAvatar)

//        fun setData(user: User) {
//            tvName.text = user.name
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        view.resources
        return UserVH(view)

    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val currentUser = userList[position]
        holder.tvName.text = currentUser.name
        Picasso.get().load(currentUser.profileImage).fit().into(holder.vAvatar)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("image",currentUser.profileImage)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = userList?.size ?: 0
}