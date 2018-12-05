package demo;
/**
 * 游戏运行主类，负责创建棋盘，监控并控制棋局的正常运行，包含棋盘运行的基本函数
 * 类对象在GameFrame中创建并调用相对应函数对棋盘进行操作
 * 包含各运行子类的对象，即 AI。此外调用 GameFrame与 GamePanel对象绘制棋盘
 */
public class Game {
	public static final int size = 15;// 棋盘大小 固定正方形
	public final int WIN_NUM = 5;// 胜利条件，连子的数量 五子连珠
	public int wx[], wy[];// 胜利时，连珠端点(2个)的落点位置(x0,y0)、(x1,y1),提供给GamePanel.paint(Graphics)绘制五子连珠

	private int win;// 是否胜利，值为-1、0、1。 -1表示和局，0表示胜负未分，1表示胜负已分
	private boolean legal=false;// 当前回合走法是否合法
	private int map[][]; // 棋盘，保存当前棋局状态，值为0,1,2。  0表示空，1表示黑子，2表示白子
	private int nowPlayer; // 当前玩家编号
	private int nx, ny; // 当前回合落子坐标
	private int round;// 回合数

	private GomokuAi gomokuAi;

	private GameFrame gameFrame;//未设置实体，由setGameFrame(GameFrame)设置实体，在GameFrame中调用函数进行设置
	private GamePanel panel;// 未设置实体，由setGamePanel(GamePanel)设置实体，在GamePanel中调用函数进行设置

	Game() {
		/**
		 * 构造函数 数据初始化
		 */
		System.out.println("创建Game类...");
		System.out.println("初始化Game类...");

		gomokuAi = new GomokuAi();
		map = new int[size][size];
		wx = new int[2];
	    wy = new int[2];
		win = 0;// 0表示胜负未分
		nowPlayer = 1;// 黑子先行

		System.out.println("Game类初始化成功!");
		System.out.println("Game类创建成功!");
	}

	void setGameFrame(GameFrame gf) {
		/**
		 * 设置实体，在GameFrame中调用函数进行设置
		 */
		gameFrame = gf;
	}

	void setGamePanel(GamePanel pan) {
		/**
		 * 设置实体，在GamePanel中调用函数进行设置
		 */
		panel = pan;
	}

	public void initialize() {
		/**
		 * 初始化棋盘及状态
		 * 当前玩家设置 黑子 1,回合数设置0,胜负状态设置 胜负未分 0,清空棋盘棋子状态
		 */
		System.out.println("初始化棋盘...");
		nowPlayer = 1;
		round = 0;
		win = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = 0;
			}
		}
		panel.repaint();
		System.out.print("初始化棋盘成功!");
	}


	private void swapPlayer() {
		/**
	     * 交换当前玩家
	     */
		nowPlayer = (nowPlayer == 1 ? 2 : 1);
		round++;
		System.out.println();
		System.out.println("当前回合数 : " + round);
		//System.out.println("交换玩家");
		System.out.println("当前玩家 玩家" + nowPlayer);
	}

	public boolean isLegal() {
		/**
		 * 判断(nx,ny)是否是合法下子点 合法返回true
		 */
		System.out.println();

		if (map[ny][nx] == 0 && nx >= 0 && ny >= 0 && nx < size && ny < size) {
			//越界检查
			legal=true;
			System.out.println("当前位置 ( " + nx + " , " + ny + " ) 合法");
			return true;
		} else if (map[ny][nx] != 0) {
			//重复检查，不能覆盖已有棋子
			legal=false;
			System.out.println("当前位置 ( " + nx + " , " + ny + " ) 已有棋子");
			return false;
		} else {
			legal=false;
			System.out.println("当前位置 ( " + nx + " , " + ny + " ) 不合法");
			return false;
		}
	}

	public boolean isEnd() {
		/**
		 * 检查是否和局
		 * 检测棋盘是否有空位
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
		 * 检测是否分出胜负
		 * 以当前落子点(nx,ny)为中心向4个方向(横，竖，左斜，右斜)比对
		 */
		win = 0;// 判断前设置初始状态
		int maxCnt = 0;// 记录最大连子数
		int cnt = 1;// 记录当前连子数，自己本身算1
		int tempX = nx;// 循环判断的当前x变量
		int tempY = ny;// 循环判断的当前y变量
		boolean sameFlag = false;// 表示是否连续即同色

		int dir[][][] = {
			//方向向量
			// 横
			{ { -1, 0 }, { 1, 0 } },
			// 竖
			{ { 0, -1 }, { 0, 1 } },
			// 左斜
			{ { -1, -1 }, { 1, 1 } },
			// 右斜
			{ { 1, -1 }, { -1, 1 } } };

		for (int i = 0; i < 4; i++) {
			// 四个方向循环判断
			cnt = 1;// 记录当前方向上连子数，自己本身算1
			for (int j = 0; j < 2; j++) {
				// 两边
				sameFlag = true;// 是否同色
				while (sameFlag) {
					//如果同色，计算下一坐标点
					tempX = tempX + dir[i][j][0];
					tempY = tempY + dir[i][j][1];
					wx[j] = tempX;
					wy[j] = tempY;
					if (tempX >= 0 && tempX < size && tempY >= 0 && tempY < size) {
						// 越界检查
						if ((map[tempY][tempX] == map[ny][nx])) {
							//连续
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
			//若当前方向上连珠数达成，记录、修改数据并结束判断
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
			System.out.println("胜利者是玩家" + nowPlayer + "!");
			return true;
		} else if (isEnd()) {
			return false;
		} else {
			return false;
		}
	}

	public void pvp(int x, int y) {
		/**
		 * 玩家对玩家
		 * 判断当前落子合法性，,判断胜负，交换玩家
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
		 * 玩家对AI
		 * 判断当前落子合法性，判断胜负，交由AI下子
		 */
		nx = x;
		ny = y;
		if (isLegal()) {
			if (nowPlayer != aiPlayerNum) {
				// 玩家部分
				System.out.println("玩家下于 ( " + nx + " , " + ny + " )");
				map[ny][nx] = nowPlayer;
				swapPlayer();
				panel.repaint();
				if (isWin()) {
					return;
				}
			}
			if (nowPlayer == aiPlayerNum) {
				// Ai部分
				gomokuAi.caculate(map.clone());//传递.clone保证ai不对map进行修改
				nx = gomokuAi.getX();
				ny = gomokuAi.getY();

				System.out.println();
				System.out.println("AI下于 ( " + nx + " , " + ny + " )");

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
