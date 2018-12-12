package demo2;

import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import demo.Game;

/**
 * 棋盘类，储存棋盘的棋子 "游戏平台" 不对操作检查合法性 TODO 当自己改变（下棋，悔棋）给界面发送消息，刷新界面
 * 
 * @author MaoxianCk
 */
public class ChessBoard implements BoardObserverable {
	public static final int BOARD_SIZE = 15;
	
	
	
	private ArrayList<BoardObserver> observersList;
	private int winx[] = new int[2];
	private int winy[] = new int[2];
	private int nx=-1;
	private int ny=-1;
	// 棋盘
	private Chessman board[][];
	// 下棋栈，存放下棋的坐标记录，用于悔棋退回
	private Stack<Point> putStack;

	public ChessBoard() {
		observersList=new ArrayList<BoardObserver>();
		putStack = new Stack<Point>();
		board = new Chessman[BOARD_SIZE][BOARD_SIZE];
	}

	// 在p处放一个c棋子
	public void setChess(Point p, Chessman c) {
		System.out.println("在(" + p.x + "," + p.y + ")处，放了一个棋子");
		board[p.y][p.x] = c;
		nx=p.x;
		ny=p.y;
		putStack.push(p);
		// 发送棋盘信息
		sendMessages();
	}

	// 撤回上一步放的棋子
	public void backChess() {
		if (putStack.isEmpty() == true) {
			System.out.println("没有棋子可以撤回了...");
		} else {
			Point p = putStack.pop();
			board[p.y][p.x] = Chessman.BLANK_SPACE;
			System.out.println("在(" + p.x + "," + p.y + ")处，撤回了一个棋子");
		}
		// 发送棋盘信息
		sendMessages();
	}

	/**
	 * 判断是否胜负 返回值为int，1表示胜利，0表示胜负未分，-1表示平局（棋盘已满且胜负未分）
	 */
	public Status isEnd() {
		/*
		 * 检测是否分出胜负 以当前落子点p=栈顶 为中心向4个方向(横，竖，左斜，右斜)比对
		 * 如果是空栈，返回Gaming，游戏刚开始
		 */
		if (putStack.empty()) {
			return Status.GAMING;
		}
		Point p = putStack.peek();

		Status win = Status.GAMING;// 判断前设置初始状态
		int maxCnt = 0;// 记录最大连子数
		int cnt = 1;// 记录当前连子数，自己本身算1
		int tempX = p.x;// 循环判断的当前x变量
		int tempY = p.y;// 循环判断的当前y变量
		boolean sameFlag = false;// 表示是否连续即同色

		int dir[][][] = {
				// 方向向量
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
					// 如果同色，计算下一坐标点
					tempX = tempX + dir[i][j][0];
					tempY = tempY + dir[i][j][1];
					winx[j] = tempX;
					winy[j] = tempY;
					if (tempX >= 0 && tempX < BOARD_SIZE && tempY >= 0 && tempY < BOARD_SIZE) {
						// 越界检查
						if ((board[tempY][tempX] == board[p.y][p.x])) {
							// 连续
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
			// 若当前方向上连珠数达成，记录、修改数据并结束判断

			if (cnt >= 5) {
				win = Status.WIN;
				break;
			} else {
				win = Status.GAMING;
			}
		}

		if (isDraw() == true && win == Status.GAMING) {
			win = Status.DRAW;
		}
		return win;
	}

	public boolean isDraw() {
		/**
		 * 检查是否和局 检测棋盘是否有空位
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
			// 越界检查
			System.out.println("当前位置 ( " + p.x + " , " + p.y + " ) 合法");
			return true;
		} else if (board[p.y][p.x] != Chessman.BLANK_SPACE) {
			// 重复检查，不能覆盖已有棋子
			System.out.println("当前位置 ( " + p.x + " , " + p.y + " ) 已有棋子");
			return false;
		} else {
			System.out.println("当前位置 ( " + p.x + " , " + p.y + " ) 不合法");
			return false;
		}
	}

	public void cleanBoard() {
		System.out.println("清空棋盘...");
		nx=-1;
		ny=-1;
		winx=new int[2];
		winy=new int[2];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = Chessman.BLANK_SPACE;
			}
		}
		System.out.println("清空下棋记录...");
		putStack.clear();
		// 发送棋盘信息
		sendMessages();
	}

	public Chessman[][] getBoard() {
		return board.clone();
	}
//list sent messages
	@Override
	public void registerObserver(BoardObserver o) {
		observersList.add(o);
	}
	
	private void sendMessages() {
		for(BoardObserver list:observersList) {
			list.update(board.clone(), winx, winy, nx, ny, isEnd());
		}
	}
}
