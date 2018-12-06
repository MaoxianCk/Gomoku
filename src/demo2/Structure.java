package demo2;

class Point {
	int x;
	int y;
}

enum Chessman{
	BLANK_SPACE,//空白，blank与black太接近用了space
	BLACK_CHESS,
	WHITE_CHESS;
}

enum Status{
	WIN,//胜
	GAMING,//胜负未分
	DRAW;//平局
}

enum GameMode{
	PVC,
	PVP;
}