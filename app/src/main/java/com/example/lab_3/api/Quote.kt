package com.example.lab_3.api

import com.google.gson.annotations.SerializedName

class Quote {

    @SerializedName("quote")
    val myProfileQuote: String = ""

    @SerializedName("author")
    val author: String = ""


    override fun toString(): String {
        return "Quote(myQuote='$myProfileQuote')"
    }


}