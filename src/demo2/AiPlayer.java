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
	
	public Point putChess(Point p) {
		p=caculate();
		return p;
	}
	
	/**
	 * ¼ÆËãÂäµã
	 * @return
	 */
	private Point caculate() {
		Point p=new Point();
		return p;
	}
}