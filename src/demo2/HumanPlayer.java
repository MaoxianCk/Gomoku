package demo2;


/**
 * 人类玩家
 * @author MaoxianCk
 */
public class HumanPlayer extends Player {
	BoardObserver boardObserver;
	
	public HumanPlayer() {
		super("玩家",Chessman.BLANK_SPACE);
	}
	
	public HumanPlayer(Chessman chessman) {
		super("玩家",chessman);
	}
	
	public Point putChess(Point p) {
		return p;
	}
}
