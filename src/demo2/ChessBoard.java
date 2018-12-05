package demo2;

import java.util.Stack;

import demo.Game;

/**
 * �����࣬�������̵����� "��Ϸƽ̨" ���Բ������Ϸ��� TODO ���Լ��ı䣨���壬���壩�����淢����Ϣ��ˢ�½���
 * 
 * @author MaoxianCk
 */
public class ChessBoard implements BoardObserver {
	public static final int BOARD_SIZE = 15;
	private int winx[] = new int[2];
	private int winy[] = new int[2];
	// ����
	private Chessman board[][];
	// ����ջ���������������¼�����ڻ����˻�
	private Stack<Point> putStack;

	public ChessBoard() {
		putStack = new Stack<Point>();
		board = new Chessman[BOARD_SIZE][BOARD_SIZE];
		cleanBoard();
	}

	// ��p����һ��c����
	public void setChess(Point p, Chessman c) {
		System.out.println("��(" + p.x + "," + p.y + ")��������һ������");
		board[p.y][p.x] = c;
		putStack.push(p);
	}

	// ������һ���ŵ�����
	public void backChess() {
		if (putStack.isEmpty() == true) {
			System.out.println("û�����ӿ��Գ�����...");
		} else {
			Point p = putStack.pop();
			board[p.y][p.x] = Chessman.BLANK_SPACE;
			System.out.println("��(" + p.x + "," + p.y + ")����������һ������");
		}
	}
	/**
	 * �ж��Ƿ�ʤ��
	 * ����ֵΪint��1��ʾʤ����0��ʾʤ��δ�֣�-1��ʾƽ�֣�����������ʤ��δ�֣�
	 */
	public int isEnd() {
		/*
		 * ����Ƿ�ֳ�ʤ�� �Ե�ǰ���ӵ�p=ջ�� Ϊ������4������(�ᣬ������б����б)�ȶ�
		 */
		Point p = putStack.peek();

		int win = 0;// �ж�ǰ���ó�ʼ״̬
		int maxCnt = 0;// ��¼���������
		int cnt = 1;// ��¼��ǰ���������Լ�������1
		int tempX = p.x;// ѭ���жϵĵ�ǰx����
		int tempY = p.y;// ѭ���жϵĵ�ǰy����
		boolean sameFlag = false;// ��ʾ�Ƿ�������ͬɫ

		int dir[][][] = {
				// ��������
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
					// ���ͬɫ��������һ�����
					tempX = tempX + dir[i][j][0];
					tempY = tempY + dir[i][j][1];
					winx[j] = tempX;
					winy[j] = tempY;
					if (tempX >= 0 && tempX < BOARD_SIZE && tempY >= 0 && tempY < BOARD_SIZE) {
						// Խ����
						if ((board[tempY][tempX] == board[p.y][p.x])) {
							// ����
							cnt++;
						} else {
							sameFlag = false;
						}
					} else {
						sameFlag = false;
					}
				}
				tempX = p.x;
				tempY = p.y;
			}
			if (cnt > maxCnt) {
				maxCnt = cnt;
			}
			// ����ǰ��������������ɣ���¼���޸����ݲ������ж�

			if (cnt >= 5) {
				win = 1;
				break;
			} else {
				win = 0;
			}
		}

		if (isDraw() == true && win == 0) {
			win = -1;
		}
		return win;
	}

	public boolean isDraw() {
		/**
		 * ����Ƿ�;� ��������Ƿ��п�λ
		 */
		for (int i = 0; i < Game.size; i++) {
			for (int j = 0; j < Game.size; j++) {
				if (board[i][j] == Chessman.BLANK_SPACE) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isLegal(Point p) {

		if (board[p.y][p.x] == Chessman.BLANK_SPACE && p.x >= 0 && p.y >= 0 && p.x < BOARD_SIZE && p.y < BOARD_SIZE) {
			// Խ����
			System.out.println("��ǰλ�� ( " + p.x + " , " + p.y + " ) �Ϸ�");
			return true;
		} else if (board[p.y][p.x] != Chessman.BLANK_SPACE) {
			// �ظ���飬���ܸ�����������
			System.out.println("��ǰλ�� ( " + p.x + " , " + p.y + " ) ��������");
			return false;
		} else {
			System.out.println("��ǰλ�� ( " + p.x + " , " + p.y + " ) ���Ϸ�");
			return false;
		}
	}

	public void cleanBoard() {
		System.out.println("�������...");
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[j].length; j++) {
				board[i][j] = Chessman.BLANK_SPACE;
			}
		}
		System.out.println("��������¼...");
		putStack.clear();
	}
}