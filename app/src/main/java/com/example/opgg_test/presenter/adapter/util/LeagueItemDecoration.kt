package com.example.opgg_test.presenter.adapter.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.opgg_test.util.dpToPx

class LeagueItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.left = 16f.dpToPx(parent.context).toInt()
            outRect.right = 4f.dpToPx(parent.context).toInt()
        } else {
            outRect.left = 4f.dpToPx(parent.context).toInt()
            outRect.right = 16f.dpToPx(parent.context).toInt()
        }
    }
}