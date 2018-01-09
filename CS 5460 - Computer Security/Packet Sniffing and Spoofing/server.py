from socket import *

def createServer():
    serversocket = socket(AF_INET, SOCK_STREAM)
    serversocket.bind(('localhost',9000))
    serversocket.listen(5)
    while(1):
        (clientsocket, address) = serversocket.accept()
        clientsocket.send("HTTP/1.1 200 OK\n"
         +"Content-Type: text/html\n"
         +"\n" # Important!
         +"<html><body>Hello World</body></html>\n")
        clientsocket.shutdown(SHUT_WR)
        clientsocket.close()

    serversocket.close()

createServer()
