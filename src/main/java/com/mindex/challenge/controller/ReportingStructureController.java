package com.mindex.challenge.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ReportingStructureService reportingStructureService;
    
    @GetMapping("/reporting-structure/{id}")
    public ReportingStructure getReportingStructure(@PathVariable String id) {
        LOG.debug("Received reporting structure request for id [{}]", id);
        
        List<Employee> employees = employeeService.readAll();

        return reportingStructureService.buildReportingStructure(employeeService.buildEmployeeMap(employees), id);
    }

}
