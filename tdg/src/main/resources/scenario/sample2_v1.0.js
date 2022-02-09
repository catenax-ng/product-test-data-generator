

var rel = scenario.defineSchema("relation", "1.0");
var emp = scenario.defineSchema("employee", "1.0");

var relations = scenario.generateTestData(rel, 10);
var parent = null;

function relation(item) {
	var e = scenario.generateTestData(emp);
	item.put("child", e.get("name"));

	if(parent) {
		item.put("parent", parent.get("child"));
	} else {
		item.put("parent", null);
		scenario.clone(item, rel);
	}

	parent = item;
}

relations.forEach(relation);