package goBang_804_V2;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
/**
 * 
 * @author peterwong
 *
 */

public class Handler_for_ENTERclicked implements KeyListener{
	
	
	private JTextField theQQNum, theQQpwd;
	protected JFrame a;
	public Handler_for_ENTERclicked  (JTextField a, JTextField b) {
		this.theQQNum = a;
		this.theQQpwd = b;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar()== KeyEvent.VK_ENTER ) {
			if (this.theQQNum.getText().equals(GoBang_database.QQNum)&&this.theQQpwd.getText().equals(GoBang_database.QQpwd)) {
				new StartGameWindowV1_804().StartGameWindow();
				this.a.dispose();
			}
			else {
				
				new StartLoginWindow().getWindowAfterloginfailed();
				
			}
		}
		
			
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	

}
