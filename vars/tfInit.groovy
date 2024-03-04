def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tfInit(workDir: \".\")")
        exit 1
    }
    env.WORKDIR = config.workDir
    if (!config.terraformWorkspace) {
        error("Error: please add mandatory parameter 'terraformWorkspace' to the function call, e.g. tfInit(terraformWorkspace: \"test\")")
        exit 1
    }
    env.TERRAFORM_WORKSPACE = config.terraformWorkspace

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        cd $WORKDIR
        pwd
        terraform --version
        terraform init -upgrade
        unset TF_WORKSPACE
        terraform workspace select -or-create $TERRAFORM_WORKSPACE
        terraform workspace list
    '''
}
