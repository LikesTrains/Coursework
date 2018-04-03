#include <openssl/evp.h>
#include <stdio.h>
#include <string.h>


int main() {
    // constants for this Task
    const unsigned char ciphertext[] = {0x8D,0x20,0xE5,0x05,0x6A,0x8D,0x24,0xD0,0x46,0x2C,0xE7,0x4E,0x49,0x04,0xC1,0xB5,0x13,0xE1,0x0D,0x1D,0xF4,0xA2,0xEF,0x2A,0xD4,0x54,0x0F,0xAE,0x1C,0xA0,0xAA,0xF9};
    const unsigned char iv[] = {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
    const char intext[] = "This is a top secret.";

    // set up input from file
    FILE * fp;
    fp = fopen("words.txt", "r");
    char buffer[16];
    //fill buffer with empty spaces
    int i = 0;
    for (; i < 16; i++) {
      buffer[i] = 0x20;
    }
    // read line from file
    char * guy = fgets(buffer, 16, fp);
    int flag = 0;
    char * solution;
    while (guy != NULL && flag == 0) {
      //remove \n from buffer
      int it = strlen(guy) - 1;
      for (; it < 16; it++) {
        buffer[it] = 0x20;
      }
      // perform encryption operations, encrypted value will be stored in outbuf
      unsigned char outbuf[1024];
      int outlen, tmplen;
      EVP_CIPHER_CTX * ctx;
      ctx = EVP_CIPHER_CTX_new();
      EVP_EncryptInit_ex(ctx, EVP_aes_128_cbc(), NULL, buffer, iv);
      if (!EVP_EncryptUpdate(ctx, outbuf, & outlen, intext, strlen(intext))) {
        EVP_CIPHER_CTX_free(ctx);
        return 1;
      }
      if (!EVP_EncryptFinal_ex(ctx, outbuf + outlen, & tmplen)) {
        EVP_CIPHER_CTX_free(ctx);
        return 1;
      }
      outlen += tmplen;
      EVP_CIPHER_CTX_free(ctx);
      // store encrypted values in result for checking
      unsigned char result[32];
      i = 0;
      for (; i < outlen; i++) {
        result[i] = outbuf[i];
      }
      // check if result is the same as ciphertext
      i = 0;
      for (; i < 32; i++) {
        if (result[i] != ciphertext[i]) {
          break;
        }
        if (result[i] == ciphertext[i] && i == 31) {
          flag = 1;
          solution = buffer;
        }
      }
      // print word if the result is the same
      if (flag == 1) {
        printf("%s%s", "word found!, word was ", solution);
        printf("\n");
      }
      // reset buffer and read next line
      i = 0;
      for (; i < 16; i++) {
        buffer[i] = 0x20;
      }
      guy = fgets(buffer, 16, fp);
    }
    fclose(fp);
    return 0;
}
