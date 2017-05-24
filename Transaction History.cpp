/*This program automatically reads in a file containing a Transaction history. Compares it to the users
Master file and prints out the Customers current record. Including the previous balance, orders & payments 
processed and the current balance after the Transactions have been processed  */

#include <iostream>
#include <fstream>
#include <string>

using namespace std;

class Record{
	private:
	    //Variable used to store Records Identification Number
		int Num;
		//Variable used to store Records balance
		double bal;
		//Variable used to store Records Company name
		string Name;
	public:
		//Constructor which sets  the Records ID,bal and name to arguments that have been passed 
		Record(int x, double y, string z):Num(x), bal(y), Name(z){};
		//Pay a portion of the balance on a Record
		void Pay(double x){
			bal-=x;
		}
		//Add an amount to the balance on a Record
		void Order(double x){
			bal+=x;
		}
		//Verify the Identification Number of a Record 
		bool Checkint(int x){
			return Num==x;
		}
		//Verify the Company name of a Record
		bool Check(string x){
			return Name==x;
		}
		//Prints the initial balance of a Record that has activity 
		void FPrint(void){
			cout<< Name<<"\t"<< Num<<endl<<endl;
			cout<<"\t PREVIOUS BALANCE: $ "<< bal<<endl<<endl;
		}
		//Print the finnal balance of a Record after the transacaions have been processed
		void LPrint(void){
			cout<<endl<<"\t BALANCE DUE:\t $ "<<bal<<endl<<endl<<endl;
		}
		//Find a particular Record and Verify if that the Identification number is only used 
		//for one Record
		bool Find(int x){
			//Open Master File with all Records in the users books
			ifstream MFile("Master.txt");
			//Variablle used to read ID number from Master File
			int CNum;
			//Variable used to  read the balance of a Record from Master File
			double b;
			//Variable used to read the Company name of a Record from Master File
			string n;
			
			//Read in all of the information from the Master file
			do{
				//Read in ID, name and balance from the Master file
				MFile>>CNum>>n>>b;
				//If the ID number is the same as the one being looked for do the following
				if(CNum==x){
					//If this ID number is already set for this Record do the following 
					if(CNum==Num){
						//If the name on this Record is not the same as the name from the Master file
						//there is a Duplicate Record on file
						if(!Check(n)){
							//Notify the user of the error. Ask them to correct the  issue.
							cout<<" Duplicate Record. Please Review Customer Number "<< x<<" and start program over."<<endl;
							//Record was not found
							return false;
						}
					}
					//If this ID number is not already set for the Record do the following
					else {
					//Set the ID on the Record to the one from the Master file
					Num=CNum;
					//Set the name on the Record to the one from the Master file
					Name=n;
					//Set the balance on the Record to the one from the Master File
					bal=b;
					//Print the beginning of the transaction receipt
					FPrint();
				}
				}
			}while(!MFile.fail());
			//If the ID number of the Record is not the one that was being looked for do the following 
			if(Num!=x){
				//Notify the user of the error. Ask them to correct the issue.
				cout<<"Record Not Found. Please remove Customer Number "<<x<<" and start program over."<<endl;
				//Record was not found
				return false;
			}
			//Record was found
			return true;			
		}
};
int main() {
	//Open the file with the Transactions that are being processed
	ifstream TFile("Transaction.txt");
	//Variables used to read in Transaction information
	//c keeps count of the number of Transactions
	int CNum,Num, tnum, c=0;
	double bal, quan, price;
	string Name, item;
	//Identifies if the record can be found on the Master File
	bool x=true;
	//Variable containing the Order or Payment command
	char op;
	//Construct a Record
	Record Cust(-1,0," ");
	
	//Read in first transaction command and ID number from Transaction file
	TFile>>op>>Num; 	 
	//Read in all the information from the Transaction file and process each order or payment 	 
	do{
		//If the ID number does not match current Customer do the following 
	 	if(!Cust.Checkint(Num)){
			//If this is not the first time in this condition do the following 
	 		if(c>0){
				//Print the Final balance on the Customer's Transaction history
	 			Cust.LPrint();
			}
			//Find the next Customers infomation based on the ID number from the Transaction file
			//Prints out the Customer's initial balance
			x=Cust.Find(Num);
			//If the ID number is a duplicate or the ID number is not found do the following
			if(!x){
				//Terminate Program
				return 1;
			}
			//Increase the count
			c++;		
		 }
		//If the command is a Payment do the following
		if(op=='P'){
			//Read in the remainig information from the Transaction file
			TFile>>tnum>>price;
			//Deduct the payment amount from the Customer's balance
			Cust.Pay(price);
			//Print out the Payment Record
			cout<<tnum<<"\tPAYMENT\t  - $ "<<price<<endl;
		}
		//If the command is an Order do the following 
		else if(op=='O'){
			//Read in the  remaing information from the Treansaction file
			TFile>>tnum>>item>>quan>>price;
			//Add the order amount to the Customer's balance
			Cust.Order(quan*price);
			//Prinnt out the Order Record
			cout<<tnum<<"\t"<<item<<"\t    $ "<<quan*price<<endl;
		}
		//Read in the next Transaction
		TFile>>op>>Num;
	}while(!TFile.fail());
	//Print the  final Customer's Final balance
	Cust.LPrint();

	
	return 0;
}
