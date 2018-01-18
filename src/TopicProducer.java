import java.util.Date;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicProducer {
	private static final String CONNECTIONFACTORY = "jms/RemoteConnectionFactory";
	private static final String QUEUE_DESTINATION = "jms/queue/testqueue";
	private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
	private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";

	public static void main(String[] args) throws NamingException {
		Context namingContext = null;
		JMSContext context = null;

		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL, PROVIDER_URL);
		env.put(Context.SECURITY_PRINCIPAL, "guest");
		env.put(Context.SECURITY_CREDENTIALS, "guest");
		try {
			namingContext = new InitialContext(env);
			ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(CONNECTIONFACTORY);
			Destination destination = (Destination) namingContext.lookup(QUEUE_DESTINATION);
			context = connectionFactory.createContext();
			context.createProducer().send(destination, "This is my hello JMS message at " + new Date());
			System.out.println("Message sent.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (namingContext != null) {
				namingContext.close();
			}
			if (context != null) {
				context.close();
			}
		}

	}

}
