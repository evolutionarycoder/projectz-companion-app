package com.forzipporah.mylove.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.forzipporah.mylove.R;


public class ListCursorAdapter extends CursorAdapter {
    private String column;

    public ListCursorAdapter(Context context, Cursor c, int flags, String column) {
        super(context, c, flags);
        this.column = column;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // inflate poem item
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.leftImage = (ImageView) view.findViewById(R.id.ivLeft);
        holder.rightImage = (ImageView) view.findViewById(R.id.ivRight);
        holder.text = (TextView) view.findViewById(R.id.text);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String text = cursor.getString(cursor.getColumnIndex(this.column));

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(text);

        // change image icon appropriately
        if ((cursor.getPosition() % 2) == 0) {
            holder.leftImage.setImageResource(R.drawable.red_heart);
            holder.rightImage.setImageResource(R.drawable.connected_red_heart);
        } else {
            holder.rightImage.setImageResource(R.drawable.red_heart);
            holder.leftImage.setImageResource(R.drawable.connected_red_heart);
        }
    }

    private static class ViewHolder {
        ImageView leftImage, rightImage;
        TextView text;
    }

}
