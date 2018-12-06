package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 游戏调度及运行入口
 * 
 * @author MaoxianCk
 */
public class Game implements MouseListener {
	final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	final int spacing = ChessPanel.spacing;

	private Player player[];
	private HumanPlayer humanPlayer;
	private AiPlayer aiPlayer;
	private ChessBoard chessBoard;
	private int nowPlayerNum;
	private int humanPlayerNum;//人类玩家在player[]里的编号（0,1）  2表示PVP

	// 游戏是否结束,0未结束，1胜负以分，-1平局
	private Status isEnd = Status.GAMING;
	private int winner = 0;

	private int round = 0;

	Game() {
		humanPlayer = new HumanPlayer();
		aiPlayer = new AiPlayer();
		chessBoard = new ChessBoard();

		// TEST
		player = new Player[2];
		//if human first
		player[0] = humanPlayer;
		player[1] = aiPlayer;
		//if ai first
		//swap(player[0],player[1]);
	}

	public void PVC(Point p) {
		for (int i = 0; i < 2; i++) {
			nowPlayerNum = round % 2;
			
			p=player[i].putChess(p);
			
			if (chessBoard.isLegal(p)) {
				chessBoard.setChess(p, player[nowPlayerNum].getChessman());
				isEnd = chessBoard.isEnd();
			}
			if (isEnd != Status.GAMING) {
				winner = nowPlayerNum;
				System.out.println("winner is " + winner);
			}
			round++;
		}
	}
	public void PVP(Point p) {
		nowPlayerNum = round % 2;
		
		p=player[nowPlayerNum].putChess(p);
		
		if (chessBoard.isLegal(p)) {
			chessBoard.setChess(p, player[nowPlayerNum].getChessman());
			isEnd = chessBoard.isEnd();
		}
		if (isEnd != Status.GAMING) {
			winner = nowPlayerNum;
			System.out.println("winner is " + winner);
		}
		round++;
	}

	public int getWinner() {
		return winner;
	}

	public int getRound() {
		return round;
	}

	public Status getIsEnd() {
		return isEnd;
	}

	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		Game game = new Game();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x, y;
		Point p = new Point();
		// 鼠标点击事件处理
		System.out.println("--------------------------------------");
		x = e.getX();// 获取鼠标点击位置坐标
		y = e.getY();
		// 检查越界及点击误差判断
		if (x >= 15 && x < BOARD_SIZE * spacing + 15 && y >= 15 && y < BOARD_SIZE * spacing + spacing / 2) {
			x = (x + BOARD_SIZE) / spacing - 1; // 是为了取得交叉点的坐标，将鼠标点击坐标转化为棋盘具体坐标(x,y)：0-14
			y = (y + BOARD_SIZE) / spacing - 1;
			if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
				p.x = x;
				p.y = y;
				PVC(p);// 调用pvc游戏模式
				// PVP(p);//调用pvp游戏模式
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public int getHumanPlayerNum() {
		return humanPlayerNum;
	}

	public void setHumanPlayerNum(int humanPlayerNum) {
		this.humanPlayerNum = humanPlayerNum;
	}
}
