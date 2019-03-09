#include <iostream>
#include "transform.h"
#include "mainFunction.hpp"
using namespace std;

// the transform of ip
extern int Ip_Trans[64];
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

int main(){
    int x = 3, y = 4, pos = 0;
    char **lr, **cd;
    char data[64] = {'1','1','1','1','1','1','1','1','1','1','1','1',
					'1','1','a','1','1','1','1','1','1','1','1','1',
					'1','1','1','1','1','1','1','1','1','1','1','1',
					'1','1','1','1','1','1','1','1','1','1','1','1',
					'0','0','0','0','0','0','0','0','0','0','0','0',
					'0','0','0','0'};
    
	lr = lr0(data, Ip_Trans, 64);
	cd = lr0(data, PC_1_Trans, 56);
	cout << d[1][6];
    
    return 0;
}

