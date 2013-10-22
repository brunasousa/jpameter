package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import view.ConectionView;

public class ActionsController implements ActionListener{
	private ConectionView cv;
	private ConectionController cc;
	
	public ActionsController(ConectionView cv){
		this.cv = cv;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(cv!=null){
			cc = new ConectionController();
			cc.getConnection(cv.getUrl(), cv.getUser(), cv.getPass());
			ArrayList<String> dbs = cc.buscarDataBases();
			if(dbs.size()>0){
				cv.getcbBD().removeAllItems();
				
				for(String db : dbs)
					cv.getcbBD().addItem(db);
				
				cv.getcbBD().setEnabled(true);
				cv.getcbBD().repaint();
			}
		}
	}

}
