package com.chj.common;
import javax.swing.*;
import java.awt.BorderLayout;

public class CustomDialogExample {

    public static void showCustomDialog(String getterId, String message) {
        // 创建对话框
        JDialog dialog = new JDialog();
        dialog.setTitle("User " + getterId);
        dialog.setModal(true); // 设置为模态对话框，阻塞用户与其他窗口的交互

        // 创建文本区域
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // 将文本区域添加到对话框
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 模拟一些动态追加的文本
        textArea.append(message);
//            try {
//                // 暂停一段时间，模拟实际应用中不断追加文本的情况
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }

        // 设置对话框大小
        dialog.setSize(300, 200);
        // 设置对话框可见性
        dialog.setVisible(true);
    }
}

