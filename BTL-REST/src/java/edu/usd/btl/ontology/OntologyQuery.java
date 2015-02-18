/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usd.btl.ontology;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.core.DatasetImpl;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Xinghua
 */
public class OntologyQuery {
    
    private String service = null;
    private String apikey = null;

    public OntologyQuery(String service, String apikey) {
        
        this.service = service;
        this.apikey = apikey;
    }

    /**
     * run sparql and get query result 
     * @param queryString
     * @return
     * @throws Exception 
     */
    public ResultSet executeQuery(String queryString) throws Exception {
        //get the sparql query string
        Query query = QueryFactory.create(queryString) ;
        //connect the server API
        QueryEngineHTTP qexec = QueryExecutionFactory.createServiceRequest(this.service, query);
        //get the API key
        qexec.addParam("apikey", this.apikey);
        //run the query
        ResultSet results = qexec.execSelect() ;
        return results;
    }
    
    /**
     * Server and API-Key are included inside the query language
     * 
     * @param queryString
     * @return
     * @throws Exception 
     */
    
     public ResultSet executeQueryWithOutServerSet(String queryString) throws Exception {
        
            QueryExecution exec =  QueryExecutionFactory.create(QueryFactory.create(queryString), new
                            DatasetImpl(ModelFactory.createDefaultModel()));
            return exec.execSelect();

	}
    
    /**
     * overload executeQuery with acceptFormat
     * Accept formats can be: "text/plain", "application/json", "application/rdfxml", "text/csv", "text/tab-separated-values"
     * @param queryText
     * @param acceptFormat
     * @return
     * @throws Exception 
     */
    
    public String executeQuery(String queryText, String acceptFormat) throws Exception {
		String httpQueryString = String.format("query=%s&apikey=%s", 
			     URLEncoder.encode(queryText, "UTF-8"), 
			     URLEncoder.encode(this.apikey, "UTF-8"));

		URL url = new URL(this.service + "?" + httpQueryString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", acceptFormat);

		conn.connect();
		InputStream in = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder buff = new StringBuilder();
		String line = null;
		while ((line = reader.readLine())!=null) {
			buff.append(line);
			buff.append("\n");
		}
		conn.disconnect();
		return buff.toString();
	}
}
