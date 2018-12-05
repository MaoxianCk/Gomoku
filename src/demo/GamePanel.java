package demo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 画板，对输入信息的处理及对应窗口的具体绘制
 * 实现MouseListener,MouseMotionListener接口
 * 对鼠标点击事件进行处理并交由Game对象进行处理
 */
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;// serialVersionUID设置版本号

	public static final int spacing = 30; // 线条间隔
	public static final int rectSize = 10; // 中心正方形尺寸
	public static final int ovalSize = 22; // 棋子尺寸
	public static final int mapSize = Game.size;
	public static final int redMarkSize = 5;// 当前位置红色小标记

	public ImageIcon backgroundImgIcon;// 背景图片
	public Image backgroundImg;

	public int x;// 鼠标点击坐标对应棋盘坐标(x,y)
	public int y;// 鼠标点击坐标对应棋盘坐标(x,y)
	private int mx;// 鼠标位置
	private int my;// 鼠标位置

	Game game;

	GamePanel() {
		//System.out.println(Gomoku.class.getResource("./Resource/Gomoku.jpg"));
		backgroundImgIcon = new ImageIcon(Gomoku.class.getResource("./Resource/Gomoku.jpg"));// 设置背景图片路径
		backgroundImg = backgroundImgIcon.getImage();
		game = new Game();
		game.setGamePanel(this);// 设置game中GamePanel对象
		// repaint();
		addMouseListener((MouseListener) this);// 添加鼠标监听事件到本身
		addMouseMotionListener((MouseMotionListener) this);// 添加鼠标监听事件到本身
	}

	public void paint(Graphics g) {
		/**
		 * 实现JPanel中paint函数
		 * 对窗口图形进行具体绘制
		 * 由repaint()及窗口改变后自动调用
		 */

		// super.paint(g);
		int imgWidth = backgroundImg.getWidth(this);
		int imgHeight = backgroundImg.getHeight(this);
		int FWidth = getWidth();
		int FHeight = getHeight();
		int x = (FWidth - imgWidth) / 2;// 计算绘图点左上角坐标x
		int y = (FHeight - imgHeight) / 2;

		g.drawImage(backgroundImg, x, y, null);
		g.setFont(new Font("宋体", 0, 17));
		// 绘制棋盘
		for (int i = 0; i < Game.size; i++) {
			g.drawLine(30, spacing + spacing * i, Game.size * spacing, spacing + spacing * i);
			g.drawLine(spacing + spacing * i, 30, spacing + spacing * i, Game.size * spacing);
		}
		// 绘制五个中心点
		g.fillRect(spacing * (int) (Game.size / 2) + spacing - rectSize / 2,
				spacing * (int) (Game.size / 2) + spacing - rectSize / 2, rectSize, rectSize); // 绘制中心的正方形
		g.fillRect(spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制右下的正方形
		g.fillRect(spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制右上的正方形
		g.fillRect(spacing * (int) (Game.size / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制左下的正方形
		g.fillRect(spacing * (int) (Game.size / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size / 4) + spacing - rectSize / 2, rectSize, rectSize); // 绘制左上的正方形
		// 绘制棋子
		for (int i = 0; i < Game.size; i++) {
			for (int j = 0; j < Game.size; j++) {
				draw(g, i, j, game.getMapClone());
			}
		}
		// 绘制五子连珠线
		if (game.getWin() == 1) {
			drawWinLine(g, game.wx, game.wy);
		}
        // 打印文字提示信息
		g.setColor(Color.black);
		if (game.getNowPlayer() == 1) {
			g.drawString("黑子下棋", spacing * 3, spacing + spacing * Game.size + 15);
		}
		if (game.getNowPlayer() == 2) {
			g.drawString("白子下棋", spacing * 3, spacing + spacing * Game.size + 15);
		}
	}

	public void showWin() {
		// 游戏结束弹窗
		JOptionPane.showMessageDialog(game.getGameFrame(), "游戏结束," + (game.getNowMap() == 1 ? "黑方" : "白方") + "获胜!");
	}

	public void showEnd() {
		// 游戏结束弹窗
		JOptionPane.showMessageDialog(game.getGameFrame(), "游戏结束,和局");
	}

	public void drawWinLine(Graphics g, int wx[], int wy[]) {
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

	public void draw(Graphics g, int x, int y, int[][] map) {
		// 绘制棋子

		// 黑色棋子
		if (map[y][x] == 1) {
			g.setColor(Color.black);
			g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize, ovalSize);
		}
		// 白色棋子
		if (map[y][x] == 2) {
			g.setColor(Color.white);
			g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize, ovalSize);
		}
		// 当前落点的红色提示
		if (y == game.getNy() && x == game.getNx() && game.getRound() > 0 && game.getLegal()) {
			g.setColor(Color.red);
			g.fillOval(spacing * x + spacing - redMarkSize / 2, spacing * y + spacing - redMarkSize / 2, redMarkSize,
					redMarkSize);
		}
	}

	public void mousePressed(MouseEvent e) {
		//鼠标点击事件处理
		System.out.println("--------------------------------------");
		x = e.getX();// 获取鼠标点击位置坐标
		y = e.getY();
		//检查越界及点击误差判断
		if (x >= 15 && x < mapSize * spacing + 15 && y >= 15 && y < mapSize * spacing + spacing / 2) {
			x = (x + mapSize) / spacing - 1; // 是为了取得交叉点的坐标，将鼠标点击坐标转化为棋盘具体坐标(x,y)：0-14
			y = (y + mapSize) / spacing - 1;
			if (x >= 0 && x < mapSize && y >= 0 && y < mapSize) {
				game.pvc(x, y, 2);//调用pvc游戏模式
				// game.pvp(x, y);//调用pvp游戏模式

				//胜负判断
				if (game.getWin() == 1) {
					showWin();
					game.initialize();
				} else if (game.getWin() == -1) {
					showEnd();
					game.initialize();
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//鼠标移动事件，一般仅调试时使用
		int ex, ey;
		ex = e.getX();
		ey = e.getY();

		if (ex >= 15 && ex < mapSize * spacing + 15 && ey >= 15 && ey < mapSize * spacing + spacing / 2) {
			ex = (ex + mapSize) / spacing - 1; // 是为了取得交叉点的坐标
			ey = (ey + mapSize) / spacing - 1;
			if (ex == mx && ey == my) {
				return;
			} else {
				mx = ex;
				my = ey;
			}
			if (mx >= 0 && mx < mapSize && my >= 0 && my < mapSize) {
				// game.showScore(mx,my);
			}
		}
	}
}
