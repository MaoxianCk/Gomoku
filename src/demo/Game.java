package demo;
/**
 * ��Ϸ�������࣬���𴴽����̣���ز�������ֵ��������У������������еĻ�������
 * �������GameFrame�д������������Ӧ���������̽��в���
 * ��������������Ķ��󣬼� AI��������� GameFrame�� GamePanel�����������
 */
public class Game {
	public static final int size = 15;// ���̴�С �̶�������
	public final int WIN_NUM = 5;// ʤ�����������ӵ����� ��������
	public int wx[], wy[];// ʤ��ʱ������˵�(2��)�����λ��(x0,y0)��(x1,y1),�ṩ��GamePanel.paint(Graphics)������������

	private int win;// �Ƿ�ʤ����ֵΪ-1��0��1�� -1��ʾ�;֣�0��ʾʤ��δ�֣�1��ʾʤ���ѷ�
	private boolean legal=false;// ��ǰ�غ��߷��Ƿ�Ϸ�
	private int map[][]; // ���̣����浱ǰ���״̬��ֵΪ0,1,2��  0��ʾ�գ�1��ʾ���ӣ�2��ʾ����
	private int nowPlayer; // ��ǰ��ұ��
	private int nx, ny; // ��ǰ�غ���������
	private int round;// �غ���

	private GomokuAi gomokuAi;

	private GameFrame gameFrame;//δ����ʵ�壬��setGameFrame(GameFrame)����ʵ�壬��GameFrame�е��ú�����������
	private GamePanel panel;// δ����ʵ�壬��setGamePanel(GamePanel)����ʵ�壬��GamePanel�е��ú�����������

	Game() {
		/**
		 * ���캯�� ���ݳ�ʼ��
		 */
		System.out.println("����Game��...");
		System.out.println("��ʼ��Game��...");

		gomokuAi = new GomokuAi();
		map = new int[size][size];
		wx = new int[2];
	    wy = new int[2];
		win = 0;// 0��ʾʤ��δ��
		nowPlayer = 1;// ��������

		System.out.println("Game���ʼ���ɹ�!");
		System.out.println("Game�ഴ���ɹ�!");
	}

	void setGameFrame(GameFrame gf) {
		/**
		 * ����ʵ�壬��GameFrame�е��ú�����������
		 */
		gameFrame = gf;
	}

	void setGamePanel(GamePanel pan) {
		/**
		 * ����ʵ�壬��GamePanel�е��ú�����������
		 */
		panel = pan;
	}

	public void initialize() {
		/**
		 * ��ʼ�����̼�״̬
		 * ��ǰ������� ���� 1,�غ�������0,ʤ��״̬���� ʤ��δ�� 0,�����������״̬
		 */
		System.out.println("��ʼ������...");
		nowPlayer = 1;
		round = 0;
		win = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = 0;
			}
		}
		panel.repaint();
		System.out.print("��ʼ�����̳ɹ�!");
	}


	private void swapPlayer() {
		/**
	     * ������ǰ���
	     */
		nowPlayer = (nowPlayer == 1 ? 2 : 1);
		round++;
		System.out.println();
		System.out.println("��ǰ�غ��� : " + round);
		//System.out.println("�������");
		System.out.println("��ǰ��� ���" + nowPlayer);
	}

	public boolean isLegal() {
		/**
		 * �ж�(nx,ny)�Ƿ��ǺϷ����ӵ� �Ϸ�����true
		 */
		System.out.println();

		if (map[ny][nx] == 0 && nx >= 0 && ny >= 0 && nx < size && ny < size) {
			//Խ����
			legal=true;
			System.out.println("��ǰλ�� ( " + nx + " , " + ny + " ) �Ϸ�");
			return true;
		} else if (map[ny][nx] != 0) {
			//�ظ���飬���ܸ�����������
			legal=false;
			System.out.println("��ǰλ�� ( " + nx + " , " + ny + " ) ��������");
			return false;
		} else {
			legal=false;
			System.out.println("��ǰλ�� ( " + nx + " , " + ny + " ) ���Ϸ�");
			return false;
		}
	}

	public boolean isEnd() {
		/**
		 * ����Ƿ�;�
		 * ��������Ƿ��п�λ
		 */
		for (int i = 0; i < Game.size; i++) {
			for (int j = 0; j < Game.size; j++) {
				if (map[i][j] == 0) {
					return false;
				}
			}
		}
		win = -1;
		return true;
	}

	public boolean isWin() {
		/**
		 * ����Ƿ�ֳ�ʤ��
		 * �Ե�ǰ���ӵ�(nx,ny)Ϊ������4������(�ᣬ������б����б)�ȶ�
		 */
		win = 0;// �ж�ǰ���ó�ʼ״̬
		int maxCnt = 0;// ��¼���������
		int cnt = 1;// ��¼��ǰ���������Լ�������1
		int tempX = nx;// ѭ���жϵĵ�ǰx����
		int tempY = ny;// ѭ���жϵĵ�ǰy����
		boolean sameFlag = false;// ��ʾ�Ƿ�������ͬɫ

		int dir[][][] = {
			//��������
			// ��
			{ { -1, 0 }, { 1, 0 } },
			// ��
			{ { 0, -1 }, { 0, 1 } },
			// ��б
			{ { -1, -1 }, { 1, 1 } },
			// ��б
			{ { 1, -1 }, { -1, 1 } } };

		for (int i = 0; i < 4; i++) {
			// �ĸ�����ѭ���ж�
			cnt = 1;// ��¼��ǰ���������������Լ�������1
			for (int j = 0; j < 2; j++) {
				// ����
				sameFlag = true;// �Ƿ�ͬɫ
				while (sameFlag) {
					//���ͬɫ��������һ�����
					tempX = tempX + dir[i][j][0];
					tempY = tempY + dir[i][j][1];
					wx[j] = tempX;
					wy[j] = tempY;
					if (tempX >= 0 && tempX < size && tempY >= 0 && tempY < size) {
						// Խ����
						if ((map[tempY][tempX] == map[ny][nx])) {
							//����
							cnt++;
						} else {
							sameFlag = false;
						}
					} else {
						sameFlag = false;
					}
				}
				tempX = nx;
				tempY = ny;
			}
			//����ǰ��������������ɣ���¼���޸����ݲ������ж�
			if (cnt > maxCnt) {
				maxCnt = cnt;
			}
			if (cnt >= WIN_NUM) {
				win = 1;
				break;
			} else {
				win = 0;
			}
		}

		if (win==1) {
			System.out.println("ʤ���������" + nowPlayer + "!");
			return true;
		} else if (isEnd()) {
			return false;
		} else {
			return false;
		}
	}

	public void pvp(int x, int y) {
		/**
		 * ��Ҷ����
		 * �жϵ�ǰ���ӺϷ��ԣ�,�ж�ʤ�����������
		 */
		nx = x;
		ny = y;
		if (isLegal()) {
			map[ny][nx] = nowPlayer;
			swapPlayer();
		}
		panel.repaint();
		isWin();
	}

	public void pvc(int x, int y, int aiPlayerNum) {
		/**
		 * ��Ҷ�AI
		 * �жϵ�ǰ���ӺϷ��ԣ��ж�ʤ��������AI����
		 */
		nx = x;
		ny = y;
		if (isLegal()) {
			if (nowPlayer != aiPlayerNum) {
				// ��Ҳ���
				System.out.println("������� ( " + nx + " , " + ny + " )");
				map[ny][nx] = nowPlayer;
				swapPlayer();
				panel.repaint();
				if (isWin()) {
					return;
				}
			}
			if (nowPlayer == aiPlayerNum) {
				// Ai����
				gomokuAi.caculate(map.clone());//����.clone��֤ai����map�����޸�
				nx = gomokuAi.getX();
				ny = gomokuAi.getY();

				System.out.println();
				System.out.println("AI���� ( " + nx + " , " + ny + " )");

				map[ny][nx] = nowPlayer;
				swapPlayer();
				panel.repaint();
				isWin();
			}
		}
	}

	public boolean getLegal() {
		return legal;
	}

	public int getWin() {
		return win;
	}

	public int getRound() {
		return round;
	}

	public int getNx() {
		return nx;
	}

	public int getNy() {
		return ny;
	}

	public int[][] getMapClone() {
		return map.clone();
	}

	public int getNowMap() {
		return map[ny][nx];
	}

	public int getNowPlayer() {
		return nowPlayer;
	}

	public GameFrame getGameFrame() {
		return gameFrame;
	}
}
