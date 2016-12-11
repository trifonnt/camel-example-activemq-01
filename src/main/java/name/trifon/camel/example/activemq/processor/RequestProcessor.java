package name.trifon.camel.example.activemq.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author trifon
 */
public class RequestProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		exchange.getIn().setBody("Request: " + body);

		return;
	}
}