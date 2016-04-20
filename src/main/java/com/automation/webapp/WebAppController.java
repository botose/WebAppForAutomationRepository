package com.automation.webapp;

import com.automation.webapp.http.OpenRepoRequest;
import com.automation.webapp.http.RestApiConnector;
import com.automation.webapp.model.ExtendedScenario;
import com.automation.webapp.model.ExtendedScenariosFromJSON;
import com.automation.webapp.model.File;
import com.automation.webapp.model.FileFromJSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
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

        if (ifError(map, restApiConnector)) {
            return "index";
        }

        restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL, HttpMethod.GET);
        files = FileFromJSON.map(restApiConnector.getResponse());
        map.addAttribute("files", files);

        return "redirect:/getFiles";
    }

    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public String mainPage(ModelMap map) throws IOException {
        if(files == null || files.size() == 0) {
            map.addAttribute("errorMessage", "First you have to load a repository");
            return "forward:/";
        }
        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL, HttpMethod.GET);

        if (ifError(map, restApiConnector)) {
            return "index";
        }

        files = FileFromJSON.map(restApiConnector.getResponse());
        map.addAttribute("files", files);

        return "files";
    }

    @RequestMapping(value = "/getFile/{fileName}", method = RequestMethod.GET)
    public String getFile(@PathVariable String fileName,
                          ModelMap map){
        if(files == null || files.size() == 0) {
            map.addAttribute("errorMessage", "First you have to load a repository");
            return "forward:/";
        }

        for(File file : files) {
            if(file.getFileName().contains(fileName)) {
                map.addAttribute("file", file);
            }
        }
        return "fileContent";
    }

    @RequestMapping(value="/updateScenario", method = RequestMethod.POST)
    @ResponseBody
    public Response updateScenario(@RequestParam(name = "fileName") String featureFileName,
                                      @RequestParam(name = "scenarioTitle") String scenarioTitle,
                                      HttpEntity<String> httpEntity) throws IOException {


        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.setPostingString(new StringEntity(httpEntity.getBody()));
        featureFileName = featureFileName.replace(".groovy", "");
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL + "/" + featureFileName +
                "/scenarios/search?scenarioTitle=" + URLEncoder.encode(scenarioTitle, "UTF-8"), HttpMethod.POST);

        return new Response(restApiConnector.getResponse(), restApiConnector.getStatusCode());
    }

    @RequestMapping(value="/searchForScenario", method = RequestMethod.GET)
    public String findScenario(@RequestParam(name = "title") String scenarioTitle, ModelMap map) throws IOException {

        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + LOCAL_REPO_URL + "/searchScenario?scenarioTitle=" + URLEncoder.encode(scenarioTitle, "UTF-8"), HttpMethod.GET);

        if (ifError(map, restApiConnector)) {
            return "index";
        }

        List<ExtendedScenario> extendedScenarios = ExtendedScenariosFromJSON.map(restApiConnector.getResponse());

        map.addAttribute("extendedScenarios", extendedScenarios);

        return "scenarios";
    }

    private boolean ifError(ModelMap map, RestApiConnector restApiConnector) {
        if(restApiConnector.getStatusCode() != HttpStatus.SC_OK &&
                restApiConnector.getStatusCode() != HttpStatus.SC_CREATED) {

            map.addAttribute("errorMessage", "Error happened so please login again");
            map.addAttribute("response", restApiConnector.getResponse());
            map.addAttribute("statusCode", restApiConnector.getStatusCode());
            return true;
        }
        return false;
    }

    @RequestMapping(value="/addCommitPush", method = RequestMethod.GET)
    @ResponseBody
    public Response gitCommit(@RequestParam(name = "message") String message, ModelMap map) throws IOException {
        RestApiConnector restApiConnector = new RestApiConnector();
        restApiConnector.doCall(ROOT_API_URL + GIT_REPO_URL + "/addCommitPush?commitMessage=" + message, HttpMethod.GET);

        return new Response(restApiConnector.getResponse(), restApiConnector.getStatusCode());
    }

    public class Response {
        public String errorMessage;
        public int statusCode;

        public Response(String errorMessage, int statusCode) {
            this.errorMessage = errorMessage;
            this.statusCode = statusCode;
        }
    }
}
