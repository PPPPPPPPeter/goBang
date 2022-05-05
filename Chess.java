package goBang_804_V2;

import java.awt.Color;

import java.awt.Graphics;
/**
 * 
 * @author peterwong
 *白黑优先，以及下棋顺序都放在这个类中！
 */

public class Chess {
	
	private int x, y;
	//X, Y;可直接调用以中心点绘图
	private int X, Y;
	//boardX, boardY是当以棋盘为标准时的棋子中心点坐标，
	//不可调用作为绘制。
	private int boardX, boardY;
	private Graphics g;
	private Color c;
	private String btnCommand;
	
	public Chess( Graphics g, String btnCommand, int x, int y) {
		
		this.g = g;
		this.btnCommand = btnCommand;
		this.x = x;
		this.y = y;
		
	}
	public Chess( Graphics g, int boardX, int boardY) {
		this.g = g;
		this.boardX = boardX;
		this.boardY = boardY;
	}
	
	public void drawChess(Color c) {
		this.c = c;
		if (this.x >= GoBang_database.START_X && this.x<=GoBang_database.START_X
				+GoBang_database.BOARD_WIDTH
				&& this.y>=GoBang_database.START_Y && 
				this.y<=GoBang_database.BOARD_HEIGHT+GoBang_database.START_Y) {
			
			//this.count_control = 1;
			
			if ((this.x-80)%60>30) {
				this.x = ((this.x-80)/60+1)*60+80;
			}
			else 
				this.x =  ((this.x-80)/60)*60+80;
			
			if ((this.y-80)%60>30) {
				this.y = ((this.y-80)/60+1)*60+80;
			}
			else
				this.y = ((this.y-80)/60)*60+80;
			
			this.g.setColor(this.c);
			this.g.fillOval(this.x-20 , this.y-20, 40, 40);
			//System.out.println("2-----"+this.count_control);
			//this.c = Color.BLACK;
			this.X = this.x-20;
			this.Y = this.y-20;
			this.boardX = (this.x -80)/60;
			this.boardY = (this.y -80)/60;
			System.out.println("The chess is in "+"("+this.boardX+" ,"+this.boardY+")");
			//System.out.println("The chessboard y is "+this.boardY);
		}
		
	}
	
	public void drawAIChess(Color c) {
		this.c = c;
//		this.x = (this.aiArrayX*60)+80;
//		this.y = (this.aiArrayY*60)+80;
		this.g.setColor(this.c);
		//System.out.println("3-------- ");
		this.g.fillOval((this.boardX*60+80)-20 ,(this.boardY*60+80)-20, 40, 40);
		//System.out.println("4-------- ");
		this.X = (this.boardX*60+80)-20;
		this.Y = (this.boardY*60+80)-20;
		System.out.println("The chess by AI is in "+"("+this.boardX+" ,"+this.boardY+")");
		//System.out.println("The chessboard y from AI is "+this.aiArrayY);
	}
	
	public int getbigX() {
		return this.X;
	}
	
	public int getbigY() {
		return this.Y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Color getColor() {
		return this.c;
	}
	
	public int getBoardX() {
		return this.boardX;
	}
	
	public int getBoardY() {
		return this.boardY;
	}
	
	public String getbtnCommand() {
		return this.btnCommand;
	}
	
	public Graphics getG() {
		return this.g;
	}
}
