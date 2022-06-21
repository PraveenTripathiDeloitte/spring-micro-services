package com.spring.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
//    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    @RateLimiter(name = "default") // to set the limits of request in specific interval of time
    @Bulkhead(name = "default") // to set the no of concurrent call allowed
    public String sampleAPI() {
        logger.info("Sample API call received");
//        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/some-dummy-url",
//                String.class);
//        return responseEntity.getBody();
        return "sample-api";
    }

    /*
    will return this response if after calling the API maximum times
    no response is obtained
     */
    public String hardCodedResponse(Exception ex) {
        return "fallback-response";
    }
}
