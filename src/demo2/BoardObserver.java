package demo2;
/**
 * �۲��߽ӿ�
 * @author MaoxianCk
 */
public interface BoardObserver {
	public void update(Chessman board[][],int wx[],int wy[],int nx,int ny,Status status);
}
