package demo2;
/**
 * ���۲��߽ӿ�
 * @author MaoxianCk
 */
public interface Observerable {
	public void registerObserver(Observer o);
    public void removeObserver(Observer o);
}
