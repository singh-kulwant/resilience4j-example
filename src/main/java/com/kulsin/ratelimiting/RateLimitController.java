package com.kulsin.ratelimiting;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RateLimitController {

    @GetMapping("/getMessage")
    @RateLimiter( name = "getMessage", fallbackMethod = "getMessageFallBack" )
    public ResponseEntity<String> getMessage(
            @RequestParam(value = "name") String name
    ) {
        return ResponseEntity.ok().body(String.format("Welcome %s!", name));
    }

    public ResponseEntity<String> getMessageFallBack(RequestNotPermitted exception) {
        log.info("getMessage rate limit is applied");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests! please try after some time.");
    }

}
