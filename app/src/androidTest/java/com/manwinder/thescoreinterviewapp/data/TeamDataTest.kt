package com.manwinder.thescoreinterviewapp.data

import android.content.Context
import android.content.res.Resources
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manwinder.thescoreinterviewapp.R
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class TeamDataTest {

    lateinit var teamsData: List<TeamData>

    @Before
    fun setUp() {

    }

    @Test
    fun sortTeamsListNameAsc() {
        val context: Context = getInstrumentation().context
        val resources: Resources = context.resources
        val inputStream: InputStream = resources.openRawResource(R.raw.teams_network_response)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val networkResponse: String = reader.readLine()

        val type = object : TypeToken<List<TeamData>>() {}.type
        teamsData =  Gson().fromJson(networkResponse, type)
    }
}