package com.chj.code.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageAppenderFrame extends JFrame {

    private static JTextArea messageArea;

    public MessageAppenderFrame() {
        // 设置窗口标题
        super("Message Appender");

        // 创建文本区域
        messageArea = new JTextArea();
        messageArea.setEditable(false); // 设置为不可编辑
        JScrollPane scrollPane = new JScrollPane(messageArea);

        // 将文本区域添加到窗口
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 创建一个面板用于放置输入框和发送按钮
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        JButton appendButton = new JButton("Append Message");

        // 添加按钮的点击事件监听器
        appendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                appendMessage(inputField.getText());
            }
        });

        // 将输入框和按钮添加到输入面板
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(appendButton, BorderLayout.EAST);

        // 将输入面板添加到窗口底部
        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // 设置窗口大小和关闭操作
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示窗口
    }

    public static void appendMessage(String message) {
        // 在消息区域追加新的消息
        messageArea.append(message + "\n");
        // 清空输入框
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

}
