
package com.rampit.rask3.dailydiary;

import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Adapter.RecyclerViewAdaptercollection;

public class ItemMoveCallback2 extends ItemTouchHelper.Callback {

    int dragFrom = -1;
    int dragTo = -1;
    public static final float ALPHA_FULL = 1.0f;
    private ItemTouchHelperContract mAdapter;

    public ItemMoveCallback2(ItemTouchHelperContract adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }



    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }


    //    @Override
//    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
//                          RecyclerView.ViewHolder target) {
//        mAdapter.onRowMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        return true;
//    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        if(dragFrom == -1) {
            dragFrom =  fromPosition;
        }
        dragTo = toPosition;
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        RecyclerViewAdaptercollection.MyViewHolder myViewHolder1=
                (RecyclerViewAdaptercollection.MyViewHolder) viewHolder;
        mAdapter.onRowMoved(myViewHolder1,viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    private void reallyMoved(RecyclerView.ViewHolder viewHolder,int from, int to) {
        // I guessed this was what you want...
        RecyclerViewAdaptercollection.MyViewHolder myViewHolder1=
                (RecyclerViewAdaptercollection.MyViewHolder) viewHolder;
        mAdapter.oncle(myViewHolder1,from,to);
    }
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder,
                                  int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof RecyclerViewAdaptercollection.MyViewHolder) {
                RecyclerViewAdaptercollection.MyViewHolder myViewHolder=
                        (RecyclerViewAdaptercollection.MyViewHolder) viewHolder;
                mAdapter.onRowSelected(myViewHolder,myViewHolder.getAdapterPosition());
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        RecyclerViewAdaptercollection.MyViewHolder myViewHolder= (RecyclerViewAdaptercollection.MyViewHolder) viewHolder;
        if(dragFrom != -1 && dragTo != -1 && dragFrom != dragTo) {
//            if(dragFrom > 10 && dragTo < 2) {
                reallyMoved(myViewHolder, dragFrom, dragTo);
//            }
            }
        dragFrom = dragTo = -1;
    }
//    @Override
//    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//
//        RecyclerViewAdaptercollection.MyViewHolder myViewHolder=
//                (RecyclerViewAdaptercollection.MyViewHolder) viewHolder;
//        Log.d("cleared22",String.valueOf(myViewHolder.getAdapterPosition()));
//        mAdapter.onRowClear(myViewHolder,myViewHolder.getAdapterPosition());
//
//    }
//    @Override
//    public void clearView(RecyclerView recyclerView,
//                          RecyclerView.ViewHolder viewHolder) {
//        super.clearView(recyclerView, viewHolder);
//
//        if (viewHolder instanceof RecyclerViewAdaptercollection.MyViewHolder) {
//            RecyclerViewAdaptercollection.MyViewHolder myViewHolder=
//                    (RecyclerViewAdaptercollection.MyViewHolder) viewHolder;
//            Log.d("cleared22",String.valueOf(myViewHolder.getAdapterPosition()));
//            mAdapter.onRowClear(myViewHolder,myViewHolder.getAdapterPosition());
//
//        }
//    }

    public interface ItemTouchHelperContract {

        void onRowMoved(RecyclerViewAdaptercollection.MyViewHolder myViewHolder,int fromPosition, int toPosition);
        void onRowSelected(RecyclerViewAdaptercollection.MyViewHolder myViewHolder, int position);
        void onRowClear(RecyclerViewAdaptercollection.MyViewHolder myViewHolder, int position);
        void oncle(RecyclerViewAdaptercollection.MyViewHolder myViewHolder,int from,int to);
    }


}

