pipeline {
    agent any
    # comment
    stages {
        stage('build') {
            steps {
                sh './mvnw compile'
            }
        }
        stage('test') {
            steps {
                sh './mvnw test'
            }
        }
        stage('test') {
            steps {
                sh './mvnw deploy'
            }
        }
    }
}
