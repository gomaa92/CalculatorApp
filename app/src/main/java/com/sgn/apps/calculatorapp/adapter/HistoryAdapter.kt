package com.sgn.apps.calculatorapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sgn.apps.calculatorapp.listener.ItemClickListener
import com.sgn.apps.calculatorapp.R
import com.sgn.apps.calculatorapp.model.RecyclerViewItem
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter(mListener: ItemClickListener) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private lateinit var mData: ArrayList<RecyclerViewItem>
    private lateinit var mListener: ItemClickListener

    init {
        this.mListener = mListener
        mData = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        if (mData != null)
            return this.mData.size
        else return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData.get(position)
        holder.onBind(item)
        holder.itemView.setOnClickListener {
            mListener.onItemClick(item, position)
        }
    }

    fun addItem(item: RecyclerViewItem) {
        mData.add(item)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mData.removeAt(position)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun onBind(item: RecyclerViewItem) {
            itemView.history_item_text_view.setText(item.operationType + item.operationValue)
        }
    }
}