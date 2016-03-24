FROM ubuntu:16.04

RUN apt-get update -y && apt-get upgrade -y

# git
RUN apt-get install -y git
RUN mkdir ~/.ssh
RUN touch ~/.ssh/known_hosts
RUN ssh-keyscan -H github.com >> ~/.ssh/known_hosts

# tcpkali
RUN apt-get install -y git \
                       autoconf \
                       automake \
                       libtool \
                       bison \
                       flex \
                       g++ \
                       ncurses-dev
RUN git clone git@github.com:machinezone/tcpkali.git

WORKDIR "/tcpkali"
RUN test -f configure || autoreconf -iv
RUN ./configure
RUN make
RUN make install
