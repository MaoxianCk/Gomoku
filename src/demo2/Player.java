package demo2;

public abstract class Player implements PlayerBehaviour {
	private Chessman chessman;
	private String name;

	public Player() {
		this.chessman = Chessman.BLANK_SPACE;
		this.name = null;
	}
	public Player(String name,Chessman chessman) {
		this.chessman = chessman;
		this.name=name;
	}

	public Chessman getChessman() {
		return chessman;
	}

	public void setChessman(Chessman chessman) {
		this.chessman = chessman;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
