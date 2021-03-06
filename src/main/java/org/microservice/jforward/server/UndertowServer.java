package org.microservice.jforward.server;

import java.util.List;
import java.util.Map;

import org.microservice.jforward.circuitbreaker.RequestCircuitBreakerHandlerWrapper;
import org.microservice.jforward.configure.ForwardConfig.StaticConfig;
import org.microservice.jforward.configure.ForwardConfig.UpstreamConfig;
import org.microservice.jforward.handler.RootHandler;

import io.undertow.Undertow;

/**
 *  the forward server implements by undertow
 */
public class UndertowServer implements Server {
	
	private String                          name;
	private String                          host;
	private int                             port;
	
	private Map<String, String>       urlMapping;
	private List<UpstreamConfig>  upstreamConfig;
	private List<StaticConfig>      staticConfig;
	
	private Undertow                      server;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Map<String, String> getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(Map<String, String> urlMapping) {
		this.urlMapping = urlMapping;
	}

	public List<UpstreamConfig> getUpstreamConfig() {
		return upstreamConfig;
	}

	public void setUpstreamConfig(List<UpstreamConfig> upstreamConfig) {
		this.upstreamConfig = upstreamConfig;
	}
	
	public void init(){
		Undertow.Builder builder = Undertow.builder();
		RootHandler rootHandler = new RootHandler(urlMapping, upstreamConfig, staticConfig);
		RequestCircuitBreakerHandlerWrapper requestWrapper = new RequestCircuitBreakerHandlerWrapper(rootHandler);
		builder = builder.addHttpListener(port, host)
					.setIoThreads(2)
					.setWorkerThreads(Runtime.getRuntime().availableProcessors() * 10)
					.setHandler(requestWrapper);
		server = builder.build();
	}

	@Override
	public void start() {
		try {
			server.start();
		} catch (Exception e){
			// stop the server
			server.stop();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void stop() {
		if ( server != null )
			server.stop();
	}

}
