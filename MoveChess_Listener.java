package goBang_804_V2;

/*
 * bugs records:
 * 1. 在ai落子后，玩家依然可以在原先位置落子。
 * 2. 偶有ai重复落子的现象。
 */
/*
 * Flags:
 * 1. 修复所有bugs
 * 2. 单独将权值写成一个class或者method，并且尝试使用深度学习。
 */


import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author peterwong
 *
 */
public class MoveChess_Listener extends JFrame implements ActionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Image bgimg = new ImageIcon("src/goBang_804/img/img.png").getImage();
	private int x, y;
	Graphics g;
	private Chess draw, aidraw;
	private String btnCommand;
	private int count_control;//控制下黑棋还是白棋
	//private int black_regret_count, white_regret_count;//记录黑白棋悔棋次数
	protected JButton btnWhite, btnBlack, btnregret,btnAi;
	protected JFrame startGameWin;
	private Chess[][] saveChessBoard = new Chess[16][16];
	private int [][] abstractChessBoard = new int[16][16];
	private int [][] aiAbstractChessBoard = new int[16][16];
	protected ArrayList<Chess> chesseslists = new ArrayList<>(10);
	private ArrayList<Integer> aiChesseslists = new ArrayList<>();
	private HashMap<String,Integer> codemap = new HashMap<> ();
	private int boardx,boardy;
	private int whiteNo, blackNo, total;
	private int aibtnNo=0;
	private int wbbtnNo=0;
	
	//private Regret_repaint regretpaint; 
	//private int regretcount=0;
	private Chess theLast;
	
	public MoveChess_Listener(Graphics g) {
		this.g = g;
		//	this.count_control = count_control;
		//this.btnCommand = btnCommand;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.btnCommand = e.getActionCommand();
		//this.regret_count_control = 0;
		if ("Black First!".equals(this.btnCommand)) {
			this.count_control = 0;
			this.btnBlack.setEnabled(false);
			this.btnWhite.setEnabled(false);
			this.wbbtnNo++;
			
		}
		if ("White First!".equals(this.btnCommand)) {
			this.count_control = 1;
			this.btnBlack.setEnabled(false);
			this.btnWhite.setEnabled(false);
			this.wbbtnNo++;
		}
		if ("AI(慎点！)".equals(this.btnCommand)) {
			this.btnBlack.setEnabled(false);
			this.btnWhite.setEnabled(false);
			this.btnAi.setEnabled(false);
			//死一连：
			this.codemap.put("012", 100);
			this.codemap.put("021", 100);
//			this.codemap.put("120", 100);
//			this.codemap.put("210", 100);
//			//死二连：
//			this.codemap.put("1220", 500);
//			this.codemap.put("2110", 500);
			this.codemap.put("0112", 500);
			this.codemap.put("0221", 500);
//			//死三连：
//			this.codemap.put("12220", 2000);
//			this.codemap.put("21110", 2000);
			this.codemap.put("01112", 2000);
			this.codemap.put("02221", 2000);
//			//死四连：
//			this.codemap.put("122220", 100000);
//			this.codemap.put("211110", 100000);
			this.codemap.put("011112", 100000);
			this.codemap.put("022221", 100000);
			//活一连：
			this.codemap.put("010", 200);
			this.codemap.put("020", 200);
			//活二连：
			this.codemap.put("0220", 13000);
			this.codemap.put("0110", 13000);
			//活三连：
			this.codemap.put("01110", 20000);
			this.codemap.put("02220", 20000);
			//活四连：
			this.codemap.put("011110", 200000);
			this.codemap.put("022220", 200000);
			this.aibtnNo++;
			if (this.wbbtnNo!=0) {
				this.startAi(this.abstractChessBoard);
			}
			
		}
		if ("Regret!".equals(this.btnCommand)) {
			
			this.theLast = this.chesseslists.get(this.chesseslists.size()-1);
			
			for (int i =0; i<this.saveChessBoard.length; i++) {
				for (int j= 0; j<this.saveChessBoard.length; j++) {
					if (this.theLast.equals(this.saveChessBoard[i][j])) {
						if (this.saveChessBoard[i][j].getColor()==Color.BLACK) {
							this.count_control = 0;
						}
						else
							this.count_control = 1;
						this.chesseslists.remove(this.theLast);
						this.saveChessBoard[i][j]=null;
						this.abstractChessBoard[i][j]=0;
						this.aiAbstractChessBoard[i][j]=0;
						this.aiChesseslists.clear();
						
					}
				}
			}
			regret();
			if (this.aibtnNo!=0) {
				this.btnCommand = "AI(慎点！)";
			}
			
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//regret();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.x = e.getX();
		this.y = e.getY();
		this.draw = new Chess(this.g, this.btnCommand,
				this.x, this.y);
		/*
		 * java类的在另一个类中被实例化后会运行类中的constructor，但是不会
		 * 自动运行其中的方法：所以无法调用。
		 */
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
		this.boardx = (this.x -80)/60;
		this.boardy = (this.y -80)/60;
		this.drawChesses(this.boardy, this.boardx);
		
		
		
	}
	/**
	 * 
	 * @param boardy
	 * @param boardx
	 */
	private void drawChesses(int boardy, int boardx) {
		if (this.saveChessBoard[boardy][boardx]==null) {
			//return;
			if (this.btnCommand!=null) {
				if (this.count_control == 0) {	
					this.draw.drawChess(Color.BLACK);
					//将draw对象装入arraylist中：
					this.chesseslists.add(this.draw);
					//将draw对象装入数组中；
					this.saveChessBoard[boardy][boardx] = this.draw;
					this.abstractChessBoard[boardy][boardx] = 1;
					this.aiAbstractChessBoard[boardy][boardx] = 1;
					this.count_control++;
					
				}
				else if (this.count_control != 0) {
					this.draw.drawChess(Color.WHITE);
					//将draw对象装入数组中；
					this.chesseslists.add(this.draw);
					this.saveChessBoard[boardy][boardx] = this.draw;
					this.abstractChessBoard[boardy][boardx] = 2;
					this.aiAbstractChessBoard[boardy][boardx] = 2;
					this.count_control=0;
					
				}
			}
		}
		else 
			return;
	}
	//问题在这里！
	public void drawAiChesses(int aiArrayY, int aiArrayX) {
		//System.out.println("3-------- ");
		//问题在这里！！
		this.aidraw = new Chess(this.g, aiArrayX, aiArrayY);
		/*
		 *
		 * 
		 */
//		System.out.println("the information of saveChessBoard is "+
//		 this.saveChessBoard[aiArrayX][aiArrayY]);
//		System.out.println("the information of saveChessBoard is "+
//				 this.saveChessBoard[this.boardx][this.boardy]);
		/*
		 * 
		 */
		//if (this.saveChessBoard[aiArrayX][aiArrayY]==null) {
		if (this.btnCommand!=null) {
			if (this.count_control == 0) {
					
				this.aidraw.drawAIChess(Color.BLACK);
				this.chesseslists.add(this.aidraw);
				this.saveChessBoard[aiArrayY][aiArrayX] = this.aidraw;
				this.abstractChessBoard[aiArrayY][aiArrayX] = 1;
				this.aiAbstractChessBoard[aiArrayY][aiArrayX] = 1;
				this.count_control++;
			}
			else if (this.count_control != 0) {
					
				this.aidraw.drawAIChess(Color.WHITE);
					//System.out.println("4-------- ");
				this.chesseslists.add(this.aidraw);
				this.saveChessBoard[aiArrayY][aiArrayX] = this.aidraw;
				this.abstractChessBoard[aiArrayY][aiArrayX] = 2;
				this.aiAbstractChessBoard[aiArrayY][aiArrayX] = 2;
				this.count_control=0;
			}
		}
	//}
		else
			return;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
//		if (this.saveChessBoard[this.boardy][this.boardx]==null && this.abstractChessBoard[this.boardy][this.boardx]==0) {
//			this.judgeWin(this.boardx, this.boardy);
//			if (this.btnCommand.equals("AI(慎点！)")) {
//				this.startAi(this.abstractChessBoard);
//				this.judgeWin(this.boardx, this.boardy);
//			}
//		}
//		else
//			return;
		this.judgeWin(this.boardx, this.boardy);
		if (this.btnCommand.equals("AI(慎点！)")) {
			this.startAi(this.abstractChessBoard);
			this.judgeWin(this.boardx, this.boardy);
		}
//		/*
//		 * 
////		 */
//		for (int i=0; i<this.saveChessBoard.length; i++) {
//			for (int j=0; j<this.saveChessBoard.length; j++) {
//				System.out.print("   "+this.saveChessBoard[i][j]);
//			}
//			System.out.println();
//		}
//		/*
//		 * 
//		 */
//		for (int i=0; i<this.chesseslists.size(); i++) {
//			System.out.println(this.chesseslists.get(i));
//		}
		
		
	}

	private void startAi(int [][] abstractChessBoard) {
		this.aiChesseslists.clear();
		//空棋处生成权值：
		for(int i =0; i<this.abstractChessBoard.length; i++) {
			for (int j =0; j<this.abstractChessBoard.length; j++) {
				//这里！！！
				if (this.abstractChessBoard[i][j]!=1 &&this.abstractChessBoard[i][j]!=2 
						&& this.saveChessBoard[i][j]==null) {
					this.rightLeft(i, j);
					this.topDown(i, j);
					this.catercorneA(i, j);
					this.catercorneB(i, j);
					//shiyan:
//					this.aiChesseslists.add(this.total);
					this.total =0;
					
					
				}
			}
		}
//		this.aiChesseslists.add(this.total);
		//this.total =0;
		
		for (int i=0; i<this.aiAbstractChessBoard.length; i++) {
			for(int j=0; j<this.aiAbstractChessBoard.length; j++) {
				//System.out.print("   "+this.aiAbstractChessBoard[i][j]);
				this.aiChesseslists.add(this.aiAbstractChessBoard[i][j]);
			}
			//System.out.println();
		}
		int maxElement = Collections.max(this.aiChesseslists);
		int aiShouldx =0; 
		int aiShouldy = 0;
		//System.out.println("the max is "+maxElement);
		//落子：
		//System.out.println("1-------- ");
		for (int i=0; i<this.aiAbstractChessBoard.length; i++) {
			for(int j=0; j<this.aiAbstractChessBoard.length; j++) {
				if (this.aiAbstractChessBoard[i][j] == maxElement) {
					aiShouldx =j;
					aiShouldy =i;	
				}
			}
		}
		//System.out.println("2-------- ");
		
		//this.draw = new Chess(this.g, aiShouldy, aiShouldx);
		this.drawAiChesses(aiShouldy, aiShouldx);
		this.boardx = aiShouldx;
		this.boardy = aiShouldy;
		
		//System.out.println("5-------- ");
		
	}
	
	private void rightLeft(int y, int x) {
		//先向右：
		//System.out.println("1====");
		int X = x;
		int Y = y;
		this.total =0;
		if (X+1<=15 && (this.abstractChessBoard[Y][X+1]==1||
				this.abstractChessBoard[Y][X+1]==2)) {
			String code = "0";
			while (X+1<=15) {
				
				code = code+this.abstractChessBoard[Y][X+1];
				//System.out.println("2--"+code);
				if (this.codemap.get(code)!=null) {
					//System.out.println("3--"+code+" the value is "+this.codemap.get(code));
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				X++;
			}
			

			//System.out.println("the total value is "+this.abstractChessBoard[y][x]);
		}
		//换方向向左：
		X = x;
		Y = y;
		if (X-1>=0 && (this.abstractChessBoard[Y][X-1]==1||
				this.abstractChessBoard[Y][X-1]==2)) {
			String code = "0";
			while (X-1>=0) {
				code = code+this.abstractChessBoard[Y][X-1];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				X--;
			}
		}
	}
	
	private void topDown(int y, int x) {
		//先向上：
		int X = x;
		int Y = y;
		if (Y-1>=0 && (this.abstractChessBoard[Y-1][X]==1||
				this.abstractChessBoard[Y-1][X]==2)) {
			String code = "0";
			while (Y-1>=0) {
				code = code+this.abstractChessBoard[Y-1][X];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				Y--;
			}
		}
		//换方向向下：
		X = x;
		Y = y;
		if (Y+1<=15 && (this.abstractChessBoard[Y+1][X]==1||
				this.abstractChessBoard[Y+1][X]==2)) {
			String code ="0";
			while (Y+1<=15) {
				code = code+this.abstractChessBoard[Y+1][X];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				Y++;
			}
		}
		
	}
	
	private void catercorneA(int y, int x) {
		//向右下：
		int X = x;
		int Y = y;
		if (Y+1<=15 && X+1<=15 && (this.abstractChessBoard[Y+1][X+1]==1||
				this.abstractChessBoard[Y+1][X+1]==2)) {
			String code ="0";
			while (Y+1<=15 && X+1<=15) {
				code = code+this.abstractChessBoard[Y+1][X+1];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				Y++;
				X++;
			}
		}
		//换方向左上：
		X = x;
		Y = y;
		if (Y-1>=0 && X-1>=0 && (this.abstractChessBoard[Y-1][X-1]==1||
				this.abstractChessBoard[Y-1][X-1]==2)) {
			String code ="0";
			while (Y-1>=0 && X-1>=0) {
				code = code+this.abstractChessBoard[Y-1][X-1];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				Y--;
				X--;
			}
		}
		
	}
	
	private void catercorneB(int y, int x) {
		//向左下：
		int X = x;
		int Y = y;
		if (X-1>=0 && Y+1<=15 &&  (this.abstractChessBoard[Y+1][X-1]==1||
				this.abstractChessBoard[Y+1][X-1]==2)) {
			String code ="0";
			while (Y+1<=15 && X-1>=0) {
				code = code+this.abstractChessBoard[Y+1][X-1];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				Y++;
				X--;
			}
			
		}
		//换方向向右上：
		X = x;
		Y = y;
		if (X+1<=15 && Y-1>=0 &&  (this.abstractChessBoard[Y-1][X+1]==1||
				this.abstractChessBoard[Y-1][X+1]==2)) {
			String code ="0";
			while (X+1<=15 && Y-1>=0) {
				code = code+this.abstractChessBoard[Y-1][X+1];
				if (this.codemap.get(code)!=null) {
					this.aiAbstractChessBoard[y][x] = this.total+this.codemap.get(code);
					this.total = this.aiAbstractChessBoard[y][x];
					break;
				}
				Y--;
				X++;
			}
		}
		//this.total = 0;
		
	
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
//		// TODO Auto-generated method stub
//		this.draw = new Chess(this.g, 6, 4);
//		this.draw.drawAIChess(Color.BLACK);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	private void windowForWinner() {
		JFrame a = new JFrame();
		String winner;
		if (this.saveChessBoard[this.boardy][this.boardx].getColor()==
				Color.BLACK) {
			winner = "有人赢了？！还是黑的？";
		}
		else
			winner = "有人赢了？！还是白的？";
		a.setTitle(winner);
		a.setBounds(400, 400, 500, 500);
		a.setDefaultCloseOperation(EXIT_ON_CLOSE);
		a.setLayout(new FlowLayout());
		JButton btnClose = new JButton("Close");
		JButton btnRecord = new JButton("Watch the record");
		JButton btnagain = new JButton("Again");
		a.add(btnagain);
		a.add(btnClose);
		a.add(btnRecord);
		a.setVisible(true);
		btnagain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				a.dispose();
				startGameWin.dispose();
				new StartLoginWindow();
				
			}
			
		});
		btnRecord.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				a.dispose();
				try {
					recordPad();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
	}
	/**
	 * 
	 */
	public void regret() {
		//super.paint(g);
		g.setColor(getBackground());
		g.fillRect(60, 60, 1000, 1000);
		g.drawImage(bgimg, 80, 80, GoBang_database.BOARD_WIDTH, GoBang_database.BOARD_HEIGHT, 
				null);
		g.setColor(Color.BLACK);
		g.drawLine(80, 80, 980, 80);
		g.drawLine(980, 80, 980, 980);
		g.drawLine(980, 980, 80, 980);
		g.drawLine(80, 980, 80, 80);
		for (int i = 0; i<15; i++) {
			g.drawLine(80, 80+GoBang_database.SINGLE_WIDTH*i, 980, 
					80+GoBang_database.SINGLE_WIDTH*i);
			g.drawLine(80+GoBang_database.SINGLE_WIDTH*i, 980, 
					80+GoBang_database.SINGLE_WIDTH*i, 80);
		}
		
//		/*
//		 * 
//		 */
//		for (int i=0; i<this.saveChessBoard.length; i++) {
//			for (int j=0; j<this.saveChessBoard.length; j++) {
//				System.out.print("   "+this.saveChessBoard[i][j]);
//			}
//			System.out.println();
//		}
//		/*
//		 * 
//		 */
//		for (int i=0; i<this.chesseslists.size(); i++) {
//			System.out.println(this.chesseslists.get(i));
//		}
		
		for (int i =0; i<this.saveChessBoard.length; i++) {
			for (int j= 0; j<this.saveChessBoard.length; j++) {
				if (this.saveChessBoard[i][j]!=null) {
					g.setColor(this.saveChessBoard[i][j].getColor());
					g.fillOval(this.saveChessBoard[i][j].getbigX(),
							this.saveChessBoard[i][j].getbigY(), 40, 40);
				}
			}
		}
		
	}
	
	public void recordPad() throws InterruptedException {
		g.setColor(getBackground());
		g.fillRect(60, 60, 1000, 1000);
		g.drawImage(bgimg, 80, 80, GoBang_database.BOARD_WIDTH, GoBang_database.BOARD_HEIGHT, 
				null);
		g.setColor(Color.BLACK);
		g.drawLine(80, 80, 980, 80);
		g.drawLine(980, 80, 980, 980);
		g.drawLine(980, 980, 80, 980);
		g.drawLine(80, 980, 80, 80);
		for (int i = 0; i<15; i++) {
			g.drawLine(80, 80+GoBang_database.SINGLE_WIDTH*i, 980, 
					80+GoBang_database.SINGLE_WIDTH*i);
			g.drawLine(80+GoBang_database.SINGLE_WIDTH*i, 980, 
					80+GoBang_database.SINGLE_WIDTH*i, 80);
			Thread.sleep(160);
		}
		for (int i=0; i<this.chesseslists.size(); i++) {
			g.setColor(this.chesseslists.get(i).getColor());
			g.fillOval(this.chesseslists.get(i).getbigX(), 
					this.chesseslists.get(i).getbigY(), 40, 40);
			Thread.sleep(1000);
		}
		windowForWinner();
		
	}
	/**
	 * 
	 * @param x最后落下的棋子的棋盘横坐标
	 * @param y最后落下的棋子的棋盘纵坐标
	 */
	public void judgeWin(int x, int y) {
		//一共四个方向：
		this.blackNo=0;
		this.whiteNo=0;
		if (this.saveChessBoard[this.boardy][this.boardx]!=null&&
				this.saveChessBoard[this.boardy][this.boardx].getColor()==Color.BLACK) {
			//横向先右后左：
			for (int i=this.boardx; i<=15; i++) {
				if (this.saveChessBoard[this.boardy][i]==null||this.
						saveChessBoard[this.boardy][i].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
			}
			//换方向向左：
			for (int i=this.boardx-1; i>=0; i--) {
				if (this.saveChessBoard[this.boardy][i]==null||this.
						saveChessBoard[this.boardy][i].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
			}
			//判断：
			if (this.blackNo>=5) {
				 windowForWinner();
				 return;
			}
			this.blackNo=0;
			//竖向先下后上：
			for (int i=this.boardy; i<=15; i++) {
				if (this.saveChessBoard[i][this.boardx]==null||this.
						saveChessBoard[i][this.boardx].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
			}
			//换方向向上：
			for (int i=this.boardy-1; i>=0; i--) {
				if (this.saveChessBoard[i][this.boardx]==null||this.
						saveChessBoard[i][this.boardx].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
			}
			//判断：
			if (this.blackNo>=5) {
				 windowForWinner();
				 return;
			}
			this.blackNo=0;
			//左上到右下从目标先去左上：
			while (x>=0 && x<=15 && y>=0 && y<=15) {
				if (this.saveChessBoard[y][x]==null||this.
						saveChessBoard[y][x].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
				x--;
				y--;	
			}
			//换方向从目标去右下：
			x= this.boardx;
			y= this.boardy;
			while (x>=0 && x<=15 && y>=0 && y<=15) {
//				if (x+1>15||y+1>15) {
//					System.err.println("--------!");
//				}
				if (x+1>15||y+1>15||this.saveChessBoard[y+1][x+1]==null||this.
						saveChessBoard[y+1][x+1].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
				x++;
				y++;
			}
			//判断：
			if (this.blackNo>=5) {
				 windowForWinner();
				 return;
			}
			this.blackNo=0;
			//右上到左下从目标先去右上：
			while (x>=0 && x<=15 && y>=0 && y<=15) {
				if (this.saveChessBoard[y][x]==null||this.
						saveChessBoard[y][x].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
				x++;
				y--;
			}
			//换方向从目标去左下：
			x= this.boardx;
			y= this.boardy;
			while (x>=0 && x<=15 && y>=0 && y<=15) {
//				if (x-1<0||y+1>15) {
//					System.err.println("--------!");
//				}
				if (y+1>15||x-1<0||this.saveChessBoard[y+1][x-1]==null||this.
						saveChessBoard[y+1][x-1].getColor()!=Color.BLACK) {
					break;
				}
				this.blackNo++;
				x--;
				y++;
			}
			//判断：
			if (this.blackNo>=5) {
				 windowForWinner();
				 return;
			}
			this.blackNo=0;
			
		}
		/**
		 * 
		 */
		if (this.saveChessBoard[this.boardy][this.boardx]!=null&&
				this.saveChessBoard[this.boardy][this.boardx].getColor()==Color.WHITE) {
			//横向先右后左：
			for (int i=this.boardx; i<=15; i++) {
				if (this.saveChessBoard[this.boardy][i]==null||this.
						saveChessBoard[this.boardy][i].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
			}
			//换方向向左：
			for (int i=this.boardx-1; i>=0; i--) {
				if (this.saveChessBoard[this.boardy][i]==null||this.
						saveChessBoard[this.boardy][i].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
			}
			//判断：
			if (this.whiteNo>=5) {
				 windowForWinner();
				 return;
			}
			this.whiteNo=0;
			//竖向先下后上：
			for (int i=this.boardy; i<=15; i++) {
				if (this.saveChessBoard[i][this.boardx]==null||this.
						saveChessBoard[i][this.boardx].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
			}
			//换方向向上：
			for (int i=this.boardy-1; i>=0; i--) {
				if (this.saveChessBoard[i][this.boardx]==null||this.
						saveChessBoard[i][this.boardx].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
			}
			//判断：
			if (this.whiteNo>=5) {
				 windowForWinner();
				 return;
			}
			this.whiteNo=0;
			//左上到右下从目标先去左上：
			while (x>=0 && x<=15 && y>=0 && y<=15) {
				if (this.saveChessBoard[y][x]==null||this.
						saveChessBoard[y][x].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
				x--;
				y--;	
			}
			//换方向从目标去右下：
			x= this.boardx;
			y= this.boardy;
			while (x>=0 && x<=15 && y>=0 && y<=15) {
//				
//				if (x+1>15||y+1>15) {
//					System.err.println("--------!");
//				}
				if (x+1>15||y+1>15||this.saveChessBoard[y+1][x+1]==null||this.
						saveChessBoard[y+1][x+1].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
				x++;
				y++;
			}
			//判断：
			if (this.whiteNo>=5) {
				 windowForWinner();
				 return;
			}
			this.whiteNo=0;
			//右上到左下从目标先去右上：
			while (x>=0 && x<=15 && y>=0 && y<=15) {
				if (this.saveChessBoard[y][x]==null||this.
						saveChessBoard[y][x].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
				x++;
				y--;
			}
			//换方向从目标去左下：
			x= this.boardx;
			y= this.boardy;
			while (x>=0 && x<=15 && y>=0 && y<=15) {
//				if (x-1<0||y+1>15) {
//					System.err.println("--------!");
//				}
				if (this.saveChessBoard[y+1][x-1]==null||this.
						saveChessBoard[y+1][x-1].getColor()!=Color.WHITE) {
					break;
				}
				this.whiteNo++;
				x--;
				y++;
			}
			//判断：
			if (this.whiteNo>=5) {
				 windowForWinner();
				 return;
			}
			this.whiteNo=0;
		}
	}
	
}
