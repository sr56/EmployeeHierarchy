/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testcompany.employeedata;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author user
 */
public class Employee {

    private static final Logger logger = Logger.getLogger(Employee.class.getName());
    public static final String INVALID_EMPLOYEE_LISTING_NAME = "Unknown Employees";
    public static final int INVALID_EMPLOYEE_LISTING_ROOT = -99;
    public static final String UNKNOWN_EMPLOYEE_NAME = "Unknown Employee";
    public static final int VALID_EMPLOYEE_LISTING_ROOT = 0;
    public static final String VALID_EMPLOYEE_LISTING_NAME = "Organization";
    private int employeeID;
    private String name;
    private Employee manager;
    private ArrayList<Employee> teamMembers;
    private boolean isCEO;
    private boolean isValid;

    public Employee(int employeeID, String name, Employee manager, boolean isCEO) {
        this(employeeID, name, manager, new ArrayList<Employee>(), isCEO);
    }

    public Employee(int employeeID, String name, Employee manager, ArrayList<Employee> teamMembers, boolean isCEO) {
        this.employeeID = employeeID;
        this.name = name;
        this.manager = manager;
        this.teamMembers = teamMembers;
        this.isCEO = isCEO;
//        this.isValid = this.selfValidate();
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
//        this.selfValidate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
//        this.selfValidate();
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
//        this.selfValidate();
    }
    

    public List<Employee> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<Employee> teamMembers) {
        this.teamMembers = teamMembers;
//        this.selfValidate();
    }

    public void addTeamMember(Employee teamMember) {
        this.teamMembers.add(teamMember);
    }

    public boolean isIsCEO() {
        return isCEO;
    }

    public void setIsCEO(boolean isCEO) {
        this.isCEO = isCEO;
//        this.selfValidate();
    }

    public boolean isIsValid() {
        return isValid;
    }


    @Override
    public String toString() {
        String teamMemberStr = this.teamMembers
                .stream()
                .map(Employee::getEmployeeID)
                .collect(Collectors.toList())
                .toString();
        return "Employee{"
                + "employeeID=" + employeeID
                + ", name=" + name
                + ", manager=" + (manager != null ? manager.getEmployeeID() : "null")
                + ", teamMembers=" + teamMemberStr
                + ", isCEO=" + isCEO + "}\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (this.employeeID != other.employeeID) {
            return false;
        }
        return true;
    }

    private boolean selfValidate() {
        boolean isValidData = false;
        if (this.employeeID <= 0) {
            logger.log(Level.WARNING, "INVALID! EmployeeID is {0}", this.employeeID);
            return isValidData;
        }
        if (this.name == null || this.name.trim().isEmpty()) {
            logger.log(Level.WARNING, "INVALID! Name is {0}", this.name);
            return isValidData;
        }
        if (this.manager == null && !this.isCEO) {
            logger.log(Level.WARNING, "INVALID! Manager is null and not marked as CEO");
            return isValidData;
        }
        if (this.manager.teamMembers.contains(this)) {
            logger.severe("Circular dependency! Manager cannot be a team member!");
            throw new RuntimeException("Manager cannot be a team member of same employee. Employee: " + this.employeeID + " manager: " + this.manager.getEmployeeID());
        }

        isValidData = true;
        return isValidData;
    }
}
