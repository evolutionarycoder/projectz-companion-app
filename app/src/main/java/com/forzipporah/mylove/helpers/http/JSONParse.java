package com.forzipporah.mylove.helpers.http;

import com.forzipporah.mylove.models.ILove;
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

    public static ArrayList<ILove> parseJSONToILoves(String jsonString) {
        ArrayList<ILove> iLoves = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                String serverId = json.getString("id");
                String love = json.getString("love");
                ILove ilove = new ILove(serverId, love);

                iLoves.add(ilove);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return iLoves;
    }

}
