package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

/**
 * 游戏调度及运行入口
 * 
 * @author MaoxianCk
 */
public class Game implements MouseObserver {

	private Player player[];
	private HumanPlayer humanPlayer;
	private AiPlayer aiPlayer;
	private ChessBoard chessBoard;
	private int nowPlayerNum;
	private int humanPlayerNum;//人类玩家在player[]里的编号（0,1）  2表示PVP
	private GameMode gameMode;

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
		gameMode=GameMode.PVC;
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
		gameFrame.setGameMouseListener(game);
	}


	public int getHumanPlayerNum() {
		return humanPlayerNum;
	}

	public void setHumanPlayerNum(int humanPlayerNum) {
		this.humanPlayerNum = humanPlayerNum;
	}

	@Override
	public void mouseAction(Point p) {
		if(gameMode==GameMode.PVC) {
			PVC(p);
		}
		else if(gameMode==GameMode.PVP) {
			PVP(p);
		}
		
	}
}
