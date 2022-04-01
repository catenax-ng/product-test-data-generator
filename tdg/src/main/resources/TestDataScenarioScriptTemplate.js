
function cloneElement(element) {
	var result = element;
	
	var cName = element.getClass().toString();

	if(cName.equals("class org.json.JSONObject")) {
		result = new org.json.JSONObject(element.toString());
	} else if(cName.equals("class org.json.JSONArray")) { 
		log.info("CLONE JSON ARRAY");
	} else {
		log.info("CLONE OBJECT");
		log.info(element.getClass());
	}
	
	
	return result;
}

${script}

