# MBOT

MBOT is a twitch chatbot that does some stuff

## Installation

you need [maven](https://maven.apache.org/) to install the dependencies. After cloning the project, run on command line: 

```bash
mvn clean install
```

you need to put some configurations such as twitch account credentials (oauth tokens and client id/secret). Check the files ``application-prod / application-local`` for more information. 

## Dependencies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [SpringData](https://spring.io/projects/spring-data)
- [twitch4j](https://twitch4j.gitlab.io/twitch4j/getting-started/installation/)
- [Flyway](https://flywaydb.org/)
- [Model Mapper](http://modelmapper.org/user-manual/)
- [Gson](https://github.com/google/gson)

## Running locally (its gonna use in-memory database H2)

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Running locally using Postgre DB

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```


## Contributing
Pull requests and suggestions are welcome. For major changes, please open an issue first to discuss what you would like to change.

