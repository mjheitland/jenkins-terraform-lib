def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tfDestroy(workDir: \".\")")
        exit 1
    }
    env.WORKDIR = config.workDir

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        cd $WORKDIR
        pwd
        aws --version
        terraform --version
        aws sts get-caller-identity
        terraform show $WORKSPACE/.artifacts/terraform-destroy.plan
        terraform apply -destroy -auto-approve -input=false $WORKSPACE/.artifacts/terraform-destroy.plan | tee $WORKSPACE/.artifacts/terraform-destroy.txt
    '''
}
