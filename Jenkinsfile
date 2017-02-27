@Library('common') import common
def commonLib = new common()

def notifyFailed(reason, error) {
    def commons = new common()
    changelog = commons.getChangeString()
    chatmsg = "**[mia ${version}](https://modapp-t1.adeo.no/mia/) ${reason} **\n\n${changelog}"
    mattermostSend channel: 'fo-mia', color: '#FF0000', message: chatmsg
    currentBuild.result = 'FAILED'
    step([$class: 'StashNotifier', commitSha1: env.GIT_COMMIT])
    throw error
}

node {
    commonLib.setupTools("Maven 3.3.3", "java8")

    stage('Checkout') {
        checkout([$class: 'GitSCM', doGenerateSubmoduleConfigurations: false, extensions: [], gitTool: 'Default', submoduleCfg: [], userRemoteConfigs: [[url: 'ssh://git@stash.devillo.no:7999/fo/mia.git']]])
        step([$class: 'StashNotifier'])
    }

    stage('Set version') {
        pom = readMavenPom file: 'pom.xml'
        commitShaShort = env.GIT_COMMIT.substring(0, 7)
        if(env.BRANCH_NAME == 'master') {
            version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}.${commitShaShort}")
        } else {
            version = pom.version.replace("-SNAPSHOT", ".${currentBuild.number}.${commitShaShort}-SNAPSHOT")
        }
        sh "mvn versions:set -DnewVersion=${version}"
    }

    if(env.BRANCH_NAME != "master") {
        stage('Merge master') {
            sh "git merge origin/master"
        }
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

    dir('web/src/frontend') {
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
                sh "npm run coverage"
                sh "npm run eslint"
            } catch(Exception e) {
                notifyFailed("Javascript tester/linter feilet", e)
            }
        }
    }

    stage('Deploy nexus') {
        try {
            sh "mvn -B deploy -DskipTests -P pipeline"
            currentBuild.description = "Version: ${version}"
            if(env.BRANCH_NAME == "master") {
                sh "git tag -a ${version} -m ${version} HEAD && git push --tags"
            }
        } catch (Exception e) {
            notifyFailed("Deploy av artifakt til nexus feilet", e)
        }
    }
}

if(env.BRANCH_NAME == 'master') {
    stage("Deploy app") {
        callback = "${env.BUILD_URL}input/Deploy/"
        node {
            def author = sh(returnStdout: true, script: 'git --no-pager show -s --format="%an <%ae>" HEAD').trim()
            def deploy = commonLib.deployApp('mia', version, "t1", callback, author).key

            try {
                timeout(time: 15, unit: 'MINUTES') {
                    input id: 'deploy', message: "deployer ${deploy}, deploy OK?"
                }
            } catch(Exception e) {
                msg = "Deploy feilet [" + deploy + "](https://jira.adeo.no/browse/" + deploy + ")"
                notifyFailed(msg, e)
            }
        }
    }

    stage("Int. tester") {
        node {
            try {
                dir("web/src/frontend") {
                    //sh("node nightwatch.js --env phantomjs --url https://modapp-t1.adeo.no/mia")
                }
            } catch (Exception e) {
                notifyFailed("Integrasjonstester feilet", e)
                step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/*.int.xml'])
            }

            currentBuild.result = 'SUCCESS'
        }
    }

    chatmsg = "**[mia ${version}](https://modapp-t1.adeo.no/mia/) Bygg og deploy OK**\n\n${commonLib.getChangeString()}"
    mattermostSend channel: 'fo-mia', color: '#00FF00', message: chatmsg
}

node {
    step([$class: 'StashNotifier', commitSha1: env.GIT_COMMIT])
}

