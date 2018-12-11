package demo2;

import javax.swing.JOptionPane;
/**
 * 游戏调度及运行入口
 * 
 * @author MaoxianCk
 */
public class Game implements MouseObserver {

	private Player player[];
	private ChessBoard chessBoard;
	private int nowPlayerNum;
	private int humanPlayerNum;// 人类玩家在player[]里的编号（0,1） 2表示PVP
	private GameMode gameMode;

	// 游戏是否结束,0未结束，1胜负以分，-1平局
	private Status isEnd = Status.GAMING;
	private int winner ;

	private int round ;

	Game() {
		chessBoard = new ChessBoard();

		// TEST
		player = new Player[2];
////		 PVP MODE
//			gameMode = GameMode.PVP;
//			player[0] = new HumanPlayer();
//			player[1] = new HumanPlayer();
		
		//PVC MODE
		gameMode=GameMode.PVC;
		player[0]=new HumanPlayer();
		
		AiPlayer aiPlayer=new AiPlayer();
		chessBoard.registerObserver(aiPlayer);
		player[1]=aiPlayer;

		// if AI first
		// swap(player[0],player[1]);

		player[0].setChessman(Chessman.BLACK_CHESS);
		player[1].setChessman(Chessman.WHITE_CHESS);
	}
	public void start() {
		/**
		 * 游戏开始前处理
		 */
		isEnd=Status.GAMING;
		round=0;
		chessBoard.cleanBoard();
	}
	public void PVC(Point p) {
		for (int i = 0; i < 2; i++) {
			nowPlayerNum = round % 2;

			p = player[i].putChess(p);
			System.out.println("Player[" + i + "]" + player[i].getChessman());
			if (chessBoard.isLegal(p)) {
				chessBoard.setChess(p, player[i].getChessman());
				isEnd = chessBoard.isEnd();
				round++;
			}else {
				i--;
			}
			if (isEnd != Status.GAMING) {
				winner = nowPlayerNum;
				System.out.println("winner is " + winner);
				break;
			}
		}
	}

	public void PVP(Point p) {
		nowPlayerNum = round % 2;

		p = player[nowPlayerNum].putChess(p);

		if (chessBoard.isLegal(p)) {
			chessBoard.setChess(p, player[nowPlayerNum].getChessman());
			isEnd = chessBoard.isEnd();
			round++;
		}
		if (isEnd != Status.GAMING) {
			winner = nowPlayerNum;
			System.out.println("winner is " + winner);
		}
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
		gameFrame.setGameMouseListener(game);
		game.setChessPanel(gameFrame.getChessPanel());
		game.start();
	}

	public int getHumanPlayerNum() {
		return humanPlayerNum;
	}

	public void setHumanPlayerNum(int humanPlayerNum) {
		this.humanPlayerNum = humanPlayerNum;
	}

	public void setChessPanel(BoardObserver o) {
		chessBoard.registerObserver(o);
	}

	@Override
	public void mouseAction(Point p) {
		if (gameMode == GameMode.PVC) {
			PVC(p);
		} else if (gameMode == GameMode.PVP) {
			PVP(p);
		}
		if(isEnd==Status.WIN) {
			JOptionPane.showMessageDialog(null, "胜利者是"+player[winner].getName());
			chessBoard.cleanBoard();
		}

	}
}
