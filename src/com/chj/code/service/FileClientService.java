package com.chj.code.service;

import com.chj.code.utils.ByteArrayEncryption;
import com.chj.common.Message;
import com.chj.common.MessageType;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileClientService {

    public void sendFileToOne(String src, String dest, String senderId, String getterId) {

        //读取src文件  -->  message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);

        //需要将文件读取
        FileInputStream fileInputStream = null;

        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            //SecretKey secretKey = ByteArrayEncryption.generateSecretKey();
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入到程序的字节数组
            //System.out.println(fileBytes);
            //fileBytes = ByteArrayEncryption.encryptData(fileBytes, secretKey);
            //将文件对应的字节数组设置message
            message.setFileBytes(fileBytes);
            //message.setSecretKey(secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //提示信息
        System.out.println("\n" + senderId + " 给 " + getterId + " 发送文件: " + src
                + " 到对方的电脑的目录 " + dest);
        //发送
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
