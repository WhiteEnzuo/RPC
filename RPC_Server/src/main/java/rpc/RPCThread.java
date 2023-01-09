package rpc;

import RPCAgreement.RPCBody;
import RPCAgreement.RPCRequest;
import RPCAgreement.RPCResponse;
import Service.impl.UserServiceImpl;
import lombok.Data;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;

/**
 * @Classname RPCThread
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 17:38
 * @Created by Enzuo
 */
@Data
public class RPCThread implements Runnable{
    private HashMap<String, Object> registerMap;
    private Socket client;
    @Override
    public void run() {
        /**
         * 输入输出流
         * */
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            objectInputStream=new ObjectInputStream(client.getInputStream());
            RPCRequest rpcRequest = (RPCRequest)objectInputStream.readObject();
            byte[] body = rpcRequest.getBody();
            RPCBody rpcBody  = (RPCBody)new ObjectInputStream(new ByteArrayInputStream(body)).readObject();
            UserServiceImpl service = (UserServiceImpl) registerMap.get(rpcBody.getInterfaceName());
            ByteArrayOutputStream bios = new ByteArrayOutputStream();
            new ObjectOutputStream(bios).writeObject(service);
            RPCResponse rpcResponse = RPCResponse.builder().bytes(bios.toByteArray()).build();


            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(rpcResponse);
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }  finally {
            /** 释放资源*/
            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(client!=null){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


}
