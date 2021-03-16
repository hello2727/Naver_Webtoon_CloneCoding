package com.example.android.webtoon.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.webtoon.R
import com.example.android.webtoon.model.remote.EpisodeList

class deepWebtoonAdapter(val context: Context?, val deepWebtoonList: ArrayList<EpisodeList>, val itemClick: (EpisodeList) -> Unit) : RecyclerView.Adapter<deepWebtoonAdapter.Holder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // create a new view
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_deepwebtoon, parent, false)

        return Holder(view, itemClick)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return deepWebtoonList.size
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (context != null) {
            holder?.bind(deepWebtoonList[position], context)
        }
    }

    inner class Holder(itemView: View, itemClick: (EpisodeList) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val iv_preview = itemView?.findViewById<ImageView>(R.id.iv_preview)
        val tv_title = itemView?.findViewById<TextView>(R.id.tv_title)
        val iv_starCnt = itemView?.findViewById<ImageView>(R.id.iv_starCnt)
        val tv_star = itemView?.findViewById<TextView>(R.id.tv_star)
        val tv_date = itemView?.findViewById<TextView>(R.id.tv_date)

        fun bind (list: EpisodeList, context: Context) {
            if (list.iv_preview != "") {
                Glide.with(context)
                    .load(list.iv_preview)
                    .into(iv_preview)
            } else {
                iv_preview?.setImageResource(R.drawable.n)
            }

            tv_title?.text = list.tv_title
            Glide.with(context)
                .load(R.drawable.redstar)
                .into(iv_starCnt)
            tv_star?.text = list.tv_star
            tv_date?.text = list.tv_date

            /* 아이템 클릭시 동작 */
            itemView.setOnClickListener { itemClick(list) }
        }
    }
}