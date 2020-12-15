package ml.yihao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.code.guice.jsonconfig.JsonConfigModule;
import com.google.code.guice.jsonconfig.JsonConfigProvider;
import com.google.inject.*;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * @author zyh
 * @Description:
 * @date 2020/12/1510:31 下午
 */
public class Example5 {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new JsonConfigModule(), new Module() {
            @Override
            public void configure(Binder binder) {
                JsonConfigProvider.bind(binder, "druid.server", DruidServerConfig.class);
            }

            @Provides
            @Singleton
            Properties init() {
                Properties prop = new Properties();
                prop.put("druid.server.host", "127.0.0.1");
                prop.put("druid.server.port", 9999);
                return prop;
            }

        });
        DruidServerConfig config = injector.getInstance(DruidServerConfig.class);
        System.out.println(config.getHost() + ":" + config.getPort());
    }

}

@Data
class DruidServerConfig{

    @JsonProperty @NotNull private String host;

    @JsonProperty @Min(6060) private int port = 8080;

}