pipeline {
    agent any

    environment {
        // --- CONFIGURATION ---
        // Your Docker Hub Username
        DOCKER_HUB_USER = 'yguptaji' 
        
        // Your Roll Number (Must match the Repo name you created on Docker Hub)
        REPO_NAME = 'IMT2023125' 
        
        // The ID of the credentials stored in Jenkins
        REGISTRY_CRED = 'dockerhub-login'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Running Maven Build and Tests...'
                // runs the JUnit tests. If this fails, the pipeline stops.
                bat 'mvn clean test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    echo 'Tests passed. Building Docker Image...'
                    
                    // WINDOWS FIX: We use 'withCredentials' to handle login manually 
                    // This avoids the "context desktop-linux" error
                    withCredentials([usernamePassword(credentialsId: REGISTRY_CRED, passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        
                        // 1. Log in to Docker Hub
                        // We pipe the password into the command for security
                        bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                        
                        // 2. Build the image
                        bat "docker build -t ${DOCKER_HUB_USER}/${REPO_NAME}:${env.BUILD_ID} ."
                        
                        // 3. Tag it as 'latest'
                        bat "docker tag ${DOCKER_HUB_USER}/${REPO_NAME}:${env.BUILD_ID} ${DOCKER_HUB_USER}/${REPO_NAME}:latest"
                        
                        // 4. Push the numbered version (e.g., :15)
                        bat "docker push ${DOCKER_HUB_USER}/${REPO_NAME}:${env.BUILD_ID}"
                        
                        // 5. Push the 'latest' version
                        bat "docker push ${DOCKER_HUB_USER}/${REPO_NAME}:latest"
                        
                        // 6. Logout for security
                        bat 'docker logout'
                    }
                }
            }
        }
    }
}