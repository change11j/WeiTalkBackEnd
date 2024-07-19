pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    environment {
        DOCKER_HOST = 'tcp://13.125.233.208:2375'
        IMAGE_NAME = 'weitalk-backend'
        CONTAINER_NAME = 'weitalk-backend-container'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build and Test') {
            parallel {
                stage('Compile') {
                    steps {
                        sh 'mvn compile'
                    }
                }
                stage('Unit Tests') {
                    steps {
                        sh 'mvn test'
                    }
                }
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Docker Build and Push') {
            steps {
                script {
                    docker.withServer(env.DOCKER_HOST) {
                        def customImage = docker.build("${env.IMAGE_NAME}:${env.BUILD_NUMBER}", "-f Dockerfile.slim .")
                        customImage.push()
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker.withServer(env.DOCKER_HOST) {
                        sh """
                            docker stop ${CONTAINER_NAME} || true
                            docker rm ${CONTAINER_NAME} || true
                            docker run -d -p 8080:8080 --name ${CONTAINER_NAME} ${IMAGE_NAME}:${BUILD_NUMBER}
                        """
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}