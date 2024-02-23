package fr.pagesjaunes.socletechnique.webmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping(value = "/", produces = {"text/plain"})
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("OK");
    }
}
