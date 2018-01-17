/*  vulp.c  */

#include <stdio.h>
#include <unistd.h>


int main(){
	char * fn = "/tmp/XYZ";
	char buffer[60];
	FILE *fp;

	/* get user input */
	scanf("%50s", buffer );

	uid_t real, effective;
	real = getuid();
	effective = geteuid();

	seteuid(real);

	fp = fopen(fn, "a+");
	if(fp!=NULL){
		fwrite("\n", sizeof(char), 1, fp);
		fwrite(buffer, sizeof(char), strlen(buffer), fp);
		fclose(fp);
	}
	else printf("No permission \n");

	seteuid(effective);
}
