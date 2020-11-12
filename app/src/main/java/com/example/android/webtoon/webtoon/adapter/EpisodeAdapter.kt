package com.example.android.webtoon.webtoon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.data.EpisodeList
import com.example.android.webtoon.webtoon.data.contentUnit

class EpisodeAdapter(val context: Context?, val episodeUnit: ArrayList<contentUnit>, val itemClick: (contentUnit) -> Unit) : RecyclerView.Adapter<EpisodeAdapter.Holder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // create a new view
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_episode, parent, false)

        return Holder(view, itemClick)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return episodeUnit.size
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (context != null) {
            holder?.bind(episodeUnit[position], context)
        }
    }

    inner class Holder(itemView: View, itemClick: (contentUnit) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val iv_content = itemView?.findViewById<ImageView>(R.id.iv_content)

        fun bind (list: contentUnit, context: Context) {
            iv_content?.setImageResource(R.drawable.sample8)

            /* 아이템 클릭시 동작 */
            itemView.setOnClickListener { itemClick(list) }
        }
    }
}