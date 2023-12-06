package com.chj.code.view;

import com.chj.code.service.FileClientService;
import com.chj.code.service.MessageClientService;
import com.chj.code.service.UserClientService;
import com.chj.code.utils.KeyTransferExample;
import com.chj.code.utils.MessageAppenderFrame;
import com.chj.code.utils.Utility;
import com.chj.code.utils.HuffmanNode;
import com.chj.common.CustomDialogExample;
import com.chj.code.service.ClientConnectServerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.PublicKey;

public class View extends JFrame {
    private boolean loop = true; //控制是否显示菜单
    private String key = ""; // 接收用户的键盘输入
    private UserClientService userClientService = new UserClientService();//对象是用于登录服务/注册用户
    private MessageClientService messageClientService = new MessageClientService();//对象用户私聊/群聊.
    private FileClientService fileClientService = new FileClientService();//该对象用户传输文件

    public static void main(String[] args) {
        HuffmanNode huffmanNode = new HuffmanNode();
        SwingUtilities.invokeLater(() -> {
            new MessageAppenderFrame().setVisible(true);
            new View().createAndShowGUI();
        });

    }

    public static void showonlist(String[] onlineList){
        JDialog dialog = new JDialog();
        dialog.setTitle("OnlineUsers");
        dialog.setModal(true); // 设置为模态对话框，阻塞用户与其他窗口的交互

        // 创建文本区域
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // 将文本区域添加到对话框
        dialog.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // 模拟一些动态追加的文本

        for(int i = 0; i < onlineList.length; i ++){
            textArea.append("                           User: " + onlineList[i] + "\n");
        }

        // 设置对话框大小
        dialog.setSize(300, 250);
        // 设置对话框可见性
        dialog.setVisible(true);
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
                try {
                    handleLogin();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExit();
            }
        });
    }

    private void handleLogin() throws Exception {
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
    private void showSecondMenu(String userId) throws Exception {
        while (loop) {
            String[] options = {"Send Public to Server","Display Online Users", "Send Group Message", "Send Private Message", "Send File", "Exit"};
            int choice = JOptionPane.showOptionDialog(this, "Network Communication System - User " + userId, "Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    KeyPair keyPair = KeyTransferExample.generateKeyPair();
                    userClientService.SendPK(keyPair);
                    UserClientService.setPrivateKey(keyPair);
                    break;

                case 1:
                    userClientService.onlineFriendList();

                    break;
                case 2:
                    String groupMessage = JOptionPane.showInputDialog("Enter message to send to all users:");
                    MessageAppenderFrame.appendMessage(userId + " to all:" + groupMessage + "\n");
                    groupMessage = HuffmanNode.EncodeTxT(groupMessage);
                    messageClientService.sendMessageToAll(groupMessage, userId);

                    break;
                case 3:
                    String getterId = JOptionPane.showInputDialog("Enter the user ID to send a private message:");
                    String privateMessage = JOptionPane.showInputDialog("Enter the private message:");

                    MessageAppenderFrame.appendMessage(userId + " to " + getterId + ":" + privateMessage);

                    String newmessage = HuffmanNode.EncodeTxT(privateMessage);
                    messageClientService.sendMessageToOne(newmessage, userId, getterId);
                    break;
                case 4:
                    String fileGetterId = JOptionPane.showInputDialog("Enter the user ID to send a file:");
                    String fileSrc = JOptionPane.showInputDialog("Enter the file source path:");
                    String fileDest = JOptionPane.showInputDialog("Enter the file destination path:");
                    fileClientService.SendPkRequest(userId, fileGetterId);
                    ////在这里阻塞一会线程
                    PublicKey Pk = ClientConnectServerThread.UserPK.get(fileGetterId);
                    System.out.println(userId + ClientConnectServerThread.UserPK.get(fileGetterId));
                    fileClientService.sendFileToOne(fileSrc, fileDest, userId, fileGetterId, Pk);
                    break;
                case 5:
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