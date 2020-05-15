package com.manwinder.thescoreinterviewapp.ui.listadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.manwinder.thescoreinterviewapp.R
import com.manwinder.thescoreinterviewapp.data.Sort
import com.manwinder.thescoreinterviewapp.data.TeamData
import com.manwinder.thescoreinterviewapp.data.sortTeamsList
import com.manwinder.thescoreinterviewapp.databinding.RowTeamBinding

class TeamsAdapter(val teamClicked: ((TeamData?, Int) -> Unit)?) : RecyclerView.Adapter<TeamsAdapter.TeamRowViewHolder>() {

    private var teams: List<TeamData>? = null

    fun updateTeams(data: List<TeamData>) {
        teams = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TeamRowViewHolder(RowTeamBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = teams?.size ?: 0

    override fun onBindViewHolder(holder: TeamRowViewHolder, position: Int) {
        holder.bind(teams?.get(position))
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.binding.root.context, R.anim.row_slide_in)
    }

    fun sort(sortField: Sort, ascending: Boolean) {
        teams?: return

        teams = sortTeamsList(teams!!, sortField, ascending)

        notifyDataSetChanged()
    }

    inner class TeamRowViewHolder(val binding: RowTeamBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(team: TeamData?) {
            binding.team = team

            binding.root.setOnClickListener {
                teamClicked?.let { it(team, layoutPosition) }
            }
        }
    }
}
