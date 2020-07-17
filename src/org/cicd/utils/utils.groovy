package org.cicd.utils



/**
 * 清理当前工程的目录
 * @return
 */
def clearSpace(list=['jar','zip'],path) {
    list.each {
        sh "find ./ -type f -name *.$it | xargs rm -rf"
    }
    deleteFile("${path}/files/*")
}

/**
 * 格式化输出
 * @param value
 * @param color
 * @return
 */
def printMessage(value, color) {
    def colors = ['groovy': "\033[40;31m >>>>>>>>>>>${value}<<<<<<<<<<< \033[0m",
                  'blue'  : "\033[47;34m ${value} \033[0m",
                  'green' : "[1;32m>>>>>>>>>>${value}>>>>>>>>>>[m",
                  'green1': "\033[40;32m >>>>>>>>>>>${value}<<<<<<<<<<< \033[0m"]
    ansiColor('xterm') {
        println(colors[color])
    }
}


/**
 * 创建临时目录
 * @return
 */
def initTmpDir(path) {
    println("初始化临时目录")
    sh """
        if [ ! -d "${path}" ]; then
                echo "创建文件夹"
                mkdir -p "${path}"
        fi
    """
}


/**
 * 检查文件是否存在glob: 'script/*.sh'
 * @param path
 * @return
 */
def checkFileExist(path) {
    def files = findFiles(glob: "${path}")
    println("${files}")
    if (null != files && files.length >= 1) {
        return true
    }
    return false
}

/**
 * 删除文件
 * @param path
 * @return
 */
def deleteFile(path) {
    println("删除文件$path")
    sh """
    rm  -rf ${path}
    """
}


def zipFile(path, fileName) {
    zip dir: "${path}", glob: '', zipFile: "${fileName}"
}

