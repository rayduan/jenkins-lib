import org.cicd.devops.*
import org.cicd.enums.PipelineType
import org.cicd.utils.utils

def call(paramMap) {
    def ansible = new ansible()
    //部署
    stage("部署代码") {
        script {
            ansible.deploy(paramMap)
        }
    }

}

return this

