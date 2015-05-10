#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <time.h>
#include "tinydir.h"

enum Bool { False, True };

int arraySize(char *ptr) {
    int offset = 0;
    int count = 0;
    while (*(ptr + offset) != '\0') {
    ++count;
    ++offset;
  }
  return count;
}

int writeLine(char* sign) {
  int lineCounter = 0;
  for(lineCounter; lineCounter < 80; lineCounter = lineCounter + 1) {
    printf(sign);
  }
  printf("\n");
  return 0;
}

int sendFile(int clientSocket, char path[]) {
  struct stat fileInformation;
  FILE *file;

  printf("\tReading file information.\n");
  stat(path, &fileInformation);
  long fileSize = fileInformation.st_size;
  printf("\t\tFile size: %.2f MB\n", ((double)fileSize/1024)/1024);
  fileSize = htonl((long)fileSize);

  printf("\tSending file size.\n");
  send(clientSocket, &fileSize, sizeof(int), 0);

  fileSize = fileInformation.st_size;
  long totalSent = 0, read = 0, sent = 0;
  unsigned char buffer[1024];

  printf("\tFile sending.\n");
  file = fopen(path, "rb");

  time_t start, middle, end;
  time(&start);
  middle = start;
  while (totalSent < fileSize) {
    read = fread(buffer, 1, sizeof(buffer), file);
    sent = send(clientSocket, buffer, read, 0);
    if (read != sent)
      break;
    totalSent = totalSent + sent;
    time(&end);
    double totalSentMB = (double)(totalSent / 1024)/1024;
    double transferSpeed = (totalSentMB * 8.0) / difftime(end, start);
    if((int)difftime(end, middle) >= 1 || middle == 0)
      printf("\t\tSent %.2f MB with speed %.2f Mb/s\n", totalSentMB, transferSpeed);
    middle = end;
  }
  time(&end);
  printf("\t\tFile has been sent. Total time: %d sec.\n", (int)difftime(end, start));

  return 0;
}

int handleConnection(int clientSocket) {
  int pathSize;
  short connectionClosed = False;
  int action;
  char actualPath[] = "./files";
  while(!connectionClosed) {
    printf("\tRetrieving action demand.\n");
    recv(clientSocket, &action, sizeof(int), 0);
    action = ntohl(action);

    switch(action) {
      case 1:
        printf("\tFile list demand.\n");
        tinydir_dir dir;
        tinydir_open_sorted(&dir, actualPath);

        int i, nameLength, isDirectory, countFiles = dir.n_files;

        countFiles = htonl(countFiles);
        send(clientSocket, &countFiles, sizeof(int), 0);
        for (i = 0; i < dir.n_files; i++) {
          tinydir_file file;
          tinydir_readfile_n(&dir, &file, i);
          isDirectory = file.is_dir;
          nameLength = arraySize(&file.name[0]);
          char tmp[nameLength];

          printf("\t%d\t%d\t%s\n", isDirectory, nameLength, file.name);

          isDirectory = htonl(isDirectory);
          nameLength = htonl(nameLength);

          send(clientSocket, &isDirectory, sizeof(int), 0);
          send(clientSocket, &nameLength, sizeof(int), 0);
          send(clientSocket, &file.name, sizeof(tmp), 0);
        }

        tinydir_close(&dir);
        break;
    }
    connectionClosed = 1;
    /*printf("\tRetrieving pathSize.\n");
    recv(clientSocket, &pathSize, sizeof(int), 0);
    pathSize = ntohl(pathSize);
    printf("\t\tPath size: %d\n", pathSize);

    char path[pathSize];
    memset(path, 0, pathSize);

    printf("\tRetrieving path.\n");
    recv(clientSocket, &path, sizeof(path), 0);
    printf("\t\tPath: \"%s\"\n", path);

    sendFile(clientSocket, path);*/
  }
  return 0;
}

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

  writeLine("═");

  printf("Server configuration:\n");
  printf("\tip address: %s\n", inet_ntoa(server.sin_addr));
  printf("\tport: %d\n", ntohs(server.sin_port));

  if (bind(listeningSocket, (struct sockaddr*) &server, sizeof(struct sockaddr)) == -1) {
    printf("Binding failed.\n");
    return -1;
  }

  listen(listeningSocket, maxConnection);
  while(True) {
    sin_size = sizeof(struct sockaddr_in);
    clientSocket = accept(listeningSocket, (struct sockaddr*) &client, &sin_size);
    printf("\v%s");
    writeLine("─");
    printf("\tConnected with %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));
    if(fork() == 0) {
      printf("\tConnection handling.\n");
      handleConnection(clientSocket);
      printf("\tClosing connection.\n");
      close(clientSocket);
      exit(0);
    }
    else {
      printf("Waiting for connection.\n");
      continue;
    }
  }
  return 0;
}
