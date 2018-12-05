package demo2;
/**
 * 被观察者接口
 * @author MaoxianCk
 */
public interface Observerable {
	public void registerObserver(Observer o);
    public void removeObserver(Observer o);
}
