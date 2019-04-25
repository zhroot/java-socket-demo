## java socket demo

## Source Code
- com/server/Server.java  server code
- com/server/Manifest.MF  server.jar manifest

- com/client/Client.java  test client code
- com/client/Manifest.MF  client.jar manifest


## Compile

    javac com/server/Server.java

    javac com/client/Client.java


##  Create jar files

### server.jar

    jar -cvfm server.jar com/server/Manifest.MF com/server/*.class

### client.jar

    jar -cvfm client.jar com/client/Manifest.MF com/client/*.class


## Run

### server

    java -jar server.jar


### client

    java -jar client.jar


## Example

### server 

    $ java -jar server.jar
    server start and listen the port 23333
    Waiting for clients...
    Client connected from Socket[addr=/127.0.0.1,port=49802,localport=23333]
    Client connected from Socket[addr=/127.0.0.1,port=49803,localport=23333]
    receive:hello
    receive:world
    receive:haha
    receive:hehe


### client 1 

    java -jar client.jar
    connected to Socket[addr=localhost/127.0.0.1,port=23333,localport=49802]
    hello
    send:hello
    receive:hello
    receive:world
    haha
    send:haha
    receive:haha
    receive:hehe

### client 2

    java -jar client.jar
    connected to Socket[addr=localhost/127.0.0.1,port=23333,localport=49803]
    receive:hello
    world
    send:world
    receive:world
    receive:haha
    hehe
    send:hehe
    receive:hehe







