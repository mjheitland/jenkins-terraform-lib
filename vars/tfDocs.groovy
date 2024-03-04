def call(Map config = [:]) {
    if (!config.workDir) {
        error("Error: please add mandatory parameter 'workDir' to the function call, e.g. terraformDocs(workDir: \".\")")
        exit 1
    }
    env.WORKDIR = config.workDir

    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        cd $WORKDIR
        pwd
        terraform-docs --version
        status=0
        terraform-docs markdown table . --output-check --output-file README.md || status=$?
        if [[ $status -ne 0 ]]; then
            cp README.md README_original.md
            terraform-docs markdown table . --output-file README.md
            mv README.md README_corrected.md
            git --no-pager diff --no-index README_original.md README_corrected.md || true
            cp README_corrected.md $WORKSPACE/.artifacts/README_corrected.md
            set +x
            echo "terraform-docs: $WORKDIR/README.md is out of date. Please update README.md running 'terraform-docs markdown table . --output-file README.md'."
            exit 1
        fi
    '''
}