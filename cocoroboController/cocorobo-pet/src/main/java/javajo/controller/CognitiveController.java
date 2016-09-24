package javajo.controller;

import javajo.dto.FeelDTO;
import javajo.dto.SpeechDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * MS Azure CognitiveAPIとやりとりをするAPI.
 */
@RestController
@RequestMapping("/api")
public class CognitiveController {

    private final Logger log = LoggerFactory.getLogger(CognitiveController.class);



}
