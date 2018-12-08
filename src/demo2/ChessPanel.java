package demo2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * ���棬��BackgroundPanel֮�ϣ��������ӣ���ChessBoard���������������ֵ�������ӣ��۲���ģʽ��
 * 
 * @author MaoxianCk
 */
public class ChessPanel extends JPanel implements PanelObserver {
	private Status isEnd;
	private Chessman board[][];
	private int wx[], wy[];
	private int nx, ny;

	public static final int spacing = 30; // �������
	public static final int rectSize = 10; // ���������γߴ�
	public static final int ovalSize = 22; // ���ӳߴ�
	public static final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	public static final int redMarkSize = 5;// ��ǰλ�ú�ɫС���

	private static final long serialVersionUID = 1L;// serialVersionUID���ð汾��

	private ImageIcon backgroundImgIcon;// ����ͼƬ
	private Image backgroundImg;

	public ChessPanel() {
		// System.out.println(Gomoku.class.getResource("./Resource/Gomoku.jpg"));
		backgroundImgIcon = new ImageIcon(ChessPanel.class.getResource("./Resource/Gomoku.jpg"));// ���ñ���ͼƬ·��
		backgroundImg = backgroundImgIcon.getImage();
	}

	public void paint(Graphics g) {

		drawBackground(g);
		drawChess(g);
		drawWinLine(g);

	}

	private void drawBackground(Graphics g) {
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

	private void drawWinLine(Graphics g) {

		// ��������������
		if (isEnd == Status.WIN) {
			// ���ƽ�����������
			int a = ovalSize - 10, b = ovalSize - 10;
			if (wx[0] == wx[1]) {
				a = 0;
			}
			if (wy[0] == wy[1]) {
				b = 0;
			}
			if (wx[0] > wx[1]) {
				a = -a;
			}
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2.2f));
			g2.setColor(Color.red);
			g2.drawLine(spacing * wx[0] + spacing + a, spacing * wy[0] + spacing + b, spacing * wx[1] + spacing - a,
					spacing * wy[1] + spacing - b);
		}
	}

	private void drawChess(Graphics g) {
		if (board != null) {
			for (int y = 0; y < ChessBoard.BOARD_SIZE; y++) {
				for (int x = 0; x < ChessBoard.BOARD_SIZE; x++) {
					// ��������
					// System.out.println("drawChess");
					// ��ɫ����
					if (board[y][x] == Chessman.BLACK_CHESS) {
						System.out.println("��(" + x + "," + y + ")���ƺ���");
						g.setColor(Color.black);
						g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize,
								ovalSize);
					}
					// ��ɫ����
					if (board[y][x] == Chessman.WHITE_CHESS) {

						System.out.println("��(" + x + "," + y + ")���ư���");
						g.setColor(Color.white);
						g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize,
								ovalSize);
					}
					// ��ǰ���ĺ�ɫ��ʾ
					if (y == ny && x == nx) {
						g.setColor(Color.red);
						g.fillOval(spacing * x + spacing - redMarkSize / 2, spacing * y + spacing - redMarkSize / 2,
								redMarkSize, redMarkSize);
					}
				}
			}
		}
	}

	@Override
	public void update(Chessman board[][], int wx[], int wy[], int nx, int ny,Status status) {
		this.board = board;
		this.wx = wx;
		this.wy = wy;
		this.nx = nx;
		this.ny = ny;
		this.isEnd=status;
		System.out.println("����...");
		repaint();
	}
}
