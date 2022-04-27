package com.catenax.tdm;

public class Env {

	public static final String TDG_API_KEY = "TDG_API_KEY";
	public static final String TDG_API_SECURE = "TDG_API_SECURE";
	public static final String TDG_API_HOST_NAME = "TDG_API_HOST_NAME";
	public static final String TDG_API_HOST_PORT = "TDG_API_HOST_PORT";

	public static final String TDG_HOST_SECURE = "TDG_HOST_SECURE";
	public static final String TDG_HOST_NAME = "TDG_HOST_NAME";
	public static final String TDG_HOST_PORT = "TDG_HOST_PORT";
	public static final String TDG_HOST_PORT_EXTERNAL = "TDG_HOST_PORT_EXTERNAL";

	public static final String TDG_IAM_SERVER_URL = "TDG_IAM_SERVER_URL";
	public static final String TDG_IAM_RESOURCE = "TDG_IAM_RESOURCE";
	public static final String TDG_IAM_REALM = "TDG_IAM_REALM";
	public static final String TDG_IAM_SECRET = "TDG_IAM_SECRET";

	// ToDo: disable / remove when infra allows it
	public static boolean SSL_TRUST_ALWAYS = true;

	public static String get(String key, String defaultValue) {
		String result = defaultValue;

		if (System.getenv().containsKey(key)) {
			result = System.getenv(key);
		}

		return result;
	}

	public static String getBaseUrl() {
		String result = "";
		if ("false".equals(Env.get(TDG_HOST_SECURE, "false"))) {
			result = "http://";
		} else {
			result = "https://";
		}
		result += Env.get(TDG_HOST_NAME, "localhost") + ":" + Env.get(TDG_HOST_PORT_EXTERNAL, "8090");
		return result;
	}
}
