package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
	
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

	@Override
	public ReportingStructure buildReportingStructure(HashMap<String, Employee> employeeMap, String id) {
        LOG.debug("Creating ReportingStructure for employee [{}]", id);
        if (id == null) {
            throw new RuntimeException("employeeId is null");
        }
             
        ReportingStructure struct = new ReportingStructure();
        
        struct.setEmployeeId(id);
        struct.setNumberOfReports(calculateNumberOfReports(employeeMap, id));

        return struct;
	}
	
	/* This approach for calculating number of reports uses a map with the entire employee list.
	Doing this helps with performance due to only needing to go out to the DB once,	as well 
	as not being at risk of stack overflow.  Of course, the tradeoff is that we have to load every employee into memory.
	*/
	private int calculateNumberOfReports(HashMap<String, Employee> employeeMap, String id) {
		if(employeeMap == null || employeeMap.isEmpty()) {
			return 0;
		}
		
		int numReports = 0;
		Queue<String> queue = new LinkedList<>();
		queue.add(id);
		while(!queue.isEmpty()) {
			String currentId = queue.poll();
			Employee currentEmployee = employeeMap.get(currentId);
			if(currentEmployee==null) {
				throw new RuntimeException("Invalid employeeId: " + currentId);
			}
			List<Employee> directReports = currentEmployee.getDirectReports();
			if(directReports != null) {
				for(Employee e : directReports) {
					numReports++;
					queue.offer(e.getEmployeeId());
				}
			}

		}
		return numReports;	
	}
	
}
