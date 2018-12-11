package demo2;

import java.util.ArrayList;

/**
 * AI ���
 * 
 * @author MaoxianCk
 */
public class AiPlayer extends Player implements BoardObserver {
	private final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	// ��ʵ���̵Ŀ�����ʵʱ����
	private Chessman[][] boardCopy;

	public AiPlayer() {
		super("AI", Chessman.BLANK_SPACE);
		boardCopy = null;
	}

	public AiPlayer(Chessman chessman) {
		super("AI", chessman);
		boardCopy = null;
	}

	public Point putChess(Point p) {
		System.out.println("---------AI---------");
		p = new Point(0, 0);
		p = caculate();
		return p;
	}

	// �۲��߸�������
	public void update(Chessman[][] board, int[] wx, int[] wy, int nx, int ny, Status status) {
		this.boardCopy = board;
	}

	/**
	 * AI���֣�����ȫΪ˽�У���ΪAI�ڲ��ж�...
	 */

	/**
	 * ���㣬�������ռ�����
	 */
	private Point caculate() {
		Point p = null;
		int maxScore = 0;
		// ���ڼ����board
		Chessman[][] boardCal = boardCopy.clone();
		ArrayList<Point> cango = getChessList(3, boardCal, false);
		for (Point e : cango) {
				System.out.println("boardCal[e.y][e.x]"+boardCal[e.y][e.x]+"getChessman()"+getChessman());
			if (boardCal[e.y][e.x] == Chessman.BLANK_SPACE) {
				//ģ����һ��
				//TODO ģ����ˣ����ࣩ�߲����Ƚ�ȡ���ӵص�
				erreo
				boardCal[e.y][e.x]=getChessman();
				int score = caculatePerPoint(new Point(e.y, e.x), boardCal);
				//����ɾ��
				boardCal[e.y][e.x]=Chessman.BLANK_SPACE;
				System.out.println("(" + e.x + "," + e.y + ") score = " + score);
				if (score > maxScore) {
					p = e;
					maxScore = score;
				}
			}
		}
		return p;
	}

	/**
	 * ����������������ǰ�����ϵ��ܷ�
	 */
	private int evaluate(Chessman[][] board) {
		int result = 0;

		// �õ������������ӵ�����
		ArrayList<Point> points = getChessList(0, board, true);
		for (Point e : points) {
			result += caculatePerPoint(e, board);
		}
		return result;
	}

	/**
	 * ����space�ļ���ж�ԭ��+space*2��Χ���Ƿ����������� space ��࣬board
	 * ���̣�hasChess�����ĵ㼯���Ƿ������ӣ�true��ʾ����list�еĵ㶼���ǿյأ�false��ʾlist�еĵ㶼�ǿյ�
	 */
	private ArrayList<Point> getChessList(int space, Chessman[][] board, boolean hasChess) {
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				/**
				 * �жϸõ���Χ5*5��Χ���Ƿ�������
				 */
				if (hasChess == true && board[i][j] == Chessman.BLANK_SPACE) {
					// ���hasChessΪtrue�Ҹõ��ǿյ�������
					continue;
				}
				boolean mark = false;
				for (int y = i - space; ((y < i + space) && (mark == false)); y++) {
					for (int x = j - space; ((x < j + space) && (mark == false)); x++) {
						if (y >= 0 && y < BOARD_SIZE && x >= 0 && x < BOARD_SIZE) {
							if (board[y][x] != Chessman.BLANK_SPACE || hasChess==true) {
								//System.out.println("(" + j + "," + i + ")");
								list.add(new Point(j, i));
								mark = true;
								break;
							}
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * ���ֵ��ң�����p��ķ���
	 */
	private int caculatePerPoint(Point p, Chessman[][] map) {
		int nx = p.x;
		int ny = p.y;
		// ��ά�����¼�ᣬ�ݣ���б����б
		int dir[][][] = { { { -1, 0 }, { 1, 0 } }, { { 0, -1 }, { 0, 1 } }, { { -1, -1 }, { 1, 1 } },
				{ { 1, -1 }, { -1, 1 } } };
		int dir2[] = { -1, 1 };
		int temp;
		int tempY;
		int tempX;
		int score = 0;
		Chessman situationMap[] = new Chessman[9];// ����ÿ�������������� [4+1+4] ����situation()�����������д��
		// �յ㲻������
		if (map[ny][nx] == Chessman.BLANK_SPACE) {
			return 0;
		}

		if (nx > 3 && nx < 11 && ny > 3 && ny < 11) {
			// ���ķ�Χ�� ������ֵ��
			score += 50;
		}

		// ��¼4�������ϵ��������(�ᣬ�ݣ���б����б)���ֱ𽻸�������������
		for (int i = 0; i < 4; i++) {
			// ����
			for (int j = 0; j < 2; j++) {
				tempY = ny;
				tempX = nx;
				temp = 4;
				// һ���������ĸ�
				for (int k = 0; k < 4; k++) {
					tempX += dir[i][j][0];
					tempY += dir[i][j][1];
					temp += dir2[j];
					if (tempX >= 0 && tempX < BOARD_SIZE && tempY >= 0 && tempY < BOARD_SIZE) {
						situationMap[temp] = map[tempY][tempX];
					} else {
						situationMap[temp] = null;
					}
				}
			}
			score += situation(situationMap);
		}
		return score;
	}

	/**
	 * caculatePerPoint�Ӻ��� �����situationmap[4]Ϊ����([4]Ϊ��ǰ�µ����ӵ�)������һ�������Ʋ����о����жϲ����
	 * ����һ��situationMap[9]���飬������Ӧ������situationMap[4]Ϊ���ĵ�����(�������������)4��Χ�ڵ��������
	 */
	private int situation(Chessman situationMap[]) {
		// ��situationMap�������
		boolean[] die = { false, false };// ��¼�����Ƿ񱻶���
		Chessman player = situationMap[4];// ��ǰ������ΪsituationMap[4]Ԫ�أ�������Ԫ��
		int cnt = 1;// ����������������1
		int cntBlank = 0;// ��������������û���жϵĿո����� ����:0011 1 2000 ��ֻ��ǰ���00��cntBlank==2
		int[] dir = { -1, 1 };// ���ҷ�������
		for (int i = 0; i < 2; i++) {
			// ����
			int tempPosition = 4;
			boolean flag = false; // �Ƿ��ж� true��ʾ�ж� �ж�֮��˷����������չ��ת����һ�߽�����չ
			for (int j = 0; j < 4; j++) {
				// ������������ĸ�
				tempPosition += dir[i];
				Chessman tempChess = situationMap[tempPosition];
				if (tempChess == player && !flag) {
					// ���������Ҵ�ǰû���ж�
					cnt++;// �����е�����
				} else if ((tempChess != player && tempChess != Chessman.BLANK_SPACE) || (tempChess == null) && !flag) {
					// �����ǰ���situationMap[4]���ӻ���Ϊnull(�߽�)���Ҵ�ǰû���ж� �ж����ڴ��ж�
					die[i] = true;
					flag = true;
					break;
				} else {
					// ����ǿյ�
					if (tempChess == Chessman.BLANK_SPACE && !flag) {
						cntBlank++;
					}
				}
			}
		}

		// �����϶η�������ۺϴ��
		int score = 1;
		if (cnt >= 5) {
			return 100000;
		} else if (cnt == 4) {
			if (!die[0] && !die[1]) {
				// ����
				return 10000;

			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 1))) {
				// ����
				return 5000;
			} else if (die[0] && die[1]) {
				// ����
				return 4;
			}
		} else if (cnt == 3) {
			if (!die[0] && !die[1]) {
				// ����
				return 1000;
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 2))) {
				// ����
				return 500;
			} else if (die[0] && die[1]) {
				// ����
				return 3;
			}
		} else if (cnt == 2) {
			if (!die[0] && !die[1]) {
				// ���
				return 100;
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 3))) {
				// ���
				return 50;
			} else if (die[0] && die[1]) {
				// ����
				return 2;
			}
		}

		return score;
	}
}