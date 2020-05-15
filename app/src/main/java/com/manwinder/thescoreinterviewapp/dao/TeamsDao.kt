package com.manwinder.thescoreinterviewapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manwinder.thescoreinterviewapp.data.TeamData

@Dao
interface TeamsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTeams(teamsData: List<TeamData>)

    @Query("SELECT * from teams_table ORDER BY full_name ASC")
    fun getTeams() : LiveData<List<TeamData>>

    @Query("SELECT COUNT(*) from teams_table")
    fun count() : Int
}