package demo2;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener,MouseObserverable {

	final int BOARD_SIZE = ChessBoard.BOARD_SIZE;
	final int spacing = ChessPanel.spacing;
	
	private MouseObserver mouseObserver;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x, y;
		Point p = new Point();
		// 鼠标点击事件处理
		System.out.println("--------------------------------------");
		x = e.getX();// 获取鼠标点击位置坐标
		y = e.getY();
		System.out.println("鼠标点击位置("+x+","+y+")");
		// 检查越界及点击误差判断
		if (x >= 15 && x < BOARD_SIZE * spacing + 15 && y >= 15 && y < BOARD_SIZE * spacing + 15) {
			
			// 是为了取得交叉点的坐标，将鼠标点击坐标转化为棋盘具体坐标(x,y)：0-14
			x = (x + BOARD_SIZE) / spacing - 1; 
			y = (y + BOARD_SIZE) / spacing - 1;
			if (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE) {
				p.x = x;
				p.y = y;
				mouseObserver.mouseAction(p);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerObserver(MouseObserver o) {
		this.mouseObserver=o;
		
	}
}
