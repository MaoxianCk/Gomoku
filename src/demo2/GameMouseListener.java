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
		// ������¼�����
		System.out.println("--------------------------------------");
		x = e.getX();// ��ȡ�����λ������
		y = e.getY();
		System.out.println("�����λ��("+x+","+y+")");
		// ���Խ�缰�������ж�
		if (x >= 15 && x < BOARD_SIZE * spacing + 15 && y >= 15 && y < BOARD_SIZE * spacing + 15) {
			
			// ��Ϊ��ȡ�ý��������꣬�����������ת��Ϊ���̾�������(x,y)��0-14
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
