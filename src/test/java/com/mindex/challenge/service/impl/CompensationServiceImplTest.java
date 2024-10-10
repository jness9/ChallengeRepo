package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;
    private String persistenceId;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        
        //Create a compensation for later persistence testing
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(100000);
        testCompensation.setEffectiveDate(LocalDate.now());
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        persistenceId=createdCompensation.getEmployeeId();
    }
    

    @Test
    public void testCreateReadUpdate() {
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(100000);
        testCompensation.setEffectiveDate(LocalDate.now());

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployeeId(), readCompensation.getEmployeeId());
        assertCompensationEquivalence(createdCompensation, readCompensation);


        // Update checks
        readCompensation.setSalary(150000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation =
                restTemplate.exchange(compensationIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Compensation>(readCompensation, headers),
                        Compensation.class,
                        readCompensation.getEmployeeId()).getBody();

        assertCompensationEquivalence(readCompensation, updatedCompensation);
    }
    
    //Checking that the update functionality automatically updates effective date.
    @Test
    public void testNewDateUpdate() {
        Compensation testCompensation = new Compensation();
        testCompensation.setSalary(100000);
        testCompensation.setEffectiveDate(LocalDate.of(1991, 1, 5));

        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();


        createdCompensation.setSalary(150000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Compensation updatedCompensation =
                restTemplate.exchange(compensationIdUrl,
                        HttpMethod.PUT,
                        new HttpEntity<Compensation>(createdCompensation, headers),
                        Compensation.class,
                        createdCompensation.getEmployeeId()).getBody();
        
        assertEquals(LocalDate.now(), updatedCompensation.getEffectiveDate());
    }
    
    //Test persistence
    @Test
    public void testPersistence() {
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, persistenceId).getBody();
        assertEquals(persistenceId, readCompensation.getEmployeeId());
        assertEquals(100000, readCompensation.getSalary());
        assertEquals(LocalDate.now(), readCompensation.getEffectiveDate());

    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
