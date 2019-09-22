/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testcompany.employeedata;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author user
 */
public class MainClass {

    private static final Logger logger = Logger.getLogger(MainClass.class.getName());

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please input a file Path with Employee Data");
            return;
        }
        if (args[0] == null || args[0].trim().isEmpty()) {
            System.err.println("Please input a valid file Path with Employee Data");
            return;
        }
        try {
            MainClass mainClass = new MainClass(args[0]);
            mainClass.load();
            mainClass.print();
        } catch (Exception ex) {
            Logger.getLogger(EmployeeData.class.getName()).log(Level.SEVERE, "Error in EmployeeData. ", ex);
        }
    }

    private final String dataFilePath;
    private final EmployeeData empData;

    public MainClass(String employeDataFilePath) throws Exception {
        this.dataFilePath = employeDataFilePath;
        this.empData = new EmployeeData();
    }

    public void load() throws Exception {
        try (Stream<String> dataStream = Files.lines(Paths.get(this.dataFilePath))) {
            dataStream.filter((entry) -> entry != null)
                    .filter((entry) -> !entry.trim().isEmpty())
                    .forEach((entry) -> {
                        logger.log(Level.INFO, " Read Entry: {0}", entry);
                        try {
                            parseAndStoreEmployee(entry);
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE, "Error storing Employee Details: " + entry, ex);
                        }
                    });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error Reading Employee Data from File. {0} Details: {1}", new Object[]{this.dataFilePath, e.getMessage()});
            throw e;
        }
    }

    private void parseAndStoreEmployee(String data) throws Exception {
        if (data == null) {
            logger.log(Level.WARNING, "Null passed from the file for parsing");
            throw new Exception("Null line passed from file for parsing");
        }
        data = data.trim();
        if (data.trim().isEmpty()) {
            logger.log(Level.WARNING, "Blank line passed from the file for parsing");
            throw new Exception("Blank line passed from file for parsing");
        }
        String[] empDetailArr = data.split(",");
        if (empDetailArr == null || empDetailArr.length == 0) {
            logger.log(Level.WARNING, "Blank line passed from the file for parsing");
            throw new Exception("Blank line passed from file for parsing");
        }
        String empName = empDetailArr[0];
        int empID = Integer.parseInt(empDetailArr[1].trim());
        if (empID <= 0) {
            logger.log(Level.SEVERE, "EmployeeID cannot be <= 0");
            throw new Exception("EmployeeID is 0");
        }
        int managerID = 0;
        if (empDetailArr.length > 2) {
            try {
                managerID = Integer.parseInt(empDetailArr[2].trim());
            } catch (NumberFormatException nfe) {
                logger.log(Level.FINE, "ManagerID is not defined for employeeID : {0}", empID);
            }
        }
        logger.log(Level.INFO, "Adding Employee with data. EmpID: {0}, manager:{1}", new Object[]{empID, managerID});
        this.empData.addEmployee(empID, empName, managerID);
    }

    public void print() throws Exception {
        this.empData.printEmployeeData();
    }

}
