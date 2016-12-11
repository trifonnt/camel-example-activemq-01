package name.trifon.camel.example.activemq;

import org.apache.camel.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.trifon.camel.example.activemq.route.JmsRouteBuilder;

/**
 * @author trifon
 *
 */
public class CamelExampleActiveMqMain {

	static Logger LOG = LoggerFactory.getLogger(CamelExampleActiveMqMain.class.getName());

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String... args) throws Exception {
		Main main = new Main();

		//   Running Camel standalone and have it keep running
		// - http://camel.apache.org/running-camel-standalone-and-have-it-keep-running.html

		//   How do I embed a Broker inside a Connection
		// - http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html


		// Bind MyBean into the registry
//		main.bind("foo", new MyBean());
/*
    private static class MyRouteBuilder extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("timer:foo?delay={{millisecs}}")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Invoked timer at " + new Date());
                    }
                })
                .bean("foo");
        }
    }
 */

		// Add Camel Routes
//		main.addRouteBuilder(new FileRouteBuilder());
		main.addRouteBuilder(new JmsRouteBuilder());


		// Add event listener
        main.addMainListener(new CamelEventListener());

        // Set the properties from a file - DO NOT WORK!!!
//        main.setPropertyPlaceholderLocations("example.properties");

        // Run until you terminate the JVM
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run( args );
	}

}