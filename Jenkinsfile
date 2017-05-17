pipeline {
    agent any
    stages {
        stage('build') {
            withMaven (maven: 'Maven') {
                sh 'mvn compile'
            }
        }
        stage('test') {
            withMaven (maven: 'Maven') {
                sh 'mvn test'
            }
        }
        stage('deploy') {
            withMaven (maven: 'Maven') {
                sh 'mvn deploy'
            }
        }
    }
}
