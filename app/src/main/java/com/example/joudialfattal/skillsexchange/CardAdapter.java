package com.example.joudialfattal.skillsexchange;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<ListItem> items;

    public CardAdapter(String[] skills, String[] ids){
        super();
        items = new ArrayList<>();
        for(int i =0; i<skills.length; i++){
            ListItem item = new ListItem();
            item.setSkill(skills[i]);
            item.setId(ids[i]);
            items.add(item);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem myAdapter =  items.get(position);
        holder.skillName.setText(myAdapter.getSkill());
        holder.skillId.setText(String.valueOf(myAdapter.getId()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView skillId;
        public TextView skillName;

        public ViewHolder(View itemView) {
            super(itemView);
            skillId = (TextView) itemView.findViewById(R.id.skillId);
            skillName = (TextView) itemView.findViewById(R.id.skillName);

        }
    }
}