// This is a generic bot class that moves around the screen
import java.awt.*;
import java.lang.Math.*;

public class enemy
{

	//variable declaration

	public int xpos;			// xposition
	public int ypos;			// yposition

	public int xposOld,yposOld;

	public int dx=1;
	public int dy=1;
	public int speed = 3;

	boolean isAlive;		// A boolean to hold if the sprite is alive or not.

	public int height = 50;
	public int width = 50;

	public int hitCount = 0;

	public Rectangle rec;

	public enemy(int x, int y)
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

		/*if(xpos<=0)
		{dx=-dx;}
		if(ypos<=0)
		{dy=-dy;}
		if(xpos>=700)		// this sets bounce if xpos> then the width of the applet
		{dx=-dx;}
		if(ypos>=500)
		{dy=-dy;}
*/
		rec= new Rectangle(xpos,ypos,width,height);	// makes a rectangle around xpos,ypos each time move is called

	}
}