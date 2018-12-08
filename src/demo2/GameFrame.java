package demo2;

import java.awt.Component;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 * GUI���
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
		
		scWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // ��ȡ��Ļ��С
		scHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		
		//backgroundPanel.setLocation(0, 0);
		
		
		//add(backgroundPanel);
		add(chessPanel);
		//backgroundPanel.add(chessPanel);
		chessPanel.addMouseListener(gameMouseListener);
		setSize(487, 568);// ���ô��ڴ�С
		setTitle("������       by ë��");// ���ô��ڱ���
		setLocation((scWidth - 500) / 2, (scHeight - 500) / 2);// ���þ���
		setResizable(false);// ��ֹ��������
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
