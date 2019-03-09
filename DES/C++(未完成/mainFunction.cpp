#include <iostream>
using namespace std;

// the transform of ip
extern int ip_Trans;
extern int InIp_Trans[64];
// transform key64 to newkey56 by pc_1
extern int PC_1_Trans[56];
// do the left shift action on C0 D0 for C16 D16
extern int left_shift[16];
// transform CD to K
extern int PC_2_Trans[48];
// transform the R32 to R48
extern int E_Trans[48];
// eight S boxes
extern int S1[8][64];
// P box ¡ª transs
extern int P_Trans[32];

char* transforming(int trans[], int len, char data[]){
	char* res = new char [len];
	for(int i = 0; i < len; ++i){
			res[i] = data[trans[i]-1];
	}
	return res;
}


// produce the l0 and r0
char** lr0(char* data, int trans[], int len){
	char** res = new char* [2];
	res[0] = new char[len/2];
	res[1] = new char[len/2];
	data = transforming(trans, len, data);
	memcpy(res[0], data, len/2);
	memcpy(res[1], &(data[len/2]), len/2);
	return res;
} 

