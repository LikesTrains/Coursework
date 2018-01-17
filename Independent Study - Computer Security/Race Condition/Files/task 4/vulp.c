/*  vulp.c  */
#include <stdio.h>
#include <unistd.h>


int main()
    {
       char * fn = "/tmp/XYZ";
       char buffer[60];
       FILE *fp;

       /* get user input */
       scanf("%50s", buffer );

       if(!access(fn, W_OK)){
       /*This is the window that we are trying to exploit*/
            fp = fopen(fn, "a+");
		printf("opened file \n");
            fwrite("\n", sizeof(char), 1, fp);
		printf("writing to file \n");
            fwrite(buffer, sizeof(char), strlen(buffer), fp);
            fclose(fp);
	    printf("permission \n");
       }
       else printf("No permission \n");
    }
