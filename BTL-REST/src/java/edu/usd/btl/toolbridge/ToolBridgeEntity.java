package edu.usd.btl.toolbridge;

import edu.usd.btl.toolbets.ToolBetsEntity;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tyler_000
 */
@Entity
@Table(name = "tool_bridge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ToolBridgeEntity.findAll", query = "SELECT t FROM ToolBridgeEntity t"),
    @NamedQuery(name = "ToolBridgeEntity.findById", query = "SELECT t FROM ToolBridgeEntity t WHERE t.id = :id"),
    @NamedQuery(name = "ToolBridgeEntity.findByOntologyId", query = "SELECT t FROM ToolBridgeEntity t WHERE t.ontologyId = :ontologyId")})
public class ToolBridgeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ontology_id")
    private String ontologyId;
    @NotNull
    @JoinColumn(name = "tool_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ToolBetsEntity toolId;

    public ToolBridgeEntity() {
    }

    public ToolBridgeEntity(Integer id) {
        this.id = id;
    }

    public ToolBridgeEntity(Integer bridgeId, String ontologyId) {
        this.id = bridgeId;
        this.ontologyId = ontologyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOntologyId() {
        return ontologyId;
    }

    public void setOntologyId(String ontologyId) {
        this.ontologyId = ontologyId;
    }

    public ToolBetsEntity getToolId() {
        return toolId;
    }

    public void setToolId(ToolBetsEntity toolId) {
        this.toolId = toolId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ToolBridgeEntity)) {
            return false;
        }
        ToolBridgeEntity other = (ToolBridgeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bridge ID = " + id);
        sb.append("Tool ID = " + toolId);
        sb.append("Ontology ID: " + ontologyId);
        return sb.toString();
    }
    
}
