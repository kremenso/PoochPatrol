package com.example.poochpatrol.adapter;

import java.util.List;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.poochpatrol.R;
import com.example.poochpatrol.model.FacebookFriend;

public class FacebookFriendAdapter extends ArrayAdapter<FacebookFriend> {

	private final List<FacebookFriend> list;
    private final Activity context;
	
	public FacebookFriendAdapter(Activity context, List<FacebookFriend> list) {
        super(context, R.layout.facebook_friend, list);
        this.context = context;
        this.list = list;
    }
 
    static class ViewHolder {
        protected TextView text, sub;
        protected CheckBox checkbox;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.facebook_friend, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.sub = (TextView) view.findViewById(R.id.sub);
            viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
            viewHolder.checkbox
                    .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView,
                                boolean isChecked) {
                            FacebookFriend element = (FacebookFriend) viewHolder.checkbox.getTag();
//                            element.setSelected(buttonView.isChecked());
                            System.out.println("Checked : " + element.getName());
                        }
                    });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText("ID:" + list.get(position).getId());
        holder.sub.setText(list.get(position).getName());
//        holder.checkbox.setChecked(list.get(position).isSelected());
        return view;
    }

}
