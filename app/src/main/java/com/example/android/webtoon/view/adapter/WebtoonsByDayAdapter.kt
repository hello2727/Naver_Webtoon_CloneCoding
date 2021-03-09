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
import com.example.android.webtoon.model.remote.ListItem

class WebtoonsByDayAdapter(val context: Context?, val webtoonList: ArrayList<ListItem>, val itemClick: (ListItem) -> Unit) : RecyclerView.Adapter<WebtoonsByDayAdapter.Holder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // create a new view
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_webtoonlist, parent, false)

        return Holder(view, itemClick)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return webtoonList.size
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val item = webtoonList[position]

        if (context != null) {
            holder?.bind(item, context)
        }
    }

    inner class Holder(itemView: View, itemClick: (ListItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val iv_preview = itemView?.findViewById<ImageView>(R.id.iv_preview)
        val tv_title = itemView?.findViewById<TextView>(R.id.tv_title)
        val tv_star = itemView?.findViewById<TextView>(R.id.tv_star)
        val iv_upOrpause = itemView?.findViewById<ImageView>(R.id.iv_upOrpause)
        val tv_author = itemView?.findViewById<TextView>(R.id.tv_date)
        val iv_isNew = itemView?.findViewById<ImageView>(R.id.iv_isNew)

        fun bind (list: ListItem, context: Context) {
            if (list.iv_preview != "") {
                Glide.with(context)
                    .load(list.iv_preview)
                    .into(iv_preview)
            } else {
                Glide.with(context)
                    .load(R.drawable.n)
                    .into(iv_preview)
            }

            if (list.iv_upOrpause == "휴재") {
                iv_upOrpause.visibility = View.VISIBLE

                Glide.with(context)
                    .load(R.drawable.pause)
                    .into(iv_upOrpause)
            } else if(list.tv_cutToon == "컷툰"){
                iv_upOrpause.visibility = View.VISIBLE

                Glide.with(context)
                    .load(R.drawable.cuttoon)
                    .into(iv_upOrpause)
            }else if(list.check_isToday == true){
                iv_upOrpause.visibility = View.VISIBLE

                Glide.with(context)
                    .load(R.drawable.up)
                    .into(iv_upOrpause)
            }else{
                iv_upOrpause.visibility = View.GONE
            }

            if(list.tv_isNew == "NEW"){
                iv_isNew.visibility = View.VISIBLE
            }else{
                iv_isNew.visibility = View.INVISIBLE
            }

            tv_title?.text = list.tv_title
            tv_star?.text = list.tv_star
            tv_author?.text = list.tv_author

            /* 아이템 클릭시 동작 */
            itemView.setOnClickListener { itemClick(list) }
        }
    }
}