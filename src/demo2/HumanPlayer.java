package demo2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 人类玩家
 * @author MaoxianCk
 */
public class HumanPlayer extends Player implements MouseListener {
	BoardObserver boardObserver;
	
	public HumanPlayer() {
		super("玩家",Chessman.BLANK_SPACE);
	}
	public HumanPlayer(Chessman chessman) {
		super("玩家",chessman);
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
		//生成点击坐标p
		Point p=null;
		//if(在点击范围内，并且有效)
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
