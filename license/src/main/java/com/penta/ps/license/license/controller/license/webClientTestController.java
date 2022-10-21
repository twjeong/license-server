package com.penta.ps.license.license.controller.license;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test/license")
@Slf4j
public class webClientTestController {
	
	@GetMapping()
    public String get() {
		log.error("TEST MSG0");
		return "hello license";
	}
	
	@GetMapping(value="/{string}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getString(@PathVariable("string") String text) {
		log.error("TEST MSG1");
		return text;
	}

}
