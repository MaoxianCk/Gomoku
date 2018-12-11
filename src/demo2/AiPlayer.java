package demo2;

import java.util.ArrayList;

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
	 * 计算，返回最终计算结果
	 */
	private Point caculate() {
		Point p = null;
		int maxScore = 0;
		// 用于计算的board
		Chessman[][] boardCal = boardCopy.clone();
		ArrayList<Point> cango = getChessList(3, boardCal, false);
		for (Point e : cango) {
				System.out.println("boardCal[e.y][e.x]"+boardCal[e.y][e.x]+"getChessman()"+getChessman());
			if (boardCal[e.y][e.x] == Chessman.BLANK_SPACE) {
				//模拟走一步
				//TODO 模拟敌人（人类）走步，比较取下子地点
				erreo
				boardCal[e.y][e.x]=getChessman();
				int score = caculatePerPoint(new Point(e.y, e.x), boardCal);
				//走完删除
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
	 * 评估函数，评估当前棋盘上的总分
	 */
	private int evaluate(Chessman[][] board) {
		int result = 0;

		// 得到所有已有棋子的坐标
		ArrayList<Point> points = getChessList(0, board, true);
		for (Point e : points) {
			result += caculatePerPoint(e, board);
		}
		return result;
	}

	/**
	 * 根据space的间距判断原点+space*2范围内是否有其他棋子 space 间距，board
	 * 棋盘，hasChess搜索的点集合是否有棋子，true表示返回list中的点都不是空地，false表示list中的点都是空地
	 */
	private ArrayList<Point> getChessList(int space, Chessman[][] board, boolean hasChess) {
		ArrayList<Point> list = new ArrayList<Point>();
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				/**
				 * 判断该点周围5*5范围内是否有棋子
				 */
				if (hasChess == true && board[i][j] == Chessman.BLANK_SPACE) {
					// 如果hasChess为true且该点是空地则跳过
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
}