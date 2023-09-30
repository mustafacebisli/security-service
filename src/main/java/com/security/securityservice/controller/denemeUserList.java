package com.security.securityservice.controller;


import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class denemeUserList {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/")
    public String handleRequest(Model model) {

        List<ServiceInstance> instances = discoveryClient.getInstances("Movie-Service");
        if (instances != null && !instances.isEmpty()) {
            ServiceInstance serviceInstance = instances.get(0);
            String url = serviceInstance.getUri().toString();
            url = url + "/list";
            RestTemplate restTemplate = new RestTemplate();
            List<Movie> result = restTemplate.getForObject(url, List.class);

            model.addAttribute("result", result);
        }

        return "movie";
    }
}
