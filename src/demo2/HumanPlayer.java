package demo2;


/**
 * �������
 * @author MaoxianCk
 */
public class HumanPlayer extends Player {
	
	public HumanPlayer() {
		super("���",Chessman.BLANK_SPACE);
	}
	
	public HumanPlayer(Chessman chessman) {
		super("���",chessman);
	}
	
	public Point putChess(Point p) {
		return p;
	}
}
