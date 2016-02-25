package com.myneighbourhood.Yordan_Yordanov;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myneighbourhood.R;
import com.myneighbourhood.utils.News;
import com.myneighbourhood.utils.User;

import java.util.ArrayList;

/**
 * Created by yordanyordanov on 25/02/2016.
 */
public class CustomNeighbourhoodRowAdapter extends ArrayAdapter<String> implements View.OnClickListener {

    private ArrayList<User> neighbours;

    public CustomNeighbourhoodRowAdapter(Context context, String[] userNames, ArrayList<User> neighbours) {
        super(context, R.layout.custom_neighbourhood_row, userNames);
        this.neighbours = neighbours;
    }

    static class ViewHolderItem {
        ImageView userImage;
        TextView username;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_neighbourhood_row, parent, false);
            viewHolder = new ViewHolderItem();

            viewHolder.userImage = (ImageView) convertView.findViewById(R.id.RowNeighbourImage);
            viewHolder.username = (TextView) convertView.findViewById(R.id.RowNeighbourUsername);
//            viewHolder.userImage.setImageBitmap(neighbours.get(position).getImage());
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        viewHolder.username.setText(neighbours.get(position).getUsername());
//        viewHolder.userImage.setImageIcon();



        return convertView;
    }

    @Override
    public void onClick(View v) {
        //SHOW USER
    }


}
