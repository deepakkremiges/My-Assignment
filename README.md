Hi, I am Deepak !

Step 1. Paste this into pre-request script in postman
function generateRandomString(length) {
   var result = '';
   var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
   var charactersLength = characters.length;
   for (var i = 0; i < length; i++) {
       result += characters.charAt(Math.floor(Math.random() * charactersLength));
   }
   return result;
}
var randomString = generateRandomString(20);
pm.globals.set("requestId", randomString);
pm.globals.set("timestamp", new Date().getTime());



Q1. localhost:8080/hello   -GET
Postman-body: -
{
   "token": "kdfnvls783jfd",            
   "data": {                   
   },
   "reqid": "{{requestId}}",
   "_client_ts": "{{timestamp}}"
}


Q2. localhost:8080/hello    -POST
{
   "token": "”,
   "data": {
       "name":"Deepak Kumar"
   },
   "reqid": "{{requestId}}",
   "_client_ts": "{{timestamp}}"
}


Q3. localhost:8080/mysum    -POST
{
  "token": "",
  "data": {
      "operation":"multiply",
      "num1":3.7,
      "num2":5
  },
  "reqid": "{{requestId}}",
  "_client_ts": "{{timestamp}}"
}


Q4. localhost:8080/mycalc   --POST
{
  "token": "",
  "data": {
      "operation":"min",
     "numbers":[2.6,3.7,4.0,0.3,89.21]
  },
  "reqid": "{{requestId}}",
  "_client_ts": "{{timestamp}}"
}


Q5. localhost:8080/myproperties  -POST
{
  "token": "your_bearer_token",
  "data": {
      "propertyIdentifiers": ["property1", "property2", "property3","property14"]
  },
  "reqid": "{{requestId}}",
 "_client_ts": "{{timestamp}}"
}

Q6. 
SQL Scripts

-- Create the departments table
CREATE TABLE departments (
    id SERIAL PRIMARY KEY,
    deptname VARCHAR(100) NOT NULL
);

-- Populate the departments table with at least 10 records
INSERT INTO departments (deptname) VALUES
('Sales'),
('Marketing'),
('Finance'),
('Human Resources'),
('Information Technology'),
('Research and Development'),
('Customer Service'),
('Operations'),
('Legal'),
('Administration');

-- Create the ranks table
CREATE TABLE ranks (
    id SERIAL PRIMARY KEY,
    rankdesc VARCHAR(100) NOT NULL,
    parentrankid INT
);

-- Populate the ranks table with at least 10 records
INSERT INTO ranks (rankdesc, parentrankid) VALUES
('Manager', NULL),
('Assistant Manager', 1),
('Senior Executive', 1),
('Executive', 3),
('Senior Analyst', NULL),
('Analyst', 5),
('Senior Consultant', 1),
('Consultant', 7),
('Associate', 1),
('Senior Associate', 9);

-- Create the employee table
CREATE TABLE employee (
    id BIGSERIAL PRIMARY KEY,
    empid BIGINT UNIQUE NOT NULL,
    fname VARCHAR(50) NOT NULL,
    fullname VARCHAR(100),
    dob DATE NOT NULL,
    doj DATE NOT NULL,
    salary INTEGER NOT NULL,
    reportsto BIGINT,
    deptid BIGINT REFERENCES departments(id),
    rankid BIGINT REFERENCES ranks(id),
    createdat TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updatedat TIMESTAMP,
    client_reqid VARCHAR(50) NOT NULL
);




Q7. localhost:8080/myhr/employee/add -POST
{
 "token": "",
 "data": {
     "empId":"DPK125",
     "fname":"Sharad",
     "fullname":"Sharad Bais",
     "dob":"1996-07-12",
     "doj":"2022-02-21",
     "salary":67872,
     "reportsTo":3,
     "departmentId":2,
     "rankId":5
 },
 "reqid": "{{requestId}}",
 "_client_ts": "{{timestamp}}"
}


Q8. localhost:8080/myhr/employee/list    - GET


Q9. localhost:8080/myhr/employee/lists?filter=Shivendra      -GET


Q10. localhost:8080/myhr/employee/get/3       -GET


Q11. localhost:8080/myhr/employee/update   -PUT
{
"token": "",
"data": {
       "id": 4,
       "empId": "EMP132",
       "fname": "Shyam",
       "fullname": "Shyam Tripathi",
       "dob": "1974-11-01",
       "doj": "2021-08-12",
       "salary": 876700,
       "reportsTo": 17
},
"reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


Q12. created employee_shadow table


Q13. localhost:8080/myhr/employee/delete/5     -DELETE


Q14. When employee updated or deleted, data stores in the employee_shadow
Q15. Added Debug logs


Q16. We can see in the terminal, after hitting the API 
user=<userId>|api=EmployeeController.getFilteredEmployees|status=success|error_code=|error_message=|client_reqid=<_reqid>|req=["Prabhu"]


Q17. localhost:8080/myhr/employee/list?mediaType=xml    -GET
mediaType=xml  -> Response in xml  format
mediaType=json  -> Response in JSON format

Q18.  localhost:8080/myhr/employee/list?type=xlsx    -GET
It will create the Excel file, if not created earlier


Q19. localhost:8080/api/generate-pdf    -GET
It will generate the pdf


Q20.  Redis Introduction
(i)   localhost:8080/redis/addEmployee    -POST
{
"token": "",
"data": {
  "empName":"Deepak"
},
"reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


(ii) localhost:8080/redis/getEmployeeValue?empName=Deepak      -GET
{
"token": "",
"data": {
 
},
"reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


(iii)  localhost:8080/redis/incrementEmployeeValue?empName=Deepak  -POST
{
"token": "",
"data": {
 
},
"reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


(iv)   localhost:8080/redis/decrementEmployeeValue?empName=Deepak     -POST
{
"token": "",
"data": {
 
},
"reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


(v)   localhost:8080/redis/setEmployeeTTL?empName=Deepak&ttlInSeconds=3600     -POST
{
"token": "",
"data": {
 
},
"reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


Q21. localhost:8080/redis/updateEmployeeContribution   -POST
{
   "token": "your_token_value",
   "data": {
       "department": "backendDeveloper2",
       "employeeId": "DPK676",
       "count": 1
   },
  "reqid": "{{requestId}}",
"_client_ts": "{{timestamp}}"
}


Q22. localhost:8080/redis/myhr/employee/getContribution?department=backendDeveloper2   -GET


Q23. localhost:8080/redis/Sales/12/contribution    -GET


Q24. localhost:8080/auth/login  -POST
{
   "email": "deepakk",
   "password":"mailpass"
}


Q25. After hitting the API endpoints from postman, you can see the logger message in the vs code terminal













































