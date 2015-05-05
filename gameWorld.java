//Charles Wang
//14 March 2011

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.lang.Math.*;

public class gameWorld extends Applet implements KeyListener, MouseListener, MouseMotionListener
{
	//*****VARIABLE DECLARATION*****
	//Variables
	public int[][] blockXpos,blockYpos;
	public int[][] blockNumber;
	public int score;
	public int balls;
	public double divisor;
	public int bulletArray=10;
	public int enemyArray=5;

	//Images
	public Image[] enemyPic;
	public Image[] ash;
	public Image[] blockImage;
	public Image gameover;
	public Image 	pokeballPic,pokeball1,
					ashdownleft,ashdownright,
					ashupleft,ashupright,
					ashleftleft,ashleftright,
					ashrightleft,ashrightright;

	//class array
	public block[][] block;
	public player[] player;
	public bullet[] pokeball;
	public enemy2[] enemy;

	//audio
	public AudioClip shot;

	//color
	public Color healthColor;

	//thread
	public gameEngine masterThread;

	//String
	public String keyin;

	//Cursor
	//Cursor myCursor;

	//Buffer Graphics
	Graphics bufferGraphics;
	Image offscreen;

	public int counter=0;
	public int[] rememberx;
	public int[] remembery;
	public boolean enemyFound=false;

	//*****INIT METHOD*****
	public void init()
	{
		addKeyListener( this );
		addMouseListener(this);
		addMouseMotionListener(this);

		player = new player[2];
		ash = new Image[2];
		pokeball = new bullet[bulletArray];
		enemy = new enemy2[enemyArray];
		enemyPic = new Image[enemyArray];

		block = new block[6][6];
		blockImage = new Image[16];
		blockXpos = new int[6][6];
		blockYpos = new int[6][6];
		blockNumber = new int[6][6];
		rememberx = new int [100];
		remembery = new int [100];

		score = 0;
		balls = 0;

		for(int i=0;i<2;i++)
		{
			player[i] = new player(8,500+i*50);
		}

		for(int i=0;i<bulletArray;i++)
		{
			pokeball[i] = new bullet(0,0);
			balls++;
		}

		for(int i=0;i<enemyArray;i++)
		{
			enemy[i] = new enemy2((int)(5*Math.random())*100+10,(int)(5*Math.random())*100+10);
		}

		for(int i=0;i<100;i++)
		{
			rememberx[i]=-1;
			remembery[i]=-1;
		}

		pokeballPic = getImage(getDocumentBase(),"Files/pokeball.png");
		pokeball1 = getImage(getDocumentBase(),"Files/pokeball1.gif");
		ashdownleft = getImage(getDocumentBase(),"Files/ash_down_left.png");
		ashdownright = getImage(getDocumentBase(),"Files/ash_down_right.png");
		ashupleft = getImage(getDocumentBase(),"Files/ash_up_left.png");
		ashupright = getImage(getDocumentBase(),"Files/ash_up_right.png");
		ashleftleft = getImage(getDocumentBase(),"Files/ash_left_left.png");
		ashleftright = getImage(getDocumentBase(),"Files/ash_left_right.png");
		ashrightleft = getImage(getDocumentBase(),"Files/ash_right_left.png");
		ashrightright = getImage(getDocumentBase(),"Files/ash_right_right.png");
		gameover = getImage(getDocumentBase(),"Files/gameover.jpg");

		blockImage[0] = getImage(getDocumentBase(),"Blocks/Block0.png");
		blockImage[1] = getImage(getDocumentBase(),"Blocks/Block1.png");
		blockImage[2] = getImage(getDocumentBase(),"Blocks/Block2.png");
		blockImage[3] = getImage(getDocumentBase(),"Blocks/Block3.png");
		blockImage[4] = getImage(getDocumentBase(),"Blocks/Block4.png");
		blockImage[5] = getImage(getDocumentBase(),"Blocks/Block5.png");
		blockImage[6] = getImage(getDocumentBase(),"Blocks/Block6.png");
		blockImage[7] = getImage(getDocumentBase(),"Blocks/Block7.png");
		blockImage[8] = getImage(getDocumentBase(),"Blocks/Block8.png");
		blockImage[9] = getImage(getDocumentBase(),"Blocks/Block9.png");
		blockImage[10] = getImage(getDocumentBase(),"Blocks/Block10.png");
		blockImage[11] = getImage(getDocumentBase(),"Blocks/Block11.png");
		blockImage[12] = getImage(getDocumentBase(),"Blocks/Block12.png");
		blockImage[13] = getImage(getDocumentBase(),"Blocks/Block13.png");
		blockImage[14] = getImage(getDocumentBase(),"Blocks/Block14.png");
		blockImage[15] = getImage(getDocumentBase(),"Blocks/Block15.png");

		for(int x=0;x<6;x++)
		{
			for(int y=0;y<6;y++)
			{
				block[x][y] = new block(100*x,100*y);
			}

		}

		//*****CREATE MAP*****
		//row0
		blockNumber[0][0] = 14;
		blockNumber[1][0] = 7;
		blockNumber[2][0] = 14;
		blockNumber[3][0] = 4;
		blockNumber[4][0] = 9;
		blockNumber[5][0] = 12;
		//row1
		blockNumber[0][1] = 14;
		blockNumber[1][1] = 2;
		blockNumber[2][1] = 9;
		blockNumber[3][1] = 3;
		blockNumber[4][1] = 14;
		blockNumber[5][1] = 7;
		//row2
		blockNumber[0][2] = 8;
		blockNumber[1][2] = 4;
		blockNumber[2][2] = 9;
		blockNumber[3][2] = 2;
		blockNumber[4][2] = 7;
		blockNumber[5][2] = 10;
		//row3
		blockNumber[0][3] = 11;
		blockNumber[1][3] = 10;
		blockNumber[2][3] = 14;
		blockNumber[3][3] = 7;
		blockNumber[4][3] = 10;
		blockNumber[5][3] = 10;
		//row4
		blockNumber[0][4] = 14;
		blockNumber[1][4] = 2;
		blockNumber[2][4] = 7;
		blockNumber[3][4] = 1;
		blockNumber[4][4] = 2;
		blockNumber[5][4] = 3;
		//row5
		blockNumber[0][5] = 14;
		blockNumber[1][5] = 9;
		blockNumber[2][5] = 6;
		blockNumber[3][5] = 11;
		blockNumber[4][5] = 15;
		blockNumber[5][5] = 11;

		for(int x=0;x<6;x++)
		{
			for(int y=0;y<6;y++)
			{
				block[x][y].block = blockImage[blockNumber[x][y]];
				block[x][y].blockNumber = blockNumber[x][y];
			}
		}

		for(int i=0;i<2;i++)
		{
			ash[i] = ashrightleft;
		}

		{
			int enemyCounter=0;
			for(int i=0;i<enemyArray;i++)
			{
				if(enemyCounter==0)
				{enemyPic[i] = getImage(getDocumentBase(),"Files/rayquaza.png");}
				if(enemyCounter==1)
				{enemyPic[i] = getImage(getDocumentBase(),"Files/bulbasaur.png");}
				if(enemyCounter==2)
				{enemyPic[i] = getImage(getDocumentBase(),"Files/charmander.png");}
				if(enemyCounter==3)
				{enemyPic[i] = getImage(getDocumentBase(),"Files/squirtle.png");}
				if(enemyCounter==4)
				{enemyPic[i] = getImage(getDocumentBase(),"Files/pikachu.png");}
				if(enemyCounter==5)
				{enemyPic[i] = getImage(getDocumentBase(),"Files/charizard.png");}

				if(enemyCounter<5)
				{
					enemyCounter++;
				}
				else
				{
					enemyCounter=0;
				}
			}
		}

		shot = getAudioClip(getDocumentBase(),"gun.wave");
		shot.play();

		//keyin = "S";
		healthColor = Color.black;

		offscreen = createImage(800,600);
		bufferGraphics = offscreen.getGraphics();

		/*Point hotSpot = new Point(50,50);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("crosshairs.gif");
		Cursor cursor = toolkit.createCustomCursor(image, hotSpot, "Boom");
		setCursor(cursor);*/

		masterThread = new gameEngine(this);
		masterThread.start();
	}

	//*****MOVE METHOD*****
	public void moveStuff()
	{
		for(int i=0;i<2;i++)
		{
			player[i].move();
		}

		getDirection();
		for(int i=0;i<enemyArray;i++)
		{
			if(enemy[i].isAlive==true)
			{
				enemy[i].move();
			}
		}

		for(int i=0;i<bulletArray;i++)
		{
			if(pokeball[i].isAlive==true)
			{
				pokeball[i].move();
			}
		}
	}

	//*****CHECK METHOD*****
	public void checkStuff()
	{
		for(int i=0;i<bulletArray;i++)
		{
			for(int x=0;x<enemyArray;x++)
			{
				if(pokeball[i].rec.intersects(enemy[x].rec)==true
				&& pokeball[i].isAlive==true && enemy[x].isAlive==true)
				{
					pokeball[i].isAlive=false;
					enemy[x].isAlive=false;
					score++;
				}
			}
		}

		//flashing health bar
		for(int i=0;i<enemyArray;i++)//enemy array
		{
			for(int x=0;x<2;x++)//player array
			{
				if(enemy[i].rec.intersects(player[x].rec)==true
				&& player[x].isAlive==true && enemy[i].isAlive==true)
				{
					player[x].health--;
					if(masterThread.counter==1||masterThread.counter==2)
					{
						healthColor=Color.black;
						bufferGraphics.setColor(healthColor);
					}
					if(masterThread.counter==3)
					{
						healthColor=Color.darkGray;
						bufferGraphics.setColor(healthColor);
					}
					if(masterThread.counter==4)
					{
						healthColor=Color.gray;
						bufferGraphics.setColor(healthColor);
					}
					if(masterThread.counter==5)
					{
						healthColor=Color.lightGray;
						bufferGraphics.setColor(healthColor);
					}
					if(masterThread.counter==6)
					{
						healthColor=Color.white;
						bufferGraphics.setColor(healthColor);
					}
				}
				else
				{
					healthColor=Color.black;
				}
			}
		}

		//shooting other player
		for(int i=0;i<bulletArray;i++)
		{
			if(pokeball[i].rec.intersects(player[1].rec) && pokeball[i].isAlive==true && player[1].isAlive==true)
			{
				player[1].health-=5;
				pokeball[i].isAlive=false;
			}
		}

		/*
		//enemy follow
		for(int i=0;i<1;i++)
		{
			//if(enemy[i].isAlive==true)
			//{
				enemy[i].dx = player[0].xpos+26-enemy[i].xpos;
				enemy[i].dy = player[0].ypos+28-enemy[i].ypos;
				divisor = Math.sqrt(Math.pow(enemy[i].dx,2)+Math.pow(enemy[i].dy,2))/enemy[i].speed;
				//System.out.println("divisor = "+divisor);
				if((int)divisor>0)
				{
					enemy[i].dx = (int)(enemy[i].dx/divisor);
					enemy[i].dy = (int)(enemy[i].dy/divisor);
				}
				else
				{
					enemy[i].dx = enemy[i].dx/1;
					enemy[i].dy = enemy[i].dy/1;
				}
			//}
		}
		*/

		for(int i=0;i<2;i++)
		{
			if(player[i].health<=0)
			{
				player[i].isAlive=false;
			}
		}

		if(masterThread.counter<=3)
		{
			if(player[0].up==true)
			{ash[0]=ashupleft;}

			if(player[0].down==true)
			{ash[0]=ashdownleft;}

			if(player[0].left==true)
			{ash[0]=ashleftleft;}

			if(player[0].right==true)
			{ash[0]=ashrightleft;}

			if(player[1].up==true)
			{ash[1]=ashupleft;}

			if(player[1].down==true)
			{ash[1]=ashdownleft;}

			if(player[1].left==true)
			{ash[1]=ashleftleft;}

			if(player[1].right==true)
			{ash[1]=ashrightleft;}
		}
		else
		{
			if(player[0].up==true)
			{ash[0]=ashupright;}

			if(player[0].down==true)
			{ash[0]=ashdownright;}

			if(player[0].left==true)
			{ash[0]=ashleftright;}

			if(player[0].right==true)
			{ash[0]=ashrightright;}

			if(player[1].up==true)
			{ash[1]=ashupright;}

			if(player[1].down==true)
			{ash[1]=ashdownright;}

			if(player[1].left==true)
			{ash[1]=ashleftright;}

			if(player[1].right==true)
			{ash[1]=ashrightright;}
		}

		//This is going to be the intersection thing for the blocks
		for(int x=0;x<6;x++)
		{
			for(int y=0;y<6;y++)
			{
				//checks for player intersect
				for(int i=0;i<2;i++)
				{
					if(player[i].isAlive==true && block[x][y].rec.intersects(player[i].rec))
					{
						if(block[x][y].blockNumber == 0)
						{

						}
						if(block[x][y].blockNumber == 1)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
						}
						if(block[x][y].blockNumber == 2)
						{
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 3)
						{
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
						}
						if(block[x][y].blockNumber == 4)
						{
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 5)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 6)
						{
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}						}
						if(block[x][y].blockNumber == 7)
						{
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
						}
						if(block[x][y].blockNumber == 8)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 9)
						{
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 10)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
						}
						if(block[x][y].blockNumber == 11)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}						}
						if(block[x][y].blockNumber == 12)
						{
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 13)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
						}
						if(block[x][y].blockNumber == 14)
						{
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}
						}
						if(block[x][y].blockNumber == 15)
						{
							//top wall
							if(player[i].xpos<=block[x][y].xpos+player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+player[i].dx;
							}
							//left wall
							if(player[i].ypos<=block[x][y].ypos+player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+player[i].dy;
							}
							//right wall
							if(player[i].xpos+player[i].width>=block[x][y].xpos+block[x][y].width-player[i].dx)
							{
								player[i].xpos=block[x][y].xpos+block[x][y].width-player[i].width-player[i].dx;
							}
							//bottom wall
							if(player[i].ypos+player[i].height>=block[x][y].ypos+block[x][y].height-player[i].dy)
							{
								player[i].ypos=block[x][y].ypos+block[x][y].height-player[i].height-player[i].dy;
							}//if
						}//if
					}//if
				}//for i

				//pokeball intersect
				for(int p=0;p<bulletArray;p++)
				{
					if(pokeball[p].isAlive==true && block[x][y].rec.intersects(pokeball[p].rec))
					{
						if(block[x][y].blockNumber == 0)
						{

						}
						if(block[x][y].blockNumber == 1)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 2)
						{
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 3)
						{
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 4)
						{
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 5)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 6)
						{
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 7)
						{
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 8)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 9)
						{
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 10)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 11)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 12)
						{
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 13)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 14)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}
						if(block[x][y].blockNumber == 15)
						{
							if(pokeball[p].xpos<=block[x][y].xpos-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos<=block[x][y].ypos-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].xpos+pokeball[p].width>=block[x][y].xpos+block[x][y].width-pokeball[p].dx)
							{
								pokeball[p].isAlive=false;
							}
							if(pokeball[p].ypos+pokeball[p].height>=block[x][y].ypos+block[x][y].height-pokeball[p].dy)
							{
								pokeball[p].isAlive=false;
							}
						}//if
					}//if pokeball is alive
				}//for p

				//for enemy intersect
				for(int i=0;i<enemyArray;i++)
				{
					if(enemy[i].isAlive==true && block[x][y].rec.intersects(enemy[i].rec))
					{
						if(block[x][y].blockNumber == 0)
						{

						}
						if(block[x][y].blockNumber == 1)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
						}
						if(block[x][y].blockNumber == 2)
						{
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 3)
						{
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
						}
						if(block[x][y].blockNumber == 4)
						{
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 5)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 6)
						{
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 7)
						{
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
						}
						if(block[x][y].blockNumber == 8)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 9)
						{
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 10)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
						}
						if(block[x][y].blockNumber == 11)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 12)
						{
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 13)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
						}
						if(block[x][y].blockNumber == 14)
						{
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}
						}
						if(block[x][y].blockNumber == 15)
						{
							//top wall
							if(enemy[i].xpos<=block[x][y].xpos-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos-enemy[i].dx;
							}
							//left wall
							if(enemy[i].ypos<=block[x][y].ypos-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos-enemy[i].dy;
							}
							//right wall
							if(enemy[i].xpos+enemy[i].width>=block[x][y].xpos+block[x][y].width-enemy[i].dx)
							{
								enemy[i].xpos=block[x][y].xpos+block[x][y].width-enemy[i].width-enemy[i].dx;
							}
							//bottom wall
							if(enemy[i].ypos+enemy[i].height>=block[x][y].ypos+block[x][y].height-enemy[i].dy)
							{
								enemy[i].ypos=block[x][y].ypos+block[x][y].height-enemy[i].height-enemy[i].dy;
							}//if
						}//if
					}//if enemy is Alive intersect
				}//for i
			}//for y
		}//for x

	}//checkStuff

	//*****PAINT METHOD*****
	public void paint(Graphics g)
	{
		bufferGraphics.clearRect(0,0,800,600);
		//bufferGraphics.drawImage(pokeball1,0,0,800,600,this);



		//here i draw out the map
		//considering doing map randomization later
		for(int x=0;x<6;x++)
		{
			for(int y=0;y<6;y++)
			{
				bufferGraphics.drawImage(block[x][y].block,block[x][y].xpos,block[x][y].ypos,block[x][y].width,block[x][y].height,this);
			}
		}



		bufferGraphics.setColor(Color.black);
		bufferGraphics.drawString("You've caught "+score+"/"+enemyArray+" Pokemon!",610,45);
		bufferGraphics.drawString("You have "+balls+" Pokeballs left;",610,30);

		//player 1 health
		bufferGraphics.setColor(Color.lightGray);
		bufferGraphics.fillRect(658,2,104,16);
		bufferGraphics.setColor(Color.black);
		bufferGraphics.drawString("Health : ",610,15);
		bufferGraphics.setColor(healthColor);
		bufferGraphics.fillRect(660,4,player[0].health,12);
		//player 2 health
		bufferGraphics.setColor(Color.lightGray);
		bufferGraphics.fillRect(658,62,104,16);
		bufferGraphics.setColor(Color.black);
		bufferGraphics.drawString("Health : ",610,75);
		bufferGraphics.setColor(healthColor);
		bufferGraphics.fillRect(660,64,player[1].health,12);

		for(int i=0;i<2;i++)
		{
			//if(player[i].isAlive==true)
			{
				bufferGraphics.drawImage(ash[i],player[i].xpos,player[i].ypos,player[i].width,player[i].height,this);
			}
		}
		for(int i=0;i<enemyArray;i++)
		{
			if(enemy[i].isAlive==true)
			{
				bufferGraphics.drawImage(enemyPic[i],enemy[i].xpos,enemy[i].ypos,enemy[i].width,enemy[i].height,this);
			}
		}

		for(int i=0;i<bulletArray;i++)
		{
			if(pokeball[i].isAlive==true)
			{
				bufferGraphics.drawImage(pokeballPic,pokeball[i].xpos,pokeball[i].ypos,pokeball[i].width,pokeball[i].height,this);
			}
		}

		if(player[0].isAlive==false && player[1].isAlive==false)
		{
			bufferGraphics.drawImage(gameover,250,150,300,300,this);
			bufferGraphics.setColor(Color.white);
			bufferGraphics.fillRect(300,410,200,20);
			bufferGraphics.setColor(Color.black);
			bufferGraphics.drawString("You caught "+score+" Pokemon!",330,420);
		}
		g.drawImage(offscreen,0,0,this);
	}

	public void update(Graphics g)
	{
		paint(g);
	}

//implementing mouselistener

	public void mousePressed(MouseEvent e)
	{
		//*****MOUSE AIMING*****
		getDev(e.getX(),e.getY());

		for(int i=0;i<bulletArray;i++)
		{
			if(pokeball[i].isAlive==false)// && balls>0)
			{
				pokeball[i].isAlive=true;
				pokeball[i].xpos = player[0].xpos+14;
				pokeball[i].ypos = player[0].ypos+17;
				i=10;
			}
		}

		if(balls>0)
		{balls--;}
		else
		{balls=0;}

		shot.play();
	}

	public void mouseReleased(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{
				keyin = "";
	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void mouseClicked(MouseEvent e)
	{

	}

    public void mouseMoved(MouseEvent e)
    {

    }

    public void mouseDragged(MouseEvent e)
    {

    }

//implement keylistener

	public void keyPressed( KeyEvent event )
	{
		//String keyin; 	// a variable defined in a method is not public!
		keyin = ""+event.getKeyText( event.getKeyCode());

		if (keyin.equals("W"))
		{
			player[0].up = true;
			player[0].down = false;
			player[0].left = false;
			player[0].right = false;
		}

		if (keyin.equals("S"))
		{
			player[0].up = false;
			player[0].down = true;
			player[0].left = false;
			player[0].right = false;
		}

		if (keyin.equals("A"))
		{
			player[0].up = false;
			player[0].down = false;
			player[0].left = true;
			player[0].right = false;
		}

		if (keyin.equals("D"))
		{
			player[0].up = false;
			player[0].down = false;
			player[0].left = false;
			player[0].right = true;
		}

		if(keyin.equals("Shift"))
		{
			player[0].dx=10;
			player[0].dy=10;
		}

		if (keyin.equals("Up"))
		{
			player[1].up = true;
			player[1].down = false;
			player[1].left = false;
			player[1].right = false;
		}

		if (keyin.equals("Down"))
		{
			player[1].up = false;
			player[1].down = true;
			player[1].left = false;
			player[1].right = false;
		}

		if (keyin.equals("Left"))
		{
			player[1].up = false;
			player[1].down = false;
			player[1].left = true;
			player[1].right = false;
		}

		if (keyin.equals("Right"))
		{
			player[1].up = false;
			player[1].down = false;
			player[1].left = false;
			player[1].right = true;
		}

		if(keyin.equals("Shift"))
		{
			player[1].dx=10;
			player[1].dy=10;
		}

		System.out.println(keyin);
	}

	public void keyReleased( KeyEvent event )
	{
		//String keyin;	// a variable defined in a method is not public!
		keyin = ""+event.getKeyText( event.getKeyCode());

		if (keyin.equals("W"))
		{
			player[0].up = false;
		}

		if (keyin.equals("S"))
		{
			player[0].down = false;
		}

		if (keyin.equals("A"))
		{
			player[0].left = false;
		}

		if (keyin.equals("D"))
		{
			player[0].right = false;
		}
		if(keyin.equals("Shift"))
		{
			player[0].dx=7;
			player[0].dy=7;
		}

		if (keyin.equals("Up"))
		{
			player[1].up = false;
		}

		if (keyin.equals("Down"))
		{
			player[1].down = false;
		}

		if (keyin.equals("Left"))
		{
			player[1].left = false;
		}

		if (keyin.equals("Right"))
		{
			player[1].right = false;
		}
		if(keyin.equals("Shift"))
		{
			player[1].dx=7;
			player[1].dy=7;
		}

		keyin = "";
	}

	public void keyTyped( KeyEvent event )
	{
		//does nothing but required to be defined.
	}

	public void getDev(int x,int y)
	{
		//*****AIMING USING PYTHAGOREAN THEOREM*****
		for(int i=0;i<bulletArray;i++)
		{
			if(pokeball[i].isAlive==false)
			{
				pokeball[i].dx = x-player[0].xpos-26;
				pokeball[i].dy = y-player[0].ypos-28;
				divisor = Math.sqrt(Math.pow(pokeball[i].dx,2)+Math.pow(pokeball[i].dy,2))/pokeball[i].speed;
				//System.out.println("divisor = "+divisor);
				pokeball[i].dx = (int)(pokeball[i].dx/divisor);
				pokeball[i].dy = (int)(pokeball[i].dy/divisor);
			}
		}
	}

	public boolean lookAhead(int x,int y,int xp,int yp)
	{
		//String direction="right";
		boolean up=false;
		boolean down=false;
		boolean left=false;
		boolean right=false;

		rememberx[counter]=x;
		remembery[counter]=y;
		counter++;

		System.out.println("level "+counter);
		System.out.println("Searching "+x+","+y);
		enemyFound=enemyFound(x,y);
		System.out.println("enemy found is "+enemyFound);
		if(enemyFound)
		{
			counter--;
			return(true);
		}
		if(!enemyFound)
		{
			if(block[x][y].blockNumber == 0)
			{
				left=true;
				right=true;
				up=true;
				down=true;
			}
			if(block[x][y].blockNumber == 1)
			{
				right=true;
				up=true;
				down=true;
			}
			if(block[x][y].blockNumber == 2)
			{
				left=true;
				right=true;
				up=true;
			}
			if(block[x][y].blockNumber == 3)
			{
				left=true;
				up=true;
				down=true;
			}
			if(block[x][y].blockNumber == 4)
			{
				left=true;
				right=true;
				down=true;
			}
			if(block[x][y].blockNumber == 5)
			{
				left=true;
				down=true;
			}
			if(block[x][y].blockNumber == 6)
			{
				left=true;
				up=true;
			}
			if(block[x][y].blockNumber == 7)
			{
				left=true;
				down=true;
			}
			if(block[x][y].blockNumber == 8)
			{
				right=true;
				down=true;
			}
			if(block[x][y].blockNumber == 9)
			{
				left=true;
				right=true;
			}
			if(block[x][y].blockNumber == 10)
			{
				up=true;
				down=true;
			}
			if(block[x][y].blockNumber == 11)
			{
				up=true;
			}
			if(block[x][y].blockNumber == 12)
			{
				left=true;
			}
			if(block[x][y].blockNumber == 13)
			{
				down=true;
			}
			if(block[x][y].blockNumber == 14)
			{
				right=true;
			}

			if(left && !(xp==x-1 && yp==y))
			{
				enemyFound=lookAhead(x-1,y,x,y);
				if(enemyFound)
				{
					counter--;
					return(true);
				}
			}
			if(right && !(xp==x+1 && yp==y))
			{
				enemyFound=lookAhead(x+1,y,x,y);
				if(enemyFound)
				{
					counter--;
					return(true);
				}
			}
			if(up && !(xp==x && yp==y-1))
			{
				enemyFound=lookAhead(x,y-1,x,y);
				if(enemyFound)
				{
					counter--;
					return(true);
				}
			}
			if(down && !(xp==x && yp==y+1))
			{
				enemyFound=lookAhead(x,y+1,x,y);
				if(enemyFound)
				{
					counter--;
					return(true);
				}
				//enemyFound=false;
			}
		}
		counter--;
		//enemyFound=false;
		return(false);
	}

	public void giveDirection(int z)
	{
		enemy[z].dx=3;
		enemy[z].dy=3;
		for(int i=0;i<100;i++)
		{
			System.out.println("trail "+i+" is "+rememberx[i]+","+remembery[i]);
		}
		if(rememberx[1]-rememberx[0]==1)
		{
			change("right",z);
		}
		if(rememberx[0]-rememberx[1]==1)
		{
			change("left",z);
		}
		if(remembery[1]-remembery[0]==1)
		{
			change("down",z);
		}
		if(remembery[0]-remembery[1]==1)
		{
			change("up",z);
		}
		if(player[0].rec.intersects(block[rememberx[0]][remembery[0]].rec))
		{
			//enemy follow
			if(enemy[z].isAlive==true)
			{
				enemy[z].dx = player[0].xpos+26-enemy[z].xpos;
				enemy[z].dy = player[0].ypos+28-enemy[z].ypos;
				divisor = Math.sqrt(Math.pow(enemy[z].dx,2)+Math.pow(enemy[z].dy,2))/enemy[z].speed;
				//System.out.println("divisor = "+divisor);
				if((int)divisor>0)
				{
					enemy[z].dx = (int)(enemy[z].dx/divisor);
					enemy[z].dy = (int)(enemy[z].dy/divisor);
				}
				else
				{
					enemy[z].dx = enemy[z].dx/1;
					enemy[z].dy = enemy[z].dy/1;
				}
			}
		}
	}

	public boolean enemyFound(int x,int y)
	{
		if(player[0].rec.intersects(block[x][y].rec))
		{
			System.out.println("enemy found at "+x+","+y);
			return(true);
			//enemyFound=true;
		}
		return(false);
	}

	public void getDirection()
	{
		for(int i=0;i<enemyArray;i++)
		{
			for(int x=0;x<6;x++)
			{
				for(int y=0;y<6;y++)
				{
					if(enemy[i].rec.intersects(block[x][y].rec))
					{
						if( enemy[i].xpos>block[x][y].xpos &&
							enemy[i].ypos>block[x][y].ypos &&
							enemy[i].xpos<block[x][y].xpos+block[x][y].width-enemy[i].width &&
							enemy[i].ypos<block[x][y].ypos+block[x][y].height-enemy[i].height)
						{
									lookAhead(x,y,-1,-1);
									giveDirection(i);
						}//if
					}//if
				}//for i
			}//for y
		}//for x

	}//getDirection

	public void change(String x,int i)
	{
		if(x.equals("right"))
		{
			enemy[i].up=false;
			enemy[i].down=false;
			enemy[i].left=false;
			enemy[i].right=true;
		}
		if(x.equals("left"))
		{
			enemy[i].up=false;
			enemy[i].down=false;
			enemy[i].left=true;
			enemy[i].right=false;
		}
		if(x.equals("down"))
		{
			enemy[i].up=false;
			enemy[i].down=true;
			enemy[i].left=false;
			enemy[i].right=false;
		}
		if(x.equals("up"))
		{
			enemy[i].up=true;
			enemy[i].down=false;
			enemy[i].left=false;
			enemy[i].right=false;
		}
	}
}