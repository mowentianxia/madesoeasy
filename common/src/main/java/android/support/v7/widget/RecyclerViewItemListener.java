package android.support.v7.widget;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewItemListener extends RecyclerView.SimpleOnItemTouchListener {
    private GestureDetectorCompat gestureDetector;

    public interface OnItemListener {

        void onItemClick(RecyclerView.ViewHolder view, int pos);

        void onItemLongClick(RecyclerView.ViewHolder view, int pos);

        void onItemDoubleClick(RecyclerView.ViewHolder view, int pos);
    }

    public RecyclerViewItemListener(final RecyclerView recyclerView, final OnItemListener listener) {
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        if (listener != null) {
                            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if (childView != null) {
                                listener.onItemClick(recyclerView.getChildViewHolder(childView), recyclerView.getChildAdapterPosition(childView));
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (listener != null) {
                            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if (childView != null) {
                                listener.onItemDoubleClick(recyclerView.getChildViewHolder(childView), recyclerView.getChildAdapterPosition(childView));
                            }
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        if (listener != null) {
                            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            if (childView != null) {
                                listener.onItemLongClick(recyclerView.getChildViewHolder(childView),
                                        recyclerView.getChildAdapterPosition(childView));
                            }
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

}
