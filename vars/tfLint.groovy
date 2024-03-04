def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tfLint(workDir: \".\")")
        exit 1
    }
    env.WORKDIR = config.workDir

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        cd $WORKDIR
        pwd
        tflint --version
        tflint --init
        tflint --format=compact --chdir . || true
        tflint --format=sarif --chdir . >$WORKSPACE/.artifacts/tflint.sarif
    '''
}
