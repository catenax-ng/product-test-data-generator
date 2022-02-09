

var geo = scenario.getSchema("geo", "1.0");
var person = scenario.getSchema("person", "1.0");
var array = scenario.getSchema("arrays", "1.0");
var emp = scenario.getSchema("employee", "1.1");

var x = scenario.generateTestData(array, 10);
var g = scenario.generateTestData(geo, 5);
var e = scenario.generateTestData(emp, 10);

var p1 = scenario.generateTestData(person);
var a1 = scenario.generateTestData(array);


p1.put("firstName", "George");

a1.getJSONArray("fruits").put(0, "Apple");
a1.getJSONArray("vegetables").get(0).put("veggieName", "radic");
