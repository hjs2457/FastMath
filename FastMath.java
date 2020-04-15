import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FastMath extends JApplet{

	private static final long serialVersionUID = 1L;
	int WIDTH=1920/2, HEIGHT=1080/2+200;
	public void init() {
		this.setSize(WIDTH,HEIGHT);
		add(new MyPanel());
	}
}

class MyPanel extends JPanel implements ActionListener, MouseListener, KeyListener{
	private static final long serialVersionUID = 1L;
	Timer timer = new Timer( 100, new ActionListener(){
		  public void actionPerformed( ActionEvent e ){
			  time-=0.1;
			  formattedTime = String.format("%2.1f%n", time);
			  time=Double.parseDouble(formattedTime);
			  if(time==0) {
				  nextProblem();
			  } 
		  }
	});
	int lastMax = 0;
	double time = 5.0;
	String formattedTime;
	int WIDTH=1920/2, HEIGHT=1080/2+200;
	int PROBLEMS_PER_ROUND=3;
	char mode = '+';
	Rectangle add, sub, mul, div, mod, sq;
	Rectangle three,five,ten,fifteen;
	boolean home = true;
	String instructions = "Solve problems as fast as possible";
	int problem = 0;
	int score = 0;
	int lastScore = 0;
	ArrayList<Equation> eqs = new ArrayList<Equation>();
	int i = 0;
	Random rand = new Random();
	String input = "";
	public MyPanel() {
		
		this.setFocusable(true);
		this.requestFocus();
		add = new Rectangle(100,150,150,50);
		sub = new Rectangle(400,150,150,50);
		mul = new Rectangle(100,250,150,50);
		div = new Rectangle(400,250,150,50);
		mod = new Rectangle(700,150,150,50);
		sq = new Rectangle(700,250,150,50);
		three = new Rectangle(150,450,100,100);
		five = new Rectangle(350,450,100,100);
		ten = new Rectangle(550,450,100,100);
		fifteen = new Rectangle(750,450,100,100);
		this.addMouseListener(this);
		this.addKeyListener(this);
		Timer t = new Timer(20,this);
		timer.start();
		t.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(home) {
			g.setColor(Color.blue);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.yellow);
			/*
			g.drawRect(add.x, add.y, add.width, add.height);
			g.drawRect(sub.x, sub.y, sub.width, sub.height);
			g.drawRect(mul.x, mul.y, mul.width, mul.height);
			g.drawRect(div.x, div.y, div.width, div.height);
			g.drawRect(mod.x, mod.y, mod.width, mod.height);
			g.drawRect(sq.x, sq.y, sq.width, sq.height);
			*/
			/*
			g.drawRect(three.x, three.y, three.width, three.height);
			g.drawRect(five.x, five.y, five.width, five.height);
			g.drawRect(ten.x, ten.y, ten.width, ten.height);
			g.drawRect(fifteen.x, fifteen.y, fifteen.width, fifteen.height);
			*/
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,32));
			g.drawString("Select Mode", WIDTH/2-100, 100);
			g.drawString("Addition", add.x+5, add.y+30);
			g.drawString("Subtraction", sub.x+5, sub.y+30);
			g.drawString("Multiplication", mul.x+5, mul.y+30);
			g.drawString("Division", div.x+5, div.y+30);
			g.drawString("Modulus", mod.x+5, mod.y+30);
			g.drawString("Squares", sq.x+5, sq.y+30);
			g.drawString("Select Problem Count", WIDTH/2-200, 400);
			g.drawString("3", three.x+40, three.y+60);
			g.drawString("5", five.x+40, five.y+60);
			g.drawString("10", ten.x+40,ten.y+60);
			g.drawString("15", fifteen.x+40, fifteen.y+60);
			g.drawString(instructions, 200, 600);
			g.drawString("Previous Score: "+lastScore+"/"+lastMax,300,650);
		}else {
			g.setColor(Color.blue);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.yellow);
			g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,32));
			g.drawString(input, WIDTH/2-100+50, 300);
			if(eqs.get(problem).op=='^') {
				g.drawString(eqs.get(problem).num1+"^2", 450, 200);
			}else {
				g.drawString(eqs.get(problem).num1+" "+eqs.get(problem).op+" "+eqs.get(problem).num2, 450, 200);
			}
			
			g.drawString("Score: "+score, 400, 400);
			g.drawString("Time: "+formattedTime, 700, 300);
			g.drawString(problem+1+"/"+PROBLEMS_PER_ROUND, 400, 600);
			
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(home) {
			
		}else {
			
		}
		this.repaint();
	}
	
	public void nextProblem() {
		if(problem<(PROBLEMS_PER_ROUND)) {
			if(!input.equals("")) {
				int inp = Integer.parseInt(input);
				if(inp==eqs.get(problem).ans) {
					score++;
				}
			}
			problem++;
			input="";
			time=5.0;
		}if(problem==PROBLEMS_PER_ROUND){
			lastMax=eqs.size();
			eqs.clear();
			input="";
			problem=0;
			lastScore=score;
			score=0;
			home=true;
			timer.stop();
			time=5.0;
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX(), my = e.getY();
		if(home) {
			Rectangle mr = new Rectangle(mx,my,1,1);
			if(mr.intersects(add)) {
				home=false;
				mode='+';
				for(int i = 0; i<PROBLEMS_PER_ROUND; i++) {
					int a = rand.nextInt(99)+1;
					int b = rand.nextInt(99)+1;
					int answ=a+b;;
					eqs.add(new Equation(a,b,mode, answ));
				}
			}
			if(mr.intersects(sub)) {
				home=false;
				mode='-';
				for(int i = 0; i<PROBLEMS_PER_ROUND; i++) {
					int a = rand.nextInt(144)+1;
					int b = rand.nextInt(144)+1;
					if(b>a) {
						int t = a;
						a=b;
						b=t;
					}
					int answ=a-b;
					eqs.add(new Equation(a,b,mode,answ));
				}
				
			}
			if(mr.intersects(mul)) {
				home=false;
				mode='*';
				for(int i = 0; i<PROBLEMS_PER_ROUND; i++) {
					int a = rand.nextInt(12)+1;
					int b = rand.nextInt(12)+1;
					int answ=a*b;;
					eqs.add(new Equation(a,b,mode, answ));
				}
				
			}
			if(mr.intersects(div)) {
				home=false;
				mode='/';
				int c = 1;
				for(int i = 0; i<PROBLEMS_PER_ROUND; i++) {
					int a = rand.nextInt(144)+1;
					int b = rand.nextInt(11)+2;
					while(a%b!=0) {
						b = rand.nextInt(11)+2;
						c++;
						if(c==10) {
							a=rand.nextInt(144)+1;
							c=1;
						}
					}
					int answ=a/b;
					eqs.add(new Equation(a,b,mode,answ));
				}
			}
			if(mr.intersects(mod)) {
				home=false;
				mode='%';
				for(int i = 0; i<PROBLEMS_PER_ROUND; i++) {
					int a = rand.nextInt(144)+1;
					int b = rand.nextInt(12)+1;
					int answ = a%b;
					eqs.add(new Equation(a,b,mode,answ));
				}
			}
			if(mr.intersects(sq)) {
				home=false;
				mode='^';
				for(int i = 0; i<PROBLEMS_PER_ROUND; i++) {
					int a = rand.nextInt(15)+1;
					int answ = a*a;
					eqs.add(new Equation(a,a,mode,answ));
				}
			}
			if(mr.intersects(three)) PROBLEMS_PER_ROUND=3;
			if(mr.intersects(five)) PROBLEMS_PER_ROUND=5;
			if(mr.intersects(ten)) PROBLEMS_PER_ROUND=10;
			if(mr.intersects(fifteen)) PROBLEMS_PER_ROUND=15;
			if(home==false) timer.start();
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	
	public void keyReleased(KeyEvent e) {
		if(!home) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				nextProblem();
			}else {
				char ch = e.getKeyChar();
				if(ch<='9' && ch>='0') {
					String k = e.getKeyChar()+"";
					input+=k;
				}else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && input.length()>0) {
					input=input.substring(0,input.length()-1);
				}
			}
		}
	}
	public void keyPressed(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
