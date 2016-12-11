package name.trifon.camel.example.activemq;

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.CamelContext;
import org.apache.camel.main.MainListener;
import org.apache.camel.main.MainSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author trifon
 *
 */
public class CamelEventListener implements MainListener {

	Logger log = LoggerFactory.getLogger(this.getClass());


	@Override
	public void beforeStart(MainSupport main) {
		log.info("TRIFON - beforeStart.");
	}

	@Override
	public void configure(CamelContext camelContext) {
		log.info("TRIFON - configure.");

		//   How do I embed a Broker inside a Connection
		// - http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html
		BrokerService broker = new BrokerService();
//		broker.setBrokerName("fred"); // vm://fred
		broker.setUseJmx( true );
		broker.setUseShutdownHook( true );

		// Add plugin
//		broker.setPlugins(new BrokerPlugin[]{new JaasAuthenticationPlugin()});

		// Add a network connection
//		NetworkConnector connector = answer.addNetworkConnector("static://"+"tcp://somehost:61616");
//		connector.setDuplex(true);

		try {
			broker.addConnector("tcp://localhost:61616");
			broker.start();
		} catch (Exception ex) {
			log.error( ex.getMessage() );
			ex.printStackTrace();
		}


		camelContext.addComponent("activemq", activeMQComponent("vm://localhost?broker.persistent=true"));
	}

	@Override
	public void afterStart(MainSupport main) {
		log.info("TRIFON - afterStart.");
	}

	@Override
	public void beforeStop(MainSupport main) {
		log.info("TRIFON - beforeStop.");
	}

	@Override
	public void afterStop(MainSupport main) {
		log.info("TRIFON - afterStop.");
	}
}