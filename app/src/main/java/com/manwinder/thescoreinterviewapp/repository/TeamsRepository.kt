package com.manwinder.thescoreinterviewapp.repository

import android.app.Application
import com.manwinder.thescoreinterviewapp.api.ScoreApi
import com.manwinder.thescoreinterviewapp.dao.TeamsDao
import com.manwinder.thescoreinterviewapp.data.TeamData
import com.manwinder.thescoreinterviewapp.database.TeamsDatabase

class TeamsRepository(application: Application) {

    private var teamsDao: TeamsDao?

    init {
        val db = TeamsDatabase.getDatabase(application)
        teamsDao = db?.teamsDao()
    }

    fun getTeams() = teamsDao?.getTeams()

    private fun addTeams(teamsData: List<TeamData>){
        teamsDao?.addTeams(teamsData)
    }

    /**
     * @return true if connected to network
     * @return false if not connect to internet
     */
    suspend fun fetchTeamData() {
        val service = ScoreApi.getApi()

        val request = service.getTeamData()
        val response = request.await()
        addTeams(response)
    }
}