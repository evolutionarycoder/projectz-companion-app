package com.forzipporah.mylove.helpers.http;

import com.forzipporah.mylove.models.ILove;
import com.forzipporah.mylove.models.Memory;
import com.forzipporah.mylove.models.Notification;
import com.forzipporah.mylove.models.Poem;
import com.forzipporah.mylove.models.Promise;
import com.forzipporah.mylove.models.Reassure;

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

    public static ArrayList<Promise> parseJSONToPromises(String jsonString) {
        ArrayList<Promise> promises = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);

                String serverId = json.getString("id");
                String love = json.getString("promise");
                Promise promise = new Promise(serverId, love);
                promises.add(promise);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return promises;
    }

    public static ArrayList<Memory> parseJSONToMemories(String jsonString) {
        ArrayList<Memory> memories = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String serverId = json.getString("id");
                String mem = json.getString("memory");

                Memory memory = new Memory(serverId, mem);
                memories.add(memory);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return memories;
    }

    public static ArrayList<Reassure> parseJSONToReassurances(String jsonString) {
        ArrayList<Reassure> memories = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String serverId = json.getString("id");
                String reass = json.getString("reassure");

                Reassure reassure = new Reassure(serverId, reass);
                memories.add(reassure);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return memories;
    }

    public static ArrayList<Notification> parseJSONToNotifications(String jsonString) {
        ArrayList<Notification> notifications = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String serverId = json.getString("id");
                String text = json.getString("text");
                String synced = json.getString("synced");

                Notification notification = new Notification(serverId, text, synced);
                notifications.add(notification);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return notifications;
    }

}
