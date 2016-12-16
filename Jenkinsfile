@Library('common') import common
def common = new common()

def notifyFailed(reason, error) {
    chatmsg = "**[mia ${version}](https://modapp-t1.adeo.no/mia/) ${reason} **\n\n${common.getChangeString()}"
    mattermostSend channel: 'fo-mia', color: '#FF0000', message: chatmsg
    throw error
}

node {
    common.setupTools("Maven 3.3.3", "java8")

    stage('Checkout') {
        checkout([$class: 'GitSCM', branches: [[name: "*/master"]], doGenerateSubmoduleConfigurations: false, extensions: [], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[url: 'ssh://git@stash.devillo.no:7999/fo/mia.git']]])
        pom = readMavenPom file: 'pom.xml'
        if (useSnapshot == 'true') {
            version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}-SNAPSHOT")
        } else {
            version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}")
        }
        sh "mvn versions:set -DnewVersion=${version}"
    }

    stage('Build (java)') {
        try {
            sh "mvn clean install -DskipTests -P pipeline"
        } catch(Exception e) {
            notifyFailed("Bygg feilet", e)
        }
    }

    stage('Run tests (java)') {
        try {
            sh "mvn test -P pipeline"
        } catch(Exception e) {
            step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
            notifyFailed("Java-tester feilet", e)
        }
    }

    dir('web/src/main/frontend') {
        stage('build js') {
            try {
                sh "npm install"
                sh "npm run buildProd"
            } catch(Exception e) {
                notifyFailed("Javascript-bygging feilet", e)
            }
        }

        stage('run tests (js)') {
            try {
                sh "npm test"
                sh "npm run eslint"
            } catch(Exception e) {
                notifyFailed("Javascript tester/linter feilet", e)
            }
        }
    }

    stage('Deploy nexus') {
        try {
            sh "mvn deploy -DskipTests -P pipeline"
            currentBuild.description = "Version: ${version}"
            sh "mvn versions:set -DnewVersion=${pom.version}"
            if (useSnapshot != 'true') {
                sh "git tag -a ${version} -m ${version} HEAD && git push --tags"
            }
        } catch(Exception e) {
            notifyFailed("Deploy av artifakt til nexus feilet", e)
        }
    }
}

stage("Deploy app") {
    try {
        callback = "${env.BUILD_URL}input/Deploy/"
        node {
            def author = sh(returnStdout: true, script: 'git --no-pager show -s --format="%an <%ae>" HEAD').trim()
            common.deployApp('mia', version, "${miljo}", callback, author)
        }

        timeout(time: 15, unit: 'MINUTES') {
            input id: 'deploy', message: 'deploy OK?'
        }
    } catch(Exception e) {
        notifyFailed("Deploy feilet", e)
    }
}

stage("Int. tester") {
    try {
        node {
            dir("web/src/main/frontend") {
                sh("node nightwatch.js --env phantomjs --url ${testurl}")
            }
        }
    } catch(Exception e) {
        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/*.int.xml'])
        notifyFailed("Integrasjonstester feilet", e)
    }
}

chatmsg = "**[mia ${version}](https://modapp-t1.adeo.no/mia/) Bygg og deploy OK**\n\n${common.getChangeString()}"
mattermostSend channel: 'fo-mia', color: '#00FF00', message: chatmsg


