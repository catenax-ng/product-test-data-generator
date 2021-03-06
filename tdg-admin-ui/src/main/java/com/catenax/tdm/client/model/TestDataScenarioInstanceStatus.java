/*
 * Catena-X Speedboat Test Data Generator (TDG)
 * Disclaimer: This service serves synthetic, none-productive data for testing purposes only. All BOMs, part trees, VINs, serialNos etc. are synthetic
 *
 * OpenAPI spec version: 1.0.1-SNAPSHOT
 * Contact: christian.kabelin@partner.bmw.de
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.catenax.tdm.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
/**
 * TestDataScenarioInstanceStatus
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-01-25T13:02:41.126Z[GMT]")
public class TestDataScenarioInstanceStatus {
  @SerializedName("name")
  private String name = null;

  @SerializedName("scenario")
  private String scenario = null;

  @SerializedName("version")
  private String version = null;

  public TestDataScenarioInstanceStatus name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @Schema(description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TestDataScenarioInstanceStatus scenario(String scenario) {
    this.scenario = scenario;
    return this;
  }

   /**
   * Get scenario
   * @return scenario
  **/
  @Schema(description = "")
  public String getScenario() {
    return scenario;
  }

  public void setScenario(String scenario) {
    this.scenario = scenario;
  }

  public TestDataScenarioInstanceStatus version(String version) {
    this.version = version;
    return this;
  }

   /**
   * Get version
   * @return version
  **/
  @Schema(description = "")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestDataScenarioInstanceStatus testDataScenarioInstanceStatus = (TestDataScenarioInstanceStatus) o;
    return Objects.equals(this.name, testDataScenarioInstanceStatus.name) &&
        Objects.equals(this.scenario, testDataScenarioInstanceStatus.scenario) &&
        Objects.equals(this.version, testDataScenarioInstanceStatus.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, scenario, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestDataScenarioInstanceStatus {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    scenario: ").append(toIndentedString(scenario)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
