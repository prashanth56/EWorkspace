package aSnakesGame; 
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
@SuppressWarnings("serial")
public class Snakes extends JFrame implements Runnable,KeyListener,ActionListener{
	Graphics2D bufer,background,gr;
	Image ibufer,head,grass,ibackground;
	boolean left,right,up,down,esc,aPlayer,demo,enter,initiated;
	Button start,net,out,abot;
	int status,counter,cont=0,ex,ey;
	String firstName;
	SnakeX snakeArray[]=new SnakeX[2],tu;
	TextField text;
	Snakes(){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        @SuppressWarnings("unused")
		GraphicsDevice device = env.getDefaultScreenDevice();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setIgnoreRepaint(true);
		setTitle("Snakes");
		setUndecorated(true);
		setLayout(null);
		setSize(600,600);
		setVisible(true);
		//device.setFullScreenWindow(this);
		//DisplayMode display=device.getDisplayMode();
		//if(device.isDisplayChangeSupported())device.setDisplayMode(new DisplayMode(800,600,display.getBitDepth() ,DisplayMode.REFRESH_RATE_UNKNOWN));
		ex=getWidth()/2-300;
		ey=getHeight()/2-300;
		status=0;
		counter=0;
		ibufer=createImage(600,600);
		bufer=(Graphics2D)ibufer.getGraphics();
		ibackground=createImage(getWidth(),getHeight());
		background=(Graphics2D)ibackground.getGraphics();
		gr=(Graphics2D)getGraphics();
		head=obtImagen("head.png");
		grass=obtImagen("pasto.png");
		start=new Button("Single Player");
		net=new Button("Net Game");
		out=new Button("Exit");
		abot=new Button("About");
		text=new TextField("First Name");
		text.setBounds(getWidth()/2-60,ey+245,120,30);
		text.setBackground(Color.RED);
		text.setForeground(Color.WHITE);
		start.setBounds(text.getX(),text.getY()+40,120,30);
		net.setBounds(start.getX(),start.getY()+40,120,30);
		net.setEnabled(false);
		abot.setBounds(start.getX(),net.getY()+40,120,30);
		out.setBounds(start.getX(),abot.getY()+40,120,30);
		start.addActionListener(this);
		net.addActionListener(this);
		abot.addActionListener(this);
		out.addActionListener(this);
		add();
		addKeyListener(this);
		new Thread(this).start();
		resmart();
	}
	private void resmart(){
		start.repaint();
		net.repaint();
		abot.repaint();
		out.repaint();
		text.repaint();
		start.requestFocus();
		net.requestFocus();
		abot.requestFocus();
		out.requestFocus();
		text.requestFocus();
	}
	private Image obtImagen(String img){
		Toolkit tk=Toolkit.getDefaultToolkit();
		return tk.getImage(getClass().getResource(img));
	}
	public void terminate(){
		initiated=false;
		status=3;
	}
	private void demo(){
		start();
		demo=true;
		initiated=true;
		snakeArray[0].setPc(true);
		snakeArray[1].setPc(true);
		quitar();
		requestFocus();
		status=2;
	}
	private void start(){
		snakeArray[0]=new SnakeX(50,300,0,Color.BLUE,this,1);
		snakeArray[1]=new SnakeX(550,300,2,Color.RED,this,2);
		snakeArray[0].setOtro(snakeArray[1]);
		snakeArray[1].setOtro(snakeArray[0]);
		demo=false;
	}
	private void add(){
		add(start);
		add(net);
		add(abot);
		add(out);
		add(text);
		counter=0;
	}
	private void quitar(){
		remove(start);
		remove(net);
		remove(abot);
		remove(out);
		remove(text);
	}
	public void smart(){
		for(int i=0;i<12;i++){
			for(int j=0;j<12;j++){
				bufer.drawImage(grass,i*50,j*50,this);
			}
		}
		if(status==0){
			//MENU PRINCIPAL
			drawHome();
		}
		else if(status==1){
			//abot
			drawHome();
			bufer.setColor(Color.BLACK);
			bufer.setFont(new Font("",Font.PLAIN,12));
			bufer.drawString("Design and Programming:",180,270);
			bufer.drawString("Prashanth",180,290);
			bufer.drawString("Prachu all rights reserved",180,340);
			bufer.drawString("left data",180,360);
		}
		else if(status==2){
			//JUGANDO
			snakeArray[0].smart(bufer);
			snakeArray[1].smart(bufer);
		}
		else if(status==3){
			//TERMINADO
			bufer.setFont(new Font("",Font.BOLD,20));
			snakeArray[0].smart(bufer);
			snakeArray[1].smart(bufer);
			bufer.setColor(new Color(0x80,0x80,0x80,180));
			bufer.fillRect(130,250,360,170);
			if(tu.enemy.dead){
				bufer.setColor(tu.enemy.color);
				bufer.drawString(tu.enemy.firstName+" Lost!",150,300);
			}
			else{
				bufer.setColor(tu.color);
				bufer.drawString(tu.firstName+" Lost!",150,300);
			}
			bufer.drawString("Press ESC to quit...",150,350);
			bufer.drawString("Press ENTER to restart...",150,400);
		}
		background.setColor(Color.BLACK);
		background.fillRect(0,0,getWidth(),getHeight());
		background.drawImage(ibufer,ex,ey,this);
		gr.drawImage(ibackground,0,0,this);
		if(cont<10){
			cont++;
			resmart();
		}
	}
	private void drawHome(){
		bufer.setColor(new Color(0x80,0x80,0x80,120));
		bufer.fillRect(150,150,300,300);
		bufer.drawImage(head,150,150,this);
	}
	public void update(){
		if(status==0){
			counter++;
			if(counter==500){
				counter=0;
				demo();
			}
		}
		else if(status==2){
			if(!demo){
				if(left){
					if(tu.dir!=0)tu.dir=2;
				}
				else if(right){
					if(tu.dir!=2)tu.dir=0;
				}
				else if(up){
					if(tu.dir!=1)tu.dir=3;
				}
				else if(down){
					if(tu.dir!=3)tu.dir=1;
				}
			}
			if(esc){
				add();
				status=0;
			}
			if(initiated){
				snakeArray[0].update();
				snakeArray[1].update();
			}
			if(snakeArray[0].dead||snakeArray[1].dead)terminate();
		}
		if(status==3){
			if(esc||demo){
				add();
				status=0;
			}
			if(enter)
				aPlayer();
		}
	}
	public void run(){
		long tiempo,temp,max=40;
		while(true){
			tiempo=System.currentTimeMillis();
			update();
			smart();
			temp=System.currentTimeMillis()-tiempo;
			tiempo=max-temp;
			try{
				//System.out.println(tiempo);
				if(tiempo>0)Thread.sleep(tiempo);
			}catch(Exception e){}
		}
	}
	public void keyReleased(KeyEvent evt){
		int key=evt.getKeyCode();
		if(key==KeyEvent.VK_LEFT)left=false;
		if(key==KeyEvent.VK_RIGHT)right=false;
		if(key==KeyEvent.VK_UP)up=false;
		if(key==KeyEvent.VK_DOWN)down=false;
		if(key==KeyEvent.VK_ESCAPE)esc=false;
		if(key==KeyEvent.VK_ENTER)enter=false;
	}
	public void keyPressed(KeyEvent evt){
		if(status==2)initiated=true;
		int key=evt.getKeyCode();
		if(key==KeyEvent.VK_LEFT)left=true;
		if(key==KeyEvent.VK_RIGHT)right=true;
		if(key==KeyEvent.VK_UP)up=true;
		if(key==KeyEvent.VK_DOWN)down=true;
		if(key==KeyEvent.VK_ESCAPE)esc=true;
		if(key==KeyEvent.VK_ENTER)enter=true;
	}
	public void keyTyped(KeyEvent evt){}
	public void aPlayer(){
		firstName=text.getText();
			if(firstName.length()==0)firstName="Desconocido";
			aPlayer=true;
			start();
			tu=snakeArray[0];
			tu.setPc(false);
			tu.enemy.setPc(true);
			tu.firstName=firstName;
			tu.enemy.firstName="PC";
			quitar();
			requestFocus();
			status=2;
	}
	public void actionPerformed(ActionEvent evt){
		Object src=evt.getSource();
		if(src==start){
			aPlayer();
		}
		else if(src==net){
			aPlayer=false;
		}
		else if(src==abot){
			quitar();
			add(out);
			status=1;
		}
		else if(src==out){
			if(status==0){
				System.exit(0);
			}
			else if(status==1){
				remove(out);
				add();
				status=0;
			}
		}
	}
	public static void main(String arg[]){
		new Snakes();
	}
}
