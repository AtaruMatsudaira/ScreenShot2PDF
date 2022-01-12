package com;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.nio.file.Paths;
import java.util.*;

import util.*;

public class ResolutionWindow {
    JFrame frame;
    JLabel screen = new JLabel();
    ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();
    LinkedHashMap<String, JTextField> boundsFields = new LinkedHashMap<String, JTextField>() {
        {
            put("x", new JTextField("0", 3));
            put("y", new JTextField("0", 3));
            put("width", new JTextField("1200", 3));
            put("height", new JTextField("900", 3));
        }
    };

    public ResolutionWindow() {
        frame = new JFrame("Resolution Window");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel boundsPanel = new JPanel();
        Thread screenThread = new Thread(() -> {
            while (true) {
                try {
                    screen.setIcon(new ImageIcon(ImageUtil.getScaledImage(
                            captureScreen(
                                    new Rectangle(getBounds()[0], getBounds()[1], getBounds()[2], getBounds()[3])),
                            getScreenDisplayWidth(350), 350)));
                    Thread.sleep(1000 / 60);
                } catch (Exception e) {
                   // e.printStackTrace();
                }
            }
        });
        screenThread.start();
        boundsPanel.setBounds(0, 0, 400, 300);
        for (Map.Entry<String, JTextField> pair : boundsFields.entrySet()) {
            JPanel subPanel = new JPanel();
            // subPanel.setLayout(new GridLayout(1, 4));
            JLabel label = new JLabel(pair.getKey());
            subPanel.add(label);
            subPanel.add(pair.getValue());
            pair.getValue().addActionListener(l -> {
                for (int i = 0; i < 4; i++) {

                }
            });
            boundsPanel.add(subPanel);
        }

        JPanel screenPanel = new JPanel();
        screenPanel.add(screen);

        JPanel downPanel = new JPanel();
        JButton shotButton = new JButton("shot");
        JLabel pageLabel = new JLabel("Page 0");
        JButton saveButton = new JButton("save");

        downPanel.add(shotButton);
        downPanel.add(pageLabel);
        downPanel.add(saveButton);

        mainPanel.add(boundsPanel);
        mainPanel.add(screenPanel);
        mainPanel.add(downPanel);
        frame.add(mainPanel);
        frame.setVisible(true);

        shotButton.addActionListener(l -> {
            try {
                imageList.add(
                        captureScreen(new Rectangle(getBounds()[0], getBounds()[1], getBounds()[2], getBounds()[3])));
            } catch (AWTException e) {
                e.printStackTrace();
            }
            pageLabel.setText("Page " + imageList.size());
        });
        saveButton.addActionListener(l -> {
            if (imageList.size() > 0) {
                JFileChooser chooser = new JFileChooser(Paths.get("").toAbsolutePath().toString());
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "pdfドキュメント(*.pdf)", "pdf");
                chooser.setFileFilter(filter);
                int selected = chooser.showSaveDialog(null);
                if (selected == JFileChooser.APPROVE_OPTION) {
                    String filename = chooser.getSelectedFile().toString();
                    if (!filename.substring(filename.length() - 4).equals(".pdf")) {
                        filename += ".pdf";
                    }
                    new pdf.CreatePDF(imageList, filename);
                    System.exit(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, new JLabel("1ページも登録されていません"));
            }
        });
    }

    int[] getBounds() {
        int[] bounds = new int[4];
        int count = 0;
        for (JTextField field : boundsFields.values()) {
            try {
                bounds[count] = Integer.parseInt(field.getText());
            } catch (Exception e) {
                field.setText("0");
                bounds[count] = 0;
            }
            if (count > 1 && bounds[count] < 1) {
                bounds[count] = 1;
            }
            count++;
        }
        return bounds;
    }

    int getScreenDisplayWidth(int height) {
        double ratio = (double) getBounds()[3] / (double) getBounds()[2];
        double width = (double) height / ratio;
        return (int) width;
    }

    BufferedImage captureScreen(Rectangle screenSize) throws AWTException {
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(screenSize);
        return screenShot;
    }

    public static void main(String[] args) {
        new ResolutionWindow();
    }
}
