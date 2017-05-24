import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.SpringLayout;
import javax.swing.text.*;

public class GUI implements ActionListener{
	public Suduko suduko;
	public static JFrame win;
	public static JPanel mainpan, boardpan, button,error;
	public static JScrollPane instructions, progress;
	public StyledDocument progressdoc;
	public Style style;
	public static JTextPane instructs, progression;
	public static JButton submit, clear;
	public static JLabel title, sub, sub1;
	public static Semaphore sem= new Semaphore (1,true);
	public static JTextField[][] txt = new JTextField[9][9];
	public static int rows=9, cols=9;
	
	public void clear(){
		for (int x=0;x<rows;x++){
			for(int y=0; y<cols;y++){
				txt[x][y].setText("");
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		int[][] array = new int [rows][cols];
		for (int x=0;x<rows;x++){
			for(int y=0; y<cols;y++){
				array[x][y]=0;
			}}
		
		int digit = 0;
		boolean proceed=true;
		suduko = new Suduko ();
		
		if(e.getSource()==clear){
			if(clear.getText()=="No"){
				mainpan.removeAll();
				title.setText("GOODBYE");
				title.setFont(new Font ("Arial Black", Font.BOLD, 50));
				mainpan.add(progress);
				mainpan.add(title);
				win.revalidate();
				return;
			}
			clear();
		}
		if (e.getSource()==submit){
			if(submit.getText()=="Yes"){
				clear();
				sub.setText("");
				submit.setText("Submit)");
				clear.setText("Clear");
				return;
			}
			for (int x=0;x<rows;x++){
				for(int y=0; y<cols;y++){
					if(txt[x][y].getText().equals("")){
						digit=0;
					}else{
						try{
							digit=Integer.parseInt(txt[x][y].getText());
						}catch(Exception ex){
							try{
								StyleConstants.setBold(style, true);
								progressdoc.insertString(progressdoc.getLength(),"Please Enter Digits From 1 - 9! \n", style);
								StyleConstants.setBold(style, false);
								progressdoc.insertString(progressdoc.getLength(),(txt[x][y].getText())+" is an invalid input\n"
												, style);
							}catch(Exception exc){
								System.err.println("Oops");
							}
							digit=-1;
							proceed=false;
						}
					}
					if((digit>=10||digit<0)&&proceed){
						try{
							StyleConstants.setBold(style, true);
							progressdoc.insertString(progressdoc.getLength(),"Please Enter Digits From 1 - 9! \n", style);
							StyleConstants.setBold(style, false);
							progressdoc.insertString(progressdoc.getLength(),(txt[x][y].getText())+" is an invalid input\n"
											, style);
						}catch(Exception exc){
							System.err.println("Oops");
						}
						proceed=false;
					}else if(digit<10&&digit>0){
						
						array[x][y]=digit;
						if(!suduko.itfits(array, x, y)){
							//sub1=new JLabel();
							//sub1.setText("Your Input does not follow the Suduko Rules");
							//sub1.setFont(new Font("Arial Black", Font.PLAIN, 12));
							
							try{
								StyleConstants.setBold(style, true);
								progressdoc.insertString(progressdoc.getLength(),"Input Does Not Follow Suduko Rules\n",style);
								StyleConstants.setBold(style, false);
								progressdoc.insertString(progressdoc.getLength(),(Integer.toString(digit))+ " on Row "
											+ Integer.toString(x)+" Column "+ Integer.toString(y)+" has a duplicate entry\n"
													+ "Please review the rules on your left\n", style);
							}catch(Exception exc){
								System.err.println("Oops");
							}
							proceed=false;
						}
						
					}else{
						array[x][y]=0;
					}						
				}
			}
			
			if(proceed){
				suduko.sudukocomplete(array,0,0);
				array=suduko.board;
				try{
					StyleConstants.setBold(style, true);
					progressdoc.insertString(progressdoc.getLength(),"Suduko has been Solved\n",style);
				}catch(Exception exc){}
				for(int x=0;x<9;x++){
					for(int y=0; y<9;y++){
						try{
							StyleConstants.setBold(style, false);
							progressdoc.insertString(progressdoc.getLength(),Integer.toString(array[x][y])+" ",style);
						}catch(Exception exc){}
						txt[x][y].setText(Integer.toString(array[x][y]));
					}
					try{
						StyleConstants.setBold(style, false);
						progressdoc.insertString(progressdoc.getLength(),"\n",style);
					}catch(Exception exc){}
				}
				sub.setText("Would you like to solve another Suduko?");
				sub.setFont(new Font("Arial Black", Font.PLAIN, 12));
				win.add(sub);
				submit.setText("Yes");
				clear.setText("No");
				win.revalidate();
			}else {
				win.revalidate();
			}
			
		}
	}
	
	public static void menu (){
		win =new JFrame("Suduko Solver");
		win.setSize(600,400);
		win.setLocationRelativeTo(null);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title= new JLabel("Suduko");
		sub=new JLabel("Solver");
		title.setFont(new Font("Arial Black",Font.PLAIN, 40));
		sub.setFont(new Font("Arial Black",Font.PLAIN,30));
		mainpan=new JPanel();
		mainpan.setBackground(Color.lightGray);
		mainpan.setLayout(new GridBagLayout());
		GridBagConstraints a=new GridBagConstraints();
		GridBagConstraints b=new GridBagConstraints();
		a.gridx=0;
		a.gridy=0;
		b.gridx=2;
		b.gridy=2;
		mainpan.add(title,a);
		mainpan.add(sub,b);
		win.add(mainpan);
		win.setVisible(true);
		try{
			Thread.sleep(2500);
		}catch(Exception e){};
	}
	
	public void board(){
		String message = "Please insert 16 digits from 1 to 9 into the grid following the rules of Suduko, "
				+"which are listed below, for an accurate Suduko puzzle\n Please hit Submit once you have input the digits\n\n" +"The "
				+ "classic Sudoku game involves a grid of 81 squares." 
				+"The grid is divided into nine blocks, each containing nine squares. The rules of the game"
				+"are simple: each of the nine blocks has to contain all the numbers 1-9 within its squares."
				+" Each number can only appear once in a row, column or box.";
		StyleContext context = new StyleContext();
		StyledDocument document = new DefaultStyledDocument(context);
		progressdoc=new DefaultStyledDocument(context);
		
		
		style=context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(style,StyleConstants.ALIGN_LEFT);

		StyleConstants.setFontSize(style, 12);
		StyleConstants.setSpaceAbove(style, 3);
		StyleConstants.setSpaceBelow(style, 3);
		
		try{
			document.insertString(document.getLength(),message, style);
		}catch(Exception e){
			System.err.println("Oops");
		}
		try{
			progressdoc.insertString(progressdoc.getLength(),"Let's Begin\n\n", style);
		}catch(Exception e){
			System.err.println("Oops");
		}
		instructs=new JTextPane(document);
		instructs.setEditable(false);
		instructions = new JScrollPane(instructs);
		instructions.setMinimumSize(new Dimension(200,200));
		instructions.setPreferredSize(new Dimension(200,200));
		instructions.setMaximumSize(new Dimension (200,200));
		
		progression= new JTextPane(progressdoc);
		progression.setEditable(false);
		progress=new JScrollPane(progression);
		progress.setMinimumSize(new Dimension(200,200));
		progress.setPreferredSize(new Dimension(200,200));
		progress.setMaximumSize(new Dimension (200,200));
	
		mainpan.removeAll();
		mainpan.setBackground(Color.lightGray);
		mainpan.setLayout(new GridBagLayout());
		
		GridBagConstraints a = new GridBagConstraints();
		GridBagConstraints b = new GridBagConstraints();
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints d = new GridBagConstraints();
		GridBagConstraints e = new GridBagConstraints();
		a.gridx=1;
		a.gridy=1;
		b.gridx=0;
		b.gridy=1;
		c.gridx=0;
		c.gridy=0;
		d.gridx=1;
		d.gridy=2;
		e.gridx=2;
		e.gridy=1;
		
		boardpan=new JPanel();
		boardpan.setLayout(new GridLayout(9,9));
		//boardpan.setLocation(200,200);
		for(int x=0;x<rows;x++){
			for(int y=0;y<cols;y++){
				txt[x][y]=new JTextField("");
				txt[x][y].setMaximumSize(new Dimension(20,20));
				txt[x][y].setPreferredSize(new Dimension(20,20));
				txt[x][y].setMinimumSize(new Dimension(20,20));
				txt[x][y].setFont(new Font("Times New Roman", Font.BOLD,16));
				boardpan.add(txt[x][y]);
			}
		}
		
		title.setText("SUDUKO SOLVER");
		title.setFont(new Font("Arial Black", Font.BOLD, 20));
		
		submit = new JButton("Submit");
		submit.addActionListener(this);
		clear = new JButton("Clear");
		clear.addActionListener(this);
		button=new JPanel(new FlowLayout());
		button.setBackground(Color.lightGray);
		button.add(submit);
		button.add(clear);
		
		mainpan.add(progress,e);
		mainpan.add(button, d);
		mainpan.add(title,c);
		mainpan.add(boardpan,a);
		mainpan.add(instructions,b);
		win.setContentPane(mainpan);
		win.setResizable(false);
		win.revalidate();
	}
	 public static void main( String[] args ) {
		 GUI gui=new GUI();
		 menu();
		 gui.board();
	 }

}

