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

package com.catenax.tdm.client;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-01-25T13:02:41.126Z[GMT]")public class Configuration {
    private static ApiClient defaultApiClient = new ApiClient();

    /**
     * Get the default API client, which would be used when creating API
     * instances without providing an API client.
     *
     * @return Default API client
     */
    public static ApiClient getDefaultApiClient() {
        return defaultApiClient;
    }

    /**
     * Set the default API client, which would be used when creating API
     * instances without providing an API client.
     *
     * @param apiClient API client
     */
    public static void setDefaultApiClient(ApiClient apiClient) {
        defaultApiClient = apiClient;
    }
}
