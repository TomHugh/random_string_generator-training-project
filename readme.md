# PROJECT DESCRIPTION

**Random String Generator Api**

Application generates files with random unique strings. User can define:
- characters from which string will be build
- length of the strings (min, max)
- quantity of the strings

**Endpoints**

GET /jobs - get list of all jobs

POST /jobs - create a job with parameters (charset, min, max, quantity)

GET /jobs/{id} - get a job by ID

GET /jobs/running - get number of currently processed jobs

GET /results - get all results (zip file)

GET /results/{jobID} - get result of job by ID (zip file)

Application can be run with two profiles. To change profile please provide respective property in application.properties file:
- dev - uses H2 in memory database
- prod - uses MySQL database

