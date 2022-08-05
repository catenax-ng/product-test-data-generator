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

package com.catenax.tdm.client.api;

import com.catenax.tdm.client.ApiCallback;
import com.catenax.tdm.client.ApiClient;
import com.catenax.tdm.client.ApiException;
import com.catenax.tdm.client.ApiResponse;
import com.catenax.tdm.client.Configuration;
import com.catenax.tdm.client.Pair;
import com.catenax.tdm.client.ProgressRequestBody;
import com.catenax.tdm.client.ProgressResponseBody;
import com.catenax.tdm.client.model.DataTemplate;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.*;

public class DataTemplateApi {
    private ApiClient apiClient;

    public DataTemplateApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DataTemplateApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for createDataTemplateUsingPOST
     * @param body  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call createDataTemplateUsingPOSTCall(DataTemplate body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;
        
        // create path and map variables
        String localVarPath = "/catena-x/tdm/1.1/datatemplate";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private Call createDataTemplateUsingPOSTValidateBeforeCall(DataTemplate body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        Call call = createDataTemplateUsingPOSTCall(body, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Create DataTemplate
     * Create DataTemplate
     * @param body  (optional)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object createDataTemplateUsingPOST(DataTemplate body) throws ApiException {
        ApiResponse<Object> resp = createDataTemplateUsingPOSTWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Create DataTemplate
     * Create DataTemplate
     * @param body  (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> createDataTemplateUsingPOSTWithHttpInfo(DataTemplate body) throws ApiException {
        Call call = createDataTemplateUsingPOSTValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Create DataTemplate (asynchronously)
     * Create DataTemplate
     * @param body  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call createDataTemplateUsingPOSTAsync(DataTemplate body, final ApiCallback<Object> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = createDataTemplateUsingPOSTValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for deleteDataTemplateUsingDELETE
     * @param template template (required)
     * @param version version (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call deleteDataTemplateUsingDELETECall(String template, String version, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/catena-x/tdm/1.1/datatemplate/{template}/{version}"
            .replaceAll("\\{" + "template" + "\\}", apiClient.escapeString(template.toString()))
            .replaceAll("\\{" + "version" + "\\}", apiClient.escapeString(version.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private Call deleteDataTemplateUsingDELETEValidateBeforeCall(String template, String version, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'template' is set
        if (template == null) {
            throw new ApiException("Missing the required parameter 'template' when calling deleteDataTemplateUsingDELETE(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException("Missing the required parameter 'version' when calling deleteDataTemplateUsingDELETE(Async)");
        }
        
        Call call = deleteDataTemplateUsingDELETECall(template, version, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Delete DataTemplate
     * Delete DataTemplate
     * @param template template (required)
     * @param version version (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object deleteDataTemplateUsingDELETE(String template, String version) throws ApiException {
        ApiResponse<Object> resp = deleteDataTemplateUsingDELETEWithHttpInfo(template, version);
        return resp.getData();
    }

    /**
     * Delete DataTemplate
     * Delete DataTemplate
     * @param template template (required)
     * @param version version (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> deleteDataTemplateUsingDELETEWithHttpInfo(String template, String version) throws ApiException {
        Call call = deleteDataTemplateUsingDELETEValidateBeforeCall(template, version, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Delete DataTemplate (asynchronously)
     * Delete DataTemplate
     * @param template template (required)
     * @param version version (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call deleteDataTemplateUsingDELETEAsync(String template, String version, final ApiCallback<Object> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = deleteDataTemplateUsingDELETEValidateBeforeCall(template, version, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for getDataTemplatesUsingGET
     * @param template template (required)
     * @param version version (required)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call getDataTemplatesUsingGETCall(String template, String version, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/catena-x/tdm/1.1/datatemplate/{template}/{version}"
            .replaceAll("\\{" + "template" + "\\}", apiClient.escapeString(template.toString()))
            .replaceAll("\\{" + "version" + "\\}", apiClient.escapeString(version.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private Call getDataTemplatesUsingGETValidateBeforeCall(String template, String version, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'template' is set
        if (template == null) {
            throw new ApiException("Missing the required parameter 'template' when calling getDataTemplatesUsingGET(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException("Missing the required parameter 'version' when calling getDataTemplatesUsingGET(Async)");
        }
        
        Call call = getDataTemplatesUsingGETCall(template, version, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Get DataTemplate(s)
     * Get DataTemplate(s)
     * @param template template (required)
     * @param version version (required)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object getDataTemplatesUsingGET(String template, String version) throws ApiException {
        ApiResponse<Object> resp = getDataTemplatesUsingGETWithHttpInfo(template, version);
        return resp.getData();
    }

    /**
     * Get DataTemplate(s)
     * Get DataTemplate(s)
     * @param template template (required)
     * @param version version (required)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> getDataTemplatesUsingGETWithHttpInfo(String template, String version) throws ApiException {
        Call call = getDataTemplatesUsingGETValidateBeforeCall(template, version, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Get DataTemplate(s) (asynchronously)
     * Get DataTemplate(s)
     * @param template template (required)
     * @param version version (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call getDataTemplatesUsingGETAsync(String template, String version, final ApiCallback<Object> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = getDataTemplatesUsingGETValidateBeforeCall(template, version, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for updateDataTemplateContentUsingPUT
     * @param template template (required)
     * @param version version (required)
     * @param body  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call updateDataTemplateContentUsingPUTCall(String template, String version, String body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;
        
        // create path and map variables
        String localVarPath = "/catena-x/tdm/1.1/datatemplate/{template}/{version}/content"
            .replaceAll("\\{" + "template" + "\\}", apiClient.escapeString(template.toString()))
            .replaceAll("\\{" + "version" + "\\}", apiClient.escapeString(version.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private Call updateDataTemplateContentUsingPUTValidateBeforeCall(String template, String version, String body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        // verify the required parameter 'template' is set
        if (template == null) {
            throw new ApiException("Missing the required parameter 'template' when calling updateDataTemplateContentUsingPUT(Async)");
        }
        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException("Missing the required parameter 'version' when calling updateDataTemplateContentUsingPUT(Async)");
        }
        
        Call call = updateDataTemplateContentUsingPUTCall(template, version, body, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Update DataTemplate content
     * Update DataTemplate content
     * @param template template (required)
     * @param version version (required)
     * @param body  (optional)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object updateDataTemplateContentUsingPUT(String template, String version, String body) throws ApiException {
        ApiResponse<Object> resp = updateDataTemplateContentUsingPUTWithHttpInfo(template, version, body);
        return resp.getData();
    }

    /**
     * Update DataTemplate content
     * Update DataTemplate content
     * @param template template (required)
     * @param version version (required)
     * @param body  (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> updateDataTemplateContentUsingPUTWithHttpInfo(String template, String version, String body) throws ApiException {
        Call call = updateDataTemplateContentUsingPUTValidateBeforeCall(template, version, body, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Update DataTemplate content (asynchronously)
     * Update DataTemplate content
     * @param template template (required)
     * @param version version (required)
     * @param body  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call updateDataTemplateContentUsingPUTAsync(String template, String version, String body, final ApiCallback<Object> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = updateDataTemplateContentUsingPUTValidateBeforeCall(template, version, body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
    /**
     * Build call for updateDataTemplateUsingPUT
     * @param body  (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public Call updateDataTemplateUsingPUTCall(DataTemplate body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = body;
        
        // create path and map variables
        String localVarPath = "/catena-x/tdm/1.1/datatemplate";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private Call updateDataTemplateUsingPUTValidateBeforeCall(DataTemplate body, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        Call call = updateDataTemplateUsingPUTCall(body, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Update DataTemplate
     * Update DataTemplate
     * @param body  (optional)
     * @return Object
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public Object updateDataTemplateUsingPUT(DataTemplate body) throws ApiException {
        ApiResponse<Object> resp = updateDataTemplateUsingPUTWithHttpInfo(body);
        return resp.getData();
    }

    /**
     * Update DataTemplate
     * Update DataTemplate
     * @param body  (optional)
     * @return ApiResponse&lt;Object&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<Object> updateDataTemplateUsingPUTWithHttpInfo(DataTemplate body) throws ApiException {
        Call call = updateDataTemplateUsingPUTValidateBeforeCall(body, null, null);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Update DataTemplate (asynchronously)
     * Update DataTemplate
     * @param body  (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public Call updateDataTemplateUsingPUTAsync(DataTemplate body, final ApiCallback<Object> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        Call call = updateDataTemplateUsingPUTValidateBeforeCall(body, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<Object>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}