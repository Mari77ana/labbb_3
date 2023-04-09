package com.example.lab_3.api

import retrofit2.Call
import retrofit2.http.GET

interface QuoteTextApi {

    @GET("/inspiration")
    fun getQuoteText(): Call<Quote>
}