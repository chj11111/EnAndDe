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

        // 设置窗口大小和关闭操作
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void appendMessage(String message) {
        // 在消息区域追加新的消息
        messageArea.append(message + "\n");
        // 清空输入框
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

}
