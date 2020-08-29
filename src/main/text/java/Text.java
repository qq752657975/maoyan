import com.ygb.config.SpringConfig;
import com.ygb.entity.UserVo;
import com.ygb.service.IAgentInfoService;
import com.ygb.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

public class Text {




    @Test
    public  void A(){

        ApplicationContext applicationContext = new
                AnnotationConfigApplicationContext(SpringConfig.class);
        IUserService bean = applicationContext.getBean(IUserService.class);

        UserVo byId = bean.getById("12345678");
        System.out.println(byId);
    }

    @Test
    public void b(){
        ApplicationContext applicationContext = new
                AnnotationConfigApplicationContext(SpringConfig.class);
        IAgentInfoService bean = applicationContext.getBean(IAgentInfoService.class);

    }

}
