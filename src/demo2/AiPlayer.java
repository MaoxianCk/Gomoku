package demo2;

/**
 * AI ���
 * @author MaoxianCk
 */
public class AiPlayer extends Player {
	
	public AiPlayer() {
		super("AI",Chessman.BLANK_SPACE);
	}
	
	public AiPlayer(Chessman chessman) {
		super("AI",chessman);
	}
	
	public void putChess(Point p) {
		
	}

	public void backChess() {
		//AI����Ҫ����
	}

	public void giveUp() {
		//AI��������
	}

	public void registerObserver(BoardObserver o) {
		// TODO Auto-generated method stub
		
	}

}