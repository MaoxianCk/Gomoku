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
	
	public Point putChess(Point p) {
		p=caculate();
		return p;
	}
	
	/**
	 * �������
	 * @return
	 */
	private Point caculate() {
		Point p=new Point();
		return p;
	}
}