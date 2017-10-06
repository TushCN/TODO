package com.example.cryptx.todo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CryptX on 09-09-2017.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    Context mContext;
    ArrayList<TODO> mItems;
    DeleteButtonClickListener mDeleteButtonClickListener;

    public CustomAdapter(@NonNull Context context, ArrayList<TODO> todos,DeleteButtonClickListener deleteButtonClickListener) {
        super(context, 0);
        mContext = context;
        mItems = todos;
        mDeleteButtonClickListener = deleteButtonClickListener;
    }
        @Override
        public int getCount () {
            return mItems.size();
        }


    @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.detail_row_layout,null);
                viewHolder = new ViewHolder();
                TextView title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.title = title;
                Button button = (Button)convertView.findViewById(R.id.deleteButton);
                viewHolder.button = button;
                convertView.setTag(viewHolder);
            }
        viewHolder = (ViewHolder)convertView.getTag();
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                mDeleteButtonClickListener.onDeleteClicked(position,view);
            }
        });
            ViewHolder holder = (ViewHolder)convertView.getTag();
            TODO item = mItems.get(position);
            holder.title.setText(item.getTitle());
            return convertView;
        }

    static class ViewHolder {

        TextView title;
        Button button;

    }
    static interface DeleteButtonClickListener {

        void onDeleteClicked(int position,View v);

    }
}
