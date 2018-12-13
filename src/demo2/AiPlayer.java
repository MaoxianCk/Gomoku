package demo2;

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
		p = caculateEasy();
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
		ArrayList<Point> cango = getChessList(3, boardCal, false, null);
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
		Chessman[][] boardCal = boardCopy.clone();
		// ���ɿɼ�¼�����Ŀ��ߵ�����飬�ڵ����в��ϸ��¸�ֵ������������ѡȡ��߷ֶ�Ӧ�㷵��(��ζ��max_min�м���������жϣ����ܵõ����ҵ������Ž�)��
		ArrayList<Point> cangolist = getChessList(3, boardCal, false, getChessman());
		Point_Score[] point_Scores = new Point_Score[cangolist.size()];
		for (int i = 0; i < point_Scores.length; i++) {
			point_Scores[i] = new Point_Score(cangolist.get(i));
		}

		// ����С����
		max_min(7, 0, 0, getChessman(), boardCal, point_Scores);

		// �ҵ����ߵ������ŵ�
		int max = 0;
		for (int i = 0; i < point_Scores.length; i++) {
			if (max < point_Scores[i].score) {
				max = point_Scores[i].score;
				point = point_Scores[i].p;
			}
		}
		return point;
	}

	/**
	 * ����С����������������������ڱ�ѡ�㼯��
	 */
	private void max_min(int deep, int alpha, int beata, Chessman role, Chessman[][] boardCal, Point_Score[] points) {

		// ����ָ����Ȼ��߷ֳ�ʤ��
		if (deep <= 0) {
			return;
		}
		ArrayList<Point> cango=getChessList(3, boardCal, false, getSwapChessman(role));
		for(Point p:cango) {
			boardCal[p.y][p.x]=role;
			max_min(deep-1, -beata, -alpha, getSwapChessman(role), boardCal, points);
			boardCal[p.y][p.x]=Chessman.BLANK_SPACE;
		}

	}

	private Chessman getSwapChessman(Chessman role) {
		
		if(role!=null) {
			return (role==Chessman.BLACK_CHESS?Chessman.WHITE_CHESS:Chessman.BLACK_CHESS);
		}
		return null;
	}

	/**
	 * ����������������ǰ�����ܷ� ���ֵ���
	 */
	private int evaluate(Chessman[][] board) {
		int result = 0;

		// �õ������������ӵ�����
		ArrayList<Point> points = getChessList(0, board, true, null);
		for (Point e : points) {
			result += caculatePerPoint(e, board);
		}
		return result;
	}

	/**
	 * ����space�ļ���ж�ԭ��+space*2��Χ���Ƿ����������� space ��࣬board
	 * ���̣�hasChess�����ĵ㼯���Ƿ������ӣ�true��ʾ����list�еĵ㶼���ǿյأ�false��ʾlist��[y][x]�ĵ㶼�ǿյ�
	 * ,roleҪ����[i][j]�Ķ������ӣ�null������
	 */
	private ArrayList<Point> getChessList(int space, Chessman[][] board, boolean hasChess, Chessman role) {
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
				// ���role����null �ж�[i][j]�ǲ���Ҫ�����Ķ���role ��������[i][j]
				if (role != null) {
					if (board[i][j] != role) {
						continue;
					}
				}
				boolean mark = false;
				for (int y = i - space; ((y < i + space) && (mark == false)); y++) {
					for (int x = j - space; ((x < j + space) && (mark == false)); x++) {
						if (y >= 0 && y < BOARD_SIZE && x >= 0 && x < BOARD_SIZE) {
							if (board[y][x] != Chessman.BLANK_SPACE || hasChess == true) {
								// System.out.println("(" + j + "," + i + ")");
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