# wsbench
web server/socket benchmark

# Prerequites

## Docker

### Ubuntu

`curl -fsSL https://get.docker.com/ | sh`

###  OS X

1. `brew cask install virtualbox`
2. `brew install docker`
3. `brew install docker-machine`
4. `docker-machine create --driver virtualbox default`
5. `docker-machine env default`
6. `eval $(docker-machine env default)`

# Run

`docker run -d s1rc0/wplbedev`

