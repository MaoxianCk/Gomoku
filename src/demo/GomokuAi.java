package demo;
/**
 * Ai类,五子棋ai
 * 对当前回合棋局上的每个空位进行一次打分，计算自己与对方在这点下子的得分，以最高分数的点为AI下子点
 * @author 毛线 2018年8月8日
 * 2018年9月1日 更新注释，优化变量名称
 */
public class GomokuAi {
	private int x;// 保存计算之后的落子点
	private int y;// 保存计算之后的落子点
	private int aiPlayerNum;// 当前局AI的玩家编号
	private int enemyPlayerNum;// 当前局敌人(人类)的玩家编号
	private int mapScore[][];// 保存当前地图上每个点分数
	//private int difficultLever;//难度等级

	/**
	 * 默认构造函数，设置AI编号2
	 */
	GomokuAi() {
		aiPlayerNum = 2;
		enemyPlayerNum = 3 - aiPlayerNum;
		//setdifficult(2);
		mapScore = new int[Game.size][Game.size];
		System.out.println("创建AI成功...");
	}

	/**
	 * 构造函数，根据传入 玩家编号 及 难度 设定AI
	 */
	GomokuAi(int playerNum, int difficult) {
		aiPlayerNum = playerNum;
		enemyPlayerNum = 3 - aiPlayerNum;
		//setdifficult(difficult);
		mapScore = new int[Game.size][Game.size];
		System.out.println("创建AI成功...");
	}

	public void setAiPlayerNum(int num) {
		// 设定AI编号
		System.out.println("AI难度为num");
		aiPlayerNum = num;
	}

	public void setdifficult(int num) {
		/**
		 * 修改难度
		 */
		if (num < 0) {
			num = 0;
		}
		if (num > 7) {
			num = 7;
		}
	}

	/**
	 * 对传入的situationMap[]数组进行打分，返回评分值
	 * 根据Player参数(玩家编号)返回不同的计算值 AI(自己)与非AI(人类)打分不同
	 * 传入一个situationMap[9]数组，该数组应保存以situationMap[4]为中心的左右(抽象的两个方向)4范围内的棋子情况
	 */
	public int situation(int situationMap[]) {
		//对situationMap分析情况
		boolean[] die = { false, false };// 记录两端是否被堵死
		int Player = situationMap[4];// 当前计算编号为situationMap[4]元素，即中心元素
		int cnt = 1;// 连珠数量，本身算1
		int cntBlank = 0;// 左右两边连续的没有中断的空格数量 例如:0011 1 2000 这只算前面的00即cntBlank==2
		int[] dir = { -1, 1 };// 左右方向向量
		for (int i = 0; i < 2; i++) {
			//两边
			int tempPosition = 4;
			boolean flag = false; // 是否中断 true表示中断 中断之后此方向不再向后拓展，转向另一边进行拓展
			for (int j = 0; j < 4; j++) {
				// 两个方向各找四个
				tempPosition += dir[i];
				if (situationMap[tempPosition] == Player && !flag) {
					// 连续，并且此前没有中断
					cnt++;// 计算中点连珠
				} else if ((situationMap[tempPosition] == 3 - Player) || (situationMap[tempPosition] == -1) && !flag) {
					// 如果当前点非situationMap[4]棋子或者为-1(边界)并且此前没有中断 判定其中断一次，堵死
					die[i] = true;
					flag = true;
				} else {
					// 如果是空地
					if (situationMap[tempPosition] == 0 && !flag) {
						cntBlank++;
					}
					break;
				}
			}
		}

		//根据上段分析结果综合打分
		int score = 1;
		if (cnt >= 5) {
			if (Player == aiPlayerNum) {
				return 150000;
			} else {
				return 100000;
			}
		} else if (cnt == 4) {
			if (!die[0] && !die[1]) {
				// 活四
				if (Player == aiPlayerNum) {
					return 15000;
				} else {
					return 10000;
				}
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
				return 2600;
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

	/**
	 * 对map[ny][nx]打分 并返回结果 以map[ny][nx]为虚拟落子点的9*9范围 nowPlayer是ai的玩家编号
	 */
	private int caculatePerPoint(int nx, int ny, int map[][]) {
		// 三维数组记录横，纵，左斜，右斜
		int dir[][][] = { { { -1, 0 }, { 1, 0 } }, { { 0, -1 }, { 0, 1 } }, { { -1, -1 }, { 1, 1 } },
				{ { 1, -1 }, { -1, 1 } } };
		int dir2[] = { -1, 1 };
		int temp;
		int tempY;
		int tempX;
		int score = 0;
		int situationMap[] = new int[9];// 保存每个方向的棋子情况 [4+1+4] 交由situation()评估函数进行打分
		if (map[ny][nx] != 0) {// 如果计算点不是空的返回0
			return 0;
		}
		if (nx > 3 && nx < 11 && ny > 3 && ny < 11) {
			//中心范围内 基础分值高
			score += 50;
		}
		// 若周边没有棋子则跳过计算，直接返回当前位置的基础分值
		boolean hasChess = false;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 1; k++) {
					if (ny + dir[i][j][0] > 0 && ny + dir[i][j][0] < Game.size && nx + dir[i][j][1] > 0
							&& nx + dir[i][j][1] < Game.size) {
						if (map[ny + dir[i][j][0]][nx + dir[i][j][1]] > 0) {
							hasChess = true;
							break;
						}
					}
				}
				if (hasChess) {
					break;
				}
			}
			if (hasChess) {
				break;
			}
		}
		if (!hasChess) {
			return score;
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
					if (tempX >= 0 && tempX < Game.size && tempY >= 0 && tempY < Game.size) {
						situationMap[temp] = map[tempY][tempX];
					} else {
						situationMap[temp] = -1;
					}
				}
			}

			int scoreAi = 0, scoreEnemy = 0;
			// 在此点假设己方与对方计算两次，返回最大值，即永远返回对自己最有利的分数
			situationMap[4] = aiPlayerNum;
			scoreAi = situation(situationMap);
			situationMap[4] = enemyPlayerNum;
			scoreEnemy = situation(situationMap);
			score += (scoreAi >= scoreEnemy ? scoreAi : scoreEnemy);
		}
		return score;
	}

	/**
	 * 对每个点计算打分
	 */
	void caculate(int map[][]) {
		int maxScore = 0;//保存计算每个点过程中的最大分值并更新将落子点 x,y
		int temp = 0;
		for (int i = 0; i < Game.size; i++) {
			for (int j = 0; j < Game.size; j++) {

				// 计算在这点的得分
				temp = caculatePerPoint(j, i, map);
				mapScore[i][j] = temp;
				if (temp > maxScore && map[i][j] == 0) {
					maxScore = temp;
					y = i;
					x = j;
				} else if (temp == maxScore && map[i][j] == 0) {
					if ((int) (Math.random() * 5) < 1) {
						//若当前点与最高分相等则在两点间按概率决定 是否更新坐标
						y = i;
						x = j;
					}
				}
			}
		}

		System.out.println();
		System.out.println("得分情况 : ");
		System.out.print("最高分值 : " + maxScore);
		System.out.println("  位于 ( " + x + " , " + y + " )");
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}