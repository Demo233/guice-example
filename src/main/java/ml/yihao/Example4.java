package ml.yihao;

import com.google.code.guice.lifecycle.*;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author zyh
 * @Description:
 * @date 2020/12/1510:04 下午
 */
public class Example4 {

    public static void main(String[] args) throws Exception{
        Injector injector = Guice.createInjector(new LifecycleModule());
        Bootstrap bootstrap = injector.getInstance(Bootstrap.class);
        bootstrap.start();

    }

    @ManageLifecycle
    public static class PrintLifecycle{

        @LifecycleStart
        void start(){
            System.out.println("start");
        }

        @LifecycleStop
        void stop(){
            System.out.println("stop");
        }

    }

    // 定义好类
    public static class Bootstrap{

        private PrintLifecycle printLifecycle;
        private Lifecycle lifecycle;

        @Inject
        Bootstrap(PrintLifecycle printLifecycle, Lifecycle lifecycle){
            this.printLifecycle = printLifecycle;
            this.lifecycle = lifecycle;
        }

        void start() throws Exception {
            System.out.println("bootstrap start");
            lifecycle.start();
            // 等待子线程运行完成以后主线程终止
            lifecycle.join();
        }
    }

}





