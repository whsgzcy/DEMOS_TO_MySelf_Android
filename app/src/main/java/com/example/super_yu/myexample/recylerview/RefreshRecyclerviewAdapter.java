package com.example.super_yu.myexample.recylerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

import java.util.List;


public class RefreshRecyclerviewAdapter extends RecyclerView.Adapter<RefreshRecyclerviewAdapter.MasonryView> {

    private final List<String> list;

    public RefreshRecyclerviewAdapter(List<String> list) {

        this.list=list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView holder, int position) {
        holder.textView.setText(list.get(position));
//        holder.textView.setPadding(0, 20 * position, 0, 0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MasonryView extends RecyclerView.ViewHolder {
        TextView textView;

        public MasonryView(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            textView.setWidth(500);

        }

    }
}



