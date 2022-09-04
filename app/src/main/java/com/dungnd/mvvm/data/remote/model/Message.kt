package com.dungnd.mvvm.data.remote.model

class Message {
    var name: String? = null
    var email: String? = null
    var id: String? = null
    var profileImage: String? = null

    constructor(){}

    constructor(name:String,email:String,id:String, profileImage: String){
        this.name=name
        this.email=email
        this.id=id
        this.profileImage = profileImage
    }
}