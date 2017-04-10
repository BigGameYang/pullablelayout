package com.yz.pullablelayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yz.pullablelayout.activitys.ContainerActivity;

import java.util.List;

/**
 * Created by YangZhi on 2017/4/7 1:58.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder>{

    List<Demo> demos;

    int count;

    public void setData(List<Demo> demos){
        this.demos=demos;
        if(demos!=null){
            count=demos.size();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onBindViewHolder(final DemoAdapter.ViewHolder holder, int position) {
        final Demo demo=demos.get(position);
        holder.textView.setText(demo.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(demo.isStartOther()){
                    startToOther(v.getContext(),demo);
                    return;
                }
                Toast.makeText(v.getContext(),demo.getOnClickText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView=new TextView(parent.getContext());
        RecyclerView.LayoutParams lp=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,DisplayUtils.convertDIP2PX(parent.getContext(),100));
        textView.setLayoutParams(lp);
        textView.setTextSize(24);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        return new ViewHolder(textView);
    }


    private void startToOther(Context context,Demo demo){
        ContainerActivity.goPage(context,demo.getName(),demo.getArguments(),demo.getFragmentClass());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ViewHolder(View rootView){
            super(rootView);
            this.textView=(TextView) rootView;
        }
    }
}
