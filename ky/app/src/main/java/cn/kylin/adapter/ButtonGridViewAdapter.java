package cn.kylin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.kylin.R;
import cn.kylin.utils.Utils;

/**
 * Created by kylinhuang on 26/12/2017.
 */

public class ButtonGridViewAdapter extends RecyclerView.Adapter<ButtonGridViewAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private int[][] list  ;


    public ButtonGridViewAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void update(int[][] sys) {
        this.list = sys;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.button_gridview_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv.setText(Utils.getContext().getResources().getString(list[position][0]));

        holder.iv.setBackground(ContextCompat.getDrawable(
                Utils.getContext(), list[position][1]));

        holder.ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener)onItemClickListener.onClick(position);
            }
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (null == list) return 0;
        return list.length;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

//        FrameLayout fl;
        TextView tv;
        AppCompatImageView iv;
        RelativeLayout ml;

        public MyViewHolder(View itemView) {
            super(itemView);
//            fl = (FrameLayout) itemView.findViewById(R.id.frame_layout);
            iv = (AppCompatImageView) itemView.findViewById(R.id.icon);
            tv = (TextView) itemView.findViewById(R.id.textview);
            ml = (RelativeLayout) itemView.findViewById(R.id.material_ripple_layout);
//            convertView.setTag(holder);


        }


    }
}