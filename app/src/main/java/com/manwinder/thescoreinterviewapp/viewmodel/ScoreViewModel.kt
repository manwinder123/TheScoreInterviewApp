package com.manwinder.thescoreinterviewapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.manwinder.thescoreinterviewapp.repository.TeamsRepository
import com.manwinder.thescoreinterviewapp.util.NetworkUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScoreViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TeamsRepository = TeamsRepository(application)

    fun fetchTeamData(): Boolean {
        if (!NetworkUtil.isNetworkConnected()) return false

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

        viewModelScope.launch(Dispatchers.IO + errorHandler) {
            repository.fetchTeamData()
        }

        return true
    }

    fun getTeams() = repository.getTeams()
}

