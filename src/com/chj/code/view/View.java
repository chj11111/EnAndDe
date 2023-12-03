package com.chj.code.view;

import com.chj.code.service.FileClientService;
import com.chj.code.service.MessageClientService;
import com.chj.code.service.UserClientService;
import com.chj.code.utils.Utility;
import com.chj.code.utils.HuffmanNode;
import com.chj.common.CustomDialogExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private boolean loop = true; //控制是否显示菜单
    private String key = ""; // 接收用户的键盘输入
    private UserClientService userClientService = new UserClientService();//对象是用于登录服务/注册用户
    private MessageClientService messageClientService = new MessageClientService();//对象用户私聊/群聊.
    private FileClientService fileClientService = new FileClientService();//该对象用户传输文件

    public static void main(String[] args) {
        HuffmanNode huffmanNode = new HuffmanNode();
        SwingUtilities.invokeLater(() -> {
            new View().createAndShowGUI();
        });

    }

    public void createAndShowGUI() {
        setTitle("Network Communication System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome to the Network Communication System");
        titleLabel.setBounds(60, 10, 300, 20);
        panel.add(titleLabel);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 40, 100, 25);
        panel.add(loginButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(200, 40, 100, 25);
        panel.add(exitButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });
    }

    private void handleLogin() {
        String userId = JOptionPane.showInputDialog("Enter user ID:");
        String password = JOptionPane.showInputDialog("Enter password:");

        userId = HuffmanNode.EncodeTxT(userId);
        password = HuffmanNode.EncodeTxT(password);

        if (userClientService.checkUser(userId, password)) {
            userId = HuffmanNode.DecodeTxT(userId);
            JOptionPane.showMessageDialog(this, "Welcome, User " + userId + "!");
            showSecondMenu(userId);
        } else {
            JOptionPane.showMessageDialog(this, "Login failed. Please try again.");
        }
    }

    //显示主菜单
    private void showSecondMenu(String userId) {
        while (loop) {
            String[] options = {"Display Online Users", "Send Group Message", "Send Private Message", "Send File", "Exit"};
            int choice = JOptionPane.showOptionDialog(this, "Network Communication System - User " + userId, "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    userClientService.onlineFriendList();
                    break;
                case 1:
                    String groupMessage = JOptionPane.showInputDialog("Enter message to send to all users:");
                    groupMessage = HuffmanNode.EncodeTxT(groupMessage);
                    messageClientService.sendMessageToAll(groupMessage, userId);

                    break;
                case 2:
                    String getterId = JOptionPane.showInputDialog("Enter the user ID to send a private message:");
                    String privateMessage = JOptionPane.showInputDialog("Enter the private message:");

                    JFrame frame = new JFrame("Custom Dialog Example");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    // 创建按钮，点击按钮时弹出自定义对话框
                    JButton button = new JButton("Show Custom Dialog");
                    button.addActionListener(e -> CustomDialogExample.showCustomDialog(getterId, privateMessage));

                    // 将按钮添加到主窗口
                    frame.getContentPane().add(button, BorderLayout.CENTER);

                    // 设置窗口大小和可见性
                    frame.setSize(300, 200);
                    frame.setVisible(true);


                    String newmessage = HuffmanNode.EncodeTxT(privateMessage);
                    messageClientService.sendMessageToOne(newmessage, userId, getterId);
                    break;
                case 3:
                    String fileGetterId = JOptionPane.showInputDialog("Enter the user ID to send a file:");
                    String fileSrc = JOptionPane.showInputDialog("Enter the file source path:");
                    String fileDest = JOptionPane.showInputDialog("Enter the file destination path:");
                    fileClientService.sendFileToOne(fileSrc, fileDest, userId, fileGetterId);
                    break;
                case 4:
                    userClientService.logout();
                    loop = false;
                    break;
            }
        }
    }

    private void handleExit() {
        userClientService.logout();
        loop = false;
        JOptionPane.showMessageDialog(this, "Exiting the Network Communication System.");
        System.exit(0);
    }
}