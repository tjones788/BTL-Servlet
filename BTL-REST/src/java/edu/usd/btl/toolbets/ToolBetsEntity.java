package edu.usd.btl.toolbets;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import edu.usd.btl.toolbridge.ToolBridgeEntity;

/**
 *
 * @author Tyler_000
 * @author <Bill.Conn@usd.edu>
 */
@Entity
@Table(name = "tool_bets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ToolBetsEntity.findAll", query = "SELECT t FROM ToolBetsEntity t"),
    @NamedQuery(name = "ToolBetsEntity.findById", query = "SELECT t FROM ToolBetsEntity t WHERE t.id = :id"),
    @NamedQuery(name = "ToolBetsEntity.findByName", query = "SELECT t FROM ToolBetsEntity t WHERE t.name = :name"),
    @NamedQuery(name = "ToolBetsEntity.findByVersion", query = "SELECT t FROM ToolBetsEntity t WHERE t.version = :version"),
    @NamedQuery(name = "ToolBetsEntity.findBySummary", query = "SELECT t FROM ToolBetsEntity t WHERE t.summary = :summary")})
public class ToolBetsEntity implements Serializable {
    @Lob
    @Column(name = "bets")
    private byte[] bets;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    
    @Size(max = 255)
    @Column(name = "version")
    private String version;
    
    @Size(max = 4096)
    @Column(name = "summary")
    private String summary;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toolId")
    private List<ToolBridgeEntity> toolBridgeEntityList;

    public ToolBetsEntity() {
    }

    public ToolBetsEntity(Integer id) {
        this.id = id;
    }

    public ToolBetsEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) throws Exception{
        if(id == null) {
            throw new Exception("Null tool id supplied");
        }
        if(id < 0) {
            throw new Exception("tool id cannot be negative");                   
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    @XmlTransient
    public List<ToolBridgeEntity> getToolBridgeEntityList() {
        return toolBridgeEntityList;
    }

    public void setToolBridgeEntityList(List<ToolBridgeEntity> toolBridgeEntityList) {
        this.toolBridgeEntityList = toolBridgeEntityList;
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
        if (!(object instanceof ToolBetsEntity)) {
            return false;
        }
        ToolBetsEntity other = (ToolBetsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tools Id=" + id;
    }

    public byte[] getBets() {
        return bets;
    }

    public void setBets(byte[] bets) {
        this.bets = bets;
    }
    
}
