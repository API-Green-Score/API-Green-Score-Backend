package fr.pagesjaunes.socletechnique.logging;

/**
 * @author gdespres
 */
public final class LoggingConstants {

	// ====================================================================
	// CONSTANTS
	// ====================================================================

	public static final String REQUEST_MILLIS = "httpRequest.millis";

	public static final String REQUEST_ID = "requestId";

	public static final String CONTEXT_ID = "contextId";

	public static final String REQUEST_PROTOCOL = "httpRequest.protocol";

	public static final String REQUEST_METHOD = "httpRequest.requestMethod";

	public static final String REQUEST_URL = "httpRequest.requestUrl";

	public static final String REQUEST_USERAGENT = "httpRequest.userAgent";

	public static final String REQUEST_REMOTEIP = "httpRequest.remoteIp";

	public static final String REQUEST_REFERER = "httpRequest.referer";

	public static final String REQUEST_HEADERS = "httpRequest.requestHeaders";

	public static final String RESPONSE_STATUS = "httpRequest.status";

	public static final String RESPONSE_LATENCY = "httpRequest.latency";

	public static final String RESPONSE_SIZE = "httpRequest.responseSize";

	public static final String RESPONSE_HEADERS = "httpRequest.responseHeaders";

	// ====================================================================
	// PRIVATE CONSTRUCTOR
	// ====================================================================

	private LoggingConstants() {
		// NO-OP
	}
}
