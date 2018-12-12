package demo2;

public abstract class Player implements PlayerBehaviour {
	private Chessman chessman;
	private Chessman enemyChessman;
	private String name;

	public Player() {
		setChessman(null);
		setName(null);
	}
	public Player(String name,Chessman chessman) {
		setChessman(chessman);
		setName(name);
	}

	public Chessman getChessman() {
		return chessman;
	}

	public void setChessman(Chessman chessman) {
		this.chessman = chessman;
		setEnemyChessman();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Chessman getEnemyChessman() {
		return enemyChessman;
	}
	private void setEnemyChessman() {
		this.enemyChessman=(getChessman()==Chessman.BLACK_CHESS?Chessman.WHITE_CHESS:Chessman.BLACK_CHESS);
	}

}
