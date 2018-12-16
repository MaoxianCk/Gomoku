package demo2;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;

import demo.Game;

/**
 * AI 玩家
 * 
 * @author MaoxianCk
 */
public class AiPlayer extends Player implements BoardObserver {
	private final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	// 真实棋盘的拷贝，实时更新
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

	// 观察者更新数据
	public void update(Chessman[][] board, int[] wx, int[] wy, int nx, int ny, Status status) {
		this.boardCopy = board;
	}

	/**
	 * AI部分，以下全为私有，均为AI内部判断...
	 */

	/**
	 * 简单版AI 一次评估单点成绩出结果
	 */
	private Point caculateEasy() {
		Point p = null;
		int maxScore = 0;
		// 用于计算的board
		Chessman[][] boardCal = boardCopy.clone();
		ArrayList<Point> cango = getCangoList(boardCal, 3);
		for (Point e : cango) {
			if (boardCal[e.y][e.x] == Chessman.BLANK_SPACE) {

				// 模拟走一步
				// TODO 模拟敌人（人类）走步，比较取下子地点
				boardCal[e.y][e.x] = getEnemyChessman();
				int aiScore = caculatePerPoint(new Point(e.x, e.y), boardCal);

				boardCal[e.y][e.x] = getChessman();
				int humScore = caculatePerPoint(new Point(e.x, e.y), boardCal);

				// 走完删除
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
	 * 计算，返回最终计算结果
	 */
	private Point caculate() {
		Point point = null;
		Chessman[][] boardCal = new Chessman[boardCopy.length][boardCopy[0].length];
		for (int i = 0; i < boardCopy.length; i++) {
			for (int j = 0; j < boardCopy[i].length; j++) {
				boardCal[i][j] = boardCopy[i][j];
			}
		}

		// 生成可记录分数的可走点的数组，在迭代中不断更新该值，最后从数组中选取最高分对应点返回(意味着max_min中计算可随意中断，都能得到以找到的最优解)。
		ArrayList<Point> cangolist = getCangoList(boardCal, 2);

		Point_Score[] point_Scores = new Point_Score[cangolist.size()];
		for (int i = 0; i < point_Scores.length; i++) {
			point_Scores[i] = new Point_Score(cangolist.get(i));
		}

		// 极大极小搜索
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
	 * 极大极小搜索，结果（分数）更新在备选点集中
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
	 * 评估函数，评估当前局势总分 不分敌我
	 */
	private int evaluate(Chessman[][] board) {
		int result = 0;

		// 得到所有已有棋子的坐标
		ArrayList<Point> points = getChessList(board);

		for (Point e : points) {
			result += caculatePerPoint(e, board);
		}
		return result;
	}

	/**
	 * 得到所有棋子的列表
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
	 * 得到所有可以走的位置列表
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
	 * 不分敌我，计算p点的分数
	 */
	private int caculatePerPoint(Point p, Chessman[][] map) {
		int nx = p.x;
		int ny = p.y;
		// 三维数组记录横，纵，左斜，右斜
		int dir[][][] = { { { -1, 0 }, { 1, 0 } }, { { 0, -1 }, { 0, 1 } }, { { -1, -1 }, { 1, 1 } },
				{ { 1, -1 }, { -1, 1 } } };
		int dir2[] = { -1, 1 };
		int temp;
		int tempY;
		int tempX;
		int score = 0;
		Chessman situationMap[] = new Chessman[9];// 保存每个方向的棋子情况 [4+1+4] 交由situation()评估函数进行打分
		// 空点不做计算
		if (map[ny][nx] == Chessman.BLANK_SPACE) {
			return 0;
		}

		if (nx > 3 && nx < 11 && ny > 3 && ny < 11) {
			// 中心范围内 基础分值高
			score += 50;
		}

		// 记录4个方向上的棋子情况(横，纵，左斜，右斜)，分别交给评估函数计算
		// System.out.println("(" + p.x + " " + p.y + ")");
		situationMap[4] = map[ny][nx];
		for (int i = 0; i < 4; i++) {
			// 两边
			for (int j = 0; j < 2; j++) {
				tempY = ny;
				tempX = nx;
				temp = 4;
				// 一个方向找四个
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
	 * caculatePerPoint子函数 针对以situationmap[4]为中心([4]为当前下的棋子点)，对这一方向横竖撇捺进行局势判断并打分
	 * 传入一个situationMap[9]数组，该数组应保存以situationMap[4]为中心的左右(抽象的两个方向)4范围内的棋子情况
	 */
	private int situation(Chessman situationMap[]) {
		// 对situationMap分析情况
		boolean[] die = { false, false };// 记录两端是否被堵死
		Chessman player = situationMap[4];// 当前计算编号为situationMap[4]元素，即中心元素
		int cnt = 1;// 连珠数量，本身算1
		int cntBlank = 0;// 左右两边连续的没有中断的空格数量 例如:0011 1 2000 这只算前面的00即cntBlank==2
		int[] dir = { -1, 1 };// 左右方向向量
		for (int i = 0; i < 2; i++) {
			// 两边
			int tempPosition = 4;
			boolean flag = false; // 是否中断 true表示中断 中断之后此方向不再向后拓展，转向另一边进行拓展
			for (int j = 0; j < 4; j++) {
				// 两个方向各找四个
				tempPosition += dir[i];
				Chessman tempChess = situationMap[tempPosition];
				if (tempChess == player && !flag) {
					// 连续，并且此前没有中断
					cnt++;// 计算中点连珠
				} else if ((tempChess != player && tempChess != Chessman.BLANK_SPACE) || (tempChess == null) && !flag) {
					// 如果当前点非situationMap[4]棋子或者为null(边界)并且此前没有中断 判定其在此中断
					die[i] = true;
					flag = true;
					break;
				} else {
					// 如果是空地
					if (tempChess == Chessman.BLANK_SPACE && !flag) {
						cntBlank++;
						flag = true;
					}
				}
			}
		}

		// 根据上段分析结果综合打分
		int score = 1;
		if (cnt >= 5) {
			return 100000;
		} else if (cnt == 4) {
			if (!die[0] && !die[1]) {
				// 活四
				return 10000;

			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 1))) {
				// 冲四
				return 5000;
			} else if (die[0] && die[1]) {
				// 死四
				return 4;
			}
		} else if (cnt == 3) {
			if (!die[0] && !die[1]) {
				// 活三
				return 1000;
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 2))) {
				// 冲三
				return 500;
			} else if (die[0] && die[1]) {
				// 死三
				return 3;
			}
		} else if (cnt == 2) {
			if (!die[0] && !die[1]) {
				// 活二
				return 100;
			} else if (((die[0] && !die[1]) || (!die[0] && die[1]) && (cntBlank >= 3))) {
				// 冲二
				return 50;
			} else if (die[0] && die[1]) {
				// 死二
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