package demo;
import javax.swing.JFrame;

/**
 * Gomoku ������
 * Game�� ��Ϸ������,����,�ж�����(rule����,�н����޽���)
 * ��ͼ����
 * @author ë��
 * 2018��8��7�� ����
 * 2018��8��xx�� �޸��߼�����
 * 2018��8��31�� ΪGame.java���ע��
 * 2018��9��1�� ����ע�ͣ������޸ı�������
 */
public class Gomoku {
	public static void main(String[] args) {
		GameFrame gameFrame;
		gameFrame = new GameFrame();

		gameFrame.setVisible(true);// ���ô��ڿɼ�
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ��������ʱ��������
	}
}
