package com.manwinder.thescoreinterviewapp.ui.listadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.manwinder.thescoreinterviewapp.R
import com.manwinder.thescoreinterviewapp.data.PlayerData
import com.manwinder.thescoreinterviewapp.databinding.RowPlayerBinding

class PlayersAdapter(var players: List<PlayerData>?) : RecyclerView.Adapter<PlayersAdapter.PlayerRowViewHolder>() {

    fun updateData(data: List<PlayerData>) {
        players = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlayerRowViewHolder(RowPlayerBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int = players?.size ?: 0

    override fun onBindViewHolder(holder: PlayerRowViewHolder, position: Int) {
        holder.bind(players?.get(position))
        holder.itemView.animation = AnimationUtils.loadAnimation(holder.binding.root.context, R.anim.row_slide_in)
    }

    class PlayerRowViewHolder(val binding: RowPlayerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(player: PlayerData?) {
            binding.player = player
        }
    }
}
