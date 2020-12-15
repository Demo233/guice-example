package ml.yihao;

import com.google.inject.*;
import com.google.inject.multibindings.OptionalBinder;

/**
 * @author zyh
 * @Description:
 * @date 2020/12/159:12 下午
 */
public class Example3 {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new FrameWorkModule()/*, new Module() {
            @Override
            public void configure(Binder binder) {
                OptionalBinder.newOptionalBinder(binder, Emit.class).setBinding().to(KafkaEmit.class);
            }
        }*/);
        TestService instance = injector.getInstance(TestService.class);
        instance.print();
    }
}

class TestService{

    private Emit emit;

    @Inject
    TestService(Emit emit){
        this.emit = emit;
    }

    public void print(){
        this.emit.emit();
    }



}

interface Emit{
    void emit();
}

class KafkaEmit implements Emit{

    @Override
    public void emit() {
        System.out.println("kafka");
    }
}

class HttpEmit implements Emit{

    @Override
    public void emit() {
        System.out.println("http");
    }
}

class FrameWorkModule implements Module {

    @Override
    public void configure(Binder binder) {
        OptionalBinder.newOptionalBinder(binder, Emit.class).setDefault().to(HttpEmit.class);
    }
}
