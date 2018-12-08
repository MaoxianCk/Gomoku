package demo2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 棋面，在BackgroundPanel之上，绘制棋子，与ChessBoard关联，根据其对象值绘制棋子，观察者模式？
 * 
 * @author MaoxianCk
 */
public class ChessPanel extends JPanel implements PanelObserver {
	private Status isEnd;
	private Chessman board[][];
	private int wx[], wy[];
	private int nx, ny;

	public static final int spacing = 30; // 线条间隔
	public static final int rectSize = 10; // 中心正方形尺寸
	public static final int ovalSize = 22; // 棋子尺寸
	public static final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	public static final int redMarkSize = 5;// 当前位置红色小标记

	private static final long serialVersionUID = 1L;// serialVersionUID设置版本号

	private ImageIcon backgroundImgIcon;// 背景图片
	private Image backgroundImg;

	public ChessPanel() {
		// System.out.println(Gomoku.class.getResource("./Resource/Gomoku.jpg"));
		backgroundImgIcon = new ImageIcon(ChessPanel.class.getResource("./Resource/Gomoku.jpg"));// 设置背景图片路径
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
		int x = (FWidth - imgWidth) / 2;// 计算绘图点左上角坐标x
		int y = (FHeight - imgHeight) / 2;

		g.drawImage(backgroundImg, x, y, null);
		// 绘制棋盘
		for (int i = 0; i < BOARD_SIZE; i++) {
			g.drawLine(30, spacing + spacing * i, BOARD_SIZE * spacing, spacing + spacing * i);
			g.drawLine(spacing + spacing * i, 30, spacing + spacing * i, BOARD_SIZE * spacing);
		}
		// 绘制五个中心点
		g.fillRect(spacing * (int) (BOARD_SIZE / 2) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE / 2) + spacing - rectSize / 2, rectSize, rectSize); // 绘制中心的正方形
		g.fillRect(spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制右下的正方形
		g.fillRect(spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制右上的正方形
		g.fillRect(spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制左下的正方形
		g.fillRect(spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2,
				spacing * (int) (BOARD_SIZE / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制左上的正方形
	}

	private void drawWinLine(Graphics g) {

		// 绘制五子连珠线
		if (isEnd == Status.WIN) {
			// 绘制结束后连珠线
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
					// 绘制棋子
					// System.out.println("drawChess");
					// 黑色棋子
					if (board[y][x] == Chessman.BLACK_CHESS) {
						System.out.println("在(" + x + "," + y + ")绘制黑棋");
						g.setColor(Color.black);
						g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize,
								ovalSize);
					}
					// 白色棋子
					if (board[y][x] == Chessman.WHITE_CHESS) {

						System.out.println("在(" + x + "," + y + ")绘制白棋");
						g.setColor(Color.white);
						g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize,
								ovalSize);
					}
					// 当前落点的红色提示
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
		System.out.println("更新...");
		repaint();
	}
}
