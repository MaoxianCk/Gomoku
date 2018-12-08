package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

/**
 * ��Ϸ���ȼ��������
 * 
 * @author MaoxianCk
 */
public class Game implements MouseObserver {

	private Player player[];
	private ChessBoard chessBoard;
	private int nowPlayerNum;
	private int humanPlayerNum;// ���������player[]��ı�ţ�0,1�� 2��ʾPVP
	private GameMode gameMode;

	// ��Ϸ�Ƿ����,0δ������1ʤ���Է֣�-1ƽ��
	private Status isEnd = Status.GAMING;
	private int winner ;

	private int round ;

	Game() {
		chessBoard = new ChessBoard();

		// TEST
		player = new Player[2];
		// if human first
		gameMode = GameMode.PVP;
		player[0] = new HumanPlayer();
		player[1] = new HumanPlayer();
		

		// if AI first
		// swap(player[0],player[1]);

		player[0].setChessman(Chessman.BLACK_CHESS);
		player[1].setChessman(Chessman.WHITE_CHESS);
	}
	public void start() {
		/**
		 * ��Ϸ��ʼǰ����
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

	public void setChessPanel(PanelObserver o) {
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
			JOptionPane.showMessageDialog(null, "ʤ������"+player[winner].getName());
			chessBoard.cleanBoard();
		}

	}
}
