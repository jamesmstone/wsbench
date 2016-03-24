# wsbench

[![Join the chat at https://gitter.im/wsbench/wsbench](https://badges.gitter.im/wsbench/wsbench.svg)](https://gitter.im/wsbench/wsbench?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Web server/socket benchmark tool

## Prerequisites

### Docker

#### Linux

`curl -fsSL https://get.docker.com/ | sh`

#### OS X

1. `brew cask install virtualbox`
2. `brew install docker`
3. `brew install docker-machine`
4. `docker-machine create --driver virtualbox default`
5. `docker-machine env default`
6. `eval $(docker-machine env default)`

## Run

`docker run -d wsbench/wsbench`
