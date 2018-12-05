package demo2;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * ���̱�����壬�����߼������Ļ���
 * 
 * @author MaoxianCk
 */
public class BackgroundPanel extends JPanel {

	public static final int spacing = 30; // �������
	public static final int rectSize = 10; // ���������γߴ�
	public static final int ovalSize = 22; // ���ӳߴ�
	public static final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	public static final int redMarkSize = 5;// ��ǰλ�ú�ɫС���
	
	private static final long serialVersionUID = 1L;// serialVersionUID���ð汾��

	private ImageIcon backgroundImgIcon;// ����ͼƬ
	private Image backgroundImg;
	
	private ChessPanel chessPanel;

	public BackgroundPanel() {
		chessPanel=new ChessPanel();
		// System.out.println(Gomoku.class.getResource("./Resource/Gomoku.jpg"));
		backgroundImgIcon = new ImageIcon(BackgroundPanel.class.getResource("./Resource/Gomoku.jpg"));// ���ñ���ͼƬ·��
		backgroundImg = backgroundImgIcon.getImage();

	}

	public void paint(Graphics g) {
		/**
		 * ʵ��JPanel��paint���� �Դ���ͼ�ν��о������ ��repaint()�����ڸı���Զ�����
		 */

		// super.paint(g);
		int imgWidth = backgroundImg.getWidth(this);
		int imgHeight = backgroundImg.getHeight(this);
		int FWidth = getWidth();
		int FHeight = getHeight();
		int x = (FWidth - imgWidth) / 2;// �����ͼ�����Ͻ�����x
		int y = (FHeight - imgHeight) / 2;

		g.drawImage(backgroundImg, x, y, null);
		// ��������
		for (int i = 0; i < BOARD_SIZE; i++) {
			g.drawLine(30, spacing + spacing * i, BOARD_SIZE * spacing, spacing + spacing * i);
			g.drawLine(spacing + spacing * i, 30, spacing + spacing * i, BOARD_SIZE * spacing);
		}
		// ����������ĵ�
		g.fillRect(spacing * (int) (BOARD_SIZE / 2) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE / 2) + spacing - rectSize / 2, rectSize, rectSize); // �������ĵ�������
		g.fillRect(spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������µ�������
		g.fillRect(spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������ϵ�������
		g.fillRect(spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������µ�������
		g.fillRect(spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������ϵ�������
	}
}
