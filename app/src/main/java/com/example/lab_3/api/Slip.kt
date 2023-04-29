package com.example.lab_3.api

import com.google.gson.annotations.SerializedName

class Slip {

    @SerializedName("advice")
    var myAdvice: String = ""

    @SerializedName("id")
    var myId : Int = 0

    override fun toString(): String {
        return "Slip(myAdvice='$myAdvice', myId=$myId)"
    }


}