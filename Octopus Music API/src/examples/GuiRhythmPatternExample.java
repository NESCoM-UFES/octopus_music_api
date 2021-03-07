package examples;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import octopus.gui.TimeEventPanel;

public class GuiRhythmPatternExample extends JFrame {
	public static void main(String[] args) {
		
		GuiRhythmPatternExample app = new GuiRhythmPatternExample();
		
		TimeEventPanel timeEventPanel = new TimeEventPanel( 4, 2, 4);
			
		JToolBar ferramentas = timeEventPanel.getToolBar();
		app.getContentPane().add(ferramentas, BorderLayout.WEST);
		app.getContentPane().add(new TimeEventPanel());
		
		
		app.setVisible(true);
		
	}

}
