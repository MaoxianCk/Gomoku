package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 游戏调度及运行入口
 * 
 * @author MaoxianCk
 */
public class Game implements MouseListener {
	Player player[];
	HumanPlayer humanPlayer;
	AiPlayer aiPlayer;
	ChessBoard chessBoard;

	// 游戏是否结束,0未结束，1胜负以分，-1平局
	int isEnd = 0;
	int winner = 0;

	int round = 0;

	Game() {
		humanPlayer = new HumanPlayer();
		aiPlayer = new AiPlayer();
		chessBoard = new ChessBoard();

		// TEST
		player=new Player[2];
		player[0] = humanPlayer;
		player[1] = aiPlayer;
	}

	public void run() {
		int nowPlayer=round%2;
		Point p=null;
		player[nowPlayer].putChess(p);
		if (chessBoard.isLegal(p)) {
			chessBoard.setChess(p, player[nowPlayer].getChessman());
			isEnd = chessBoard.isEnd();
		}
		if (isEnd != 0) {
			winner = 1;
			System.out.println("winner is " + winner);
		}
	}

	public static void main(String[] args) {
		GameFrame gameFrame = new GameFrame();
		Game game = new Game();

		game.run();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
}
