package demo;
import javax.swing.*;
import java.awt.*;

/**
 * ���򴰿��ܿ��
 */
public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;// serialVersionUID���ð汾��
	public final GamePanel panel;
	Container con;// ����

	private int scWidth;
	private int scHeight;
	GameFrame() {
		panel = new GamePanel();
		panel.game.setGameFrame(this);
		con = this.getContentPane();

		setSize(487, 568);// ���ô��ڴ�С
		setTitle("������       by ë��");// ���ô��ڱ���
		scWidth = Toolkit.getDefaultToolkit().getScreenSize().width; // ��ȡ��Ļ��С
		scHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setLocation((scWidth - 500) / 2, (scHeight - 500) / 2);// ���þ���
        setResizable(false);//��ֹ��������
        con.add(panel);// ���panel������
	}
}
