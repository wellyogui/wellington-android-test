# README

## Introduction

This document aims to provide a brief overview of Android testing for Zemoga.

For this project, I have implemented the MVVM architecture and used Android Studio version Electric Eel | 2022.1.1 Patch 2. To run this project, you will need to have the latest version installed.

The minimum SDK required is 26, while the compile version and target SDK are both set to API Level 33.

The Gradle version used is 7.2.2, and the Kotlin version is 1.8.0.

# Libraries
## Retrofit

Retrofit is a type-safe HTTP client for Android and Java. It simplifies the process of making RESTful API calls by allowing to define an interface for the API calls and handling the underlying network operations, such as serialization and deserialization, in the background. Retrofit also supports various data formats, including JSON and XML.

### Pros of using Retrofit

- Type-safe API calls with easy-to-use and intuitive interfaces
- Simple integration with multiple data formats such as JSON, XML, and Protobuf
- Efficient network request management, including retries, timeouts, and caching
- Easy-to-use and customizable error handling
- Retrofit has a large community with active development and support

## Hilt

Hilt is a dependency injection library for Android that simplifies the process of managing dependencies in an application. It provides a way to define and inject dependencies using annotations, making it easy to create and maintain a modular and testable codebase.
### Pros of using Hilt

- Simplified dependency injection that reduces boilerplate code
- Hilt provides compile-time verification of dependencies, making it easier to detect and fix errors
- Hilt simplifies testing by allowing developers to easily swap dependencies with mocks

## Mockito

Mockito is a Java-based mocking framework that helps to simplify unit testing by allowing to create mock objects of classes and interfaces. Mockito also provides an intuitive API for setting up and verifying mock behavior.

### Pros of using Mockito

- Mockito simplifies unit testing by allowing developers to create mock objects of classes and interfaces
- It provides an intuitive API for setting up and verifying mock behavior
- Mockito works seamlessly with other testing frameworks, such as JUnit and Espresso
- Mockito is easy to learn and has a large and supportive community

## Room

Room is a database library for Android that provides an abstraction layer over SQLite, making it easier to work with databases in an Android application. It uses annotations to define database entities, relationships, and queries, allowing developers to write less boilerplate code. Room also provides compile-time verification of SQL queries, reducing the risk of runtime errors.

### Pros of using Room

- Room provides a simple and efficient way to work with databases in Android applications
- It reduces boilerplate code by using annotations to define database entities, relationships, and queries
- Room provides compile-time verification of SQL queries, reducing the risk of runtime errors
- Room provides a type-safe API for performing database operations, making it easier to avoid common errors

## RxJava

RxJava is a reactive programming library for Java or Kotlin and Android that simplifies the process of writing asynchronous and event-based code. It provides a set of operators that can be used to transform, filter, and combine streams of data, making it easier to handle complex data flows. RxJava also provides a way to handle errors and manage backpressure, ensuring that applications remain responsive and performant.

### Pros of using RxJava

- RxJava simplifies the process of writing asynchronous and event-based code
- It provides a set of operators that can be used to transform, filter, and combine streams of data
- RxJava provides a way to handle errors and manage back