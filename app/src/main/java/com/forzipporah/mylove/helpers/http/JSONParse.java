package com.forzipporah.mylove.helpers.http;

import android.content.ContentValues;

import com.forzipporah.mylove.models.Poem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParse {
    public static ArrayList<Poem> parseJSONToPoems(String jsonString) {
        ArrayList<Poem> poems = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String serverId = jsonObject.getString("id");
                String author = jsonObject.getString("author");
                String date = jsonObject.getString("date");
                String name = jsonObject.getString("name");
                String poem = jsonObject.getString("poem");
                String favourite = jsonObject.getString("favourite");
                Poem poemObj = new Poem(serverId, name, poem, favourite, date, author);
                poems.add(poemObj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return poems;
    }

    public static ContentValues[] createContentValuesArrayFromPoemsList(ArrayList<Poem> poems) {
        ContentValues[] values = new ContentValues[poems.size()];
        for (int i = 0; i < poems.size(); i++) {
            values[i] = poems.get(i).createContentValues();
        }
        return values;
    }
}
