def call(Map config = [:]) {
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        tfsec --version 2>&1 | tail -1
        tfsec . | tee >(sed $'s/\033[[][^A-Za-z]*[A-Za-z]//g' >$WORKSPACE/.artifacts/tfsec.sarif)
    '''
}
