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