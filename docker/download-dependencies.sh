#!/bin/bash
set -e

if [[ ${SOURCE_BRANCH} == 'master' || ${SOURCE_BRANCH} == 'dev' ]]; then
	depVersion=$(curl -s https://repo1.maven.org/maven2/com/github/euler-io/opendistro-impl/maven-metadata.xml | grep '<latest>' | sed "s/.*<latest>\([^<]*\)<\/latest>.*/\1/")
else
	depVersion=$SOURCE_BRANCH
fi
echo "Using: ${depVersion}"

mkdir -p $1/dependencies
mvn dependency:copy-dependencies -Deuler.scope=provided -DoutputDirectory=$1/dependencies -f /src/pom.xml
curl -fsSL -o $1/opendistro-http-api.jar https://repo1.maven.org/maven2/com/github/euler-io/opendistro-impl/$depVersion/opendistro-impl-$depVersion.jar