// This is a generic bot class that moves around the screen
import java.awt.*;
import java.lang.Math.*;

public class wild_pokemon
{

	//variable declaration

	public int xpos;			// xposition
	public int ypos;			// yposition

	public int xposOld,yposOld;

	public int dx=3+(int)(Math.random()*7);		// dx and dy control the speed of the hero
	public int dy=3+(int)(Math.random()*7);
	public int speed = 3;
	public double divisor;

	boolean isAlive;		// A boolean to hold if the sprite is alive or not.

	public int height = 75;
	public int width = 75;

	public int hitCount = 0;
	public gameWorld gw;

	public Rectangle rec;

	public wild_pokemon(int x, int y)
	{
		xpos = x;
		ypos = y;
		isAlive=true;			//makes the sprite "alive"
		rec = new Rectangle(xpos,ypos,width,height);
	}

	public void bounce()
	{

		xposOld = xpos;
		yposOld = ypos;

		xpos=xpos+dx;
		ypos=ypos+dy;

		if(xpos<=0)
		{dx=-dx;}
		if(ypos<=0)
		{dy=-dy;}
		if(xpos>=700)		// this sets bounce if xpos> then the width of the applet
		{dx=-dx;}
		if(ypos>=500)
		{dy=-dy;}

		rec= new Rectangle(xpos,ypos,width,height);	// makes a rectangle around xpos,ypos each time move is called

	}

	/*public void follow()
	{
		dx = gw.player1.xpos+26-xpos;
		dy = gw.player1.ypos+28-ypos;
		divisor = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2))/speed;
		System.out.println("divisor = "+divisor);
		if((int)divisor>0)
		{
			dx = (int)(dx/divisor);
			dy = (int)(dy/divisor);
		}
		else
		{
			dx = dx/1;
			dy = dy/1;
		}

		xpos=xpos+dx;
		ypos=ypos+dy;
	}*/


}