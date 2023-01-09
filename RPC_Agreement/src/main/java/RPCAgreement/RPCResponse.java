package RPCAgreement;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


/**
 * @Classname RPCResponse
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 18:05
 * @Created by Enzuo
 */
@Data
@Builder
public class RPCResponse implements Serializable {
    private byte[] bytes;
}
