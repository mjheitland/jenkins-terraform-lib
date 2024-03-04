def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tflint(workDir: \".\")")
        exit 1
    }
    env.WORKDIR = config.workDir

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        cd $WORKDIR
        pwd
        terraform --version
        terraform validate | tee $WORKSPACE/.artifacts/terraform-validate.sarif
    '''
}
