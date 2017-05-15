pipeline {
    agent any
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
        stage('deploy') {
            steps {
                sh './mvnw deploy'
            }
        }
    }
}
