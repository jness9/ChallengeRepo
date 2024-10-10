package com.mindex.challenge.service;

import java.util.HashMap;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

public interface ReportingStructureService {
	ReportingStructure buildReportingStructure(HashMap<String, Employee> employeeMap, String id);

}
