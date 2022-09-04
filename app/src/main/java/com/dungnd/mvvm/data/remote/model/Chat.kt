package com.dungnd.mvvm.data.remote.model

import java.time.chrono.ChronoLocalDateTime

class Chat {
    var message: String? = null
    var senderId: String? = null
    constructor(){}

    constructor(message: String?, senderId: String?){
        this.message = message
        this.senderId = senderId

    }
}