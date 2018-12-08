package demo2;

import java.awt.Component;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * GUI框架
 * 
 * @author MaoxianCk
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private ChessPanel chessPanel;
	private GameMouseListener gameMouseListener;
	private int scWidth;
	private int scHeight;

	GameFrame() {
		gameMouseListener = new GameMouseListener();
		chessPanel = new ChessPanel();
		
		scWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // 获取屏幕大小
		scHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		
		//backgroundPanel.setLocation(0, 0);
		
		
		//add(backgroundPanel);
		add(chessPanel);
		//backgroundPanel.add(chessPanel);
		chessPanel.addMouseListener(gameMouseListener);
		setSize(487, 568);// 设置窗口大小
		setTitle("五子棋       by 毛线");// 设置窗口标题
		setLocation((scWidth - 500) / 2, (scHeight - 500) / 2);// 设置居中
		setResizable(false);// 禁止窗口缩放
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		;
	}

	public void setGameMouseListener(MouseObserver o) {
		gameMouseListener.registerObserver(o);
	}

	public PanelObserver getChessPanel() {
		return chessPanel;

	}
}
