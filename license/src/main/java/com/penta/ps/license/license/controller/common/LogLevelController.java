package com.penta.ps.license.license.controller.common;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/log/control")
public class LogLevelController {

    @GetMapping(value = "/level/{loglevel}")
    public ResponseEntity<String> changeLoggerLevel(@PathVariable("loglevel") String loggerLevel) {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        //newLevel – The new desired log level as string. E.g.: DEBUG
        rootLogger.setLevel(Level.toLevel(loggerLevel));

        (rootLogger.getAppender("CONSOLE")).start();
        (rootLogger.getAppender("FILE")).start();

        return new ResponseEntity<String>(loggerLevel, HttpStatus.OK);
    }

    @GetMapping(value = "/level")
    public ResponseEntity<String> getLoggerLevel() {
        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

        String loggerLevel = rootLogger.getLevel().toString();

        return new ResponseEntity<String>(loggerLevel, HttpStatus.OK);
    }
}
