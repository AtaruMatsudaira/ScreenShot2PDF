package ex_final;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Paths;

public class AddressBookGUI extends JFrame {
    JTextField nameField, addressField, telField, emailField;
    DefaultListModel<String> model;
    JList<String> list;
    JButton addButton, removeButton, updateButton;
    JPanel pane;
    AddressBook book;

    public static void main(String[] args) {
        JFrame w = new AddressBookGUI("AddressBookGUI");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        w.setSize(400, 300);
        w.setVisible(true);
    }

    public AddressBookGUI(String title) {
        super(title);
        book = new AddressBook();
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

        model = new DefaultListModel<String>();
        list = new JList<String>(model);
        list.addListSelectionListener(new NameSelect());
        JScrollPane sc = new JScrollPane(list);
        sc.setBorder(new TitledBorder("名前一覧"));
        pane.add(sc, BorderLayout.CENTER);

        JPanel fields = new JPanel();
        fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
        nameField = new JTextField(20);
        nameField.setBorder(new TitledBorder("名前"));
        fields.add(nameField);
        addressField = new JTextField(20);
        addressField.setBorder(new TitledBorder("住所"));
        fields.add(addressField);
        telField = new JTextField(20);
        telField.setBorder(new TitledBorder("電話"));
        fields.add(telField);
        emailField = new JTextField(20);
        emailField.setBorder(new TitledBorder("メール"));
        fields.add(emailField);

        addButton = new JButton(new AddAction());
        fields.add(addButton);
        updateButton = new JButton(new UpdateAction());
        fields.add(updateButton);
        removeButton = new JButton(new RemoveAction());
        fields.add(removeButton);

        pane.add(fields, BorderLayout.EAST);
    }

    public boolean hasFields() {
        return nameField.getText().length() > 0 && addressField.getText().length() > 0
                && telField.getText().length() > 0 && emailField.getText().length() > 0;
    }

    class NameSelect implements ListSelectionListener {
        @SuppressWarnings("unchecked")
        public void valueChanged(ListSelectionEvent e) {
            JList<String> li = (JList<String>) e.getSource();
            if (e.getValueIsAdjusting() == false) {
                String name = (String) li.getSelectedValue();
                if (name != null) {
                    Address address = book.findName(name);
                    if (address != null) {
                        String tel = address.getTel();
                        String address_text = address.getAddress();
                        String email = address.getEmail();
                        nameField.setText(name);
                        telField.setText(tel);
                        addressField.setText(address_text);
                        emailField.setText(email);
                    }
                }
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
                book.open(file.getAbsolutePath());
                list.setListData(book.getNames().toArray(new String[] {}));
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
                book.save(chooser.getSelectedFile().toString());
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
            if (hasFields() && book.findName(nameField.getText()) == null) {
                Address address = new Address(nameField.getText(), addressField.getText(), telField.getText(),
                        emailField.getText());
                book.add(address);
                list.setListData(book.getNames().toArray(new String[] {}));
            }
        }
    }

    class UpdateAction extends AbstractAction {
        UpdateAction() {
            putValue(Action.NAME, "更新");
            putValue(Action.SHORT_DESCRIPTION, "更新");
        }

        public void actionPerformed(ActionEvent e) {
            if (hasFields() && book.findName(nameField.getText()) != null) {
                book.remove(book.findName(nameField.getText()));
                Address address = new Address(nameField.getText(), addressField.getText(), telField.getText(),
                        emailField.getText());
                book.add(address);
                list.setListData(book.getNames().toArray(new String[] {}));
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
                book.remove(book.findName(select));
                list.setListData(book.getNames().toArray(new String[] {}));
                nameField.setText("");
                addressField.setText("");
                telField.setText("");
                emailField.setText("");
            }
        }
    }
}