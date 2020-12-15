package ml.yihao;

import com.google.code.guice.jsonconfig.JsonConfigModule;
import com.google.code.jersey.Jerseys;
import com.google.code.jersey.ServerConfig;
import com.google.code.jersey.jetty.JerseyJettyServer;
import com.google.code.jersey.jetty.JettyServerModule;
import com.google.inject.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Properties;

/**
 * @author zyh
 * @Description:
 * @date 2020/12/1510:46 下午
 */
public class Example6 {

    public static void main(String[] args) throws Exception{
        Injector injector = Guice.createInjector(new JettyServerModule(), new JsonConfigModule(), new Module() {
            @Override
            public void configure(Binder binder) {

                Jerseys.addResource(binder, IndexResource.class);
            }

            Properties init() {
                Properties prop = new Properties();
                prop.put("server.http.host", "0.0.0.0");
                prop.put("server.http.port", 8080);
                return prop;
            }

        });
        JerseyJettyServer jerseyJettyServer = injector.getInstance(JerseyJettyServer.class);
        jerseyJettyServer.start();
        Thread.currentThread().join();
    }



    @Singleton
    @Path("/index")
    public static class IndexResource {
        private ServerConfig serverConfig;
        @Inject
        public IndexResource(ServerConfig serverConfig) {
            this.serverConfig = serverConfig;
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public ServerConfig doGet(@Context final HttpServletRequest req) {
            return serverConfig;
        }
    }

}
