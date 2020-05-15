package com.manwinder.thescoreinterviewapp.data

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class TeamDataTest {

    lateinit var teamsData: List<TeamData>

    @Before
    fun setUp() {
        teamsData = mutableListOf()
        teamsData.add(TeamData())
    }

    @Test
    fun sortTeamsList() {
    }
}