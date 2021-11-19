# Auction Shortened Url
This is url shortening service. It is a Restfull application which written using with Spring Boot.

## Tech stack and whys :hammer_and_wrench:
- [Spring Boot](https://spring.io/projects/spring-boot)  
- [Spring Data Jpa](https://spring.io/projects/spring-data-jpa)  
- [Postgre Sql](https://www.postgresql.org/)   
- [Mapstruct](https://mapstruct.org/)  - Good performance than other java mapping framework
- [Lombok](https://projectlombok.org/)  - Less boilerplate code
- [Swagger](https://swagger.io/) - Easy implementation and widespread use
- [Docker](https://www.docker.com/)      
- Unit Test

## Business Rules :pushpin:
- The user creates a membership.  
- The user defines the URL which wants to shorten to the system. The system creates a short URL to correspond to the long URL and saves it between the user's short urls.  
- The user can view and delete shortened urls.  

<!-- GETTING STARTED -->
## Getting Started 💥
Run the service by following the steps below.   

### Prerequisites
You should have Docker to get the project up and running.   
- [Docker](https://www.docker.com/)  

### LIVE
[Auction shortened url backend demo](https://auction-shortened-url-backend.herokuapp.com/)

### Run the Application

Firstly clone the repo to your local
   ```sh
   git clone https://github.com/betul-sahin/auction-shortened-url.git
   ```

Steps to run the application on Docker.
   ```sh
   docker-compose up
   ```


<!-- USAGE EXAMPLES -->
## Usage :desktop_computer:

- Url shortening service hosted on `http://localhost:8080`  

### Swagger UI
Endpoints : `http://localhost:8080/swagger-ui.html`


## Contact 📫 
[Betül Şahin](https://www.linkedin.com/in/betulsahin/)

