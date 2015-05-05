import java.awt.*;

public class bullet
{

	//variable declaration

	public int xpos;			// xposition
	public int ypos;			// yposition

	public int dxOld,dyOld;

	public int dx=5;		// dx and dy control the speed of the hero
	public int dy=5;
	public int speed=10;

	boolean isAlive;		// A boolean to hold if the sprite is alive or not.
	boolean dead;

	public int height = 25;
	public int width = 25;

	public boolean up,down,left,right;

	public int hitCount = 0;

	public Rectangle rec;

	public bullet(int x, int y)
	{
		xpos = x;
		ypos = y;
		isAlive = false;			//makes the sprite "alive"
		dead = false;
		up = false;
		down = false;
		left = false;
		right = false;
		rec = new Rectangle(xpos,ypos,width,height);
	}

	public void move()
	{

		dxOld = dx;
		dyOld = dy;

		xpos=xpos+dx;
		ypos=ypos+dy;

		if(xpos<0)
		{isAlive=false;}
		if(ypos<=0)
		{isAlive=false;}
		if(xpos>575)		// this sets bounce if xpos> then the width of the applet
		{isAlive=false;}
		if(ypos>600)
		{isAlive=false;}

		rec= new Rectangle(xpos,ypos,width,height);	// makes a rectangle around xpos,ypos each time move is called
	}

}