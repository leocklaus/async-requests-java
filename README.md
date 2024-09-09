# WORKING with COMPLETABLE FUTURE and STRUCTURED CONCURRENCY on Java

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) 

Simple project to test async programming on Java.

## About this project

This spring project aims to create a simple API that retrieve some Pókemon info from PokemonAPI in order to test Java Async programming.

### The main goals for this project are:

* Retrieve Pókemon experience value from PokemonAPI by Pókemon name
* It should retrieve info from 10 Pókemon Async
* Returned info should be displayed on console

## Features tested on this project

* Completable Future
* Completable Future with Virtual Threads
* Structured Concurrency

## How to run this project locally

1. Clone this project
2. Open the folder you just created on your favorite IDE
3. Install dependencies with Maven
4. Start the project
5. Project will run on `http://localhost:8080`

## Endpoints

* /cf?vt=false -> comparable future without virtual threads
* /cf?vt=true -> comparable future with virtual threads
* /sc -> structured concurrency
* /sync -> sync request

## Results

After each request, check your console for results. The amount of time each request took to be completed should be displayed. This way you can compare different ways of fetching data async on Java.

## Technologies

* Java 21 and Spring Boot 3.3.2
* Spring Web


## Found a bug or want to contribute to this project?

If you've found a bug, make sure you open an Issue on this project repository. Also, all users are welcome to submit pull requests, but remember to mention on the PR which Issue are you fixing on it.



