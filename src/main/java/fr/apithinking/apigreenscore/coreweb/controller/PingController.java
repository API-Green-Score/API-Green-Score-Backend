package fr.apithinking.apigreenscore.coreweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

	@GetMapping(value = "/ping", produces = { "text/plain" })
	public ResponseEntity<String> ping() {
		return new ResponseEntity<>("pong", HttpStatus.OK);
	}
}
