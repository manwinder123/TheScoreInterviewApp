package com.manwinder.thescoreinterviewapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.manwinder.thescoreinterviewapp.dao.TeamsDao
import com.manwinder.thescoreinterviewapp.data.TeamData

@Database(entities = [TeamData::class], version = 1, exportSchema = false)
abstract class TeamsDatabase : RoomDatabase() {

    abstract fun teamsDao(): TeamsDao

    companion object {

        @Volatile
        private var INSTANCE: TeamsDatabase? = null

        fun getDatabase(context: Context): TeamsDatabase? {
            if (INSTANCE == null) {
                synchronized(TeamsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TeamsDatabase::class.java, "teams_database"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}