#include <iostream>
#include <cstring>
using namespace std;

class TbsCertificate{
	public:
	    string version;
	    string serialNumber;
	    string signature[2];// algorithm parameters
	    string issuer_[6][2];
	    string validity[2];
	    string subject_[6][2];
	    string subjectPublicKeyInfo[3];// algorithm parameters Pkey
	    string issuerUniqueID;
	    string subjectUniqueID;
	    string extensions;
};

class X509cer{
	public:
	    TbsCertificate cat;
	    string casa[2];
	    string casv;
};
