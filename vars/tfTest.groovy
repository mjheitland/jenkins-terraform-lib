def call(Map config = [:]) {
    if (!config.timeout) {
        error("Error: please add mandatory parameter 'timeout' to the function call, e.g. tfTest(timeout: \"60m\")")
        exit 1
    }
    env.TF_TEST_TIMEOUT = config.timeout
    sh '''#!/usr/bin/env bash
        set -xeo pipefail
        go version
        go env -w GOPROXY=direct # https://proxy.golang.org,direct
        go get ./...
        go mod tidy
        go test -timeout $TF_TEST_TIMEOUT -v ./test/integration | tee $WORKSPACE/.artifacts/test-results.txt
    '''
}
