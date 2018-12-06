package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * ��Ϸ���ȼ��������
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
	private int humanPlayerNum;//���������player[]��ı�ţ�0,1��  2��ʾPVP

	// ��Ϸ�Ƿ����,0δ������1ʤ���Է֣�-1ƽ��
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
		// ������¼�����
		System.out.println("--------------------------------------");
		x = e.getX();// ��ȡ�����λ������
		y = e.getY();
		// ���Խ�缰�������ж�
		if (x >= 15 && x < BOARD_SIZE * spacing + 15 && y >= 15 && y < BOARD_SIZE * spacing + spacing / 2) {
			x = (x + BOARD_SIZE) / spacing - 1; // ��Ϊ��ȡ�ý��������꣬�����������ת��Ϊ���̾�������(x,y)��0-14
			y = (y + BOARD_SIZE) / spacing - 1;
			if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
				p.x = x;
				p.y = y;
				PVC(p);// ����pvc��Ϸģʽ
				// PVP(p);//����pvp��Ϸģʽ
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
