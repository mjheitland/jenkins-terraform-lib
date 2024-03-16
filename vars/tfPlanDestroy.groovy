def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tfPlanDestroy(workDir: \".\")")
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
        terraform plan -destroy -input=false -lock=false -out $WORKSPACE/.artifacts/terraform-destroy.plan
        terraform show $WORKSPACE/.artifacts/terraform-destroy.plan > $WORKSPACE/.artifacts/terraform-destroy-plan.txt
        head -c 50000 $WORKSPACE/.artifacts/terraform-destroy-plan.txt > $WORKSPACE/.artifacts/terraform-destroy-plan-extract.txt
    '''
}
