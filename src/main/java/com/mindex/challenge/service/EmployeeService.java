package com.mindex.challenge.service;

import java.util.HashMap;
import java.util.List;

import com.mindex.challenge.data.Employee;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    List<Employee> readAll();
    Employee update(Employee employee);
	HashMap<String, Employee> buildEmployeeMap(List<Employee> employees);
}
