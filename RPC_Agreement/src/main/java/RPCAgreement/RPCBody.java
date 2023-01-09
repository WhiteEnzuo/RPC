package RPCAgreement;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RPCBody
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 18:23
 * @Created by Enzuo
 */
@Data
@Builder
public class RPCBody implements Serializable {
    /**
     * 类名
     * */
    private String interfaceName;
    /**
     * 方法名
     * */
    private String methodName;
    /**
     * 参数
     * */
    private Object[] args;
    /**
     * 类型
     * */
    private Class<?>[] argTypes;

}
