package demo2;
/**
 * 观察者接口
 * @author MaoxianCk
 */
public interface BoardObserver {
	public void update(Chessman board[][],int wx[],int wy[],int nx,int ny,Status status);
}
