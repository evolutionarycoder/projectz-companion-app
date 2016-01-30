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
import com.forzipporah.mylove.database.contracts.PoemContract;


public class PoemCursorAdapter extends CursorAdapter {
    public PoemCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // inflate poem item
        View       view   = LayoutInflater.from(context).inflate(R.layout.poem_item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.leftImage = (ImageView) view.findViewById(R.id.ivLeft);
        holder.rightImage = (ImageView) view.findViewById(R.id.ivRight);
        holder.poemName = (TextView) view.findViewById(R.id.tvPoemName);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String poemName = cursor.getString(cursor.getColumnIndex(PoemContract.COL_POEM_NAME));

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.poemName.setText(poemName);

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
        TextView poemName;
    }

}
