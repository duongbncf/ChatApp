package com.dungnd.mvvm.data.remote.model

import android.widget.ImageView

class User {
    var name: String? = null
    var email: String? = null
    var id: String? = null
    var profileImage: String? = null
    var lastMessage: String? = null
    constructor(){}

    constructor(name:String,email:String,id:String, profileImage: String){
        this.name=name
        this.email=email
        this.id=id
        this.profileImage = profileImage
    }

    constructor(name:String,email:String,id:String, profileImage: String, lastMessage: String){
        this.name=name
        this.email=email
        this.id=id
        this.profileImage = profileImage
        this.lastMessage = lastMessage
    }
}