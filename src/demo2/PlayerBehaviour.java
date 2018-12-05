package demo2;


/**
 * 棋手接口
 * 行为：下棋，悔棋，认输
 * @author MaoxianCk
 */
public interface PlayerBehaviour {
	//下棋
	public void putChess(Point p);
	//悔棋
	public void backChess();
	//认输
	public void giveUp();
}
