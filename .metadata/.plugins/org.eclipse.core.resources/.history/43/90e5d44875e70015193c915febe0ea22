package aSnakesGame; 
import java.awt.Graphics2D;
import java.awt.Color;
public class Snake{
	Part head;
	Color color;
	int dir;
	String firstName;
	Snake enemy;
	Snakes snakes;
	boolean dead,pc;
	final static int w=10;
	Snake(int x,int y,int d,Color c,Snakes s){
		head=new Part(x,y,null,null);
		dir=d;
		color=c;
		snakes=s;
		dead=false;
	}
	public void setPc(boolean b){
		pc=b;
	}
	public void setOtro(Snake enem){
		enemy=enem;
	}
	public void update(){
		if(pc)mover();
		Part newValue=null;
		if(dir==1)newValue=new Part(head.x+w,head.y,null,head);
		else if(dir==2)newValue=new Part(head.x,head.y+w,null,head);
		else if(dir==3)newValue=new Part(head.x-w,head.y,null,head);
		else if(dir==4)newValue=new Part(head.x,head.y-w,null,head);
		head.anterior=newValue;
		head=newValue;
		if(colision(head)){
			head=head.follow;
			snakes.terminate();
			dead=true;
		}
	}
	private void mover(){
		int tdir,num;
		Part newValue=null;
		if((int)(Math.random()*100)>90)if((tdir=getDir())!=-1)dir=tdir;
		else{
			num=0;
			for(int i=head.y-w;i<=head.y+w;i+=w){
				for(int j=head.x-w;j<=head.x+w;j+=w){
					newValue=new Part(j,i,null,null);
					if(!colision(newValue))num++;
				}
			}
			if(num<=1)if((tdir=getDir())!=-1)dir=tdir;
		}
		if(dir==1)newValue=new Part(head.x+w,head.y,null,head);
		else if(dir==2)newValue=new Part(head.x,head.y+w,null,head);
		else if(dir==3)newValue=new Part(head.x-w,head.y,null,head);
		else if(dir==4)newValue=new Part(head.x,head.y-w,null,head);
		if(colision(newValue)){
			tdir=getDir();
			if(tdir!=-1)dir=tdir;
		}
	}
	private int getDir(){
		int temp=-1,area;
		Part newValue=null;
		Part parts[][];
		int direction[]=new int[4],cont=0;
		for(int i=1;i<=4;i++){
			if(i==1)newValue=new Part(head.x+w,head.y,null,head);
			else if(i==2)newValue=new Part(head.x,head.y+w,null,head);
			else if(i==3)newValue=new Part(head.x-w,head.y,null,head);
			else if(i==4)newValue=new Part(head.x,head.y-w,null,head);
			if(!colision(newValue)){
				temp=i;
				direction[cont]=temp;
				cont++;
			}
		}
		if(temp!=-1){
			int high=0,dist,index=0,n,vx=0,vy=0;
			Part point=null,punto2=null;
			for(int i=0;i<cont;i++){
				dist=0;
				if(direction[i]==1){
					vx=w;
					vy=0;
				}
				else if(direction[i]==2){
					vx=0;
					vy=w;
				}
				else if(direction[i]==3){
					vx=-w;
					vy=0;
				}
				else if(direction[i]==4){
					vx=0;
					vy=-w;
				}
				point=new Part(head.x,head.y,null,null);
				while(true){
					point=new Part(point.x+vx,point.y+vy,null,null);
					if(colision(point))break;
					dist++;
					punto2=point;
				}
				n=0;
				point=punto2;
				for(int m=point.y-w;m<=point.y+w;m+=w){
					for(int mn=point.x-w;mn<=point.x+w;mn+=w){
						punto2=new Part(mn,m,null,null);
						if(!colision(punto2))n++;
					}
				}
				if(n<3)dist=0;
				else dist+=n*2;
				/*
				point=new Part(head.x+vx,head.y+vy,null,null);
				parts=new Part[600/w][600/w];
				area=area(point,parts,0);
				dist+=area;
				System.out.println("Direction ["+i+"] = "+direction[i]+" de "+cont+"  "+dist+" > "+high+" Valor = "+dist+" ( "+point.x+","+point.y+" )");
				*/
				if(dist>high){
					high=dist;
					index=i;
				}
				else if(dist==high){
					if((int)(Math.random()*100)>50)index=i;
				}
			}
			temp=direction[index];
		}
		return temp;
	}
	private int area(Part p,Part parts[][],int tope){
		parts[p.x/w][p.y/w]=p;
		tope++;
		Part p2;
		p2=new Part(p.x+w,p.y,null,null);
		if(!colision(p2)&&parts[p2.x/w][p2.y/w]==null)tope=area(p2,parts,tope);
		p2=new Part(p.x,p.y+w,null,null);
		if(!colision(p2)&&parts[p2.x/w][p2.y/w]==null)tope=area(p2,parts,tope);
		p2=new Part(p.x-w,p.y,null,null);
		if(!colision(p2)&&parts[p2.x/w][p2.y/w]==null)tope=area(p2,parts,tope);
		p2=new Part(p.x,p.y-w,null,null);
		if(!colision(p2)&&parts[p2.x/w][p2.y/w]==null)tope=area(p2,parts,tope);
		return tope;
	}
	private boolean colision(Part p){
		if(p.x<0)return true;
		else if(p.x>=600)return true;
		else if(p.y<0)return true;
		else if(p.y>=600)return true;
		else{
			if(enemy.colisionSnake(p))return true;
			else if(head.follow!=null){
				return head.follow.colision(p);
			}
			else return false;
		}
	}
	public boolean colisionSnake(Part c){
		if(head.colision(c))return true;
		else return false;
	}
	public void smart(Graphics2D g){
		Part Parts[]=aArray();
		for(int i=0;i<Parts.length;i++){
			if(i!=0)g.setColor(color);
			else g.setColor(color.GREEN);
			g.fillRect(Parts[i].x,Parts[i].y,w,w);
			g.setColor(Color.BLACK);
			g.drawRect(Parts[i].x,Parts[i].y,w,w);
		}
	}
	private int length(){
		return head.length();
	}
	private Part[] aArray(){
		Part Parts[]=new Part[length()];
		head.aArray(Parts,0);
		return Parts;
	}
	public class Part{
		Part anterior,follow;
		int x,y;
		Part(int px,int py,Part ant,Part sig){
			x=px;
			y=py;
			anterior=ant;
			follow=sig;
		}
		public void aArray(Part Parts[],int n){
			Parts[n]=this;
			if(follow!=null)follow.aArray(Parts,n+1);
		}
		public int length(){
			if(follow==null)return 1;
			else return 1+follow.length();
		}
		public boolean colision(Part p){
			if(p.x==x&&p.y==y)return true;
			else{
				if(follow==null)return false;
				else return follow.colision(p);
			}
		}
		public boolean equals(Part p){
			return p.x==x&&p.y==y;
		}
	}
}
