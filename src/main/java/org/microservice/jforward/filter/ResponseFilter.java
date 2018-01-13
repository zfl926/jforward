package org.microservice.jforward.filter;

import org.microservice.jforward.ResponseWrapper;

/**
 * Filter all the response
 */
public interface ResponseFilter {
	/**
	 * @param request
	 * @return
	 */
	boolean shouldFilter(ResponseWrapper response);
	/**
	 *  do the filter process
	 */
	void filter();
	/**
	 * @return
	 */
	int getOrder();
	/**
	 * @param order
	 */
	void setOrder(int order);
}
