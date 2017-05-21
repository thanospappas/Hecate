package gr.uoi.cs.daintiness.hecate.gui.swing;

import gr.uoi.cs.daintiness.hecate.Hecate;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ManualDialog extends JDialog{
	private JLabel name;
	private JLabel manualText;
	private JButton close;
	private ImageIcon hecateIcon;

	public ManualDialog(){
		initialize();
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		addName();
				
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		hecateIcon = new ImageIcon(Hecate.class.getResource("art/icon.png"));
		JLabel iconLabel = new JLabel(hecateIcon);
		iconLabel.setAlignmentX(0.5f);
		add(iconLabel);
		
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		addManualText();

		add(Box.createRigidArea(new Dimension(0, 15)));
		
		addCloseButton();
		
		
		add(Box.createRigidArea(new Dimension(0, 15)));
		
		draw();
	}
	
	private void addName(){
		name = new JLabel("      Hecate  0.2     ");
		name.setFont(new Font("Serif", Font.BOLD, 15));
		name.setAlignmentX(CENTER_ALIGNMENT);
		add(name);
	}
	
	private void addManualText(){
		manualText = new JLabel("<html><br>"
            + "<font color=#FF0000><b>&nbsp;&nbsp;&nbsp;&nbsp; Red color:</b></font> Deletion<br>"
			+ "<font color=#6dce0c><b>&nbsp;&nbsp;&nbsp;&nbsp; Green color:</b></font> Insertion <br>"
			+ "<font color=#f7e011><b>&nbsp;&nbsp;&nbsp;&nbsp; Yellow color:</b></font> Update <br>");
		manualText.setFont(new Font("Serif", Font.PLAIN, 13));
		manualText.setAlignmentX(CENTER_ALIGNMENT);
		add(manualText);
	}
	
	private void addCloseButton(){
		close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		close.setAlignmentX(CENTER_ALIGNMENT);
		add(close);
	}
	
	private void initialize() {
		setTitle("Manual");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void draw() {
		pack();
		setResizable(false);
		// center on screen
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - getWidth()/2, 
		            size.height/2 - getHeight()/2);
	}
}
