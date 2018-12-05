package demo;
import javax.swing.*;
import java.awt.*;

/**
 * 程序窗口总框架
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;// serialVersionUID设置版本号
	public final GamePanel panel;
	Container con;// 容器

	private int scWidth;
	private int scHeight;
	GameFrame() {
		panel = new GamePanel();
		panel.game.setGameFrame(this);
		con = this.getContentPane();

		setSize(487, 568);// 设置窗口大小
		setTitle("五子棋       by 毛线");// 设置窗口标题
		scWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // 获取屏幕大小
		scHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((scWidth - 500) / 2, (scHeight - 500) / 2);// 设置居中
        setResizable(false);//禁止窗口缩放
        con.add(panel);// 添加panel到容器
	}
}
