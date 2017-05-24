import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUIMagicTrick implements ActionListener, Runnable{
	public JFrame win;
	public JPanel pan;
	public JButton b1, b2, b3, b4;
	public JTextField txt;
	public JLabel lab, lab1, one, two, three, four;
	public int count, pick,pick2, guess=0;
	public static Semaphore sem=new Semaphore(1,true);
	public boolean p=false;
	
	public GUIMagicTrick(){}
	public void run(){
		gui();
		win.setContentPane(pan);
		win.invalidate();
		win.validate();
	}
	
	public void actionPerformed(ActionEvent e){		
			if(guess>0){
				if(e.getSource()==b1) pick2=1;
				else if(e.getSource()==b2) pick2=2;
				else if(e.getSource()==b3) pick2=3;
				else if(e.getSource()==b4) pick2=4;
				System.out.printf("Action = %s, %d \n", pick, pick2);
				guess=0;
				sem.release();
			}else if(e.getSource()==b1){
				pick=1;
				guess++;
				//sem.release();
				lab.setText("");
				lab1.setText("What row is the same number on now?");
				one.setText("1       5      9     13");
				two.setText("2       6      7      8");
				three.setText("3     10     11     12");
				four .setText("4     14     15     16");
				return;
			}else if(e.getSource()==b2){
				pick=2;
				guess++;
				//sem.release();
				lab.setText("");
				lab1.setText("What row is the same number on now?");
				one.setText("1       5      9     13");
				two.setText("6       2      3      15");
				three.setText("7     10     11     12");
				four .setText("4     14     8      16");
				return;
			}else if(e.getSource()==b3){
				pick=3;
				guess++;
				//sem.release();
				lab.setText("");
				lab1.setText("What row is the same number on now?");
				one.setText("1       5      9     13");
				two.setText("2       11      7      8");
				three.setText("3     10      6      4");
				four .setText("12     14     15     16");
				return;
			}else if(e.getSource()==b4){
				pick=4;
				guess++;
				//sem.release();
				lab.setText("");
				lab1.setText("What row is the same number on now?");
				one.setText("1       5      9     13");
				two.setText("2       6      16      8");
				three.setText("15    10     11     12");
				four .setText("4     14     3       7");	
			}else if (e.getSource()==txt){
				count=Integer.parseInt(txt.getText());
				sem.release();
//				gui();
//				win.setContentPane(pan);
//				win.invalidate();
//				win.validate();
			}
			
			

	}
	public int getPick(){
		int temp=pick;
		pick=0;
		return temp;
	}
	
	public void gui(){
		try{
			sem.acquire();
		}catch(Exception e){}
		pan.removeAll();
		pan.setBackground(Color.GREEN);
		one = new JLabel ("1       2      3      4");
		two = new JLabel ("5       6      7      8");
		three = new JLabel("9     10     11     12");
		four = new JLabel ("13    14     15     16");
		
		b1=new JButton("1");
		b2=new JButton("2");
		b3=new JButton("3");
		b4=new JButton("4");
		
		b1.setBounds(20,75,50,50);
		b2.setBounds(20,135,50,50);
		b3.setBounds(20,195,50,50);
		b4.setBounds(20,255,50,50);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
	
		
		one.setFont(new Font("Times New Roman", Font.PLAIN,25));
		one.setBounds(200,75,500,50);
		two.setFont(new Font("Times New Roman", Font.PLAIN,25));
		two.setBounds(200,135,500,50);
		three.setFont(new Font("Times New Roman", Font.PLAIN,25));
		three.setBounds(200,195,500,50);
		four.setFont(new Font("Times New Roman", Font.PLAIN,25));
		four.setBounds(200,255,500,50);
		
		lab=new JLabel("Pick a number between 1 and 16");
		lab.setFont(new Font("Times New Roman", Font.PLAIN,20));
		lab.setBounds(150,0,400,20);
		lab1=new JLabel("Then choose the row that it is in");
		lab1.setFont(new Font("Times New Roman", Font.BOLD,20));
		lab1.setBounds(150,20,400,20);
		
		pan.setLayout(null);
		pan.add(b1);
		pan.add(b2);
		pan.add(b3);
		pan.add(b4);
		pan.add(one);
		pan.add(two);
		pan.add(three);
		pan.add(four);
		pan.add(lab);
		pan.add(lab1); 
		
		
		
//		pan1.setLayout(new GridLayout (4,4));
//		pan1.add(new JLabel("1"));
//		pan1.add(new Button("2"));
//		pan1.add(new Button("3"));
//		pan1.add(new Button("4"));
//		pan1.add(new Button("5"));
//		pan1.add(new Button("6"));
//		pan1.add(new Button("7"));
//		pan1.add(new Button("8"));
//		pan1.add(new Button("9"));
//		pan1.setBackground(Color.green);
//		pan1.setBounds(450, 300, 100, 70);
//		win.add(pan1);
//		win.removeAll();
//		win.add(pan);
		
	}
//	public void menu(){
//		win=new JFrame("Magic Trick");
//		win.setVisible(true);
//		win.setSize(600, 400);
//		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		txt=new JTextField("Test");
//		pan=new JPanel();
//		txt.addActionListener(this);
//		pan.setBackground(Color.BLUE);
//		pan.add(txt);
//		win.add(pan);
//	}

	
    public static void main( String[] args ) {
    	GUIMagicTrick gui = new GUIMagicTrick();
    	Menu men = new Menu(gui,sem);
    	Magic mag=new Magic(gui,sem);
    	Thread thread1 = new Thread(gui);
    	Thread thread2 = new Thread(men);
    	Thread thread3 = new Thread(mag);
    	
    	thread2.start();
    	thread3.start();
    	thread1.start();
        
  
//    	System.out.println("How many games");
//    	Scanner sc=new Scanner(System.in);
//        int a=sc.nextInt();
//        System.out.println(a);
    	
    	
      }
 }


