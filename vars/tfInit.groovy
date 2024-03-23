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
    if (!config.region) {
        error("Error: please add mandatory parameter 'region' to the function call, e.g. tfInit(region: \"eu-central-1\")")
        exit 1
    }
    env.TF_VAR_region = config.region
    if (!config.tfStateBucket) {
        error("Error: please add mandatory parameter 'tfStateBucket' to the function call, e.g. tfInit(tfStateBucket: \"aws-tfstate-saas-dev\")")
        exit 1
    }
    env.TF_VAR_tfstate_bucket = config.tfStateBucket
    if (!config.tfStateTable) {
        error("Error: please add mandatory parameter 'tfStateTable' to the function call, e.g. tfInit(tfStateTable: \"terraform-statelock-saas-dev\")")
        exit 1
    }
    env.TF_VAR_tfstate_table = config.tfStateTable

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        cd $WORKDIR
        pwd
        aws --version
        terraform --version

        echo "bucket         = \\"${TF_VAR_tfstate_bucket}\\"" >> config.aws.tfbackend
        echo "dynamodb_table = \\"${TF_VAR_tfstate_table}\\"" >> config.aws.tfbackend
        echo "region         = \\"${TF_VAR_region}\\"" >> config.aws.tfbackend
        cat config.aws.tfbackend
        cp config.aws.tfbackend $WORKSPACE/.artifacts/config.aws.tfbackend
        
        terraform init -upgrade -backend-config=config.aws.tfbackend
        
        unset TF_WORKSPACE
        terraform workspace select -or-create $TERRAFORM_WORKSPACE
        terraform workspace list
    '''
}
