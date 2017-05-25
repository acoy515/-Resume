import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.*;
import javax.swing.*;

public class Menu implements Runnable{
	GUIMagicTrick gui;
	Semaphore sem;
	
	public Menu(GUIMagicTrick gui, Semaphore sem){
		this.gui=gui;
		this.sem=sem;
	}
	public void run(){
		try{
			sem.acquire();
			gui.win=new JFrame("Magic Trick");
			gui.win.setSize(600, 400);
			gui.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gui.lab=new JLabel("How many magic tricks would you like to run?");
			gui.txt=new JTextField("");
			gui.lab.setFont(new Font("Times New Roman", Font.BOLD,20));
			gui.lab.setBounds(90,0,600,30);
			gui.txt.setBounds(260,50,50,30);
			gui.pan=new JPanel();
			gui.txt.addActionListener(gui);
			gui.pan.setBackground(Color.green);
			gui.pan.setLayout(null);
			gui.pan.add(gui.lab);
			gui.pan.add(gui.txt);
			gui.win.add(gui.pan);
			gui.win.setVisible(true);
		}catch (Exception e){}
	}
}
