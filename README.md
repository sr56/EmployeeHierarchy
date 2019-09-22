
# EmployeeHierarchy
Demonstrate handling of Employee Hierarchy using Tree structure and DFS search

**Aim:**  To input the sample employee data of a small company, and print the employee hierarchy.

Overview:
Classic problem of Trees and Tree Traversal.
Employee hierarchy can be visualised as a Tree with each employee being a node. 
The employee's manager becomes the parent node and the employee's subordinate team mates becomes the child nodes. 
As mentioned in requirements, the CEO will not have a manager, and so he becomes the root node of the hierarchy, from where all traversals begin.

The output format provided in the requirement document shows all the employees, Each employee printed in one line, and the Employees are listed immediately under their managers.  At each lower level of hierarchy, the employees printing location moves to right.   Employees at the same hierarchical level will be appearing in the same column.

Program Logic

 - **Read the file** and each line
 - Store the employeeid - manager, and employeeid - employee_name  data from the file.
 - Process the data one by one for each employee and add the Employee Node to the store, as follows
 - Employees can be added to the Tree recursively using a function that creates the employee note from it's id.
 - The recursive function tries to add an employee node, and before doing it , it tries to get the Manager's employee node to maintain the upstream Tree Link. If Manager employee doesn't have an Employee entry, the method is called for the Manager's employeeID.  The recursion goes on until Manager employeeid is 0 that is the employee is CEO.
 -   When Manager is created / found out, the current employee is added to the Manager's Team member list, thereby maintaining the downstream link.
 - The recursion happens multiple times until all the branches are added to the Tree.

- Invalid employees / Invalid managers   ( Who have a manager who is not listed as an employee )  are added to a different invalid employee tree )

**Printing** is also done using recursion
We store the reference to the CEO employee who is the root node of this tree. 
Our recursion method accepts a node, and tries to print the tree under that node.
For each employee under this tree, the same method is called again to print it's child tree.
By adjusting the position where print is done, we can do a Depth First or Breadth First Traversal.
For the given problem, a DFS was implemented.

The output for the program will be displayed in the Terminal. 

*EmployeList* is the file with the input provided in the sample format.

There are validations added for Circular dependency among employees and managers and for duplicate entries of employees.


*I didn't add Tests since the program was not complicated and in the interest of time. 
We could still do more error checks and validations on this program.*


**Output**
e.g

    $/>mvn clean install
    ...
    ...
    
    $/>java -jar target/employeedata.jar  EmployeeList.txt 
    
        Sep 22, 2019 11:13:19 PM com.testcompany.employeedata.EmployeeData printEmployeeData
    INFO: EmployeeData: [Employee{employeeID=400, name=Steve, manager=150, teamMembers=[190], isCEO=false}
    , Employee{employeeID=530, name=Unknown Employee, manager=-99, teamMembers=[311], isCEO=false}
    , Employee{employeeID=275, name=Alex, manager=100, teamMembers=[], isCEO=false}
    , Employee{employeeID=100, name=Alan, manager=150, teamMembers=[275, 220], isCEO=false}
    , Employee{employeeID=150, name=Jamie, manager=null, teamMembers=[400, 100], isCEO=true}
    , Employee{employeeID=311, name=John, manager=530, teamMembers=[], isCEO=false}
    , Employee{employeeID=220, name=Martin, manager=100, teamMembers=[], isCEO=false}
    , Employee{employeeID=190, name=David, manager=400, teamMembers=[], isCEO=false}
    ]
    Sep 22, 2019 11:13:19 PM com.testcompany.employeedata.EmployeeData printEmployeeData
    INFO: 

    ===================================
    E M P L O Y E E   H I E R A R C H Y 
    ===================================
    
    
    +-<CEO>Jamie[150]
    |        |        
    |        |        
    |        +-Steve[400]
    |        |        |        
    |        |        |        
    |        |        +-David[190]
    |        |        
    |        |        
    |        +-Alan[100]
    |        |        |        
    |        |        |        
    |        |        +-Alex[275]
    |        |        |        
    |        |        |        
    |        |        +-Martin[220]
    
    
    ----------------------------------------
    EMPLOYEES WITH INVALID DATA
    ----------------------------------------
    
    
    +-Unknown Employees[-99]
    |        |        
    |        |        
    |        +-Unknown Employee[530]
    |        |        |        
    |        |        |        
    |        |        +-John[311]

 



