package demo2;

import java.awt.Component;
import java.awt.Panel;
import java.awt.Toolkit;

import javax.swing.JFrame;


/**
 * GUI���
 * @author MaoxianCk
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private BackgroundPanel backgroundPanel;
	private GameMouseListener gameMouseListener;
	private int scWidth;
	private int scHeight;
	GameFrame() {
		backgroundPanel = new BackgroundPanel();
		gameMouseListener=new GameMouseListener();
		scWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // ��ȡ��Ļ��С
		scHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		add(backgroundPanel);
		addMouseListener(gameMouseListener);
		
		setSize(487, 568);// ���ô��ڴ�С
		setTitle("������       by ë��");// ���ô��ڱ���
		setLocation((scWidth - 500) / 2, (scHeight - 500) / 2);// ���þ���
        setResizable(false);//��ֹ��������
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
	}
	public void setGameMouseListener(MouseObserver o) {
		gameMouseListener.registerObserver(o);
	}
}
