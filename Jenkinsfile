pipeline {
    agent any

    environment {
        // --- CONFIGURATION ---
        // Your Docker Hub Username
        DOCKER_HUB_USER = 'yguptaji' 
        
        // Your Roll Number
        REPO_NAME = 'IMT2023125' 
        
        // The ID we created in Jenkins Credentials
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
                // This runs the JUnit tests we wrote. 
                // If tests fail, the pipeline stops here.
                bat 'mvn clean test'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    echo 'Tests passed. Building Docker Image...'
                    docker.withRegistry('', REGISTRY_CRED) {
                        // 1. Build the image
                        // We use the Dockerfile we just created
                        def appImage = docker.build("${DOCKER_HUB_USER}/${REPO_NAME}:${env.BUILD_ID}")
                        
                        // 2. Push with the Build ID (e.g., :15)
                        appImage.push()
                        
                        // 3. Push as 'latest' so it's easy to pull
                        appImage.push('latest')
                    }
                }
            }
        }
    }
}