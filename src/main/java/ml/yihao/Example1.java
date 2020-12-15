package ml.yihao;

import com.google.inject.*;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * @author zyh
 * @Description:
 * @date 2020/12/158:34 下午
 */
public class Example1 {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module() {
            public void configure(Binder binder) {
                binder.bind(DataBaseMeta.class).to(MysqlDataBaseMeta.class);
                // 注入用户名
                binder.bind(String.class).annotatedWith(Names.named("username")).toInstance("root");
                // 注入密码
                binder.bind(String.class).annotatedWith(Names.named("password")).toInstance("^5g%@!hKH");
            }
        });

        DataBaseMeta dataBaseMeta = injector.getInstance(DataBaseMeta.class);
        dataBaseMeta.print();
    }

}

interface DataBaseMeta{

    void print();
}

@Singleton
class MysqlDataBaseMeta implements DataBaseMeta {


    private String username;
    private String password;

    @Inject
    MysqlDataBaseMeta(@Named("username") String username, @Named("password") String password){
        this.username = username;
        this.password = password;
    }

    public void print() {
        System.out.println(username + ":" + password);
    }
}
