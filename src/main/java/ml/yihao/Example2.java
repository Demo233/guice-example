package ml.yihao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * @author zyh
 * @Description: 默认注入Mysql，后续覆盖成oracle
 * @date 2020/12/158:48 下午
 */
public class Example2 {

    public static void main(String[] args) {
        ImmutableList<Module> defaultModule = ImmutableList.of(binder -> {
            binder.bind(Database.class).to(MysqlDatabase.class);
        });

        ImmutableList<Module> customModule = ImmutableList.of(binder -> {
            binder.bind(Database.class).to(OracleDatabase.class);
        });

        // 默认
        //Injector injector = Guice.createInjector(defaultModule);

        // 覆盖
        Injector injector = Guice.createInjector(Modules.override(defaultModule).with(customModule));

        FrameWork instance = injector.getInstance(FrameWork.class);
        instance.start();

    }

}

class FrameWork{

    private Database database;

    @Inject
    FrameWork(Database database){
        this.database = database;
    }

    public void start(){
        database.print();
    }

}

interface Database{
    void print();
}

class MysqlDatabase implements Database {
    private String type = "mysql";

    public void print() {
        System.out.println(type);
    }
}


class OracleDatabase implements Database {
    private String type = "oracle";

    public void print() {
        System.out.println(type);
    }
}
