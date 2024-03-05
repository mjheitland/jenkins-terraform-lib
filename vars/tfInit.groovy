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
        aws --version
        terraform --version

        echo "bucket         = \\"${TF_VAR_tfstate_bucket}\\"" >> config.aws.tfbackend
        echo "dynamodb_table = \\"terraform-statelock-${ENVIRONMENT_ALIAS}\\"" >> config.aws.tfbackend
        echo "region         = \\"${TF_VAR_region}\\"" >> config.aws.tfbackend
        cat config.aws.tfbackend
        cp config.aws.tfbackend $WORKSPACE/.artifacts/config.aws.tfbackend
        
        terraform init -upgrade -backend-config=config.aws.tfbackend
        
        unset TF_WORKSPACE
        terraform workspace select -or-create $TERRAFORM_WORKSPACE
        terraform workspace list
    '''
}
