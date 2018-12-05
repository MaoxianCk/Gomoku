package demo2;

/**
 * AI 玩家
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
		//AI不需要悔棋
	}

	public void giveUp() {
		//AI不会认输
	}

	public void registerObserver(BoardObserver o) {
		// TODO Auto-generated method stub
		
	}

}