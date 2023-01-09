package RPCAgreement;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


/**
 * @Classname RPCRequest
 * @Description
 * @Version 1.0.0
 * @Date 2023/1/6 18:05
 * @Created by Enzuo
 */
@Data
@Builder
public class RPCRequest implements Serializable {
    private byte[] body;
}
