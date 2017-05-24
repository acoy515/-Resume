/* This program keeps a record of inventory collected and sold by a merchant. The user inputs the 
price for 3 items with the program assessing a 10% upcharge if item has to be transferred between Warehouses.*/

#include <iostream>
#include <string>
#include <sstream>

using namespace std;
class Record{
	private:
		//Variable used to identify each Record
		string city;
		//Variable used to keep track of inventory in each Record
		int amt[3];
	public:
		//Constructor used to label each Record with a different city and set the inventory to 0
		Record(void){
			static int x=1;
			switch(x){
			case 1:
				//Set the Record to New York
				city="New York"; amt[0]=amt[1]=amt[2]=0;
				x++;
				break;
			case 2:
				//Set the Record to Houston
				city="Houston"; amt[0]=amt[1]=amt[2]=0;
				x++;
				break;
			case 3:
				//Set the Record to Los Angeles
				city="Los Angeles"; amt[0]=amt[1]=amt[2]=0;
				x++;
				break;
			case 4:
				//Set the Record to Miami
				city="Miami"; amt[0]=amt[1]=amt[2]=0;
				x++;
				break;
			case 5:
				//Set the Record to Chicago
				city="Chicago"; amt[0]=amt[1]=amt[2]=0;
				x=1;
				break;
			}
		}
		//Verifies if the city of current Warehouse is the same as argument passed to it
		bool Check(string x){
			return city+" "==x;
		}
		//Adds the amounts that are passed as an argument to the inventory of the current Record
		void Ship (int x, int y, int z){
			amt[0]+=x;
			amt[1]+=y;
			amt[2]+=z;
			Print();
		}
		//Finds out if the amount in the inventory is greater than the amount in the argument
		//Assigns the greater value to the argument. (Finds the max)
		bool Find (int x,int &y){
			if(amt[x]>=y){
				y=amt[x];
				return true;
			}else {
			return false;
			}

		}
		//Returns the name of the city of the current Record
		string Name(void){
			return city;
		}
		//Places an order with the current Record. Subtracts the amount ordered from the inventory 
		void Order(int x, int y){
			amt[y]-=x;
		}	
		//Ship All of the Record's inventory of a specific item
		int ShipAll(int z){
			int temp=amt[z];
			amt[z]-=amt[z];
			return temp;			
		}
		//Places an order with the current Record. Subtracts the amount ordered from the inventory
		int Lend(int x, int y, int z){
			int temp=y-x;
			amt[z]-=temp;
			return temp;
			
		}
	    //Returns the number of items available in two Records
		int Max(int x, int y){
			return x+amt[y];
		}
		//Checks the inventory to see if there is enough an item to make an order
		bool Checkamt(int x, int y){
			return x<=amt[y];
		}	
		//Prints the city of a Record with the inventory available on this Record
		void Print(void){
			cout<< city<<"\t";
			cout<<amt[0]<<"\t";
			cout<<amt[1]<<"\t";
			cout<<amt[2]<<endl<<endl;
		}
	
};

double StrtoDoub(string &);
double StrtoDouble(string );
string Cut(string &);


int main() {
	//Variables used to read in commands
	string card, x1, x2;
	int amt[3], most=0, max=0,xtra[3], temp;
	//Array containig the prices of each item
	double p[3];
	//Array of the 5 Warehouses being recorded
	Record Warehouse[5];
	//Set the extra inventory to 0, which is used to determine how many items were sent from one Record to another
	xtra[0]=0;
	xtra[1]=0;
	xtra[2]=0;
	
	//Output command (Input ex. "2.00 3.00 4.00")
	cout<<"Input Prices of the three items seperated by a single space"<<endl;
	//Capture the input entered
	getline(cin,card);
	
	//Convert the input string into numbers
	p[0]=StrtoDoub(card);
	p[1]=StrtoDoub(card);
	p[2]=StrtoDouble(card);
	
	//TEST OUTPUT
	//cout<<p1<<"\t"<<p2<<"\t"<<p3<<endl<<endl;;

	//Output Command (Input ex. "S New York 10 20 30" or "O Miami 20 15 10")
	cout<<"Input the Shipment or Order Card"<<endl;
	//Capture the input entered
	getline(cin,card);

	while(card!="close"){
		
		//Seperate the command, S/O, and the name of the city from the input string
		x1=Cut(card);
		x2=Cut(card);
		//Convert the remaining string into numbers
		amt[0]=StrtoDoub(card);
		amt[1]=StrtoDoub(card);
		amt[2]=StrtoDouble(card);
	
	//TEST OUTPUT
	//cout<<x1<<endl<<x2<<endl<<amt1<<" "<<amt2<<" "<<amt3<<endl;

		//If the command is S, Shipment, do the following
		if(x1=="S "){
			//Search for the correct Record
			for(int x=0; x<5;x++){
				//If the Warehouse city matches the city on the card, do the following
				if(Warehouse[x].Check(x2)){
					//Add the shipment to the inventotry of the correct Record
					Warehouse[x].Ship(amt[0],amt[1],amt[2]);
				}
			}
		}//If the command is O, Order, do the following
		else if(x1=="O "){
			//Search for the correct Record
			for(int x=0; x<5; x++){
				//If the city matches the city on the card,do the following 
				if(Warehouse[x].Check(x2)){
					//Search for the correct item
					for(int z=0;z<3;z++){
						//If the Warehouse has enough inventory to complete the order, do the following 
						if(Warehouse[x].Checkamt(amt[z],z )){
							//Place and complete order with the correct Warehouse on Record
							Warehouse[x].Order(amt[z], z);
						}//If the Warehouse does not have enough inventory to complete the order, do the following 
						else {
							//Search the for the Record with the most of that item
							for(int y=0;y<5;y++){
								//If this Warehouse has more inventory than the recorded max
								if (Warehouse[y].Find(z,max)){
									//And if it is not the same Warehouse that was requested
									if(y!=x){
										//Assign this Warehouse as the Record with the most of this item
										most=y;}
										}
								}
								//If these these two Warehouses have enough inventory to complete the order, do the following
								if(Warehouse[x].Max(max,z)>=amt[z]){
									//Ship All the inventory from the requested Warehouse
									temp=Warehouse[x].ShipAll(z);
									//Find out how many of the item are needed to complete order
									xtra[z]=Warehouse[most].Lend(temp,amt[z],z);
									//Reset the max Warehouse
									max=0;
									//Print how many of which item was given from one Warehouse to the other
									cout<<xtra[z]<<" of item "<<z+1<<" shipped from "<<Warehouse[most].Name()<<" to "<< Warehouse[x].Name()<<endl;
									//Print the inventory left in the Warehouse with the most of the item requested
									Warehouse[most].Print();
								}
								//If these two Warehouses cannot complete the order, do the following
								else{
									//Print Order is Unfilled
									cout<<"Order Unfilled"<<endl;
									//Reset the Max Warehouse
									max=0;
									//Update amount of that item sold
									amt[z]=0;
								}
						    }
						}
					
					//Print the inventory left in the Warehouse that was requested from
					Warehouse[x].Print();
				}
				} 
				
				//Set Precision of the output
				cout.setf(ios::fixed,ios::floatfield);
				cout.precision(2);
				//Print the receipt of the order with upcharges for lent items
				cout<<"Price of Order: $"<<amt[0]*p[0]+amt[1]*p[1]+amt[2]*p[2]+xtra[0]*p[0]*.1+xtra[1]*p[1]*.1+xtra[2]*p[2]*.1<<endl;
				//Reset the extra items needed to complete order
				xtra[0]=0;
				xtra[1]=0;
				xtra[2]=0;
			
		}
			//Output Command (Input ex. "S New York 10 20 30" or "O Miami 20 15 10")
			cout<<"Input the Shipment or Order Card(Type'close' to exit)"<<endl;
			getline(cin,card);
			
		
	}

	

		return 0;
}
string Cut(string &x){
	string y;
	y=x.substr(0,x.find(" ",0)+1);
	if(y.length()<5&&y.length()>2){
		y=x.substr(0,x.find(" ",x.find(" ",0)+1)+1);
		x.erase(0,x.find(" ",x.find(" ",0)+1)+1);
	}else {
		x.erase(0,x.find(" ",0)+1);
	}
	
	return y;
}
double StrtoDouble(string x){
	double y;
	stringstream z(x);
	z>>y; return y;
}

double StrtoDoub(string &x){
	string y;
	double a;
	y=x.substr(0,x.find(" ",0)+1);
	x.erase(0,x.find(" ",0)+1);
	stringstream z(y);
	z>>a;
	return a;
	
}
