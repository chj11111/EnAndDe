package com.chj.code.service;

import com.chj.code.utils.ByteArrayEncryption;
import com.chj.code.utils.HuffmanNode;
import com.chj.code.utils.MessageAppenderFrame;
import com.chj.common.Message;
import com.chj.common.MessageType;
import com.chj.code.view.View;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    //该线程需要持有Socket
    private Socket socket;

    //构造器可以接受一个Socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    //
    @Override
    public void run() {

        //因为Thread需要在后台和服务器通信，因此我们while循环
        while (true) {


            try {
                System.out.println("客户端线程，等待从读取从服务器端发送的消息");


                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //如果服务器没有发送Message对象,线程会阻塞在这里
                Message message = (Message) ois.readObject();

                //注意，后面我们需要去使用message
                //判断这个message类型，然后做相应的业务处理
                //如果是读取到的是 服务端返回的在线用户列表
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //取出在线列表信息，并显示
                    //规定
                    String[] onlineUsers = message.getContent().split(" ");

                    View.showonlist(onlineUsers);

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {//普通的聊天消息
                    //把从服务器转发的消息，显示到控制台即可
                    message.setContent(HuffmanNode.DecodeTxT(message.getContent()));
                    MessageAppenderFrame.appendMessage(message.getSender()
                            + " to " + message.getGetter() + ":" + message.getContent() + "\n");
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //显示在客户端的控制台
                    message.setContent(HuffmanNode.DecodeTxT(message.getContent()));
                    MessageAppenderFrame.appendMessage(message.getSender() + " to all:" + message.getContent() + "\n");
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {//如果是文件消息
                    //让用户指定保存路径。。。
                    System.out.println(message.getSender() + " 给 " + message.getGetter()
                            + " 发文件: " + message.getSrc() + " 到我的电脑的目录 " + message.getDest() + "\n");

                    //取出message的文件字节数组，通过文件输出流写出到磁盘
                    System.out.println(message.getFileBytes().length);
                    //message.setFileBytes(ByteArrayEncryption.decryptData(message.getFileBytes(), message.getSecretKey()));
                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest(), true);
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    MessageAppenderFrame.appendMessage(" 保存文件成功~\n");

                } else {
                    MessageAppenderFrame.appendMessage("是其他类型的message, 暂时不处理....");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //为了更方便的得到Socket
    public Socket getSocket() {
        return socket;
    }
}
