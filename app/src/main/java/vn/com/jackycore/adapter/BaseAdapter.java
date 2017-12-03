package vn.com.jackycore.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky Hua on 9/20/2017.
 */

public class BaseAdapter<M, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    private int lastPostition = -1;
    protected Activity activity;
    private List<M> list;
    private boolean enableAnimation;
    private LayoutInflater layoutInflater;

    public BaseAdapter(Activity activity) {
        this.activity = activity;
        this.list = new ArrayList<>();
        this.enableAnimation = true;
        this.layoutInflater = LayoutInflater.from(activity.getApplicationContext());
    }

    public BaseAdapter(Activity activity, boolean enableAnimation) {
        this.activity = activity;
        this.list = new ArrayList<>();
        this.enableAnimation = enableAnimation;
        this.layoutInflater = LayoutInflater.from(getContext());
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        if (enableAnimation) {
            setAnimation(holder.itemView, position);
        }
    }

    private void setAnimation(View itemView, int position) {

    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public List<M> getList() {
        return list;
    }

    public Activity getContext() {
        return activity;
    }

    public void add(int position, M data) {
        if (data == null) {
            return;
        }
        if (position < 0) {
            return;
        }
        list.add(position, data);
        notifyItemInserted(list.size());
    }

    public void addAll(List<M> data) {
        if (data == null) {
            return;
        }
        int position = getItemCount();
        list.addAll(data);
        notifyItemChanged(position, list.size());
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public void deleteItem(int pos) {
        list.remove(pos);
        notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public M getItem(int pos) {
        if (pos >= 0 & pos < getItemCount()) {
            return list.get(pos);
        }
        return null;
    }

    public List<M> getItems() {
        return list;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    @Override
    public void onViewDetachedFromWindow(V holder) {
        if (enableAnimation) {
            holder.itemView.clearAnimation();
        }
    }

    protected boolean attachAnimationToView() {
        return enableAnimation;
    }

}
