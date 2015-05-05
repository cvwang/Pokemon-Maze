import java.awt.*;

public class player
{

	//variable declaration

	public int xpos;			// xposition
	public int ypos;			// yposition

	public int xposOld,yposOld;

	public int dx=7;		// dx and dy control the speed of the hero
	public int dy=7;

	boolean isAlive;		// A boolean to hold if the sprite is alive or not.

	public int height = 50;
	public int width = 50;

	public boolean up,down,left,right;

	public int health = 100;

	public Rectangle rec;

	public player(int x, int y)
	{
		xpos = x;
		ypos = y;
		isAlive = true;			//makes the sprite "alive"
		up = false;
		down = false;
		left = false;
		right = false;
		rec = new Rectangle(xpos,ypos,width,height);
	}

	public void move()
	{

		xposOld = xpos;
		yposOld = ypos;

		//xpos=xpos+dx;
		//ypos=ypos+dy;

		if(up==true)
		{ypos-=dy;}
		else
		{ypos-=0;}
		if(down==true)
		{ypos+=dy;}
		else
		{ypos+=0;}
		if(left==true)
		{xpos-=dy;}
		else
		{xpos-=0;}
		if(right==true)
		{xpos+=dx;}
		else
		{xpos+=0;}

		if(xpos<0)
		{xpos=0;}
		if(ypos<=0)
		{ypos=0;}
		if(xpos>550)		// this sets bounce if xpos> then the width of the applet
		{xpos=549;}
		if(ypos>550)
		{ypos=549;}

		rec= new Rectangle(xpos,ypos,width,height);	// makes a rectangle around xpos,ypos each time move is called

	}


}