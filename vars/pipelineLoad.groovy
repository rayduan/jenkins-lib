import org.cicd.enums.PipelineType
import org.cicd.utils.utils


def call(pipelineType) {
    def cfg_text = libraryResource("pipelineCfg.yaml")
    def cfg = readYaml text: cfg_text
    def paramMap = pramasBuild(pipelineType,cfg)
    def utils = new utils()
    pipeline {
        agent any
        tools {
            jdk 'JDK_11'
            maven 'M3'
            git 'GIT'
            nodejs 'NodeJS'
        }
        options {
            skipDefaultCheckout()  //删除隐式checkout scm语句
            disableConcurrentBuilds() //禁止并行
            timeout(time: 1, unit: 'HOURS')  //流水线超时设置1h
            timestamps()
        }

        stages {
            stage("初始化步骤") {
                steps {
                    script{
                        switch (pipelineType) {
                            case PipelineType.STANDARD:
                                standardPipeline(paramMap)
                                break
                            case PipelineType.DEPLOY:
                                deployPipeline(paramMap)
                                break
                        }
                    }

                }
            }
        }
        //清理工作
        post {
            always {
                script {
                    utils.clearSpace(["jar", "zip"], paramMap.get("ansible_src"))
                }
            }
        }


    }


}

return this