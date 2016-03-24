FROM ubuntu:16.04

RUN apt-get update
RUN apt-get upgrade -y

# Install tcpkali
RUN apt-get install -y git
RUN git clone git@github.com:machinezone/tcpkali.git
RUN cd tcpkali
RUN apt-get install -y autoconf
RUN apt-get install -y automake
RUN apt-get install -y libtool
RUN apt-get install -y bison
RUN apt-get install -y flex
RUN apt-get install -y g++
RUN apt-get install -y ncurses-dev
RUN test -f configure || autoreconf -iv
RUN ./configure
RUN make
RUN make install
