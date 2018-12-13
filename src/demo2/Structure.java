package demo2;

class Point {
	int x;
	int y;
	Point(){
		x=0;
		y=0;
	}
	Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

//
class Point_Score {
	public Point p;
	public int score;
	public Point_Score() {
		super();
		score=0;
	}
	Point_Score(Point p){
		this.p=p;
		score=0;
	}
	Point_Score(Point p,int score){
		this.p=p;
		this.score=score;
	}
}

enum Chessman {
	BLANK_SPACE, // �հף�blank��black̫�ӽ�����space
	BLACK_CHESS, WHITE_CHESS;
}

enum Status {
	WIN, // ʤ
	GAMING, // ʤ��δ��
	DRAW;// ƽ��
}

enum GameMode {
	PVC, PVP;
}