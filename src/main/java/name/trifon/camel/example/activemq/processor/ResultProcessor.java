package name.trifon.camel.example.activemq.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author trifon
 */
public class ResultProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		if (body.startsWith("Response")) {
			exchange.getIn().setBody("We're good");
		} else {
			exchange.getIn().setBody("Bad response");
		}
		return;
	}
}