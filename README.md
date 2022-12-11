
# StockAnalyzer
*   You decided you'd like to build an application server (Spring Boot or NodeJS) that would allow multiple users to perform the following operations concurrently:

    1.  upload a bulk data set
    2.  query for data by stock ticker (e.g., input: AA, would return 12 elements if the only data uploaded were the single data set above)
    3.  add a new record







## Author Details

- Jainam Rakeshkumar Shah
- jrshah1928@gmail.com
- +1 902 990 2727



## Run Locally

Clone the project

```bash
git clone https://github.com/jainamshah2810/stockanalyzer.git
```

Go to the project directory

```bash
cd stockanalyzer
```

create database stockanalyzerdb in MYSQL

```bash
create database stockanalyzerdb;
```

Run the main file

```bash
StockanalyzerApplication.java
```

## Run Via Docker Image

Pull the Docker Image from
[![DockerHub]](https://hub.docker.com/repository/docker/jrshah1928/stockanalyzer)

```bash
docker pull jrshah1928/stockanalyzer  
```
Run this Image
```bash
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
```
Create database stockanalyzerdb in MYSQL

```bash
create database stockanalyzerdb;
```

Pull the mysql image from DockerHub

```bash
docker pull mysql:5.7
```
Create a docker network to communicate SpringBoot Application and MYSQL Database

```bash
docker network create SpringBoot-mysql-net
```

Run the mysqlcontainer in network
```bash
docker run --name mysqldb --network springboot-mysql-net -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=stockanalyzerdb -e MYSQL_USER=sa -e MYSQL_PAS
SWORD=1234 -d mysql:5.7
```
Check database in container executable bash mode
```bash
docker exec -it {docker container id} bash
```
Check all database using mysql COMMAND
```bash
show database;
```

Change configuration in Application.properties file
```bash
spring.datasource.url=jdbc:mysql://mysqldb:3306/stockanalyzerdb?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=sa
spring.datasource.password=1234
```
Run the main file

```bash
StockanalyzerApplication.java
```
## Project Features

#### Register
Admin and User register to our system Based on the Role.
    
    {"name":"Jainam",

    "email":"jain@dal.ca",

    "password":"pass@1234",

    "roles":[{"id":2/1,"name":"USER"/"ADMIN"}]}
  
#### Login with JWT Token
    The login will be validated using the userid and password.If they match, it will return the JWT token.
    For additional functionality, the user or administrator must use a JWT token.  
#### Role Based Security
    Only Admin can see the all users.
    Normal users can see their data. 
#### Upload Data
    The user can upload the.csv file, and the data will be saved to the database.
    If the extension is different than ".csv," then it will throw an error.
#### Display Data
    The user can also view all data as well as data by stock name. 
#### Docker Image
    Create docker file with steps.
    Create docker image and push to docker hub.

## Future Improvements

#### Making the application live by Hosting on platform such as AWS
#### Security
    
    Increase security so that the user can only see their data and not able to access the data of others. 
#### Data Cleaning
    Cleaning up the data in the.csv files 

#### Docker Compose files
    Efficient way to create docker images of the application.
  














