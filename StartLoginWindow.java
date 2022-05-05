package goBang_804_V2;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class StartLoginWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnLogin;
	private FlowLayout layOut;
	private JTextField qqNum;
	private JPasswordField pwdInput;
	Handler_for_loginButton bListener;
	Handler_for_ENTERclicked eListener;
	
	public StartLoginWindow() {
		this.setBounds(600, 300, 500, 500);
		this.setTitle("LOGIN");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);;
		this.pwdInput = new JPasswordField(15);
		this.qqNum = new JTextField(15);
		this.btnLogin = new JButton("Login");
		this.layOut = new FlowLayout(FlowLayout.CENTER,10,10);
		this.setLayout(layOut);
		this.add(qqNum);
		this.add(pwdInput);
		this.add(btnLogin);
		//处理keyboard监听器：
		this.eListener = new Handler_for_ENTERclicked(this.qqNum, this.pwdInput);
		this.eListener.a = this;
		this.qqNum.addKeyListener(eListener);
		this.pwdInput.addKeyListener(eListener);
		this.setVisible(true);
		//处理按钮监听器：
		this.bListener = new Handler_for_loginButton(this.qqNum, this.pwdInput);
		this.bListener.a =this;
		this.btnLogin.addActionListener(bListener);
	}
	
	public void getWindowAfterloginfailed() {
		
		this.setBounds(800, 500, 500, 500);
		this.setTitle("Login failed! Try it again!");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new StartLoginWindow();
		
	}
}
