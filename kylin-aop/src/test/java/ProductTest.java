import com.kylin.aop.AopApplication;
import com.kylin.aop.model.OperationLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: zhebin
 * @Date: 2019/9/5 23:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AopApplication.class)
public class ProductTest {

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Test
    public void send1(){
        rabbitTemplate.convertAndSend("exchange","topic.messages","hello topic.messages RabbitMQ");
    }



    @Test
    public void send3(){
        OperationLog operationLog = new OperationLog();
        operationLog.setUser("qwe");
        operationLog.setMethod("test");
        rabbitTemplate.convertAndSend("exchange","topic.messages",operationLog);
    }
}
