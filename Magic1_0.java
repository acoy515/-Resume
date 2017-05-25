import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.*;

public class Magic implements Runnable{
	GUIMagicTrick gui;
	public static int a,b,c, num;
	Semaphore sem;
	
	public Magic(GUIMagicTrick gui, Semaphore sem){
		this.gui=gui;	
		this.sem=sem;
	}
	public void run(){
		try{
			sem.acquire();
		}catch(Exception e){}
		System.out.println("MAGIC");
		System.out.println(gui.count);
		sem.release();
		for (int x=0;x<gui.count;x++){
//			for(int y=0;y<2;y++){
//				while(())==0){
//					try{
//						Thread.sleep(100);
//					}catch(Exception e){}
//					System.out.printf("**** %s",c);
//				}
//				if(y==1)a=c;
//				if(y==2)b=c;
//			}
//			try{
//				sem.acquire();
//				a=gui.pick;
//				b=gui.pick2; 
//			}catch(Exception e){}
			findNum();
			System.out.println(num);
			System.out.printf("A= %s \n",a);
			System.out.printf("B= %s \n", b);
			display();
			gui.gui();
			gui.win.setContentPane(gui.pan);
			gui.win.invalidate();
			gui.win.validate();
		}
		gui.pan.removeAll();
		gui.lab.setText("GAME OVER");
		gui.lab.setFont(new Font("Times New Roman", Font.BOLD,50));
		gui.lab.setBounds(170,125,400,100);
		gui.pan.add(gui.lab);
		gui.win.setContentPane(gui.pan);
		gui.win.invalidate();
		gui.win.validate();
	}
	public void display(){
		try{
			sem.acquire();
			gui.pan.removeAll();
			gui.pan.setBackground(Color.GREEN);
			if(num==-1){
				gui.lab.setText("BAD MAGICIAN!!!");
				gui.lab.setFont(new Font("Times New Roman", Font.BOLD,40));
				gui.lab.setBounds(125,50,400,50);
				gui.pan.add(gui.lab);				
			}else if(num==-2){
				gui.lab.setText("VOLUNTEER CHEATED!!!");
				gui.lab.setFont(new Font("Times New Roman", Font.BOLD,40));
				gui.lab.setBounds(50,50,600,50);
				gui.pan.add(gui.lab);
				
			}else {
				gui.lab.setText("YOUR NUMBER IS: ");
				gui.lab1.setText(Integer.toString(num));
				gui.lab1.setFont(new Font("Times New Roman", Font.BOLD,40));
				gui.lab1.setBounds(250,50,50,50);
				gui.pan.add(gui.lab);
				gui.pan.add(gui.lab1);
			}
			
			gui.win.setContentPane(gui.pan);
			gui.win.invalidate();
			gui.win.validate();
			Thread.sleep(2000);
	}catch(Exception e){}
		sem.release();
	}
	public void find(int start,int end, int s, int e){
		num=0;
		for(int x=start; x<end;x++){
			for (int y=s;y<=e;y++){
				if(gui.cards.get(x)==y){
					if(num==0)num=y;
					else num=-1;
							}
			}
		}
		if(num==0)num=-2;
	}
		
	public void findNum(){
		try{
			sem.acquire();
			
			if(gui.pick==1){
				if(gui.pick2==1)find(0,4,1,4);
				else if (gui.pick2==2)find(4,8,1,4);
				else if (gui.pick2==3)find(8,12,1,4);
				else if (gui.pick2==4)find(12,16,1,4);
			}else if(gui.pick==2){
				if(gui.pick2==1)find(0,4,5,8);
				else if (gui.pick2==2)find(4,8,5,8);
				else if (gui.pick2==3)find(8,12,5,8);
				else if (gui.pick2==4)find(12,16,5,8);
			}else if(gui.pick==3){
				if(gui.pick2==1)find(0,4,9,12);
				else if (gui.pick2==2)find(4,8,9,12);
				else if (gui.pick2==3)find(8,12,9,12);
				else if (gui.pick2==4)find(12,16,9,12);
			}else if(gui.pick==4){
				if(gui.pick2==1)find(0,4,13,16);
				else if (gui.pick2==2)find(4,8,13,16);
				else if (gui.pick2==3)find(8,12,13,16);
				else if (gui.pick2==4)find(12,16,13,16);
			}
		}catch(Exception e){}
		sem.release();
//		switch(gui.pick){
//		case 1:
//			switch(gui.pick2){
//			case 1:
//				num=1;
//				break;
//			case 2:
//				num=2;
//				break;
//			case 3:
//				num=3;
//				break;
//			case 4:
//				num=4;
//				break;
//			}
//		case 2:
//			switch(gui.pick2){
//			case 1:
//				num=5;
//				break;
//			case 2:
//				num=6;
//				break;
//			case 3:
//				num=7;
//				break;
//			case 4:
//				num=8;
//				break;
//			}
//		case 3:
//			switch(gui.pick2){
//			case 1:
//				num=9;
//				break;
//			case 2:
//				num=11;
//				break;
//			case 3:
//				num=10;
//				break;
//			case 4:
//				num=12;
//				break;
//			}
//		case 4:
//			switch(gui.pick2){
//			case 1:
//				num=13;
//				break;
//			case 2:
//				num=16;
//				break;
//			case 3:
//				num=15;
//				break;
//			case 4:
//				num=14;
//				break;
//			}
//			}
		}
	}

