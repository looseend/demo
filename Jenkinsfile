pipeline {
    agent any
    stages {
        stage('build') {
        steps {
            withMaven (maven: 'Maven') {
                sh 'mvn compile'
            }
        }
        }
        steps {
        stage('test') {
            withMaven (maven: 'Maven') {
                sh 'mvn test'
            }
        }
        }
        steps {
        stage('deploy') {
            withMaven (maven: 'Maven') {
                sh 'mvn deploy'
            }
        }
        }
    }
}
