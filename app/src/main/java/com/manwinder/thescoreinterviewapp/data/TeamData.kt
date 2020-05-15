package com.manwinder.thescoreinterviewapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manwinder.thescoreinterviewapp.ui.listadapters.TeamsAdapter

@Entity(tableName = "teams_table")
@TypeConverters(PlayersListConverter::class)
data class TeamData(
    val full_name: String,

    @PrimaryKey val id: Int,

    val losses: Int,
    val players: List<PlayerData>,
    val wins: Int
)

data class PlayerData(
    val first_name: String,
    val id: Int,
    val last_name: String,
    val number: Int,
    val position: String
)

class PlayersListConverter {

    @TypeConverter
    fun fromPlayersList(value: List<PlayerData>): String {
        val type = object : TypeToken<List<PlayerData>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toPlayersList(value: String): List<PlayerData> {
        val type = object : TypeToken<List<PlayerData>>() {}.type
        return Gson().fromJson(value, type)
    }
}

enum class Sort {
    NAME, WINS, LOSSES
}

fun sortTeamsList(teams:List<TeamData>, sortField: Sort, ascending: Boolean): List<TeamData> {
    if (teams.isNullOrEmpty()) return teams

    return when (sortField) {
        Sort.NAME -> {
            if (ascending) {
                teams.sortedBy { it.full_name }
            } else {
                teams.sortedByDescending { it.full_name }
            }
        }
        Sort.WINS -> {
            if (ascending) {
                teams.sortedBy { it.wins }
            } else {
                teams.sortedByDescending { it.wins }
            }

        }
        Sort.LOSSES -> {
            if (ascending) {
                teams.sortedBy { it.losses }
            } else {
                teams.sortedByDescending { it.losses }
            }
        }
    }
}
