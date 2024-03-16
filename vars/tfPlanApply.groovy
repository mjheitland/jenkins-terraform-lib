def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. tfplanapply(workDir: \".\")")
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
        terraform plan -input=false -lock=false -out $WORKSPACE/.artifacts/terraform-apply.plan
        terraform show $WORKSPACE/.artifacts/terraform-apply.plan > $WORKSPACE/.artifacts/terraform-apply-plan.txt
        head -c 50000 $WORKSPACE/.artifacts/terraform-apply-plan.txt > $WORKSPACE/.artifacts/terraform-apply-plan-extract.txt
    '''
}
