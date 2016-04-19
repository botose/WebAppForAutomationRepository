package com.automation.webapp.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtendedScenariosFromJSON {
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<ExtendedScenario> map(String json) throws IOException {
        List<ExtendedScenario> result = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        try {
            obj = obj.getJSONObject("_embedded");
        } catch (JSONException e) {
            return result;
        }

        JSONArray fileArray = obj.getJSONArray("extendedScenarios");
        for(Object object : fileArray) {
            result.add(mapper.readValue(((JSONObject)object).toString(), ExtendedScenario.class));
        }

        return result;
    }
}
