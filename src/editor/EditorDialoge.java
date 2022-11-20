package editor;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class EditorDialoge {

	class MyFilter extends FileFilter {

		@Override
		public boolean accept(File f) {
			String name = f.getName().toLowerCase();
			if (f.isDirectory())
				return true;
			if (name.endsWith(".txt"))
				return true;

			return false;
		}

		@Override
		public  String getDescription() {

			return "Textdatei";
		}
	}

	public File OffnenDialogZeigen() {
		JFileChooser oefneneDialog = new JFileChooser();
		oefneneDialog.setFileFilter(new MyFilter());
		oefneneDialog.setAcceptAllFileFilterUsed(false);
		int status = oefneneDialog.showOpenDialog(null);
		
		if (status == JFileChooser.APPROVE_OPTION) {
			System.out.println(status);
			return (oefneneDialog.getSelectedFile());
		}
		else {
			System.out.println(status);
			return null;
		}
	}

	public File speicherDialogZeigen() {
		JFileChooser speicherDialog = new JFileChooser();
		speicherDialog.setFileFilter(new MyFilter());
		speicherDialog.setAcceptAllFileFilterUsed(false);
		int status = speicherDialog.showSaveDialog(null);
		
		if (status == JFileChooser.APPROVE_OPTION)
			return (speicherDialog.getSelectedFile());
		else
			return null;
	}

	public static void main(String[] args) {
		EditorDialoge ed = new EditorDialoge();
		System.out.println(ed.OffnenDialogZeigen());
		ed.speicherDialogZeigen();
		
		

	}

}
