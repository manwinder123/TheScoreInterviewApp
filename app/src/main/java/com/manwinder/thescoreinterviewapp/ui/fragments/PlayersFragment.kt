package com.manwinder.thescoreinterviewapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter

import com.manwinder.thescoreinterviewapp.R
import com.manwinder.thescoreinterviewapp.data.TeamData
import com.manwinder.thescoreinterviewapp.ui.listadapters.PlayersAdapter
import com.manwinder.thescoreinterviewapp.ui.listadapters.TeamsAdapter
import kotlinx.android.synthetic.main.fragment_players.view.players_recycler_view

class PlayersFragment : Fragment() {

    private val TAG = "PLAYERS_FRAGMENT"

    private lateinit var teamAdapter: TeamsAdapter
    private lateinit var playersAdapter: PlayersAdapter
    private lateinit var mergeAdapter: MergeAdapter

    private var teamData: TeamData? = null
    var teamPosition: Int = 0

    fun updateData(teamData: TeamData) {
        if (!::mergeAdapter.isInitialized) return

        this.teamData = teamData

        teamAdapter.updateTeams(listOf(teamData))
        playersAdapter.updateData(teamData.players.sortedBy { it.first_name })

        mergeAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Setting the team position will allow the activity that created this fragment to refill the recycler view with the selected teams data
        savedInstanceState?.run {
            teamPosition = getInt(TAG)
        }

        val view = inflater.inflate(R.layout.fragment_players, container, false)

        teamAdapter = TeamsAdapter(null)
        teamData?.let { teamAdapter.updateTeams(listOf(it)) }
        playersAdapter = PlayersAdapter(teamData?.players?.sortedBy { it.first_name })

        mergeAdapter = MergeAdapter(teamAdapter, playersAdapter)

        view.players_recycler_view?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = mergeAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(TAG, teamPosition)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            teamData: TeamData?,
            teamPosition: Int
        ) = PlayersFragment().apply {
            this.teamData = teamData
            this.teamPosition = teamPosition
        }
    }
}
