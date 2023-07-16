FROM ubuntu:23.04

ARG JAVA_VERSION="20.0.1-librca"
ARG GRADLE_VERSION="8.2.1"
ARG USER_UID="10000"
ARG USER_GID="10000"
ARG USER_NAME="simplesn"

RUN groupadd -g $USER_GID $USER_NAME && \
useradd -m -g $USER_GID -u $USER_UID $USER_NAME

RUN apt-get update && apt-get upgrade -y && \
apt-get install curl unzip zip -y

USER $USER_UID:$USER_GID

RUN curl -s "https://get.sdkman.io" | bash

RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && \
yes | sdk install java $JAVA_VERSION && \
yes | sdk install gradle $GRADLE_VERSION"

RUN bash -c "mkdir $HOME/app && \
chown $USER_NAME:$USER_NAME $HOME/app && \
chmod 700 $HOME/app"

WORKDIR /home/simplesn/app
COPY --chown=$USER_NAME:$USER_NAME . .
EXPOSE 8080
ENTRYPOINT bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && \
gradle bootRun"