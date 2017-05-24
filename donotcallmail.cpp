#include <iostream>
#include <fstream>
#include <String>
#include <sstream>
#include <string.h>

using namespace std;

string Cut(string &);

int main(int argc, char** argv) {
	string file="",command="",m,c,cr;
	bool test;
	
	if(argc>1){
		for (int x=0;x<argc;x++){
			cout<<!strcmp(argv[x],"C")<<" "<<true<<endl;
			if(!strcmp(argv[x],"C")||!strcmp(argv[x],"M") ){
				command=argv[x];
			}else file=argv[x];
		}
	}
	if(file.length()<4){
		cout<<"Please enter the .csv file you will be scanning (Example: Filename.csv)"<<endl;
		cin>>file;
	}
	
	while(file.substr(file.length()-4, 4)!=".csv"){
		cout<<"Please enter the .csv file you will be scanning (Example: Filename.csv)"<<endl;
		cin>>file;		
	}
	
	int rows;
	string first, last, address, city, state, header,line;
	string mail, call, customer,phone, zip;
	bool canMail, canCall, isCustomer;
	
	ofstream callout, mailout;
	ifstream  input(file.c_str());
	
	getline(input,header);
	if(command!="M"){
		c=(file.insert(0,"call")); 
		callout.open(c.c_str());
		file.erase(0,4);
		callout<<"First Name,Last Name,Address,City,State,Zip Code,Phone Number,Call"<<endl;
		}
	if(command!="C"){m=(
		file.insert(0,"mail"));
	 	mailout.open(m.c_str());
		file.erase(0,4);
		mailout<<"First Name,Last Name,Address,City,State,Zip Code,Phone Number,Mail"<<endl;
		}
	cr=(file.insert(0,"cross")); ofstream crossout(cr.c_str());
	crossout<<"First Name,Last Name,Address,City,State,Zip Code,Phone Number,Customer"<<endl;
	
	while(!input.fail()){
		getline(input, line);
	
		first=Cut(line);
		last=Cut(line);
		address=Cut(line);
		city=Cut(line);
		state=Cut(line);
		zip=Cut(line);
		phone=Cut(line);
		Cut(line);
		mail=Cut(line);
		mail=="OK to Mail,"?canMail=true:canMail=false;
		call=Cut(line);
		call=="OK to Call,"?canCall=true:canCall=false;
		Cut(line);
		customer=Cut(line);
		customer=="Customer,"?isCustomer=true:isCustomer=false;
	
		if(canMail&&!isCustomer&&command!="C"){
			mailout<<first<<last<<address<<city<<state<<zip<<phone<<mail<<endl;
		}
		if(canCall&&!isCustomer&&command!="M"){
			callout<<first<<last<<address<<city<<state<<zip<<phone<<call<<endl;
		}
		if(isCustomer){
			crossout<<first<<last<<address<<city<<state<<zip<<phone<<customer<<endl;
			
		}
	}	
	return 0;
}

string Cut(string &x){
	string y;
	y=x.substr(0,x.find(",",0)+1);
	x.erase(0,x.find(",",0)+1);
	
	return y;
}
