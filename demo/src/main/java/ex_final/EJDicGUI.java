package ex_final;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;

public class EJDicGUI extends JFrame {
	JTextField english, japanese;
	JList<String> list;
	JButton addButton, removeButton, updateButton;
	JPanel pane;
	EJDic dictionary;

	public static void main(String[] args) {
		JFrame w = new EJDicGUI("EJDicGUI");
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.setSize(300, 300);
		w.setVisible(true);
	}

	public EJDicGUI(String title) {
		super(title);
		dictionary = new EJDic();
		pane = (JPanel) getContentPane();

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("ファイル");
		menuBar.add(fileMenu);
		JMenuItem item;
		item = new JMenuItem(new OpenAction());
		fileMenu.add(item);
		item = new JMenuItem(new SaveAction());
		fileMenu.add(item);
		fileMenu.addSeparator();
		item = new JMenuItem(new ExitAction());
		fileMenu.add(item);

		JPanel fields = new JPanel(new GridLayout(1, 2));
		english = new JTextField();
		english.setBorder(new TitledBorder("英語"));
		fields.add(english);
		japanese = new JTextField();
		japanese.setBorder(new TitledBorder("日本語"));
		fields.add(japanese);
		pane.add(fields, BorderLayout.SOUTH);

		DefaultListModel<String> listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		list.addListSelectionListener(new WordSelect());
		JScrollPane sc = new JScrollPane(list);
		sc.setBorder(new TitledBorder("項目一覧"));
		pane.add(sc, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 3));
		addButton = new JButton(new AddAction());
		buttons.add(addButton);
		updateButton = new JButton(new UpdateAction());
		buttons.add(updateButton);
		removeButton = new JButton(new RemoveAction());
		buttons.add(removeButton);
		pane.add(buttons, BorderLayout.NORTH);
	}

	class WordSelect implements ListSelectionListener {
		@SuppressWarnings("unchecked")
		public void valueChanged(ListSelectionEvent e) {
			JList<String> li = (JList<String>) e.getSource();
			if (e.getValueIsAdjusting() == false) {
				String e_word = (String) li.getSelectedValue();
				String j_word = dictionary.get(e_word);
				english.setText(e_word);
				japanese.setText(j_word);
			}
		}
	}

	class OpenAction extends AbstractAction {
		OpenAction() {
			putValue(Action.NAME, "開く");
			putValue(Action.SHORT_DESCRIPTION, "開く");
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
			int selected = chooser.showOpenDialog(null);
			if (selected == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				dictionary.open(file.getAbsolutePath());
				list.setListData(dictionary.keySet().toArray(new String[] {}));
			}
		}
	}

	class SaveAction extends AbstractAction {
		SaveAction() {
			putValue(Action.NAME, "保存");
			putValue(Action.SHORT_DESCRIPTION, "保存");
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
			int selected = chooser.showSaveDialog(null);
			if (selected == JFileChooser.APPROVE_OPTION) {
				dictionary.save(chooser.getSelectedFile().toString());
			}
		}
	}

	class ExitAction extends AbstractAction {
		ExitAction() {
			putValue(Action.NAME, "終了");
			putValue(Action.SHORT_DESCRIPTION, "終了");
		}

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class AddAction extends AbstractAction {
		AddAction() {
			putValue(Action.NAME, "追加");
			putValue(Action.SHORT_DESCRIPTION, "追加");
		}

		public void actionPerformed(ActionEvent e) {
			String e_word = english.getText();
			String j_word = japanese.getText();
			if (e_word.length() > 0 && j_word.length() > 0) {
				if (!dictionary.keySet().contains(e_word)) {
					dictionary.put(e_word, j_word);
					list.setListData(dictionary.keySet().toArray(new String[] {}));
				}
			}
		}
	}

	class UpdateAction extends AbstractAction {
		UpdateAction() {
			putValue(Action.NAME, "更新");
			putValue(Action.SHORT_DESCRIPTION, "更新");
		}

		public void actionPerformed(ActionEvent e) {
			String e_word = english.getText();
			String j_word = japanese.getText();
			if (e_word.length() > 0 && j_word.length() > 0) {
				if (dictionary.keySet().contains(e_word)) {
					dictionary.put(e_word, j_word);
					list.setListData(dictionary.keySet().toArray(new String[] {}));
				}
			}
		}
	}

	class RemoveAction extends AbstractAction {
		RemoveAction() {
			putValue(Action.NAME, "削除");
			putValue(Action.SHORT_DESCRIPTION, "削除");
		}

		public void actionPerformed(ActionEvent e) {
			if (list.getSelectedValue() == null)
				return;

			int option = JOptionPane.showConfirmDialog(null, "削除しますか？",
					"確認", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (option == JOptionPane.YES_OPTION) {
				String select = list.getSelectedValue();
				dictionary.remove(select);
				list.setListData(dictionary.keySet().toArray(new String[] {}));
			}
		}
	}

}