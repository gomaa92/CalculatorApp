package com.sgn.apps.calculatorapp.listener

import com.sgn.apps.calculatorapp.model.RecyclerViewItem

interface ItemClickListener {
    fun onItemClick(item: RecyclerViewItem?, position:Int)
}