package demo;
import javax.swing.JFrame;

/**
 * Gomoku 五子棋
 * Game类 游戏主程序,下棋,判定函数(rule规则,有禁手无禁手)
 * 绘图函数
 * @author 毛线
 * 2018年8月7日 创建
 * 2018年8月xx日 修改逻辑代码
 * 2018年8月31日 为Game.java添加注释
 * 2018年9月1日 增加注释，少量修改变量名称
 */
public class Gomoku {
	public static void main(String[] args) {
		GameFrame gameFrame;
		gameFrame = new GameFrame();

		gameFrame.setVisible(true);// 设置窗口可见
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 结束窗口时结束进程
	}
}
