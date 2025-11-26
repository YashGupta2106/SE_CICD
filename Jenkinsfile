pipeline {
    agent any

    // environment {
    //     // --- CONFIGURATION ---
    //     DOCKER_HUB_USER = 'yguptaji' 
    //     REPO_NAME = 'IMT2023125' 
        
    //     // This ID must match what you created in Jenkins > Manage Jenkins > Credentials
    //     DOCKERHUB_CREDS = 'dockerhub-login'
    // }
    environment {
        DOCKERHUB_USERNAME = 'yguptaji'
        IMAGE_NAME = 'todo-cli'
        IMAGE_TAG = "${BUILD_NUMBER}"

        GITHUB_CREDS = credentials('githubCreds')
        DOCKERHUB_CREDS = credentials('dockerhub-login')

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
                    withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDS, passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        
                        // 1. Log in to Docker Hub 
                        // We pipe the password into the login command for security
                        bat 'echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin'
                        
                        // 2. Build the image
                        bat "docker build -t ${DOCKERHUB_USERNAME}/${REPO_NAME}:${env.BUILD_ID} ."
                        
                        // 3. Tag it as 'latest' as well
                        bat "docker tag ${DOCKERHUB_USERNAME}/${REPO_NAME}:${env.BUILD_ID} ${DOCKERHUB_USERNAME}/${REPO_NAME}:latest"
                        
                        // 4. Push the numbered version (e.g., :1, :2)
                        bat "docker push ${DOCKERHUB_USERNAME}/${REPO_NAME}:${env.BUILD_ID}"
                        
                        // 5. Push the 'latest' version
                        bat "docker push ${DOCKERHUB_USERNAME}/${REPO_NAME}:latest"
                        
                        // 6. Logout for security
                        bat 'docker logout'
                    }
                }
            }
        }
    }
}