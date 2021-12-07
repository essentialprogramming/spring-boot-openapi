package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Reroutes paths.
 */
@Controller
public class SwaggerApiResource {

    /**
     * Redirects swagger to default path.
     *
     * @return default path
     */
    @GetMapping("/apidoc")
    public String swaggerUI() {
        return "redirect:/swagger-ui.html";
    }

}
