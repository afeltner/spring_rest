package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class ApplicationController {

    /**
     * help will just return the valid GET end points that this application will accept.
     *
     * @return String Text containing valid GET endpoints for this application.
     */
    @GetMapping("/")
    public String getHelp() {
        return """
                valid GET requests are:
                    GET /people
                    GET /person/{name}
                """;
    }

    /**
     * help will just return the valid POST end points that this application will accept.
     *
     * @return String Text containing valid POST endpoints for this application.
     */
    @PostMapping("/")
    public String postHelp() {
        return """
                valid POST requests are:
                    POST /person
                """;
    }

}