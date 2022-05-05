package goBang_804_V2;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
/*
 * 这个类的作用是在用户点击按钮之后输出
 * 用户的personal information到控制台。
 * 下面上代码：
 */

public class Handler_for_loginButton implements ActionListener{
	/*
	 * 如果想实现上面说的功能的话，一定要将LoginWindowJTextField中的变量
	 * 传到事件监听类中并建立相关性，否则按钮都不知道你的text是啥？就不可能实现功能
	 */
	
	private JTextField theQQNum, theQQpwd;
	protected JFrame a;
	
	public Handler_for_loginButton (JTextField a, JTextField b) {
		this.theQQNum = a;
		this.theQQpwd = b;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("The user had touched the button!");
		System.out.println("The QQ number is "+ this.theQQNum.getText());
		System.out.println("The QQ password is "+ this.theQQpwd.getText());
		if (this.theQQNum.getText().equals(GoBang_database.QQNum)&&this.theQQpwd.getText().
				equals(GoBang_database.QQpwd)) {
			new StartGameWindowV1_804().StartGameWindow();
			this.a.dispose();
		}
		else {
			new StartLoginWindow().getWindowAfterloginfailed();
			
		}
	}
	
}
