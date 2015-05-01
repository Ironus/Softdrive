#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <arpa/inet.h>
#include <netdb.h>

int main(void) {
  const int port = 8080;
  const int maxConnection = 20;

  int listeningSocket, clientSocket;

  struct sockaddr_in server, client;
  struct hostent *host;
  socklen_t sin_size = sizeof(struct sockaddr_in);

  char myhostname[1024];

  listeningSocket = socket(PF_INET, SOCK_STREAM, 0);
  gethostname(myhostname, 1023);
  host = gethostbyname(myhostname);

  server.sin_family = AF_INET;
  server.sin_port = htons(port);
  server.sin_addr = *(struct in_addr*) host->h_addr;

  printf("Server configuration:\n");
  printf("\tip address: %s\n", inet_ntoa(server.sin_addr));
  printf("\tport: %d\n", ntohs(server.sin_port));

  if (bind(listeningSocket, (struct sockaddr*) &server, sizeof(struct sockaddr)) == -1) {
    printf("Binding failed.\n");
    return -1;
  }

  listen(listeningSocket, maxConnection);

  sin_size = sizeof(struct sockaddr_in);
  clientSocket = accept(listeningSocket, (struct sockaddr*) &client, &sin_size);
    printf("Connected with %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

    int pathSize;
    char path[512];
    recv(clientSocket, &pathSize, sizeof(int), 0);
    printf("\vRetrieved: %d", pathSize);

  close(clientSocket);
  return 0;
}
