package name.trifon.camel.example.activemq.route;

import org.apache.camel.builder.RouteBuilder;

import name.trifon.camel.example.activemq.processor.RequestProcessor;
import name.trifon.camel.example.activemq.processor.ResultProcessor;


/**
 * @author trifon
 */
public class JmsRouteBuilder2 extends RouteBuilder {

	/**
	 * Let's configure the Camel routing rules using Java code...
	 */
/*
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
*/
	public void configure() throws Exception {
//		from("file://data?fileName=some-file.txt&noop=true").id("file-consumer")
		from("file://src/data?noop=true").id("file-consumer")
			.log("Processing file [${file:name}]")
			.log("   file content [${body}]")

			.process(new RequestProcessor() )
			.log("BODY AFTER RequestProcessor [${body}]")

			// .toF("test-broker://queue:%s?exchangePattern=InOut&useMessageIDAsCorrelationID=%b", "personnel.records", true) // useMessageIDAsCorrelationID=true
			.toF("activemq://queue:%s?exchangePattern=InOut&useMessageIDAsCorrelationID=%b", "personnel.records", true) // useMessageIDAsCorrelationID=true
			.log("BODY AFTER JMS Queue [${body}]")

			.process(new ResultProcessor() )
			.log("BODY AFTER ResultProcessor [${body}]")

			.to("mock://result");
	}
}