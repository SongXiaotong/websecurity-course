#include <iostream>
#include "transform.h"
using namespace std;

Transform::Transform():num_x(0), num_y(0){
    
}

void Transform::init(int y, int x, int data[]){
	this->num_x = x;
	this->num_y = y;
	this->table = new int* [y];
    for(int i = 0; i < y; ++i){
        this->table[i] = new int [x];
        for(int j = 0; j < x; ++j){
        	this->table[i][j] = data[x*i+j];
        }
    }
}


int Transform::getNewPos(int y, int x){
    return this->table[y][x];
}

Transform::~Transform(){
    delete []this->table;
}

int Transform::getx(){
	return this->num_x;
}
int Transform::gety(){
	return this->num_y;
}
/*
void Transform::setNewPos(int y, int x, int newPos){
    this->table[y][x] = newPos;
}

Transform& Transform::operator=(const Transform& data2){
	delete []this->table;
	this->num_x = data2.num_x;
	this->num_y = data2.num_y;
	this->table = new int* [this->num_y];
	for(int i = 0; i < this->num_y; ++i){
		this->table[i] = new int [this->num_x];
		for(int j = 0; j < this->num_x; ++j){
			this->table[i][j] = data2.table[i][j];
		}
	}
}
*/



void setChar(char* part, int value){
	char res[4] = {'0', '0', '0', '0'};
	int pos = 3; 
	while(value != 0){
		res[pos] += value%2 == 0 ? 0 : 1;
		pos--;
		value /= 2;
	} 
	for(int i = 0; i < 4; ++i){
		part[i] = res[i];
	}
}

char* s_box(Transform &trans, char data[]){
	char* res = new char [32]; // output:32bits
	int times = 0; // 8 operations
	int curr = 0; // 32 output
	while(times < 48){
		int y = (data[times]-'0')*2 + (data[times+5]-'0');
		int x = (data[times+1]-'0')*8 + (data[times+2]-'0')*4 + (data[times+3]-'0')*2 + (data[times+4]-'0');
		for(int i = 0; i < 4; ++i){
			setChar(&res[curr], trans.getNewPos(y, x));
			// cout << res[curr] << res[curr+1] << res[curr+2] << res[curr+3] << endl; 
			curr += 4;
		}
		times += 6;
	}	
	return res;
}


