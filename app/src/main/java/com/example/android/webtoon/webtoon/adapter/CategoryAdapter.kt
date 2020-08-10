package com.example.android.webtoon.webtoon.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.webtoon.R
import com.example.android.webtoon.webtoon.data.ListItem

class CategoryAdapter(val context: Context?, val webtoonList: ArrayList<ListItem>) : RecyclerView.Adapter<CategoryAdapter.Holder>() {
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // create a new view
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout_webtoonlist, parent, false)

        return Holder(view)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return webtoonList.size
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (context != null) {
            holder?.bind(webtoonList[position], context)
        }
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_preview = itemView?.findViewById<ImageView>(R.id.iv_preview)
        val tv_title = itemView?.findViewById<TextView>(R.id.tv_title)
        val tv_star = itemView?.findViewById<TextView>(R.id.tv_star)
        val iv_upOrpause = itemView?.findViewById<ImageView>(R.id.iv_upOrpause)
        val tv_author = itemView?.findViewById<TextView>(R.id.tv_author)

        fun bind (list: ListItem, context: Context) {
            if (list.iv_preview != "") {
                val resourceId = context.resources.getIdentifier(list.iv_preview, "drawable", context.packageName)
                iv_preview?.setImageResource(resourceId)
            } else {
                iv_preview?.setImageResource(R.drawable.n)
            }

            if (list.iv_upOrpause == "휴재") {
                iv_upOrpause?.setImageResource(R.drawable.pause)
            } else {
                iv_upOrpause?.setImageResource(R.drawable.up)
            }

            tv_title?.text = list.tv_title
            tv_star?.text = list.tv_star
            tv_author?.text = list.tv_author
        }
    }
}