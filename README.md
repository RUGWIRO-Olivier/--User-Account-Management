# user-profile-management-api

`user-profile-management-api` is a backend application written in spring boot for the purpose of managing users of a soft drink company.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for testing purposes.

### Prerequisites

- you need to have [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) installed on your machine (Starting from jdk 8 or later)
- You need to have an IDE installed on your machine too, I recommend [IntellJ IDEA](https://www.jetbrains.com/idea/)
- You need to have a relational database management system on your machine, in this application I am using [MariaDb](https://go.mariadb.com/download-mariadb-server-community.html) so consider having it in your machine.
- And optionally you can have [Maven CLI](https://maven.apache.org/ref/3.6.1/maven-embedder/cli.html) installed on your machine as it help when building or doing some other maven actions in spring boot applications
- For you to be able to test the endpoints you need to have an API testing tool like [Postman Client](https://www.postman.com/downloads) on your machine or alternatively I have configured Springdoc Swagger in this application which allow you to test the application easily without having to download another API testing tool
- So to test the endpoints with Swagger use this link http://localhost:8080/swagger-ui/index.html as the default server port for this applicaion is ``8080`` so once you decide to change the server port number remember to change it in the above swagger link too


## Built with
This project is built with:
- [Spring Boot](https://spring.io)
- [Maven](https://maven.apache.org/)

## What this project can do

This application is able to do the following: 
- Allow user registration
- Users can login and get a Token
- It is able to hash user's password before saving a user in a databse for security
- It is able to let users reset their password once they are forgotten by sending them an email with the help of [Infobip](https://www.infobip.com)
- It validate users data before they register their account
- It allows user profiles to be deleted once required
- It performs CRUD operations perfectly
- It allow to search a user by either user Id or username;
- It allow users to add profile images on their account
- This project uses JWT to generate token for the users

## Gems used

```bash

1. dotenv
2. Dockerfile
3. docker-compose
```
## Author (s)
### 1. RUGWIRO Olivier
* Github: [@RUGWIRO-Olivier](https://github.com/RUGWIRO-Olivier)

## Show your support 
Give a ⭐️ if you appreciated my work!
