package com.mindex.challenge.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/compensation")
    public Compensation create(@RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);
        if(compensation==null) {
        	 throw new RuntimeException("Requested create for null compensation");
        }

        return compensationService.create(compensation);
    }

    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }

    /*
     * Requirements only requested the create and read functionality, but in a real world use case, 
     * update would likely also be required, for example when an employee receives a pay raise.
     * Implementing it was not to difficult, so I went ahead and did so. 
     */
    @PutMapping("/compensation/{id}")
    public Compensation update(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation update request for id [{}] and employee [{}]", id, compensation);

        compensation.setEmployeeId(id);
        
        //This assumes we always want effective date to reflect the date of last update to the user's compensation.
        //If there are use cases where we would not want this to occur, we would need to either remove this line,
        //or add additional logic to check for these use cases.
        compensation.setEffectiveDate(LocalDate.now());
        
        return compensationService.update(compensation);
    }
}
