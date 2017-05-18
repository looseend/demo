node {
    checkout scm

    def server = Artifactory.server 'rancher.artifactory.build'
    def rtMaven = Artifactory.newMavenBuild()
    def buildInfo = Artifactory.newBuildInfo()

    stage('Artifactory configuration') {
        rtMaven.tool = 'Maven.3.5.0' // Tool name from Jenkins configuration
        rtMaven.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: server
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: server

        def originalV = version()
        def major = originalV[0]
        def minor = originalV[1]
        def v = "${major}.${minor}-${env.BUILD_NUMBER}"
        if (v) {
            echo "Building version ${v}"
        }
    }

    stage('build') {
        rtMaven.run pom: 'pom.xml', goals: 'clean compile', buildInfo: buildInfo
    }
    stage('install & test') {
        rtMaven.run pom: 'pom.xml', goals: 'install', buildInfo: buildInfo
    }
//    stage('Build & Push image') {
//        /* Finally, we'll push the image with two tags:
//         * First, the incremental build number from Jenkins
//         * Second, the 'latest' tag.
//         * Pushing multiple tags is cheap, as all the layers are reused. */
//        def app = rtDocker.build("acme/b2b")
////            app.push("${env.BUILD_NUMBER}")
////            app.push("latest")
////
//    }

    stage('Publish build info') {
        server.publishBuildInfo buildInfo
    }

}

