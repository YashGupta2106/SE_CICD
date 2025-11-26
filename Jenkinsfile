pipeline {
    agent any

    environment {
        // --- CONFIGURATION ---
        DOCKER_HUB_USER = 'yguptaji' 
        REPO_NAME = 'IMT2023125' 
        
        // This ID must match what you created in Jenkins > Manage Jenkins > Credentials
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
                // If tests fail, the pipeline stops here.
                bat 'mvn clean test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    echo 'Tests passed. Building Docker Image...'
                    
                    // CRITICAL FIX FOR WINDOWS: 
                    // We use 'withCredentials' to handle login manually instead of using the plugin.
                    withCredentials([usernamePassword(credentialsId: REGISTRY_CRED, passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        
                        // 1. Log in to Docker Hub 
                        // We pipe the password into the login command for security
                        bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                        
                        // 2. Build the image
                        bat "docker build -t ${DOCKER_HUB_USER}/${REPO_NAME}:${env.BUILD_ID} ."
                        
                        // 3. Tag it as 'latest' as well
                        bat "docker tag ${DOCKER_HUB_USER}/${REPO_NAME}:${env.BUILD_ID} ${DOCKER_HUB_USER}/${REPO_NAME}:latest"
                        
                        // 4. Push the numbered version (e.g., :1, :2)
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