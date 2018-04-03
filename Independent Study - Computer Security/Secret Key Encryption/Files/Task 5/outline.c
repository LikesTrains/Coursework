#include <openssl/conf.h>
#include <openssl/evp.h>
#include <openssl/err.h>
#include <string.h>
#include <stdio.h>
#include <unistd.h>
/*
Where to start
1) read in input from words.txt
	-fgets() is a useful function
2) format input of words.txt to be 16 bytes in length. 
	-Make sure that you remove the \n character from the string.
	-To make the string 16 bytes in length use 0x20 not a space.

Notes on code
-the code in the while loop will print out the output of the encryption
-printf("%x", var) will print out the hex representation of the character.
*/
int main (void)
{
	EVP_CIPHER_CTX* ctx;
	unsigned char ciphertext[]={0x8d,0x20,0xe5,0x05,0x6a,0x8d,0x24,0xd0,0x46,0x2c,0xe7,0x4e,
0x49,0x04,0xc1,0xb5,0x13,0xe1,0x0d,0x1d,0xf4,0xa2,0xef,0x2a,0xd4,
0x54,0x0f,0xae,0x1c,0xa0,0xaa,0xf9};
	unsigned char plaintext[]="This is a top secret.";
	char* text;
	char buffer[16];
	int length=16;
	int outlen, tmplen;
	unsigned char output[1024];
	unsigned char* key;
	unsigned char iv[] = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
	int done = 0;
	
	while(done != 1)
	{
		int found = 0;
		int count = 0;
		int i = 0;
		int j = 0;
		for(i;i<outlen;i++)//check to see if the ecryption text matches the cipher text
		{
			count++;
			if(output[i] != ciphertext[i]) break;
			if(count == outlen-1 && output[i] == ciphertext[i]) found =1;//the text matches
		}
		for(j; j < outlen; j++)//print the output of the encryption
		{
			printf("%x", output[j]);
		}
		if(found ==1)//print out the key
		{
			printf("\n\nFOUND THE KEY: %s\n\n", key);
			fclose(fp);
			return 0;
		}
	}
	return 0;
}
