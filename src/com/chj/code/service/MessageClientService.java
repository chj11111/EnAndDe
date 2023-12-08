package com.chj.code.service;

import com.chj.code.utils.ByteArrayEncryption;
import com.chj.code.utils.HuffmanNode;
import com.chj.code.utils.KeyTransferExample;
import com.chj.common.Message;
import com.chj.common.MessageType;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.PublicKey;
import java.util.Date;

public class MessageClientService {
    public void sendMessageToAll(String content, String senderId) throws Exception {
        //构建message
        Message message = new Message();
        SecretKey secretKey = ByteArrayEncryption.generateSecretKey();

        byte[] groupmessage = content.getBytes();

        message.setFileBytes(ByteArrayEncryption.encryptData(groupmessage, secretKey));
        message.setGroupSecretkey(secretKey);
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);//群发消息这种类型
        message.setSender(senderId);

        message.setSendTime(new Date().toString());//发送时间设置到message对象
        //System.out.println(senderId + " 对大家说 " + content);
        //发送给服务端

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToOne(byte[] content, String senderId, String getterId, PublicKey Pk) throws Exception {
        //构建message

        SecretKey secretKey = ByteArrayEncryption.generateSecretKey();

        content = ByteArrayEncryption.encryptData(content, secretKey);

        byte[] newsecretkey = KeyTransferExample.encryptKey(secretKey, Pk);

//        String str = new String(ByteArrayEncryption.decryptData(content, KeyTransferExample.decryptKey(newsecretkey, UserClientService.privateKey)));

//        System.out.println(str);

        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);//普通的聊天消息这种类型
        message.setSecretKey(newsecretkey);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setFileBytes(content);
        message.setSendTime(new Date().toString());//发送时间设置到message对象
        //System.out.println(senderId + " 对 " + getterId + " 说 " + HuffmanNode.DecodeTxT(content));
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
