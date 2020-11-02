#!/bin/bash

set -e

#build python
#graalpython -m venv /euler-env
#source /euler-env/bin/activate
#cd /src/python/
#graalpython /src/python/setup.py build
#EULER_PACKAGE=$(ls /src/python/dist/euler-*.whl)
#pip3 install euler --no-index --find-links file://${EULER_PACKAGE}
#cd -

export PYTHONPATH="${PYTHONPATH}:/src/python/euler"

# build java
mvn clean package test -P graalvm
