package rpc;


import Service.impl.UserServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @Classname RPCServer
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 17:38
 * @Created by Enzuo
 */

public class RPCServer {
    static HashMap<String, Object> registerMap;
    static ExecutorService threadPool;
    static Log log;
    public static void start() {
        init();
        log.info("初始化服务器...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9000);
            log.info("服务器启动完成");
            while (true) {
                Socket client = serverSocket.accept();
                RPCThread rpcThread = new RPCThread();
                rpcThread.setClient(client);
                rpcThread.setRegisterMap(registerMap);
                threadPool.submit(rpcThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    /**
     * 初始化注册
     * */
    public static void initRegister(Object obj) {
        try {
            String name = obj.getClass().getInterfaces()[0].getName();
            registerMap.put(name, obj);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static void init(){
        try {
            log = LogFactory.getLog(RPCServer.class);
            log.info("服务器启动...");
            log.info("初始化Service...");
            registerMap = new HashMap<>();
            initRegister(new UserServiceImpl());
            log.info("Service初始化完成");
            log.info("初始化线程池...");
            threadPool = Executors.newFixedThreadPool(2);
            log.info("线程池初始化完毕...");
        }catch (Exception e){
            e.printStackTrace();
            log.error("初始化失败");
        }

    }
}
