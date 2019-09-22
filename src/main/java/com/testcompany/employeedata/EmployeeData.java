/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testcompany.employeedata;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class EmployeeData {

    private HashMap<Integer, Integer> empManagers;
    private HashMap<Integer, String> empNames;
    private EmployeeDataStore dataStore;
    private static final Logger logger = Logger.getLogger(EmployeeData.class.getName());

    public EmployeeData() {
        this.empManagers = new HashMap<>();
        this.empNames = new HashMap<>();
    }

    public void addEmployee(int empID, String name, int managerID) throws Exception {
        if(empManagers.containsKey(empID) || empNames.containsKey(empID)){
            throw new Exception("EmployeeID " + empID + " has duplicate entries");
        }
        this.empManagers.put(empID, managerID);
        this.empNames.put(empID, name);
    }

    public void createDataStore() {
        validateData();
        this.dataStore = new EmployeeDataStore();
        this.empManagers.keySet().stream()
                .filter((empID) -> !dataStore.employeeAlreadyPresent(empID))
                .forEach((empID) -> addEmployeeEntry(empID));
    }

    private Employee addEmployeeEntry(int empID) {
        System.out.println("Adding employee entry for empID: " + empID);
        String empName = this.empNames.get(empID);
        if (empName == null) {
            empName = Employee.UNKNOWN_EMPLOYEE_NAME;
        }
        Integer managerID = this.empManagers.get(empID);
        Employee manager = null;

        if (managerID == null) {
            manager = EmployeeDataStore.getINVALID_EMPLOYEE();
        } else if (managerID == 0) {
            //ManagerID = 0 when the employee is the CEO
            Employee ceoEmp = new Employee(empID, empName, manager, true);
            dataStore.addEmployee(ceoEmp);
            return ceoEmp;
        } else {
            manager = this.dataStore.getEmployee(managerID);
            if (manager == null) {
                manager = addEmployeeEntry(managerID);
            }
        }
        Employee emp = new Employee(empID, empName, manager, false);
        dataStore.addEmployee(emp);
        manager.addTeamMember(emp);
        return emp;
    }
    
    private void validateData(){
        empManagers.keySet()
                .forEach((empID) -> {
                    int manager = empManagers.get(empID);
                    if(empManagers.get(manager) == empID){
                        throw new RuntimeException("Circular Dependency for EmployeeIDs " + empID + " and " + manager);
                    }
                });
    }

    public void printEmployeeData() {

        logger.log(Level.INFO, "EmpNames: {0}", this.empNames.toString());
        logger.log(Level.INFO, "EmpManager: {0}", this.empManagers.toString());
        if (this.dataStore == null) {
            createDataStore();
        }
        if (this.dataStore.getEmployeeList().size() <= 0) {
            System.out.println("The Employee List is Empty!");
            return;
        }
        logger.log(Level.INFO, "EmployeeData: {0}", dataStore.getEmployeeList().toString());
        logger.info("\n\n");
        this.dataStore.printEmployeeListing();

    }

}
