import org.cicd.enums.PipelineType

def call(pipelineType, cfg) {
    def paramMap = [:].plus(cfg)
    paramMap.put("env_info", "$env_info")
    def repository_url = ""
    switch ("$env_info") {
        case "dev":
        case "sit":
            repository_url = paramMap.get("dev_repository_url")
            break
        case "uat":
        case "pro":
        case "dr":
            repository_url = paramMap.get("pro_repository_url")
            break
    }
    paramMap.put('repository_url', repository_url)
    paramMap.put('build_type', "$build_type")
    if ("$build_type" == "mvn") {
        paramMap.put('start_enable', "$start_enable")
        paramMap.put("artifact_id", "$artifact_id")
    }
    switch (pipelineType) {
        case PipelineType.STANDARD:
            paramMap.put("src_url", "$src_url")
            paramMap.put("branch_name", "$branch_name")
            break
        case PipelineType.DEPLOY:
            //此处覆盖上述artifact_id
            paramMap.put("artifact_id", "$artifact_id")
            paramMap.put('project_name', "$project_name")
            paramMap.put('group_id', "$group_id")
            paramMap.put('app_version', "$app_version")
            break
    }
    return paramMap

}

return this