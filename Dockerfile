FROM ubuntu:16.04

RUN apt-get update -y && apt-get upgrade -y
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

# Install tcpkali
RUN git clone https://github.com/machinezone/tcpkali.git

WORKDIR "/tcpkali"
RUN test -f configure || autoreconf -iv 
test -f configure || autoreconf -iv
RUN test -f configure || autoreconf -iv
RUN ./configure
RUN make
RUN make install
