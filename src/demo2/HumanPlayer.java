package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * �������
 * @author MaoxianCk
 */
public class HumanPlayer extends Player implements MouseListener {
	BoardObserver boardObserver;
	
	public HumanPlayer() {
		super("���",Chessman.BLANK_SPACE);
	}
	public HumanPlayer(Chessman chessman) {
		super("���",chessman);
	}
	
	public void putChess(Point p) {
		boardObserver.setChess(p, this.getChessman());
	}
	
	public void backChess() {
		
	}

	public void giveUp() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		//���ɵ������p
		Point p=null;
		//if(�ڵ����Χ�ڣ�������Ч)
		{
			putChess(p);
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerObserver(BoardObserver o) {
		this.boardObserver=o;
	}



}
