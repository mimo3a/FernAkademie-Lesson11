package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import javax.swing.Action;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import javax.swing.text.html.HTMLEditorKit;

public class MiniText extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JEditorPane eingabeFeld;
	private HTMLEditorKit htmlformat;
	
//	variabels for Action-Objects
	private MyActionen neuAct, oeffnenAct, speichernAct, beendenAct, druckenAct, 
						speicherUnterAct, weboeffnenAct, infoAct;

//	dia variable fur gespeicherte file
	private File datei;
	

	public MiniText(String text) {
		super(text);
		setLayout(new BorderLayout());
		eingabeFeld = new JEditorPane();
		htmlformat = new HTMLEditorKit();
		eingabeFeld.setEditorKit(htmlformat);
		actionEistellen();
		setJMenuBar(menuBar());

		add(new JScrollPane(eingabeFeld), BorderLayout.CENTER);
		add(symbolleiste(), BorderLayout.NORTH);
		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(600, 200));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		eingabeFeld.addMouseListener(new MeinContextMenuListener());
		setVisible(true);

		eingabeFeld.requestFocus();
		
		
		
		

	}
	
// the listener used for listen for a right button of mous on eingabeFeld
	
	class MeinContextMenuListener extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			if (e.isPopupTrigger()) {
				kontextMenu().show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	class MyActionen extends AbstractAction {

//		abstract class for actions which are items in the menu bar and buttons on the tool bar

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyActionen(String text, ImageIcon icon, String bildschirmtipp, KeyStroke shortcut, String actiontext) {
			super(text, icon);
			putValue(SHORT_DESCRIPTION, bildschirmtipp);
			putValue(ACCELERATOR_KEY, shortcut);
			putValue(ACTION_COMMAND_KEY, actiontext);

		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getActionCommand().equals("neu"))
				dateiNeu();
			if (e.getActionCommand().equals("open"))
				dateiLaden();
			if (e.getActionCommand().equals("save"))
				dateiSpeichern();
			if (e.getActionCommand().equals("beenden"))
				dateiBeenden();
			if (e.getActionCommand().equals("speichernunter"))
				dateiSpeichernUnter();
			if (e.getActionCommand().equals("webladen"))
				webLaden();
			
//			add event for "info"
			if(e.getActionCommand().equals("info"))
				info();
			
			if (e.getActionCommand().equals("print")) {

//				if a button was  pressed on a toolbar  return false 
//				if a punkt was on menubar pressed return true
				
				if (e.getSource() instanceof JButton)
					drucken(false);
				if (e.getSource() instanceof JMenuItem)
					drucken(true);
			}
		}

	}

	private void actionEistellen() {

//		create the actions from abstract class

		neuAct = new MyActionen("...Neu", new ImageIcon("icons/new24.gif"), "Erstellt ein neues Dokument",
				KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK), "neu");
		oeffnenAct = new MyActionen("...Laden", new ImageIcon("icons/open24.gif"), "Ladet ein Dokument",
				KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK), "open");
		speichernAct = new MyActionen("...Speichern", new ImageIcon("icons/save24.gif"), "Speichert den Dokument",
				KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK), "save");
		druckenAct = new MyActionen("Drucken...", new ImageIcon("icons/print24.gif"), "Druckt das aktuele Dokument",
				KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK), "print");
		beendenAct = new MyActionen("Enden", null, "Enden die Arbeit", null, "beenden");

		speicherUnterAct = new MyActionen("Speichern unter...", null, "", null, "speichernunter");
		
		weboeffnenAct = new MyActionen("Webseite...", new ImageIcon("icons/webComponent24.gif"),
				"Oeffnet eine Webseite", null, "webladen");
		
//		action-object for info
		
		infoAct = new MyActionen("info...", new ImageIcon("icons/information24.gif"), "Info zeigen",
				null, "info");
		

	}

	private JMenuBar menuBar() {

		JMenuBar menu = new JMenuBar();
		JMenu dateimenu = new JMenu("Datei");
		JMenu dateiOeffnen = new JMenu("Oeffnen");
		
//		create a menu "Hilfe"		
		JMenu hilfeMenu = new JMenu("Hilfe");
		
//		add menu item "info" in menu "Hilfe"
		hilfeMenu.add(infoAct);
		

		dateiOeffnen.add(oeffnenAct);
		dateiOeffnen.add(weboeffnenAct);

//		 create the menu items in menubar using Actions

		dateimenu.add(neuAct);
		dateimenu.add(dateiOeffnen);
		dateimenu.addSeparator();
		dateimenu.add(speichernAct);
		dateimenu.add(speicherUnterAct);
		dateimenu.add(druckenAct);
		dateimenu.addSeparator();
		dateimenu.add(beendenAct);

		menu.add(dateimenu);
		
//		add the menu Hilfe on menubar
		menu.add(hilfeMenu);
				
		return menu;
	}

	private JToolBar symbolleiste() {
		JToolBar leiste = new JToolBar();

//		 create the buttons in tool bar use Actions

		leiste.add(neuAct);
		leiste.add(oeffnenAct);
		leiste.add(weboeffnenAct);
		leiste.add(speichernAct);
		leiste.add(druckenAct);

		leiste.addSeparator();

//		 create the buttons in tool bar use StyleEditorKit

		Action fettFormat = new StyledEditorKit.BoldAction();

		fettFormat.putValue(Action.SHORT_DESCRIPTION, "Fett formatieren");
		fettFormat.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/bold24.gif"));
		leiste.add(fettFormat);

		Action kursivFormat = new StyledEditorKit.ItalicAction();
		kursivFormat.putValue(Action.SHORT_DESCRIPTION, "Kursiv formatieren");
		kursivFormat.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/italic24.gif"));
		leiste.add(kursivFormat);

		Action unterstrichenFormat = new StyledEditorKit.UnderlineAction();
		unterstrichenFormat.putValue(Action.SHORT_DESCRIPTION, "Unterstriechen formatieren");
		unterstrichenFormat.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/underline24.gif"));
		leiste.add(unterstrichenFormat);

		Action linksAbsatz = new StyledEditorKit.AlignmentAction("Linksbuindig", StyleConstants.ALIGN_LEFT);
		linksAbsatz.putValue(Action.SHORT_DESCRIPTION, "Liksbuindig ausrichten");
		linksAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignLeft24.gif"));
		leiste.add(linksAbsatz);

		Action zentriertAbsatz = new StyledEditorKit.AlignmentAction("Zentriert", StyleConstants.ALIGN_CENTER);
		zentriertAbsatz.putValue(Action.SHORT_DESCRIPTION, "Zentriert ausrichten");
		zentriertAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignCenter24.gif"));
		leiste.add(zentriertAbsatz);

		Action rechtsAbsatz = new StyledEditorKit.AlignmentAction("Rechtsbuindig", StyleConstants.ALIGN_RIGHT);
		rechtsAbsatz.putValue(Action.SHORT_DESCRIPTION, "Rechtsabsatz ausrichten");
		rechtsAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignRight24.gif"));
		leiste.add(rechtsAbsatz);

		Action blockAbsatz = new StyledEditorKit.AlignmentAction("Blocksatz", StyleConstants.ALIGN_JUSTIFIED);
		blockAbsatz.putValue(Action.SHORT_DESCRIPTION, "Im Blocksatz ausrichten");
		blockAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignJustify24.gif"));
		leiste.add(blockAbsatz);
		
//		add a menu item "info"
		leiste.add(infoAct);

		return (leiste);
	}

	private JPopupMenu kontextMenu() {
		JPopupMenu kontext = new JPopupMenu();
		kontext.add(neuAct);
		kontext.add(oeffnenAct);
		kontext.add(weboeffnenAct);
		return kontext;

	}

	private void dateiNeu() {
		if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich ein neues Dokument einlegen?", "Neues Dokument",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			eingabeFeld.setText("");
			datei = null;
		}
	}

	private void dateiLaden() {
//		if, when opening a file, the user clicked change to the value datei does not change

		MiniTextDialog dialog = new MiniTextDialog();

		File dateiLokal = dialog.OffnenDialogZeigen();
		if (dateiLokal != null) {
			try {
				eingabeFeld.read(new FileReader(dateiLokal), null);
				datei = dateiLokal;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Beim Laden hat es ein Problem gegeben", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void dateiSpeichern() {

		if (datei == null) {
//			if datei is null then we run savedialog
//			if not don't run
			MiniTextDialog dialog = new MiniTextDialog();
			datei = dialog.speicherDialogZeigen();
		}

		if (datei != null) {
			try {
				OutputStream output = new FileOutputStream(datei);
				htmlformat.write(output, eingabeFeld.getDocument(), 0, eingabeFeld.getDocument().getLength());
			} catch (IOException | BadLocationException e) {
				JOptionPane.showMessageDialog(this, "Beim Speichern hat es ein Problem gegeben", "Fehler",
						JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	private void dateiSpeichernUnter() {
		MiniTextDialog dialog = new MiniTextDialog();
		File dateiLokai = dialog.speicherDialogZeigen();
		if (dateiLokai != null) {
			datei = dateiLokai;
			dateiSpeichern();
		}
	}

	private void drucken(boolean dialogZeigen) {
		try {
			if (dialogZeigen == true) {

//				if dialogZeigen true is the dialog of Printer shows

				eingabeFeld.print();
			} else {
//				if dialogZeigen true is the dialog of Printer no shows (3-th argument) just prints
				eingabeFeld.print(null, null, false, null, null, true);
			}
		} catch (PrinterException e) {
			JOptionPane.showMessageDialog(this, "Bein Drucken hat es ein Problem gegeben", "Fehler",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void webLaden() {
		String adresse;
		adresse = JOptionPane.showInputDialog(this, "Bitte geben Sie die URL der Seite ein");
		if (adresse != null) {
			eingabeFeld.setText("");
			try {
				eingabeFeld.setPage(adresse);
				datei = null;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Beim Laden ist ein Problem getreten");
			}
		}
	}
		
	
//	Method for showing information
	private void info() {
		JOptionPane.showMessageDialog(this, "Minitext Version 5.0 \n Progmammiert von: "
				+ "Alex Programmer 2022", "Info", JOptionPane.INFORMATION_MESSAGE);
				
		
	}

	private void dateiBeenden() {
		if (JOptionPane.showConfirmDialog(this, "Sind Sie sicher?", "Beenden",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new MiniText("mini text");
	}

}
