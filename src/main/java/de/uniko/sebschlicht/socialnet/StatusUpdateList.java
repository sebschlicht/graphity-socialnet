package de.uniko.sebschlicht.socialnet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StatusUpdateList implements Iterable<StatusUpdate> {

    protected static final JSONParser JSON_PARSER = new JSONParser();

    protected List<StatusUpdate> statusUpdates;

    public StatusUpdateList() {
        statusUpdates = new LinkedList<StatusUpdate>();
    }

    public void add(StatusUpdate statusUpdate) {
        statusUpdates.add(statusUpdate);
    }

    public int size() {
        return statusUpdates.size();
    }

    public List<StatusUpdate> getList() {
        return statusUpdates;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String toString() {
        JSONArray activities = new JSONArray();
        for (StatusUpdate statusUpdate : statusUpdates) {
            activities.add(statusUpdate.getJsonObject());
        }
        return activities.toJSONString();
    }

    public static StatusUpdateList fromString(String jsonString) {
        try {
            JSONArray activities = (JSONArray) JSON_PARSER.parse(jsonString);
            StatusUpdateList list = new StatusUpdateList();
            for (Object statusUpdateJson : activities) {
                list.add(StatusUpdate.fromMap((JSONObject) statusUpdateJson));
            }
            return list;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<? extends Object> buildJsonArray() {
        List<Map<String, Object>> statusUpdateMaps =
                new LinkedList<Map<String, Object>>();
        for (StatusUpdate statusUpdate : statusUpdates) {
            statusUpdateMaps.add(statusUpdate.getMap());
        }
        return statusUpdateMaps;
    }

    @Override
    public Iterator<StatusUpdate> iterator() {
        return statusUpdates.iterator();
    }
}
