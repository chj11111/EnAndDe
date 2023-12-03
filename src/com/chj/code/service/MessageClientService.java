package com.chj.code.service;

import com.chj.code.utils.HuffmanNode;
import com.chj.common.Message;
import com.chj.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {
    public void sendMessageToAll(String content, String senderId) {
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);//群发消息这种类型
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间设置到message对象
        System.out.println(senderId + " 对大家说 " + content);
        //发送给服务端

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToOne(String content, String senderId, String getterId) {
        //构建message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);//普通的聊天消息这种类型
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间设置到message对象
        System.out.println(senderId + " 对 " + getterId + " 说 " + HuffmanNode.DecodeTxT(content));
        //发送给服务端

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());

            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
