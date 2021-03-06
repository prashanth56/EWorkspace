package aSnakesGame; 
import java.awt.Graphics2D;
import java.awt.Color;
public class SnakeX{
	Color color;
	int dir,Parts[][],yo,px,py,uarea;
	String firstName;
	SnakeX enemy;
	boolean dead,pc,change,separate;
	final static int w=10;
	SnakeX(int x,int y,int d,Color c,Snakes s,int yoo){
		Parts=new int[600/w][600/w];
		dir=d;
		color=c;
		dead=false;
		yo=yoo;
		Parts[x/w][y/w]=yo;
		px=x;
		py=y;
	}
	public void setPc(boolean b){
		pc=b;
	}
	public void setOtro(SnakeX enem){
		enemy=enem;
	}
	public void update(){
		if(pc)move();
		if(dir==0)px+=w;
		else if(dir==1)py+=w;
		else if(dir==2)px-=w;
		else if(dir==3)py-=w;
		if(colision(px,py)){
			dead=true;
			return;
		}
		Parts[px/w][py/w]=yo;
	}
	public boolean colision(int tx,int ty){
		if(tx<0||ty<0||tx>600-w||ty>600-w)return true;
		return Parts[tx/w][ty/w]!=0||enemy.Parts[tx/w][ty/w]!=0;
	}
	protected void move(){
		int ndir=direction();
		int tx=px,ty=py;
		if(!separate)
			separate=verification();
		if(dir==0)tx+=w;
		else if(dir==1)ty+=w;
		else if(dir==2)tx-=w;
		else if(dir==3)ty-=w;
		if(colision(tx,ty)||(Math.random()*100>90&&!separate)||uarea-10>area(tx,ty)||change)dir=ndir!=-1?ndir:dir;
		change=false;
	}
	public boolean verification(){
		int matrix[][]=getMatrix();
		matrix[px/w][py/w]=0;
		return verification(matrix,px,py,enemy.px,enemy.py);
	}
	public boolean verification(int matrix[][],int ox,int oy,int dx,int dy){
		if(ox==dx&&oy==dy)return false;
		if(areaColision(ox,oy,matrix))return true;
		matrix[ox/w][oy/w]=yo;
		return verification(matrix,ox+w,oy,dx,dy)&&verification(matrix,ox-w,oy,dx,dy)&&verification(matrix,ox,oy+w,dx,dy)&&verification(matrix,ox,oy-w,dx,dy);
	}
	public int direction(){
		int areae=areaE(-1,-1,getMatrix(),enemy);
		int lista[]=new int[4],val[]=new int[4],newValue[]=new int[4],act2=0,act=0,ndir=-1;
		if(!colision(px+w,py)){
			lista[act]=0;
			act++;
		}
		if(!colision(px,py+w)){
			lista[act]=1;
			act++;
		}
		if(!colision(px-w,py)){
			lista[act]=2;
			act++;
		}
		if(!colision(px,py-w)){
			lista[act]=3;
			act++;
		}
		int tx,ty,vx,vy,cuenta;
		for(int i=0;i<act;i++){
			tx=px;
			ty=py;
			vx=vy=0;
			cuenta=0;
			if(lista[i]==0)vx=w;
			else if(lista[i]==1)vy=w;
			else if(lista[i]==2)vx=-w;
			else if(lista[i]==3)vy=-w;
			tx+=vx;
			ty+=vy;
			val[i]=area(tx,ty);
			for(int ti=tx/w-1;ti<=tx/w+1;ti++)
				for(int tj=ty/w-1;tj<=ty/w+1;tj++)
					if(!colision(tx,ty))cuenta++;
			val[i]+=cuenta;
			int matrixT[][]=getMatrix();
			int tmArea;
			while(!colision(tx,ty)){
				matrixT[tx/w][ty/w]=yo;
				tx+=vx;
				ty+=vy;
			}
			try{
				matrixT[tx/w][ty/w]=yo;
			}catch(Exception ex){}
			tmArea=areaE(-1,-1,matrixT,enemy);
			if(tmArea<areae/2){
				val[i]+=50;
				change=true;
			}
			
			if(cuenta<2)if(lista[i]==dir)change=true;
		}
		int high=-1;
		for(int i=0;i<act;i++){
			if(act==0){
				high=val[i];
				newValue[act2]=lista[i];
				continue;
			}
			if(val[i]>high){
				act2=0;
				newValue[act2]=lista[i];
				high=val[i];
			}
			else if(val[i]==high){
				act2++;
				newValue[act2]=lista[i];
			}
		}
		if(act>0){
			uarea=high;
			ndir=newValue[(int)(Math.random()*(act2+1))];
		}
		return ndir;
	}
	public void smart(Graphics2D g){
		for(int i=0;i<Parts.length;i++)
			for(int j=0;j<Parts[i].length;j++)
				if(Parts[i][j]==yo){
					g.setColor(i*w==px&&j*w==py?Color.GREEN:color);
					g.fillRect(i*w,j*w,w,w);
				}
	}
	protected int length(){
		int tam=0;
		for(int i=0;i<Parts.length;i++)
			for(int j=0;j<Parts[i].length;j++)
				if(Parts[i][j]==yo)tam++;
		return tam;
	}
	protected int[][] getMatrix(){
		int matrix[][]=new int[Parts.length][Parts[0].length],i,j;
		for(i=0;i<Parts.length;i++)
			for(j=0;j<Parts[i].length;j++)
				matrix[i][j]=Parts[i][j]!=0?Parts[i][j]:enemy.Parts[i][j];
		return matrix;
	}
	protected int areaE(int tx,int ty,int matrix[][],SnakeX dest){
		if(dest.px<0||dest.py<0||dest.px>600-w||dest.py>600-w)return 0;
		matrix[dest.px/w][dest.py/w]=0;
		if(tx!=-1&&ty!=-1)matrix[tx/w][ty/w]=yo;
		return area(dest.px,dest.py,matrix,0);
	}
	protected int area(int tx,int ty){
		int matrix[][]=getMatrix();
		return area(tx,ty,matrix,0);
	}
	protected int area(int tx,int ty,int matrix[][],int area){
		if(areaColision(tx,ty,matrix))return area;
		matrix[tx/w][ty/w]=yo;
		area++;
		area=area(tx+w,ty,matrix,area);
		area=area(tx-w,ty,matrix,area);
		area=area(tx,ty+w,matrix,area);
		area=area(tx,ty-w,matrix,area);
		return area;
	}
	protected boolean areaColision(int tx,int ty,int matrix[][]){
		if(tx<0||ty<0||tx>600-w||ty>600-w)return true;
		return matrix[tx/w][ty/w]!=0;
	}
}
