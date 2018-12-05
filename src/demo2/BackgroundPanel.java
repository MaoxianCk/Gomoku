package demo2;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * 棋盘背景面板，包含线及背景的绘制
 * 
 * @author MaoxianCk
 */
public class BackgroundPanel extends JPanel {

	public static final int spacing = 30; // 线条间隔
	public static final int rectSize = 10; // 中心正方形尺寸
	public static final int ovalSize = 22; // 棋子尺寸
	public static final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	public static final int redMarkSize = 5;// 当前位置红色小标记
	
	private static final long serialVersionUID = 1L;// serialVersionUID设置版本号

	private ImageIcon backgroundImgIcon;// 背景图片
	private Image backgroundImg;
	
	private ChessPanel chessPanel;

	public BackgroundPanel() {
		chessPanel=new ChessPanel();
		// System.out.println(Gomoku.class.getResource("./Resource/Gomoku.jpg"));
		backgroundImgIcon = new ImageIcon(BackgroundPanel.class.getResource("./Resource/Gomoku.jpg"));// 设置背景图片路径
		backgroundImg = backgroundImgIcon.getImage();

	}

	public void paint(Graphics g) {
		/**
		 * 实现JPanel中paint函数 对窗口图形进行具体绘制 由repaint()及窗口改变后自动调用
		 */

		// super.paint(g);
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
}
