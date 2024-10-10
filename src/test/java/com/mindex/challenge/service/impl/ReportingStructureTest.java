package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureTest {

    private String reportingUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
    	
        reportingUrl = "http://localhost:" + port + "/reporting-structure/{id}";
    }

    @Test
    public void testMultipleReports() {
        String id = "16a596ae-edd3-4847-99fe-c4518e82c86f";
        ReportingStructure readStruct = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, id).getBody();
        assertEquals(id, readStruct.getEmployeeId());
        assertEquals(4, readStruct.getNumberOfReports());
    }
    
    @Test
    public void testZeroReports() {
        String id = "62c1084e-6e34-4630-93fd-9153afb65309";
        ReportingStructure readStruct = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, id).getBody();
        assertEquals(id, readStruct.getEmployeeId());
        assertEquals(0, readStruct.getNumberOfReports());

    }
    

    @Test
    public void testNonExistantId() {
        String id = "-6e34-4630-93fd-";
        ReportingStructure readStruct = restTemplate.getForEntity(reportingUrl, ReportingStructure.class, id).getBody();
        assertEquals(null, readStruct.getEmployeeId());
        assertEquals(0, readStruct.getNumberOfReports());

    }

}
