package goBang_804_V2;

import java.awt.FlowLayout;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
/**
 * 秋天的第一行代码：
 * @author peterwong
 *
 */
public class StartGameWindowV1_804 extends JFrame{
	private static final long serialVersionUID = 1L;
	private Graphics g;
	private JButton btnRegret, btnClean, btnBlackFirst, btnWhiteFirst, btnAI;
	private MoveChess_Listener chessActionlistener;
	//private Cleanbtn_Listener cleanListener;
	
	

	public static final Image bgimg = new ImageIcon("src/goBang_804/img/img.png").getImage();
	
	public void StartGameWindow() {
		this.setTitle("五子棋");
		this.setBounds(400, 400, 1200, 1200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.btnRegret = new JButton("Regret!");
		this.btnClean = new JButton("Clean");
		this.btnBlackFirst = new JButton("Black First!");
		this.btnWhiteFirst = new JButton("White First!");
		this.btnAI = new JButton("AI(慎点！)");
		this.add(btnRegret);
		this.add(btnClean);
		this.add(btnBlackFirst);
		this.add(btnWhiteFirst);
		this.add(btnAI);
		this.setVisible(true);
		JFrame a = this;
		
		this.g = this.getGraphics();
		//new出一个actionActioner：
		this.chessActionlistener = new MoveChess_Listener(this.g);
		
		this.addMouseListener(chessActionlistener);
		//this.cleanListener = new Cleanbtn_Listener(this);
		//将三个按钮传到actionlistener和主窗口传到类中去：
		this.chessActionlistener.startGameWin = this;
		this.chessActionlistener.btnBlack = this.btnBlackFirst;
		this.chessActionlistener.btnWhite = this.btnWhiteFirst;
		this.chessActionlistener.btnregret = this.btnRegret;
		this.chessActionlistener.btnAi = this.btnAI;
		//this.chessActionlistener.btnRegret = this.btnRegret;
		//this.chessActionlistener.btnClean = this.btnClean;
		//处理三个按钮的actionlistener：
		this.btnRegret.addActionListener(chessActionlistener);
		this.btnBlackFirst.addActionListener(chessActionlistener);
		this.btnWhiteFirst.addActionListener(chessActionlistener);
		this.btnAI.addActionListener(chessActionlistener);
		this.btnClean.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				a.dispose();
				new StartGameWindowV1_804().StartGameWindow();
			}
			
		});
		//this.btnRegret.addActionListener(chessActionlistener);
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.drawImage(bgimg, 80, 80, GoBang_database.BOARD_WIDTH, GoBang_database.BOARD_HEIGHT, 
				null);
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
		for (int i=0; i<this.chessActionlistener.chesseslists.size(); i++) {
			g.setColor(this.chessActionlistener.chesseslists.get(i).getColor());
			g.fillOval(this.chessActionlistener.chesseslists.get(i).getbigX(), 
					this.chessActionlistener.chesseslists.get(i).getbigY(), 40, 40);
		}

	}

	public static void main(String[] args) {
		new StartGameWindowV1_804().StartGameWindow();
		//new StartLoginWindow();
	}
	
	
}
