package org.microservice.jforward.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class StaticHandler implements HttpHandler {

	private String baseFolder;
	
	public StaticHandler(String folder){
		this.baseFolder = folder;
	}
	
	
	@Override
	public void handleRequest(HttpServerExchange exchange) throws Exception {
		
		
	}

}
