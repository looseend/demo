node {
    checkout scm
    env.JAVA_HOME = "${tool 'jdk8'}"
    env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"

    DOCKER_CRED = credentials('jfrog.io')

    def server = Artifactory.server 'grantking.jfrog.io'
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo = Artifactory.newBuildInfo()
    def pom = readMavenPom file: 'pom.xml'
    def version = pom.version // .replace("-SNAPSHOT", ".${currentBuild.number}")
    def rtDocker = Artifactory.docker credentialsId: 'jfrog.io'

    stage('Artifactory configuration') {
        rtMaven.tool = 'Maven.3.5.0' // Tool name from Jenkins configuration
        rtMaven.deployer releaseRepo: 'london', snapshotRepo: 'london', server: server
        rtMaven.resolver releaseRepo: 'london', snapshotRepo: 'london', server: server

        def descriptor = Artifactory.mavenDescriptor()
        descriptor.version = version
        descriptor.transform()
        echo "Version: ${version} Build: ${env.BUILD_NUMBER}"

        sh "java -version"

        echo "JDK: ${env.jdk8}"
        echo "JDK: ${env.JAVA_HOME}"

//        def major = originalV[0]
//        def minor = originalV[1]
//        def v = "${major}.${minor}-${env.BUILD_NUMBER}"
//        if (v) {
//            echo "Building version ${v}"
//        }
    }

    stage('build') {
        rtMaven.run pom: 'pom.xml', goals: 'clean compile', buildInfo: buildInfo
    }
    stage('install & test') {
        rtMaven.run pom: 'pom.xml', goals: 'install', buildInfo: buildInfo
    }
    stage('Build & Push image') {
        /* Finally, we'll push the image with two tags:
         * First, the incremental build number from Jenkins
         * Second, the 'latest' tag.
         * Pushing multiple tags is cheap, as all the layers are reused. */
        docker.withRegistry('https://grantking-london-docker.jfrog.io', 'jfrog.io') {
            withCredentials([usernamePassword(credentialsId: 'jfrog.io', passwordVariable: 'DOCKER_CRED_PSW', usernameVariable: 'DOCKER_CRED_USR')]) {

                sh 'docker login -u ${DOCKER_CRED_USR} -p ${DOCKER_CRED_PSW} grantking-london-docker.jfrog.io'
                def app = docker.build("grantking-london-docker.jfrog.io/demo")
                app.push("${version}")
                app.push("latest")
            }
        }

    }

//    stage('Docker') {
//        dir('.') {
//            sh "docker build -t acme/demo:${env.BUILD_NUMBER} ."
//        }
//    }

    stage('Publish build info') {
        server.publishBuildInfo buildInfo
    }

}

