package Client;

import RPCAgreement.RPCBody;
import RPCAgreement.RPCRequest;
import RPCAgreement.RPCResponse;
import Service.Arr;
import Service.UserService;

import java.io.*;
import java.net.Socket;

/**
 * @Classname RPCClient
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 20:17
 * @Created by Enzuo
 */

public class RPCClient {
    public static <T> T getContent(RPCRequest rpcRequest, Class<T> clazz){
        T t= null;
        Socket socket = null;
        try {
            socket = new Socket("localhost",9000);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            try {

                RPCResponse rpcResponse = (RPCResponse)objectInputStream.readObject();
                t = (T) new ObjectInputStream(new ByteArrayInputStream(rpcResponse.getBytes())).readObject();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }
    public static RPCRequest invoke(){
        String interfaceName = UserService.class.getName();
        RPCBody rpcBody = RPCBody.builder().interfaceName(interfaceName).build();
        RPCRequest rpcRequest =null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ObjectOutputStream(byteArrayOutputStream).writeObject(rpcBody);
             rpcRequest = RPCRequest.builder().body(byteArrayOutputStream.toByteArray()).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rpcRequest;
    }
    public static void main(String[] args) {
        RPCRequest rpcRequest = invoke();
        UserService content = getContent(rpcRequest, UserService.class);
        Arr test = content.say("Test");
        test.setM("123");
        System.out.println(test);
    }
}
