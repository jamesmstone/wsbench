FROM ubuntu:16.04

RUN apt-get update -y && apt-get upgrade -y

# Install tcpkali
RUN apt-get install -y git \
                        autoconf \
                        automake \
                        libtool \
                        bison \
                        flex \
                        g++ \
                        ncurses-dev

#RUN touch ~/.ssh/known_hosts
#RUN ssh-keyscan -H github.com >> ~/.ssh/known_hosts
RUN git clone git@github.com:machinezone/tcpkali.git
RUN cd tcpkali
RUN test -f configure || autoreconf -iv
RUN ./configure
RUN make
RUN make install
