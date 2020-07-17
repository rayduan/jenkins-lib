package org.cicd.enums

enum BuildType {
    MVN('mvn'),
    NPM('npm');
    private String code;

    BuildType(String code) {
        this.code = code
    }

    public String getCode() {
        return code;
    }

    public static BuildType valueOfName(String type) {
        return values().find {
            BuildType it ->
                it.code == type
        }
    }

}