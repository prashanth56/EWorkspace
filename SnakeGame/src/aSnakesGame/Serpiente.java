package aSnakesGame; 
import java.awt.Graphics2D;
import java.awt.Color;
public class Serpiente{
	Part head;
	Color color;
	int dir;
	String firstName;
	Serpiente enemy;
	Snakes snakes;
	boolean dead,pc;
	final static int w=10;
	Serpiente(int x,int y,int d,Color c,Snakes s){
		head=new Part(x,y,null,null);
		dir=d;
		color=c;
		snakes=s;
		dead=false;
	}
	public void setPc(boolean b){
		pc=b;
	}
	public void setOtro(Serpiente enem){
		enemy=enem;
	}
	public void update(){
		if(pc)mover();
		Part nueva=null;
		if(dir==1)nueva=new Part(head.x+w,head.y,null,head);
		else if(dir==2)nueva=new Part(head.x,head.y+w,null,head);
		else if(dir==3)nueva=new Part(head.x-w,head.y,null,head);
		else if(dir==4)nueva=new Part(head.x,head.y-w,null,head);
		head.anterior=nueva;
		head=nueva;
		if(colision(head)){
			head=head.siguiente;
			snakes.terminate();
			dead=true;
		}
	}
	private void mover(){
		int tdir,num;
		Part nueva=null;
		if((int)(Math.random()*100)>90)if((tdir=getDir())!=-1)dir=tdir;
		else{
			num=0;
			for(int i=head.y-w;i<=head.y+w;i+=w){
				for(int j=head.x-w;j<=head.x+w;j+=w){
					nueva=new Part(j,i,null,null);
					if(!colision(nueva))num++;
				}
			}
			if(num<=1)if((tdir=getDir())!=-1)dir=tdir;
		}
		if(dir==1)nueva=new Part(head.x+w,head.y,null,head);
		else if(dir==2)nueva=new Part(head.x,head.y+w,null,head);
		else if(dir==3)nueva=new Part(head.x-w,head.y,null,head);
		else if(dir==4)nueva=new Part(head.x,head.y-w,null,head);
		if(colision(nueva)){
			tdir=getDir();
			if(tdir!=-1)dir=tdir;
		}
	}
	private int getDir(){
		int temp=-1,area;
		Part nueva=null;
		Part parts[][];
		int direcciones[]=new int[4],cont=0;
		for(int i=1;i<=4;i++){
			if(i==1)nueva=new Part(head.x+w,head.y,null,head);
			else if(i==2)nueva=new Part(head.x,head.y+w,null,head);
			else if(i==3)nueva=new Part(head.x-w,head.y,null,head);
			else if(i==4)nueva=new Part(head.x,head.y-w,null,head);
			if(!colision(nueva)){
				temp=i;
				direcciones[cont]=temp;
				cont++;
			}
		}
		if(temp!=-1){
			int mayor=0,dist,indice=0,n,vx=0,vy=0;
			Part punto=null,punto2=null;
			for(int i=0;i<cont;i++){
				dist=0;
				if(direcciones[i]==1){
					vx=w;
					vy=0;
				}
				else if(direcciones[i]==2){
					vx=0;
					vy=w;
				}
				else if(direcciones[i]==3){
					vx=-w;
					vy=0;
				}
				else if(direcciones[i]==4){
					vx=0;
					vy=-w;
				}
				punto=new Part(head.x,head.y,null,null);
				while(true){
					punto=new Part(punto.x+vx,punto.y+vy,null,null);
					if(colision(punto))break;
					dist++;
					punto2=punto;
				}
				n=0;
				punto=punto2;
				for(int m=punto.y-w;m<=punto.y+w;m+=w){
					for(int mn=punto.x-w;mn<=punto.x+w;mn+=w){
						punto2=new Part(mn,m,null,null);
						if(!colision(punto2))n++;
					}
				}
				if(n<3)dist=0;
				else dist+=n*2;
				/*
				punto=new Part(head.x+vx,head.y+vy,null,null);
				parts=new Part[600/w][600/w];
				area=area(punto,parts,0);
				dist+=area;
				System.out.println("Direccion ["+i+"] = "+direcciones[i]+" de "+cont+"  "+dist+" > "+mayor+" Valor = "+dist+" ( "+punto.x+","+punto.y+" )");
				*/
				if(dist>mayor){
					mayor=dist;
					indice=i;
				}
				else if(dist==mayor){
					if((int)(Math.random()*100)>50)indice=i;
				}
			}
			temp=direcciones[indice];
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
			if(enemy.colisionSerpiente(p))return true;
			else if(head.siguiente!=null){
				return head.siguiente.colision(p);
			}
			else return false;
		}
	}
	public boolean colisionSerpiente(Part c){
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
		Part anterior,siguiente;
		int x,y;
		Part(int px,int py,Part ant,Part sig){
			x=px;
			y=py;
			anterior=ant;
			siguiente=sig;
		}
		public void aArray(Part Parts[],int n){
			Parts[n]=this;
			if(siguiente!=null)siguiente.aArray(Parts,n+1);
		}
		public int length(){
			if(siguiente==null)return 1;
			else return 1+siguiente.length();
		}
		public boolean colision(Part p){
			if(p.x==x&&p.y==y)return true;
			else{
				if(siguiente==null)return false;
				else return siguiente.colision(p);
			}
		}
		public boolean equals(Part p){
			return p.x==x&&p.y==y;
		}
	}
}
