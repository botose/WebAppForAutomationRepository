package com.automation.webapp;

import com.automation.webapp.http.OpenRepoRequest;
import com.automation.webapp.http.RestApiConnector;
import com.automation.webapp.model.File;
import com.automation.webapp.model.FileFromJSON;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebAppController {
    private static final String ROOT_API_URL = "http://localhost:8090/api";
    private static final String REPOSITORIES_URL = "/repositories";
    private static final String LOCAL_REPO_URL = "/localRepo";
    private static final String GIT_REPO_URL = "/git";
    private static List<File> files;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String filesPage(@RequestParam(name = "repository-url") String remoteUrl,
                            @RequestParam(name = "email-address") String emailAddress,
                            @RequestParam(name = "password") String password,
                            ModelMap map) throws IOException {
        OpenRepoRequest openRepoRequest = new OpenRepoRequest();
        openRepoRequest.remoteRepositoryUrl = remoteUrl;
        openRepoRequest.userName = emailAddress;
        openRepoRequest.password = password;

        RestApiConnector restApiConnector = new RestApiConnector();
        Gson gson = new Gson();
        restApiConnector.setPostingString(new StringEntity(gson.toJson(openRepoRequest)));
        restApiConnector.doCall(ROOT_API_URL + REPOSITORIES_URL, HttpMethod.POST);

        if(restApiConnector.getStatusCode() != HttpStatus.SC_CREATED) {
            map.addAttribute("errorMessage", "couldn't read repo");
            map.addAttribute("response", restApiConnector.getResponse());
            map.addAttribute("statusCode", restApiConnector.getStatusCode());
            return "index";
        }
        restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL, HttpMethod.GET);
        files = FileFromJSON.map(restApiConnector.getResponse());
        map.addAttribute("files", files);

        return "files";
    }

    @RequestMapping(value = "/getFiles")
    public String mainPage(ModelMap map) throws IOException {
        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL, HttpMethod.GET);
        if(restApiConnector.getStatusCode() != HttpStatus.SC_OK) {
            map.addAttribute("errorMessage", "Error happened so please login again");
            map.addAttribute("response", restApiConnector.getResponse());
            map.addAttribute("statusCode", restApiConnector.getStatusCode());
            return "index";
        }

        files = FileFromJSON.map(restApiConnector.getResponse());
        map.addAttribute("files", files);

        return "files";
    }

    @RequestMapping(value = "/getFile/{fileName}", method = RequestMethod.GET)
    public String getFile(@PathVariable String fileName,
                          ModelMap map){
        for(File file : files) {
            if(file.getFileName().contains(fileName)) {
                map.addAttribute("file", file);
            }
        }
        return "scenarios";
    }

    @RequestMapping(value="/updateScenario", method = RequestMethod.POST)
    public int updateScenario(@RequestParam(name = "fileName") String featureFileName,
                              @RequestParam(name = "scenarioTitle") String scenarioTitle,
                              HttpEntity<String> httpEntity) throws IOException {
        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.setPostingString(new StringEntity(httpEntity.getBody()));
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL + "/" + featureFileName +
                "/scenarios/search?scenarioTitle="+scenarioTitle, HttpMethod.POST);

        return restApiConnector.getStatusCode();
    }

    @RequestMapping(value="/addCommitPush", method = RequestMethod.GET)
    public int gitCommit(@RequestParam(name = "commitMessage") String message) throws IOException {
        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + GIT_REPO_URL + "/addCommitPush?commitMessage=" + message, HttpMethod.GET);

        return restApiConnector.getStatusCode();
    }
}
