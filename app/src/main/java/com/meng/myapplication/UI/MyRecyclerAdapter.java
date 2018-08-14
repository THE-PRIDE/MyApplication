package com.meng.myapplication.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meng.myapplication.R;

/**
 * Created by LMY
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private String[] tv = {"fds", "sLPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPd", "<", "fd", "as", "gogoing", "mxlg", "ming", "uzi", "omg", "rng", "we"};

    public MyRecyclerAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyRecyclerAdapter.ViewHolder(layoutInflater.inflate(R.layout.activity_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.info.setText(tv[position]);
    }

    @Override
    public int getItemCount() {
        return tv == null ? 0 : tv.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView info;

        public ViewHolder(View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.tv_item_info);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("LMY","---position"+getAdapterPosition());
                }
            });
            info.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    tv[getAdapterPosition()].
                    return true;
                }
            });
        }
    }
}
