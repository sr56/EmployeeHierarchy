/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testcompany.employeedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class EmployeeDataStore {

    private static final Logger logger = Logger.getLogger(EmployeeDataStore.class.getName());

    private final HashMap<Integer, Employee> employeeList;
    private Employee ceo;
    private static Employee INVALID_EMPLOYEE;

    public EmployeeDataStore() {
        employeeList = new HashMap<>();
        this.INVALID_EMPLOYEE = new Employee(Employee.INVALID_EMPLOYEE_LISTING_ROOT, Employee.INVALID_EMPLOYEE_LISTING_NAME, null, false);
    }

    public ArrayList<Employee> getEmployeeList() {
        return new ArrayList<>(employeeList.values());
    }

    public Employee getEmployee(int employeeID) {
        return employeeList.get(employeeID);
    }

    public Employee getCEO() {
        return this.ceo;
    }

    public void addEmployee(Employee emp) {
        if (emp.isIsCEO()) {
            this.ceo = emp;
        }
        employeeList.put(emp.getEmployeeID(), emp);
        logger.log(Level.FINE, "Employee {0} added!", emp.getEmployeeID());
    }

    public void removeEmployee(Employee emp) {
        Employee e = employeeList.remove(emp.getEmployeeID());
        if(ceo.getEmployeeID() == emp.getEmployeeID()){
            ceo = new Employee(Employee.INVALID_EMPLOYEE_LISTING_ROOT, Employee.UNKNOWN_EMPLOYEE_NAME, null, true);
        }
        logger.log(Level.FINE, "Employee {0} removed!", emp.getEmployeeID());
    }

    public boolean employeeAlreadyPresent(int employeeID) {
        return employeeList.containsKey(employeeID);
    }

    public void clearEmployeeData() {
        this.employeeList.clear();
    }

    public static Employee getINVALID_EMPLOYEE() {
        return INVALID_EMPLOYEE;
    }
    
    

    public void printEmployeeListing() {
        System.out.println("===================================");
        System.out.println("E M P L O Y E E   H I E R A R C H Y ");
        System.out.println("===================================");
        printEmployeeHierarchyUnder(ceo, 0);
        System.out.println("\n");
        System.out.println("----------------------------------------");
        System.out.println("EMPLOYEES WITH INVALID DATA");
        System.out.println("----------------------------------------");
        printEmployeeHierarchyUnder(getINVALID_EMPLOYEE(), 0);
        System.out.println("\n");
        System.out.println("----------------------------------------");
    }

    private void printEmployeeHierarchyUnder(Employee emp, int level) {
        addTab(level++);
        System.out.println((emp.isIsCEO() ? "<CEO>" : "") + emp.getName() + "[" + emp.getEmployeeID() + "]");
        for (Employee teamMember : emp.getTeamMembers()) {
            printEmployeeHierarchyUnder(teamMember, level);
        }
    }

    private void addTab(int level) {
        addBlankLevel(level);
        addBlankLevel(level);
        addDataLevel(level);
    }

    private void addBlankLevel(int tabCount) {
        for (int i = 0; (tabCount != 0 && i <= tabCount); i++) {
            System.out.print("|        ");
        }
        System.out.println("");
    }

    private void addDataLevel(int tabCount) {
        for (int i = 0; i < tabCount; i++) {
            System.out.print("|        ");
        }
        System.out.print("+-");
    }
}
