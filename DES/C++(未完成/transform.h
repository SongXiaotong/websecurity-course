#include <iostream>
using namespace std;
#ifndef TRANSFORM_H
#define TRANSFORM_H
class Transform {
	private:
		int num_x; // the number of the elements in one row
		int num_y; // the number of the rows
		int** table; // the table of the transform
	public:
		Transform();
        void init(int y, int x, int *data);
        int gety();
        int getx();
		int getNewPos(int y, int x); // get the new pos of the certain position
        // void setNewPos(int y, int x, int newPos); // set the value of the position
        // Transform& operator = (const Matrix& data2);
        ~Transform();
};


// part of s_box, do the change of the base
void setChar(char *part, int value);
// s_box choose
char* s_box(Transform &trans, char data[]);

#endif 

