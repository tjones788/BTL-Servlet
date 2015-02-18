package edu.usd.btl.toolTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.ejb.Stateless;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
"name",
"parent",
"uri"
})
@Stateless
public class EDAMNode {

@JsonProperty("name")
private String name;
@JsonProperty("parent")
private List<String> parent = new ArrayList<String>();
@JsonProperty("uri")
private String uri;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* 
* @return
* The name
*/
@JsonProperty("name")
public String getName() {
return name;
}

/**
* 
* @param name
* The name
*/
@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

/**
* 
* @return
* The parent
*/
@JsonProperty("parent")
public List<String> getParent() {
return parent;
}

/**
* 
* @param parent
* The parent
*/
@JsonProperty("parent")
public void setParent(List<String> parent) {
this.parent = parent;
}

/**
* 
* @return
* The uri
*/
@JsonProperty("uri")
public String getUri() {
return uri;
}

/**
* 
* @param uri
* The uri
*/
@JsonProperty("uri")
public void setUri(String uri) {
this.uri = uri;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}