public class gameEngine extends Thread
{
	public int counter=1;
	public int counter1=1;
	public int sleepTime=30;	// how long we pause the thread of the robot
	public gameWorld world;		// the reference to the world GameWorld.

	//constructor
	public gameEngine(gameWorld myApplet)
	{
		world=myApplet;
	}


	public void run()
	{
		while(world.player[0].isAlive==true || world.player[1].isAlive==true)
		{
			if(counter<6)
			{counter++;}
			else
			{counter=1;}

			if(counter1<3000)
			{counter1++;}
			else
			{counter1=1;}

			// move everything
			world.moveStuff();

			// check for intersections
			world.checkStuff();

			// paint everything
			world.repaint();

			//sleep to pause the thread
			try
			{
				Thread.sleep(sleepTime);			// this sets the thread to sleep
			}
			catch (InterruptedException e)
			{}
		}
	}//run
}//class