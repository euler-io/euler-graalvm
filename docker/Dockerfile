FROM openjdk:14-jdk-buster as builder

ARG MAVEN_VERSION=3.8.1
ARG MAVEN_SHA=0ec48eb515d93f8515d4abe465570dfded6fa13a3ceb9aab8031428442d9912ec20f066b2afbf56964ffe1ceb56f80321b50db73cf77a0e2445ad0211fb8e38d
ARG MAVEN_BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

# Install maven
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${MAVEN_SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Installing dependencies
RUN apt-get update \
	&& apt-get install -y --no-install-recommends \
		git

ARG DOCKER_TAG=latest
ARG REPO_URL=https://github.com/euler-io/euler-graalvm.git
ARG SOURCE_BRANCH=master
ARG CLONE_ARGS="--depth 1"
ARG COMPILE_OPTS="-Dmaven.test.skip -DdownloadSources=false -DdownloadJavadocs=false"

# Compiling branch
RUN mkdir /src \
	&& cd /src \
	&& git clone --branch $SOURCE_BRANCH $CLONE_ARGS $REPO_URL /src \
	&& mvn package $COMPILE_OPTS

ARG REPO_API_URL=https://github.com/euler-io/euler-graalvm.git

# Compiling euler-api branch
RUN mkdir /src-api \
	&& cd /src-api \
	&& git clone --branch $SOURCE_BRANCH $CLONE_ARGS $REPO_API_URL /src-api \
	&& mvn package $COMPILE_OPTS

# Downloading dependencies
RUN mkdir /dependencies \
	&& mvn dependency:copy-dependencies -DdownloadSources=false -DdownloadJavadocs=false -Deuler.scope=provided -DincludeScope=runtime -DoutputDirectory=/dependencies -f /src/pom.xml

FROM ghcr.io/graalvm/graalvm-ce:java11-21.1.0

ENV JAVA_OPTS=""
ENV APP_ARGS=""

#Install python language
RUN gu install python

# Installing app
RUN mkdir -p /app/lib \
	&& mkdir -p /opt/graalvm-ce-java11-21.1.0/languages/python/lib-graalpython/euler
COPY --from=builder /src-api/opendistro-impl/target/opendistro-http-api.jar /app/
COPY --from=builder /dependencies/ /app/lib/
COPY --from=builder /src/python/euler/ /opt/graalvm-ce-java11-21.1.0/languages/python/lib-graalpython/euler

# Adding app group and user
RUN groupadd --gid 1100 euler \
  && adduser --uid 1100 --no-create-home --gid 1100 euler --password "" \
  && chown euler:euler -R /app

USER euler
WORKDIR /app

CMD java -Dloader.path=/app/lib ${JAVA_OPTS} -jar /app/opendistro-http-api.jar ${APP_ARGS}
