package name.trifon.camel.example.activemq;

import static org.apache.activemq.camel.component.ActiveMQComponent.activeMQComponent;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.camel.CamelContext;
import org.apache.camel.main.MainListener;
import org.apache.camel.main.MainSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import name.trifon.camel.example.activemq.thread.RequestReplyThread;


/**
 * @author trifon
 */
public class CamelEventListener implements MainListener {

	Logger log = LoggerFactory.getLogger(this.getClass());

	BrokerService brokerService = null;

	RequestReplyThread requestReplyThread = new RequestReplyThread();


	protected ActiveMQConnectionFactory createConnectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerService.getVmConnectorURI().toString());
		return connectionFactory;
	}


	@Override
	public void beforeStart(MainSupport main) {
		log.info("TRIFON - beforeStart.");
	}

	@Override
	public void configure(CamelContext camelContext) {
		log.info("TRIFON - configure.");

		//   How do I embed a Broker inside a Connection
		// - http://activemq.apache.org/how-do-i-embed-a-broker-inside-a-connection.html
		brokerService = new BrokerService();
//		brokerService.setBrokerName("fred"); // vm://fred
		brokerService.setUseJmx( true );
		brokerService.setUseShutdownHook( true );
		brokerService.setPersistent( true );

		// Add plugin
//		broker.setPlugins(new BrokerPlugin[]{new JaasAuthenticationPlugin()});

		// Add a network connection
//		NetworkConnector connector = answer.addNetworkConnector("static://"+"tcp://somehost:61616");
//		connector.setDuplex(true);

		try {
			brokerService.addConnector("tcp://localhost:61616");
			brokerService.start();
		} catch (Exception ex) {
			log.error( ex.getMessage() );
			ex.printStackTrace();
		}


		camelContext.addComponent("activemq", activeMQComponent("vm://localhost?broker.persistent=true"));

		requestReplyThread.setConnectionFactory( createConnectionFactory() );
		requestReplyThread.setRequestQueueName( "personnel.records" );
		requestReplyThread.setUseMessageIDAsCorrelationID(true);
		requestReplyThread.start();
	}

	@Override
	public void afterStart(MainSupport main) {
		log.info("TRIFON - afterStart.");
	}

	@Override
	public void beforeStop(MainSupport main) {
		log.info("TRIFON - beforeStop.");

		requestReplyThread.interrupt();
		try {
			requestReplyThread.join(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void afterStop(MainSupport main) {
		log.info("TRIFON - afterStop.");
	}
}