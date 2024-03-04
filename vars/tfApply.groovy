def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tfApply(workDir: \".\")")
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
        terraform show $WORKSPACE/.artifacts/terraform-apply.plan
        terraform apply -auto-approve -input=false $WORKSPACE/.artifacts/terraform-apply.plan | tee $WORKSPACE/.artifacts/terraform-apply.txt
    '''
}
