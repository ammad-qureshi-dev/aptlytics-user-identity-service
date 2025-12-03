# Micro-Service Setup Guide

Contents
1. Environment Setup
2. Required Dependencies
3. Liquibase Setup
4. Package Publication (TBD)
5. Docker Setup (TBD)
6. GitHub Actions(TBD) 

## 1. Environment Setup

During development, code typically progresses through multiple environments before reaching end users. For our workflow,
we maintain three dedicated environments:

1. Development Environment

- Used for active feature development and local testing.
Developers push new feature branches or changes here once they are ready for integration and internal verification.

2. Staging Environment

- Once features are completed and fully tested, they are merged into the staging environment.
This environment acts as a production-like sandbox where final validation, integration testing, and quality checks occur
before deployment to production.

3. Production Environment

- The live environment serving real users and operating with real-time data.
Only stable, fully verified features are deployed to production.
Development typically consists of multiple environments. We will be using three environments due to lack of resources

### Environment Profiles
For each environment create an ```application.yaml``` configuration file. This will be used to define how our
application is configured based on environment specific requirements. Each ```application.yaml``` will also be paired
with an ```application.properties``` file to store environment specific secrets. In the future, we will use ```vault```
to store our secrets globally.
1. ```application.yaml```
   * main configuration file 
   ```yaml
    spring:
     profiles:
      active:
       - dev
     application:
       name: user-identity-service
     liquibase:
       # enable on deployment
       enabled: true
       change-log: classpath:db/changelog/master.yaml
     datasource:
       url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
       username: ${DB_USER_RW}
       password: ${DB_PWD_RW}
       driver-class-name: org.postgresql.Driver
       hikari:
         maximum-pool-size: 10
         data-source-properties:
           prepare-threshold: 0
     jpa:
       show-sql: false
       hibernate:
         ddl-auto: none
         naming:
           implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
           physical-strategy: org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
       properties:
         hibernate:
           dialect: org.hibernate.dialect.PostgreSQLDialect
     server:
       port: 8081
       error:
         include-message: always
    logging:
      level:
        org.hibernate.SQL: debug
        liquibase.executor: debug
    ```
2. ```application-dev.yaml```
   * development configuration file
   ```yaml
   spring:
    liquibase:
      enabled: false
    datasource:
      url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/postgres?user=${DB_USER_RW}&password=${DB_PWD_RW}
      username: ${DB_USER_RW}
      password: ${DB_PWD_RW}
   ```
   * here we only have environment specific values since ```application.yaml``` is our base configuration file

### Environment Secrets
Firstly, add any ```.properties``` file to the  ```.gitignore```. For example:
```.gitignore
src/main/resources/application.properties
src/main/resources/application-dev.properties
```

We do not want to expose our environment secrets anywhere. Use the ```application.properties``` file to store key-value
pairs of environment secrets. Each environment has their own ```.properties``` file. For example:
```properties
DB_USER_RW=postgres
DB_HOST=google.com
DB_PORT=5432
```

In the future, we aim to use a vault to store our secrets and share it globally

## 2. Required Dependencies

* incorporate these core dependencies into ```build.gradle```
```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-data-rest'
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
implementation 'org.springframework.boot:spring-boot-starter-web'
compileOnly 'org.projectlombok:lombok'
developmentOnly 'org.springframework.boot:spring-boot-devtools'
runtimeOnly 'org.postgresql:postgresql'
annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
annotationProcessor 'org.projectlombok:lombok'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
```
* run ```./gradlew clean build``` to ensure all dependencies are working and installed

## 3. Liquibase Setup
Liquibase is a database change management tool that tracks database changes in a structured, version-controlled way. It
allows the teams to automate database migrations, and ensuring all database changes are recorded. 

### Setup Instructions
1. Add the dependency to your ```build.gradle```
```groovy
implementation 'org.liquibase:liquibase-core:4.33.0'
```
2. Run ``` ./gradlew bootRun``` to ensure all liquibase is successfully downloaded.
3. Under ```src/main/resources```
   1. Create a folder called ```db```
   2. Create a subfolder in ```db``` called ```changelog```
      3. In the ```changelog``` folder, create a file called ```master.yaml```
         1. Here is where all the changes are tracked and you can view the database version history
         * Sample changelog:
         ```yaml
         databaseChangeLog:
             - changeSet:
                id: 0001
                author: ammad
                changes:
                - sqlFile:
                    path: db/changelog/0001_create_schema_and_user_entities.sql
                    splitStatements: true
                    endDelimiter: ;
         ```
4. Adding to your environment config as shown in the environment section

