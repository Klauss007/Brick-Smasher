package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	private static final Graphics2D Graphics2D = null;
	private boolean play = false;
	private int score = 0;
	
	private int totalbrick = 21;
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY = 350;
	private int balldirX = -1;
	private int balldirY = -2;
	
	private MapGenerator map;
	
	public GamePlay() {
		map = new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer =  new Timer(delay,this);
		timer.start();
	}
	
	public void paint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//drawing bricks
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//Score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590,30);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//Ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalbrick <= 0) {
			play = false;
			balldirX = 0;
			balldirY = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("YOU WON, score:"+score, 190, 300);
		}
		
		if(ballposX > 570) {
			play = false;
			balldirX = 0;
			balldirY = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("GAME OVER , score:"+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press enter to restart ", 230, 350);
			
		}
		
		g.dispose(); 
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				balldirY = -balldirY;
			}
			
			A: 	for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j] > 0) {
						int brickX = j* map.brickWidth+80;
						int brickY = i*map.brickHeight+50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect  = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballrect  = new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickrect = rect;
						
						if(ballrect.intersects(brickrect)) {
							map.setBrickValue(0, i , j);
							totalbrick--;
							score += 5;
							// for left and right	intersection of ball and brick;
							if(ballposX + 19 <= brickrect.x || ballposX +1 >= brickrect.x +brickrect.width) {
								balldirX = -balldirX;
							}
							else {
								balldirY = -balldirY;
							}
							break A;
						}
						
					}
						
				}
			}
			ballposX += balldirX;
			ballposY += balldirY;
			if(ballposX < 0)
			{
				balldirX = -balldirX;
			}
			if(ballposY < 0)
			{
				balldirY = -balldirY;
			}
			if(ballposX > 670)
			{
				balldirX = -balldirX;
			}
			
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if(playerX >= 600) {
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)	
		{
			if(playerX < 10) {
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}	
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = 120;
				ballposY = 250;
				balldirY = -2;
				balldirX = -1;
				playerX = 310;
				score = 0;
				totalbrick = 21;
				map = new MapGenerator(3,7);
				
				repaint();
			}
		}
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
