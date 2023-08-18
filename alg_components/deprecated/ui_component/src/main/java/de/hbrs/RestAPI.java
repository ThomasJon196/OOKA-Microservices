package de.hbrs;

import de.hbrs.data.entity.State;
import de.hbrs.views.analyse.AnalyseView;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestAPI {

    @PostMapping("/ui_status")
    // register producer
    public String status(int id, String status) {
        System.out.println("New Status for " + id + ": " + status);

        for (AnalyseView av : AnalyseView.getLoadedVaadinUIs()) {
            State state = State.valueOf(status);
            av.updateStatus(id, state);
        }

        JSONObject response = new JSONObject();
        return response.toString();
    }

    @PostMapping("/ui_result")
    // unregister producer
    public String result(Integer id, String result) {
        JSONObject response = new JSONObject();
        /*try {
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }*/
        return response.toString();
    }


    /*@PostMapping("/anchor_start")
    // unregister producer
    public String anchor_start(Integer id, String result) {

        System.out.println("AAANEW: " + id + " " + result);

        for (AnalyseView av : AnalyseView.getLoadedVaadinUIs()) {
            av.updateStatus();
        }
        return null;
    }
    */
    public static void sendAnchorStart(String oil, String fuel, String monitoring, String gearbox) {
        // final String url = "http://localhost:8090/greeting";
        // RestTemplate restTemplate = new RestTemplate();
        //restTemplate.postForObject(url, "test", String.class);

        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        // map.add("id", "2");
        // map.add("result", "RUNNING");

        final String url = "http://localhost:8090/greeting";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);

        // HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        // ResponseEntity<String> response = restTemplate.getForObject(url, String.class);
    }

    // public static void sendAnchorStart(String oil, String fuel, String monitoring, String gearbox) {
    //     final String url = "http://localhost:8090/configuration/test";
    //     RestTemplate restTemplate = new RestTemplate();
    //     //restTemplate.postForObject(url, "test", String.class);

    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    //     MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    //     map.add("configItem1", oil);
    //     map.add("configItem2", fuel);
    //     map.add("configItem3", monitoring);
    //     map.add("configItem4", gearbox);
    //     System.out.println(oil);

    //     HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    //     ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
    // }



}
