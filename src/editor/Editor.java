package editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class Editor extends JFrame {

	private JTextArea eingabeFeld;
	
	
	

	public Editor(String titel) {
		super(titel);

		setLayout(new BorderLayout());
		eingabeFeld = new JTextArea();
		add(new JScrollPane(eingabeFeld), BorderLayout.CENTER);
		
		setJMenuBar(menuBar());
		add(symbolleiste(), BorderLayout.NORTH);

		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(600, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	private JMenuBar menuBar() {
	
	JMenuBar menu = new JMenuBar();
	JMenu dateimenu = new JMenu("Datei");

	JMenuItem dateiNeu = new JMenuItem();
	dateiNeu.setText("Neu");
	dateiNeu.setIcon(new ImageIcon("icons/new24.gif"));
	dateiNeu.setActionCommand("neu");
	dateiNeu.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));

	JMenuItem dateiLaden= new JMenuItem();
	dateiLaden.setText("Offnen");
	dateiLaden.setIcon(new ImageIcon("icons/open24.gif"));
	dateiLaden.setActionCommand("open");
	dateiLaden.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));

	JMenuItem dateiSpeichern = new JMenuItem();
	dateiSpeichern.setText("Speichern");
	dateiSpeichern.setIcon(new ImageIcon("icons/save24.gif"));
	dateiSpeichern.setActionCommand("save");
	dateiSpeichern.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));

	JMenuItem dateiBeenden= new JMenuItem();
	dateiBeenden.setText("Beenden");
	dateiBeenden.setActionCommand("exit");

	MyListener listener = new MyListener();
	dateiNeu.addActionListener(listener);
	dateiLaden.addActionListener(listener);
	dateiSpeichern.addActionListener(listener);
	dateiBeenden.addActionListener(listener);

	dateimenu.add(dateiNeu);
	dateimenu.add(dateiLaden);
	dateimenu.addSeparator();
	dateimenu.add(dateiSpeichern);
	dateimenu.addSeparator();
	dateimenu.add(dateiBeenden);

	menu.add(dateimenu);
	return menu;
	}

	private void dateiNeu() {
		if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich ein neues Dokument einlegen?", "Neues Dokument",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			eingabeFeld.setText("");
		}
	}

	private void dateiLaden() {
		EditorDialoge dialog = new EditorDialoge();
		File datei = dialog.OffnenDialogZeigen();
		if (datei != null) {
			try {
				eingabeFeld.read(new FileReader(datei), null);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Beim Laden hat es ein Problem gegeben", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void dateiSpeichern() {
		EditorDialoge dialog = new EditorDialoge();
		File datei = dialog.speicherDialogZeigen();

		if (datei != null) {
			try {
				eingabeFeld.write(new FileWriter(datei));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Beim Speichern hat es ein Problem gegeben", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void dateiBeenden() {
		if (JOptionPane.showConfirmDialog(this, "Sind Sie sicher?", "Beenden",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	private JToolBar symbolleiste() {
		JToolBar leiste = new JToolBar();
		
		MyListener listener = new MyListener();
		
		JButton dateiNeuButton = new JButton();
		dateiNeuButton.setActionCommand("neu");
		dateiNeuButton.setIcon(new ImageIcon("icons/new24.gif"));
		dateiNeuButton.setToolTipText("Ersteiit ein neues Dokument");
		dateiNeuButton.addActionListener(listener);
		leiste.add(dateiNeuButton);
		
		JButton dateiOeffnenButton = new JButton();
		dateiOeffnenButton.setActionCommand("open");
		dateiOeffnenButton.setIcon(new ImageIcon("icons/open24.gif"));
		dateiOeffnenButton.setToolTipText("Oeffnet ein vorhanderes Dokument");
		dateiOeffnenButton.addActionListener(listener);
		leiste.add(dateiOeffnenButton);
		
		JButton dateiSpeichernButton = new JButton();
		dateiSpeichernButton.setActionCommand("save");
		dateiSpeichernButton.setIcon(new ImageIcon("icons/save24.gif"));
		dateiSpeichernButton.setToolTipText("speichert das aktuele Dokument");
		dateiSpeichernButton.addActionListener(listener);
		leiste.add(dateiSpeichernButton);
		
		return (leiste);
	}

	public class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("neu"))
				dateiNeu();
			if (e.getActionCommand().equals("open"))
				dateiLaden();
			if (e.getActionCommand().equals("save"))
				dateiSpeichern();
			if (e.getActionCommand().equals("exit"))
				dateiBeenden();

		}

	}
	
	

	public static void main(String[] args) {
		new Editor("Text editor");
	}

}
