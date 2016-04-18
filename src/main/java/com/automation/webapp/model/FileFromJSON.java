package com.automation.webapp.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FileFromJSON {

    public static List<File> map(String json) {
        List<File> result = new ArrayList<>();
        JSONObject obj = new JSONObject(json);
        JSONArray fileArray = obj.getJSONObject("_embedded").getJSONArray("featureFiles");

        for(Object object : fileArray) {
            JSONObject fileJSON = (JSONObject)object;
            File file = new File();
            file.setTitle(fileJSON.getString("title"));
            file.setFileName(fileJSON.getString("fileName"));
            List<String> description = new ArrayList<>();
            for(Object line : fileJSON.getJSONArray("description")) {
                description.add((String) line);
            }
            file.setDescription(description);
            List<Scenario> scenarios = new ArrayList<>();
            for(Object ob : fileJSON.getJSONArray("scenarios")) {
                JSONObject scenarioJSON = (JSONObject)ob;
                Scenario scenario = new Scenario();
                scenario.setTitle(scenarioJSON.getString("title"));
                List<String> content = new ArrayList<>();
                for(Object line: scenarioJSON.getJSONArray("content")) {
                    content.add((String)line);
                }
                scenario.setContent(content);
                scenarios.add(scenario);
            }
            file.setScenarios(scenarios);
            result.add(file);
        }
        return result;
    }
}
