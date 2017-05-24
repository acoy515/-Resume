import java.util.concurrent.Semaphore;

public class Suduko{
	Semaphore sem;
	int [][] board;
	
	public boolean itfits(int board[][], int row, int col){
		for(int x=0;x<9;x++){
			if(row!=x){
				if(board[row][col]==board[x][col]) {
					//cout<<"row false "<<board[row][col]<<" "<<row<<" "<<col<<" "<<x<<endl;
					return false;
				}
			}
			if(col!=x){
				if(board[row][col]==board[row][x]) {
					//cout<<"col false "<<board[row][col]<<" "<<row<<" "<<col<<" "<<x<<endl;
					return false;
				}
			}
		}
		if(row<3){
			if(col<3){
				for(int x=0; x<3;x++){
					for(int y=0; y<3;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}else if(col<6){
				for(int x=0; x<3;x++){
					for(int y=3; y<6;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}else if(col<9){
				for(int x=0; x<3;x++){
					for(int y=6; y<9;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}
		}else if(row<6){
			if(col<3){
				for(int x=3; x<6;x++){
					for(int y=0; y<3;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}else if(col<6){
				for(int x=3; x<6;x++){
					for(int y=3; y<6;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}else if(col<9){
				for(int x=3; x<6;x++){
					for(int y=6; y<9;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}
		}else if(row<9){
			if(col<3){
				for(int x=6; x<9;x++){
					for(int y=0; y<3;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}else if(col<6){
				for(int x=6; x<9;x++){
					for(int y=3; y<6;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}else if(col<9){
				for(int x=6; x<9;x++){
					for(int y=6; y<9;y++){
						if(row!=x&&col!=y){
							if(board[row][col]==board[x][y]){
								return false;
							}
						}
					}
				}
			}
		}
		//cout<<"true "<<board[row][col]<<" "<<row<<" "<<col<<" "<<endl;
		return true;
	}
	boolean isSolved(int board[][]){
		for(int x=0; x<9;x++){
			for(int y=0;y<9;y++){
				if(!itfits(board,x,y)){
					return false;
				}
				
			}
		}
		this.board=board;
		return true;
	}
	void print(int board[][]){
		for(int x=0;x<9;x++){
				for(int y=0;y<9;y++){
					if(board[x][y]==0){
						System.out.printf("_ ");
					}else{
						System.out.printf("%d ",board[x][y]);
					}
				}
				System.out.println();
			}
	}
	public boolean sudukocomplete(int board[][], int row, int col){
		boolean solved=false;
		if(row==9){
			print(board);
			return isSolved(board);
		}
		while(board[row][col]!=0){
			if(col>=8){
				row++;
				col=0;
			} else if(col<8){
				col++;
			}
			if(row>=9){
				return sudukocomplete(board,row,col);
			}
		}
		while(!solved){
			board[row][col]++;
			if(itfits(board, row, col)){
				if(col<8){
					solved=sudukocomplete(board, row, col+1);
				}else{
					solved=sudukocomplete(board, row+1,0);
				}
				if(solved){
						return solved;
					}
			}
			if(board[row][col]==9){
				board[row][col]=0;
				return solved;
			}
		}
		return solved;
	}

}
