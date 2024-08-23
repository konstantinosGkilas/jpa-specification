
# Dynamic Entity Filtering with JPA Specification and Metamodel

## Overview

This library provides a **generic solution** for filtering entities using **JPA Specification and Metamodel**. It aims to solve the problem of creating **dynamic and reusable filtering criteria** in your applications. By leveraging the power of JPA Specifications, you can build **complex queries at runtime** based on user-defined criteria, without the need for writing custom queries.

## Key Features

- **Generic Filtering**: The `BaseFilter` class allows you to filter on various field types, supporting operations like equality, inequality, and inclusion/exclusion in lists.
- **Range Filtering**: The `RangeFilter` class extends `BaseFilter` to support range-based criteria like greater than, less than, and like conditions.
- **Specification Building**: The `CriteriaSpecification` class provides utilities to build dynamic JPA Specifications based on filtering criteria.
- **Metamodel Usage**: By using **JPA Metamodel**, you ensure type safety when referencing entity attributes in your filters, reducing the likelihood of errors.
- **Packaging**: The project is packaged using the **Maven Assembly Plugin** to create a JAR with dependencies, making it easy to integrate with other projects.

## Problem Solved

Manually crafting queries for dynamic filtering can be **time-consuming** and **error-prone**. This library abstracts that complexity by providing **reusable filter classes** and methods to build JPA Specifications dynamically. It allows you to focus on the **business logic** without worrying about the underlying query structure.

## Why JPA Specification and Metamodel?

- **Dynamic Querying**: JPA Specification allows you to build queries dynamically at runtime, making it ideal for scenarios where the filtering criteria may change based on user input.
- **Type Safety**: The Metamodel, generated through the `hibernate-jpamodelgen-jakarta` dependency, ensures that your attribute references are type-safe, reducing the chance of runtime errors.
- **Reusability**: Specifications can be combined, reused, and composed to build complex queries in a **clean and maintainable** way.

## Example Usage

### Meta Model Example

Consider a `Book` entity with fields like `id`, `title`, and `authorName`. Using the Metamodel, you can refer to these fields in a **type-safe** manner:

```java
addIfNotNull(specifications, bookCriteriaSpecification.buildSpecification(Book_.ID, bookCriteria.getBookId()));
addIfNotNull(specifications, bookCriteriaSpecification.buildSpecification(Book_.TITLE, bookCriteria.getTitle()));
addIfNotNull(specifications, buildJoinSpecification(Book_.AUTHOR, bookCriteria.getAuthorName(), Author_.NAME));
```

In this example, `Book_` and `Author_` are generated Metamodel classes, ensuring that `ID`, `TITLE`, and `NAME` are valid attributes of their respective entities.

### REST API Example

The following example shows how to filter books by author name and book ID using the criteria filtering:

```
http://localhost:8083/api/books?authorName.like=Martin&bookId.greaterThan=1
```

This request would return books with the author's name containing "Martin" and with an ID greater than 1.

## Maven Configuration

### Assembly Plugin

The project uses the **Maven Assembly Plugin** to package the project as a JAR with dependencies. This ensures that all necessary dependencies are included in the JAR, making it easy to deploy and use in other projects.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <configuration>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
    </configuration>
    <executions>
        <execution>
            <id>make-assembly</id>
            <phase>package</phase>
            <goals>
                <goal>single</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Metamodel Generation

The Metamodel is generated using the `hibernate-jpamodelgen-jakarta` dependency. This dependency processes your JPA entities and generates static Metamodel classes for them, which you can use for **type-safe** queries.

```xml
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-jpamodelgen-jakarta</artifactId>
    <version>5.6.15.Final</version>
</dependency>
```

This dependency automatically generates the `Book_`, `Author_`, and other related classes when you build your project, ensuring that your code is always type-safe.

## How to Use

1. **Clone the Project**: Clone this repository to your local machine.
2. **Build the Project**: Use Maven to build the project and generate the JAR with dependencies.
3. **Install the JAR**: Install the generated JAR in your local Maven repository or deploy it to a shared Maven repository.
4. **Use in Another Project**: Add the dependency in your new project and start using the filtering functionality in your REST APIs.

```xml
<dependency>
    <groupId>com.kgkilas</groupId>
    <artifactId>filtering-library</artifactId>
    <version>0.0.2</version>
</dependency>
```

## Conclusion

This library simplifies the process of filtering entities using **JPA Specification and Metamodel**. It is designed to be **flexible, reusable,** and **easy to integrate** into any Spring Boot project. Whether you need simple equality checks or complex range filtering, this library provides the tools you need to build **dynamic queries** with minimal effort.
