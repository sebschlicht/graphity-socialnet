package de.uniko.sebschlicht.socialnet;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

// TODO documentation
public class StatusUpdate {

    protected JSONObject jsonObject;

    protected String author;

    protected long published;

    protected String message;

    public StatusUpdate(
            String author,
            long published,
            String message) {
        this.author = author;
        this.published = published;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public long getPublished() {
        return published;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getJsonObject() {
        if (jsonObject == null) {
            jsonObject = generateJsonObject(this);
        }
        return jsonObject;
    }

    public Map<String, Object> getMap() {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("author", getAuthor());
        jsonMap.put("published", getPublished());
        jsonMap.put("message", getMessage());
        return jsonMap;
    }

    private static JSONObject generateJsonObject(StatusUpdate statusUpdate) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(statusUpdate.getMap());
        return jsonObject;
    }

    public static StatusUpdate fromMap(Map<String, Object> map) {
        String author = (String) map.get("author");
        String sPublished = (String) map.get("published");
        String message = (String) map.get("message");
        if (author == null) {
            throw new IllegalArgumentException("author is missing");
        }
        if (sPublished == null) {
            throw new IllegalArgumentException("published is missing");
        }
        if (message == null) {
            throw new IllegalArgumentException("message is missing");
        }
        return new StatusUpdate(author, Long.valueOf(sPublished), message);
    }
}
