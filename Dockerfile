# Use an image that has Maven and Java 21 pre-installed
FROM maven:3.9-eclipse-temurin-21-alpine

# Set working directory
WORKDIR /app

# Copy all your project files (pom.xml, src, etc.) into the container
COPY . .

# Compile the code (Using Maven to handle the package paths correctly)
# We skip tests here because Jenkins already ran them in the previous stage
RUN mvn clean package -DskipTests

# Run the application
# We point to the specific class inside the package
CMD ["java", "-cp", "target/classes", "com.example.ToDoList"]