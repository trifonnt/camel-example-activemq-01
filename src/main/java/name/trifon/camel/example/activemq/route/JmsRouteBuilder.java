package name.trifon.camel.example.activemq.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author trifon
 */
public class JmsRouteBuilder extends RouteBuilder {

	/**
	 * Let's configure the Camel routing rules using Java code...
	 */
	public void configure() {

		// Sample which processes the input files
		// (leaving them in place - see the 'noop' flag)
		// then performs content based routing on the message using XPath
		from("file:src/data?noop=true")
			.to("activemq:personnel.records")
		;

		from("activemq:personnel.records")
			.choice()
				.when(xpath("/person/city = 'London'"))
					.log("UK message")
					.to("file:target/messages/uk")
				.otherwise()
					.log("Other message")
					.to("file:target/messages/others")
		;
	}
}