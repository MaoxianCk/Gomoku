package demo2;

/**
 * AI Íæ¼Ò
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

	public void registerObserver(BoardObserver o) {
		// TODO Auto-generated method stub
		
	}

}