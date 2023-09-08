package com.rampit.rask3.dailydiary.Adapter;


import androidx.recyclerview.widget.RecyclerView;

public abstract class StartDragListener {


    public interface requestDrag {
        public void requestDrag(RecyclerView.ViewHolder viewHolder);
    }
}
