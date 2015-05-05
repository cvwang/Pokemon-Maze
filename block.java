import java.awt.*;

public class block
{

	//variable declaration

	public int xpos;			// xposition
	public int ypos;			// yposition

	public Image block;

	public int height = 100;
	public int width = 100;

	public int blockNumber;

	public Rectangle rec;

	public block(int x, int y)
	{
		xpos = x;
		ypos = y;

		rec = new Rectangle(xpos,ypos,width,height);
	}

}