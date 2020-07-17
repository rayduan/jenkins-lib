package org.cicd.devops

import org.cicd.utils.utils

/**
 * sonar扫描
 * @param buildType
 * @return
 */
def sonarScan(Map params) {
    def utils = new utils()
    utils.printMessage("代码扫描", "green")
    withSonarQubeEnv('SonarQube') {
        switch (params.get("build_type")) {
            case "mvn":
                //mvn
                sh "mvn clean verify sonar:sonar   -Dmaven.test.skip=true -Dsonar.projectKey=${params.get("artifact_id")} -Dsonar.projectName=${params.get("artifact_id")} "
                break
            case "npm":
                def scannerHome = tool 'Sonar_Scanner'
                sh """
                ${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=${params.get("artifact_id")} \
                -Dsonar.projectName=${params.get("artifact_id")} \
                -Dsonar.sources=.
                """
                break
        }
    }
}
