#include <iostream>
using namespace std;
#ifndef MAINFUNCTION_HPP
#define MAINFUNCTION_HPP
// do the substitution
char* transforming(int trans[],  char data[]);
// divide the data into two parts
char** lr0(char* data, int trans[], int len);
// produce the L16 and r16
#endif
