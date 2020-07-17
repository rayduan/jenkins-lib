import org.cicd.devops.ansible
import org.cicd.devops.gitServer
import org.cicd.devops.nexus
import org.cicd.devops.sonar

def call(paramMap) {
    def gitServer = new gitServer()
    def sonar = new sonar()
    def nexus = new nexus()
    def ansible = new ansible()

    //下载代码
    stage("下载代码") { //阶段名称
        script {
            gitServer.checkOutCode(paramMap)
        }
    }

    //代码扫描
    stage("代码扫描") {
        script {
            sonar.sonarScan(paramMap)
        }
    }
    //构建代码
    stage("构建代码") {
        script {
            nexus.build(paramMap)
        }
    }
    //部署
    stage("部署代码") {
        script {
            ansible.deploy(paramMap)
        }
    }
    //iq-sever扫描
    stage("包扫描") {
        script {
            nexus.iqServerScan(paramMap)
        }
    }

}

return this