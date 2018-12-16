package demo2;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;

import demo.Game;

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
	 * �򵥰�AI һ����������ɼ������
	 */
	private Point caculateEasy() {
		Point p = null;
		int maxScore = 0;
		// ���ڼ����board
		Chessman[][] boardCal = boardCopy.clone();
		ArrayList<Point> cango = getCangoList(boardCal, 3);
		for (Point e : cango) {
			if (boardCal[e.y][e.x] == Chessman.BLANK_SPACE) {

				// ģ����һ��
				// TODO ģ����ˣ����ࣩ�߲����Ƚ�ȡ���ӵص�
				boardCal[e.y][e.x] = getEnemyChessman();
				int aiScore = caculatePerPoint(new Point(e.x, e.y), boardCal);

				boardCal[e.y][e.x] = getChessman();
				int humScore = caculatePerPoint(new Point(e.x, e.y), boardCal);

				// ����ɾ��
				boardCal[e.y][e.x] = Chessman.BLANK_SPACE;

				int score = Math.max(aiScore, humScore);
				score = aiScore + humScore;
				// System.out.println("boardCal[" + e.y + "][" + e.x + "] score : " + score);
				if (score > maxScore) {
					p = e;
					maxScore = score;
				}
			}
		}
		return p;
	}

	/**
	 * ���㣬�������ռ�����
	 */
	private Point caculate() {
		Point point = null;
		Chessman[][] boardCal = new Chessman[boardCopy.length][boardCopy[0].length];
		for (int i = 0; i < boardCopy.length; i++) {
			for (int j = 0; j < boardCopy[i].length; j++) {
				boardCal[i][j] = boardCopy[i][j];
			}
		}

		// ���ɿɼ�¼�����Ŀ��ߵ�����飬�ڵ����в��ϸ��¸�ֵ������������ѡȡ��߷ֶ�Ӧ�㷵��(��ζ��max_min�м���������жϣ����ܵõ����ҵ������Ž�)��
		ArrayList<Point> cangolist = getCangoList(boardCal, 2);

		Point_Score[] point_Scores = new Point_Score[cangolist.size()];
		for (int i = 0; i < point_Scores.length; i++) {
			point_Scores[i] = new Point_Score(cangolist.get(i));
		}

		// ����С����
		// max_min(3, 0, 0, getChessman(), boardCal, point_Scores);
		int best = Integer.MIN_VALUE;
		for (Point_Score p : point_Scores) {
			boardCal[p.p.y][p.p.x] = getChessman();
			int temp = min(2, Integer.MIN_VALUE, Integer.MAX_VALUE, boardCal);

			boardCal[p.p.y][p.p.x] = Chessman.BLANK_SPACE;
			if (best < temp) {
				System.out.println("--- best:" + temp + " point: " + p.p.x + " " + p.p.y);
				best = temp;
				point = p.p;
			}
		}
		
		System.out.println("best Point: ("+ point.x+","+point.y+")");
		return point;
	}

	/**
	 * ����С����������������������ڱ�ѡ�㼯��
	 */
	private int max(int deep, int alpha, int beata, Chessman[][] boardCal) {
		if (deep <= 0) {
			// System.out.println("max deepth:"+deep+" best:"+evaluate(boardCal));
			return evaluate(boardCal);
		}
		int best = Integer.MIN_VALUE;
		ArrayList<Point> cango = getCangoList(boardCal, 2);

		for (Point p : cango) {
			boardCal[p.y][p.x] = getChessman();
			int temp = min(deep - 1, alpha, beata, boardCal);
			boardCal[p.y][p.x] = Chessman.BLANK_SPACE;
			if (best < temp) {
				best = temp;
			}
			if(temp>alpha) {
				alpha=temp;
			}
			if(alpha>beata) {
				return alpha;
			}
		}
		// System.out.println("deepth:"+deep+" best:"+best);
		return alpha;
	}

	private int min(int deep, int alpha, int beata, Chessman[][] boardCal) {
		if (deep <= 0) {
			// System.out.println("min deepth:"+deep+" best:"+evaluate(boardCal));
			return evaluate(boardCal);
		}
		int best = Integer.MAX_VALUE;
		ArrayList<Point> cango = getCangoList(boardCal, 2);

//		int cnt=0;
//		for(Point p:cango) {
//			cnt++;
//			System.out.print(p.x+" "+p.y+"\t");
//			if(cnt%5==0) {
//				System.out.println();
//			}
//		}
//		System.out.println();

		for (Point p : cango) {
			boardCal[p.y][p.x] = getEnemyChessman();
			int temp = max(deep - 1, alpha, beata, boardCal);
			boardCal[p.y][p.x] = Chessman.BLANK_SPACE;
			if (best > temp) {
				best = temp;
			}
			if(temp<beata) {
				beata=temp;
			}
			if(alpha>beata) {
				return beata;
			}
		}
		// System.out.println("deepth:"+deep+" best:"+best);
		return beata;
	}

	private Chessman getSwapChessman(Chessman role) {

		if (role != null) {
			return (role == Chessman.BLACK_CHESS ? Chessman.WHITE_CHESS : Chessman.BLACK_CHESS);
		}
		return null;
	}

	/**
	 * ����������������ǰ�����ܷ� ���ֵ���
	 */
	private int evaluate(Chessman[][] board) {
		int result = 0;

		// �õ������������ӵ�����
		ArrayList<Point> points = getChessList(board);

		for (Point e : points) {
			result += caculatePerPoint(e, board);
		}
		return result;
	}

	/**
	 * �õ��������ӵ��б�
	 */
	private ArrayList<Point> getChessList(Chessman[][] board) {
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j] == Chessman.BLACK_CHESS || board[i][j] == Chessman.WHITE_CHESS) {
					list.add(new Point(j, i));
				}
			}
		}
		return list;
	}

	/**
	 * �õ����п����ߵ�λ���б�
	 */
	private ArrayList<Point> getCangoList(Chessman[][] board, int space) {
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				
				
				if (board[i][j] == Chessman.BLANK_SPACE) {
					for (int dy = -space; dy <= space; dy++) {
						for (int dx = -space; dx <= space; dx++) {
							if ((dx != 0 || dy != 0) && i + dy >= 0 && i + dy < BOARD_SIZE && j + dx >= 0
									&& j + dx < BOARD_SIZE) {
								if (board[i + dy][j + dx] == Chessman.BLACK_CHESS
										|| board[i + dy][j + dx] == Chessman.WHITE_CHESS) {
									list.add(new Point(j, i));
								}
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
		// System.out.println("(" + p.x + " " + p.y + ")");
		situationMap[4] = map[ny][nx];
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

						// System.out.println("temp:" + temp+" tempY:"+tempY+" tempX:"+tempX +
						// "map["+tempY+"]["+tempX+"]:"+map[tempY][tempX]);
						situationMap[temp] = map[tempY][tempX];
					} else {
						situationMap[temp] = null;
					}
				}
			}
			for (Chessman e : situationMap) {
				// System.out.print(" " + e);
			}
			// System.out.println();
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
						flag = true;
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

	private boolean getWinScore(Point_Score[] points) {
		for (Point_Score p : points) {
			if (p.score > 100000 || p.score < -100000) {
				return true;
			}
		}
		return false;
	}
}