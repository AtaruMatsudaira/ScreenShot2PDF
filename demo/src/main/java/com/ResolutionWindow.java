package com;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.concurrent.Flow;

import util.*;

public class ResolutionWindow {
    JFrame frame;
    LinkedHashMap<String, JTextField> boundsFields = new LinkedHashMap<String, JTextField>() {
        {
            put("x", new JTextField(0));
            put("y", new JTextField(0));
            put("width", new JTextField(1280));
            put("height", new JTextField(720));
        }
    };

    public ResolutionWindow() {
        frame = new JFrame("Resolution Window");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        JPanel inputPanel = new JPanel();
        // inputPanel.setBounds(0, 0, 200, 300);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        for (Map.Entry<String, JTextField> pair : boundsFields.entrySet()) {
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new GridLayout(1, 4));
            JButton button = new JButton(pair.getKey());
            button.setText("text");
            button.setFont(new Font("Arial", Font.BOLD, 4));
            subPanel.add(button);
            subPanel.add(pair.getValue());
            inputPanel.add(subPanel);
        }
        mainPanel.add(inputPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ResolutionWindow();
    }
}
