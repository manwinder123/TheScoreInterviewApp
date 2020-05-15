package com.manwinder.thescoreinterviewapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.manwinder.thescoreinterviewapp.R
import com.manwinder.thescoreinterviewapp.data.Sort
import com.manwinder.thescoreinterviewapp.data.TeamData
import com.manwinder.thescoreinterviewapp.ui.fragments.PlayersFragment
import com.manwinder.thescoreinterviewapp.ui.listadapters.TeamsAdapter
import com.manwinder.thescoreinterviewapp.viewmodel.ScoreViewModel
import kotlinx.android.synthetic.main.activity_main.teams_recycler_view
import kotlinx.android.synthetic.main.activity_main.teams_swipe_refresh_layout


class MainActivity : AppCompatActivity() {

    private val scoreViewModel by lazy { ViewModelProviders.of(this).get(ScoreViewModel::class.java)}

    // Is called when a team's row is clicked, this opens a fragment that contains that team's players
    private val teamClicked = fun(teamData: TeamData?, teamPosition: Int) {
        val fragment = PlayersFragment.newInstance(teamData, teamPosition)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .addToBackStack(getString(R.string.players_fragment_name))
            .commit()

        supportActionBar?.title = teamData?.full_name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sortMenuItem?.isVisible = false
    }

    private val teamsAdapter = TeamsAdapter(teamClicked)

    private var sortMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        teams_swipe_refresh_layout?.isRefreshing = true

        teams_recycler_view?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = teamsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        scoreViewModel.getTeams()?.observe(this, Observer {
            teamsAdapter.updateTeams(it)

            teams_swipe_refresh_layout?.isRefreshing = false

            // PlayersFragment is open when this is true, this is used to refill the recyclerview in the PlayersFragment
            if (supportFragmentManager.backStackEntryCount > 0) {
                val fragment = supportFragmentManager.fragments.first() as PlayersFragment
                fragment.updateData(it[fragment.teamPosition])
            }
        })

        if (!scoreViewModel.fetchTeamData()) teams_swipe_refresh_layout?.isRefreshing = false

        teams_swipe_refresh_layout?.setOnRefreshListener {
            if (!scoreViewModel.fetchTeamData()) teams_swipe_refresh_layout?.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        sortMenuItem = menu?.findItem(R.id.menu_sort)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                resetViewForMainActivity()
            }

            R.id.menu_sort_name -> {
                if (item.title == getString(R.string.sort_by_team_name_asc)) {
                    item.title = getString(R.string.sort_by_team_name_dsc)
                } else {
                    item.title = getString(R.string.sort_by_team_name_asc)
                }
                teamsAdapter.sort(Sort.NAME, item.title == getString(R.string.sort_by_team_name_dsc))
            }

            R.id.menu_sort_wins -> {
                if (item.title == getString(R.string.sort_by_wins_asc)) {
                    item.title = getString(R.string.sort_by_wins_dsc)
                } else {
                    item.title = getString(R.string.sort_by_wins_asc)
                }
                teamsAdapter.sort(Sort.WINS, item.title == getString(R.string.sort_by_wins_dsc))
            }

            R.id.menu_sort_losses -> {
                if (item.title == getString(R.string.sort_by_losses_asc)) {
                    item.title = getString(R.string.sort_by_losses_dsc)
                } else {
                    item.title = getString(R.string.sort_by_losses_asc)
                }
                teamsAdapter.sort(Sort.LOSSES, item.title == getString(R.string.sort_by_losses_dsc))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            resetViewForMainActivity()
        } else {
            super.onBackPressed()
        }
    }

    /*
    ** Will reset the view back to what it was in the main activity, this is called when
    *  the PlayersFragment should be popped
    */
    private fun resetViewForMainActivity() {
        supportFragmentManager.popBackStack(
            getString(R.string.players_fragment_name),
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        supportActionBar?.title = getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        sortMenuItem?.isVisible = true
    }


}
