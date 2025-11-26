# Use an image that has Maven and Java 21 pre-installed
FROM maven:3.9-eclipse-temurin-21-alpine

# Set working directory inside the container
WORKDIR /app

# Copy all your project files (pom.xml, src, etc.) into the container
COPY . .

# Compile the code
# We skip tests here because Jenkins runs them in the previous stage
RUN mvn clean package -DskipTests

# Run the application
# We point to the specific class inside the package com.example
CMD ["java", "-cp", "target/classes", "com.example.ToDoList"]