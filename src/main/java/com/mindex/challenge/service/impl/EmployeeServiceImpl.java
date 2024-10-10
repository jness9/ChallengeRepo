package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }
    
    @Override
    public List<Employee> readAll() {
        LOG.debug("Getting list of employees");

        List<Employee> employees = employeeRepository.findAll();

        if (employees == null || employees.isEmpty()) {
            throw new RuntimeException("No valid Employees");
        }

        return employees;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    
	//Create a Map for the entire employee structure.
    @Override
	public HashMap<String, Employee> buildEmployeeMap(List<Employee> employees){
		HashMap<String, Employee> employeeMap = new HashMap<>();
		for(Employee e : employees) {
			employeeMap.put(e.getEmployeeId(), e);
		}
		return employeeMap;
		
	}
}
