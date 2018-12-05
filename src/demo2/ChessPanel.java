package demo2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
	public static final int ovalSize = 22; // ���ӳߴ�
	public static final int redMarkSize = BackgroundPanel.redMarkSize;
	public static final int spacing = BackgroundPanel.spacing;

	public void paint(Graphics g) {
		// ��������
		for (int i = 0; i < ChessBoard.BOARD_SIZE; i++) {
			for (int j = 0; j < ChessBoard.BOARD_SIZE; j++) {
				drawChess(g, i, j);
			}
		}
		// ��������������
		if (isEnd == Status.WIN) {
			drawWinLine(g);
		}
	}

	public void drawWinLine(Graphics g) {
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

	public void drawChess(Graphics g, int x, int y) {
		// ��������

		// ��ɫ����
		if (board[y][x] == Chessman.BLACK_CHESS) {
			g.setColor(Color.black);
			g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize, ovalSize);
		}
		// ��ɫ����
		if (board[y][x] == Chessman.WHITE_CHESS) {
			g.setColor(Color.white);
			g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize, ovalSize);
		}
		// ��ǰ���ĺ�ɫ��ʾ
		if (y == ny && x == nx) {
			g.setColor(Color.red);
			g.fillOval(spacing * x + spacing - redMarkSize / 2, spacing * y + spacing - redMarkSize / 2, redMarkSize,
					redMarkSize);
		}
	}

	@Override
	public void update(Chessman board[][], int wx[], int wy[], int nx, int ny) {
		this.board = board;
		this.wx = wx;
		this.wy = wy;
		this.nx = nx;
		this.ny = ny;
	}
}
