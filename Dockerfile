FROM ubuntu:16.04

RUN apt-get update -y && apt-get upgrade -y

# tcpkali
RUN apt-get install -y git \
			make \
			autoconf \
			automake \
			libtool \
			bison \
			flex \
			g++ \
			ncurses-dev

RUN git clone https://github.com/machinezone/tcpkali.git
WORKDIR "/tcpkali"
RUN test -f configure || autoreconf -iv
RUN ./configure
RUN make
RUN make install
