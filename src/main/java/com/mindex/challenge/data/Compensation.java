package com.mindex.challenge.data;

import java.time.LocalDate;

public class Compensation {
	/*
	 * One thing I have not implemented is validation of the various fields (salary cannot be negative, ect.)
	 * This is because outside of some service layer constraints on the employeeId field, it looked like it was
	 * not being done for the provided Employee fields. I would suggest either using javax.validation annotations,
	 * or even using a DTO. 
	 */
	private String employeeId;
	private int salary;
	private LocalDate effectiveDate;
	
    public Compensation() {
    }
    
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}	
	
	public int getSalary() {
		return salary;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(LocalDate effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}
