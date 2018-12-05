package demo;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * ���壬��������Ϣ�Ĵ�����Ӧ���ڵľ������
 * ʵ��MouseListener,MouseMotionListener�ӿ�
 * ��������¼����д�������Game������д���
 */
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;// serialVersionUID���ð汾��

	public static final int spacing = 30; // �������
	public static final int rectSize = 10; // ���������γߴ�
	public static final int ovalSize = 22; // ���ӳߴ�
	public static final int mapSize = Game.size;
	public static final int redMarkSize = 5;// ��ǰλ�ú�ɫС���

	public ImageIcon backgroundImgIcon;// ����ͼƬ
	public Image backgroundImg;

	public int x;// ����������Ӧ��������(x,y)
	public int y;// ����������Ӧ��������(x,y)
	private int mx;// ���λ��
	private int my;// ���λ��

	Game game;

	GamePanel() {
		//System.out.println(Gomoku.class.getResource("./Resource/Gomoku.jpg"));
		backgroundImgIcon = new ImageIcon(Gomoku.class.getResource("./Resource/Gomoku.jpg"));// ���ñ���ͼƬ·��
		backgroundImg = backgroundImgIcon.getImage();
		game = new Game();
		game.setGamePanel(this);// ����game��GamePanel����
		// repaint();
		addMouseListener((MouseListener) this);// ����������¼�������
		addMouseMotionListener((MouseMotionListener) this);// ����������¼�������
	}

	public void paint(Graphics g) {
		/**
		 * ʵ��JPanel��paint����
		 * �Դ���ͼ�ν��о������
		 * ��repaint()�����ڸı���Զ�����
		 */

		// super.paint(g);
		int imgWidth = backgroundImg.getWidth(this);
		int imgHeight = backgroundImg.getHeight(this);
		int FWidth = getWidth();
		int FHeight = getHeight();
		int x = (FWidth - imgWidth) / 2;// �����ͼ�����Ͻ�����x
		int y = (FHeight - imgHeight) / 2;

		g.drawImage(backgroundImg, x, y, null);
		g.setFont(new Font("����", 0, 17));
		// ��������
		for (int i = 0; i < Game.size; i++) {
			g.drawLine(30, spacing + spacing * i, Game.size * spacing, spacing + spacing * i);
			g.drawLine(spacing + spacing * i, 30, spacing + spacing * i, Game.size * spacing);
		}
		// ����������ĵ�
		g.fillRect(spacing * (int) (Game.size / 2) + spacing - rectSize / 2,
				spacing * (int) (Game.size / 2) + spacing - rectSize / 2, rectSize, rectSize); // �������ĵ�������
		g.fillRect(spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������µ�������
		g.fillRect(spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������ϵ�������
		g.fillRect(spacing * (int) (Game.size / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size * 3 / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������µ�������
		g.fillRect(spacing * (int) (Game.size / 4) + spacing - rectSize / 2,
				spacing * (int) (Game.size / 4) + spacing - rectSize / 2, rectSize, rectSize); // �������ϵ�������
		// ��������
		for (int i = 0; i < Game.size; i++) {
			for (int j = 0; j < Game.size; j++) {
				draw(g, i, j, game.getMapClone());
			}
		}
		// ��������������
		if (game.getWin() == 1) {
			drawWinLine(g, game.wx, game.wy);
		}
        // ��ӡ������ʾ��Ϣ
		g.setColor(Color.black);
		if (game.getNowPlayer() == 1) {
			g.drawString("��������", spacing * 3, spacing + spacing * Game.size + 15);
		}
		if (game.getNowPlayer() == 2) {
			g.drawString("��������", spacing * 3, spacing + spacing * Game.size + 15);
		}
	}

	public void showWin() {
		// ��Ϸ��������
		JOptionPane.showMessageDialog(game.getGameFrame(), "��Ϸ����," + (game.getNowMap() == 1 ? "�ڷ�" : "�׷�") + "��ʤ!");
	}

	public void showEnd() {
		// ��Ϸ��������
		JOptionPane.showMessageDialog(game.getGameFrame(), "��Ϸ����,�;�");
	}

	public void drawWinLine(Graphics g, int wx[], int wy[]) {
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

	public void draw(Graphics g, int x, int y, int[][] map) {
		// ��������

		// ��ɫ����
		if (map[y][x] == 1) {
			g.setColor(Color.black);
			g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize, ovalSize);
		}
		// ��ɫ����
		if (map[y][x] == 2) {
			g.setColor(Color.white);
			g.fillOval(spacing * x + spacing - ovalSize / 2, spacing * y + spacing - ovalSize / 2, ovalSize, ovalSize);
		}
		// ��ǰ���ĺ�ɫ��ʾ
		if (y == game.getNy() && x == game.getNx() && game.getRound() > 0 && game.getLegal()) {
			g.setColor(Color.red);
			g.fillOval(spacing * x + spacing - redMarkSize / 2, spacing * y + spacing - redMarkSize / 2, redMarkSize,
					redMarkSize);
		}
	}

	public void mousePressed(MouseEvent e) {
		//������¼�����
		System.out.println("--------------------------------------");
		x = e.getX();// ��ȡ�����λ������
		y = e.getY();
		//���Խ�缰�������ж�
		if (x >= 15 && x < mapSize * spacing + 15 && y >= 15 && y < mapSize * spacing + spacing / 2) {
			x = (x + mapSize) / spacing - 1; // ��Ϊ��ȡ�ý��������꣬�����������ת��Ϊ���̾�������(x,y)��0-14
			y = (y + mapSize) / spacing - 1;
			if (x >= 0 && x < mapSize && y >= 0 && y < mapSize) {
				game.pvc(x, y, 2);//����pvc��Ϸģʽ
				// game.pvp(x, y);//����pvp��Ϸģʽ

				//ʤ���ж�
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
		//����ƶ��¼���һ�������ʱʹ��
		int ex, ey;
		ex = e.getX();
		ey = e.getY();

		if (ex >= 15 && ex < mapSize * spacing + 15 && ey >= 15 && ey < mapSize * spacing + spacing / 2) {
			ex = (ex + mapSize) / spacing - 1; // ��Ϊ��ȡ�ý���������
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
