package com;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import util.*;

public class ResolutionWindow {
    JFrame frame;
    JLabel screen = new JLabel();
    LinkedHashMap<String, JTextField> boundsFields = new LinkedHashMap<String, JTextField>() {
        {
            put("x", new JTextField("0"));
            put("y", new JTextField("0"));
            put("width", new JTextField("1280"));
            put("height", new JTextField("0720"));
        }
    };

    public ResolutionWindow() {
        frame = new JFrame("Resolution Window");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        ScreenThread screenThread = new ScreenThread();
        screenThread.start();
        inputPanel.setBounds(0, 0, 400, 300);
        // inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        for (Map.Entry<String, JTextField> pair : boundsFields.entrySet()) {
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new GridLayout(1, 4));
            JLabel label = new JLabel(pair.getKey());
            subPanel.add(label);
            subPanel.add(pair.getValue());
            pair.getValue().addActionListener(l -> {
                for (int i = 0; i < 4; i++) {

                }
            });
            inputPanel.add(subPanel);
        }
        mainPanel.add(inputPanel);
        mainPanel.add(screen);
        frame.add(mainPanel);
        frame.setVisible(true);
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

    BufferedImage captureScreen(Rectangle screenSize) throws AWTException {
        Robot robot = new Robot();
        BufferedImage screenShot = robot.createScreenCapture(screenSize);
        return screenShot;
    }

    public static void main(String[] args) {
        new ResolutionWindow();
    }

    class ScreenThread extends Thread {
        public void run() {
            while (true) {
                try {
                    screen.setIcon(new ImageIcon(ImageUtil.getScaledImage(
                            captureScreen(
                                    new Rectangle(getBounds()[0], getBounds()[1], getBounds()[2], getBounds()[3])),
                            16 * 30, 9 * 30)));
                    Thread.sleep(60 / 1000);
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
