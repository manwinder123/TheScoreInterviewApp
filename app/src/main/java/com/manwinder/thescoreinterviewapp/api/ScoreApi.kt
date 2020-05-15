package com.manwinder.thescoreinterviewapp.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.manwinder.thescoreinterviewapp.data.TeamData
import com.manwinder.thescoreinterviewapp.util.NetworkUtil
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ScoreApi {

    @GET("input.json")
    fun getTeamData(): Deferred<List<TeamData>>

    companion object Factory {
        private const val BASE_URL = "https://raw.githubusercontent.com/scoremedia/nba-team-viewer/master/"

        fun getApi(): ScoreApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(ScoreApi::class.java)
        }
    }

}