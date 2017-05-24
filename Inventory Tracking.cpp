#include <iostream>
#include <string>
#include <sstream>

using namespace std;

class node{
	private:
		//Variable used to store quanity of item in this node
		int x;
		//Variable used to store price that was paid for the item
		double price;
		//Variable containing the address to the next node on the list
		node *nxt;
	public:
		//Default Constructor
		node():x(0),nxt(NULL){};
		//Constructor used to assign given values to the nodes variables
		node(int y, double z):x(y),price(z),nxt(NULL){};
        //Insert a node onto the list
		void insertnode(node *&first){
			//If the the first node is empty do the folowing 
			if (first==NULL){
				//Make this node the first element on the list
				first=this;
			}
			//If the next node is empty do the following 
			else if(first->nxt==NULL){
				//Make this node the next element on the list
				first->nxt=this;
			}
			//If neither node is empty do the following
			else {
				//Move to the next node on the list to find a spot
				insertnode(first->nxt);
			}
		}
		//Print the number of widgets remaining and the original price paid for the item for all nodes on list
		void print (void){
			//Print number of widgets remaining and original price
			cout<<x<<" Widgets remaining\t    Original Price: $"<<price<<endl;
			//If next is not empty do the following
			if(nxt!=NULL){
				//Print to the next node
				nxt->print();
			}
		
		}
		//Remove the first node from the list
		void pop (node *&first){
				first=nxt;
				delete this;
		}
		//Sell the widgets for a 30% profit while calculating any avaialble discounts the sale should have 
		double sell(int amt, node *&first, int test, int count, double disc ){
			//Variable containing sum of the sale
			static double sum=0;
			node *temp;
			//If the function is being called from the main program do the following
			if(test==1){
				//Reset the value in the variable sum
				sum=0;
			}
			//If there are any promotions available do the following 
			if(count<0){
				//Turn the disount into a percent
				disc=disc/100;
			}
			//If there are not any promotions available do the following 
			else {
                //Set the discount to nothing 
				disc=0;
			}
			//If the amount of widgets being sold can be satisfied by this node do the following
			if(amt<=x){
				//Remove the amount being sold from the amount of widgets in this node
				x-=amt;
				//Generate the price of the widgets, minus the discounts
				sum+=amt*price*1.30-amt*price*1.3*disc;
				//Print the amount of widgets sold
				cout<<amt<<" at ";
				//Set precision for the format of the prices
				cout.setf(ios::fixed,ios::floatfield);
				cout.precision(2);
				//Print the price of each widget,minus the discount, and the total price of the entire sale
				cout<<price*1.3*(1-disc)<<" each  \tSales: $"<<amt*price*1.30-amt*price*1.3*disc<<endl;
					
			}
			//If the amount cannot be satisfied by this node and there is another node on the list do the following
			else if (x<amt&&nxt!=NULL){
				//Generate the price of the widgets, minus the discounts
				sum+=x*price*1.30-x*price*1.3*disc;
				//If there are widgets in this inventory do the following
				if(x>0){
					//Print how many widgets were sold from this node's inventory
					cout<<x<<" at ";
					//Set precision for the format of the prices
					cout.setf(ios::fixed,ios::floatfield);
					cout.precision(2);
					//Print the price of each widget,minus the discount, and the total price of the entire sale
					cout<<price*1.3*(1-disc)<<" each  \tSales: $"<<x*price*1.30-x*price*1.3*disc<<endl;
				}
				//Go to the next node to complete the sale
				nxt->sell(amt-x,first,0, count,disc*100);
				//Adjust the inventory to reflect the changes after the sale
				x=0;		
			}
			//If the order can cannot be cmopleted in full
			else if(x<amt&&nxt==NULL){
				//Generate the price for the widgets available
				sum+=x*price*1.30-x*price*1.3*disc;
				//If there are widgets in this inventory do the following 
				if(x>0){
					//Print how many widgets were sold from this node's inventory
					cout<<x<<" at ";
					//Set precision for the format of the prices
					cout.setf(ios::fixed,ios::floatfield);
					cout.precision(2);
					//Print the price of each widget, minus the discount and the total price of the entire sale
					cout<<price*1.3*(1-disc)<<" each  \tSales: $"<<x*price*1.30-x*price*1.3*disc<<endl;
				}
				//Adjust the amount based on what was sold and what remains
				amt-=x;
				//Adjust the inventory to reflect the changes after the sale
				x=0;
				//Print the remaining amount of widgets that are not available for sale
				cout<<"Remainder of "<<amt<<" Widgets not available"<<endl;
			}	

			return sum;	
		}
		//Check if the node has any widgets remaining
		node *checkamt(void){
			node* temp;
			temp=this;
			//If the node does not have any widgets remaining do the following 
			if(x==0){
				//Remove this node from the list
				pop(temp);
				//If there is a next node on the list do the following 
				if(nxt!=NULL){
					//Check the amount of widgets in the next node
					temp=nxt->checkamt();
				}
				//If there is not a next node on the list do the following 
				else {
					//Tells the program the list is empty
					temp=NULL;
				}
					
			}
			//Returns the next node, if any, with remaining widgets
			return temp;
		}
		//Check total number of widgets one the list 
		int checktot(){
			//If the list is empty do the following
			if(this==NULL){
				//Return nothing as list is empty
				return 0;
			}
			//If there is only one item on the list do the following
			else if (nxt==NULL){
				//Return the number of widgets in this node
				return x;
			}
			//If there is another node on the list do the following 
			else if(nxt!=NULL) {
				//Return the number of widgets in this node plus the remaining widgets in the next nodes, if any
				return x + nxt->checktot();
			}
		}
		//Print the header for widgets sold
		void header(int y){
			cout<<y;
			cout<<" Widgets sold"<<endl;
		}
		//Print the footer for widgets sold
		void footer(double y){
			
			cout<<"\t\t  Total Sales: $"<<y<<endl<<endl;
		}
		
};

/* run this program using the console pauser or add your own getch, system("pause") or input loop */


int main() {
	//Variable used to create the list
	node *first=NULL;
	//Variable used to read in information from card
	int quan, disc=0, count=0;
	double price,sum;
	char PSR;
	//Variable used to read in information from the user
	string card;
		
	

	do{
		//Get the card from the users input
		getline(cin,card);
		//Turn the string to stream in order to seperate the different parts
		stringstream chg(card);
		//Seperate first part, the commannd, of the card
		chg>>PSR;
		//If the command is R, receive, do the following
		if(PSR=='R'){
			//Seperate the rest of the card
			chg>>quan>>price;
			//Create temporary node with the quantity of widgets received and price paid for them
			node *temp= new node(quan,price);
			//Print amount of widgets received
			cout<<quan<<" Widgets were received at the price $";
			//Set precision for the format of the price
			cout.setf(ios::fixed,ios::floatfield);
			cout.precision(2);
			//Print the price paid for each widget
			cout<<price<<endl<<endl;
			//Reset precision
			cout.precision();
			//Insert the node onto the list 
			temp->insertnode(first);
		}
		//If the command is S, sell, do the following
		else if(PSR=='S'){
			//If there are nodes on the list
			if(first!=NULL){
				//Seperate the rest of the card
				chg>>quan;
				//If the list contains enough widgets to complete the order that has been requested do the following
				if(first->checktot()>=quan){
					//Print the header with the amount of widgets requested
					first->header(quan);	
				}else {
					//Print the header with the  amount of widget available
					first->header(first->checktot());
				}
				//Figure out the price of the total sale
				sum=first->sell(quan, first,1,count,disc);	
				//Print the total amount of the sale
				first->footer(sum);
				//Increase the count for the discounts
				count++;
			//Check the list for empty nodes
			first=first->checkamt();
			}
			//If the list is empty
			else {
				//Print that the inventory is empty
				cout<<"Nothing in Stock"<<endl<<endl;
			}
		}
		//If the command is P, promotion, do the following 
		else if(PSR=='P'){
			//Seperate the discount percentage
			chg>>disc;
			//Let the user know how much the discount will be for the next two customers
			cout<<"The next 2 customers will receive a "<<disc<<"% discount"<<endl<<endl;
			//Set the count for two users  to get the discount
			count=-2;
		}	
	}while(PSR!='X');
	
	//If there are nodes on the list do the following 
	if(first!=NULL){
		//Print the remaining amount of widgets
		first->print();
	}
	//If the llist is empty do the following
	else {
		//Print that the inventory is empty
		cout<<"No Widgets Remaining"<<endl;
	}
	
	return 0;
}
