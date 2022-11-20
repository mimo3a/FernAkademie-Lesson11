package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Editor extends JFrame {

	private JTextArea eingabeFeld;
	JMenuBar menu;
	JMenu dateimenu;
	JMenuItem dateiNeu, dateiOffnen, dateiSpeichern, dateiBeenden;

	public Editor(String titel) {
		super(titel);

		setLayout(new BorderLayout());
		eingabeFeld = new JTextArea();
		add(new JScrollPane(eingabeFeld), BorderLayout.CENTER);

		menu = new JMenuBar();
		dateimenu = new JMenu("Datei");
		
		dateiNeu = new JMenuItem();
		dateiNeu.setText("Neu");
		dateiNeu.setIcon(new ImageIcon("icons/new24.gif"));
		dateiNeu.setActionCommand("neu");
		dateiNeu.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
		
		dateiOffnen = new JMenuItem();
		dateiOffnen.setText("Offnen");
		dateiOffnen.setIcon(new ImageIcon("icons/open24.gif"));
		dateiOffnen.setActionCommand("open");
		dateiOffnen.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
		
		dateiSpeichern = new JMenuItem();
		dateiSpeichern.setText("Speichern");
		dateiSpeichern.setIcon(new ImageIcon("icons/save24.gif"));
		dateiSpeichern.setActionCommand("save");
		dateiSpeichern.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));

		dateiBeenden = new JMenuItem();
		dateiBeenden.setText("Beenden");
		dateiBeenden.setActionCommand("exit");
		
		MyListener listener = new MyListener();
		dateiNeu.addActionListener(listener);
		dateiOffnen.addActionListener(listener);
		dateiSpeichern.addActionListener(listener);
		dateiBeenden.addActionListener(listener);

		dateimenu.add(dateiNeu);
		dateimenu.add(dateiOffnen);
		dateimenu.addSeparator();
		dateimenu.add(dateiSpeichern);
		dateimenu.addSeparator();
		dateimenu.add(dateiBeenden);
		
		menu.add(dateimenu);
		setJMenuBar(menu);

		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(600, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	private void dateiNeu() {
		if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich ein neues Dokument einlegen?", "Neues Dokument",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			eingabeFeld.setText("Hallo World !!!");
		}
	}
	
	private void dateiOffnen() {
		eingabeFeld.setText("Open file");
	}
	
	private void dateiSpeichern() {
		eingabeFeld.setText("Save the Dokument");
	}
	private void dateiBeenden() {
		if(JOptionPane.showConfirmDialog(this, "Sind Sie sicher?", "Beenden", JOptionPane.YES_NO_OPTION) ==
				JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("neu"))
				dateiNeu();
			if(e.getActionCommand().equals("open"))
				dateiOffnen();
			if(e.getActionCommand().equals("save"))
				dateiSpeichern();
			if(e.getActionCommand().equals("exit"))
				dateiBeenden();

		}

	}
		

	public static void main(String[] args) {
		new Editor("Text editor");
	}

}
