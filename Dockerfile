FROM	ubuntu:latest

RUN	apt-get update -y \
	&& apt-get install -y \
	git \
	make \
	autoconf \
	automake \
	libtool \
	bison \
	flex \
	g++ \
	ncurses-dev \
	&& git clone https://github.com/machinezone/tcpkali.git \
	&& cd tcpkali \
	&& test -f configure || autoreconf -iv \
	&& ./configure \
	&& make \
	&& make install \
	&& rm -rf /tcpkali \
	&& apt-get remove -y git make autoconf automake ncurses-dev \
	&& apt-get autoremove -y

ENTRYPOINT	["tcpkali"]
