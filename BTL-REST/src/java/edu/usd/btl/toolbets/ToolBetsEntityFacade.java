package edu.usd.btl.toolbets;

import edu.usd.btl.utility.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.usd.btl.toolbets.ToolBetsEntity;

/**
 *
 * @author Tyler_000
 */
@Stateless
public class ToolBetsEntityFacade extends AbstractFacade<ToolBetsEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ToolBetsEntityFacade() {
        super(ToolBetsEntity.class);
    }

    public List<ToolBetsEntity> findByName(String name) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery cq = builder.createQuery();

        Root<ToolBetsEntity> toolBets = cq.from(ToolBetsEntity.class);
        cq.select(toolBets);
        cq.where(builder.equal(toolBets.get("name"), name));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<ToolBetsEntity> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(ToolBetsEntity.class));
        List<ToolBetsEntity> foundList = getEntityManager().createQuery(cq).getResultList();
        //sort list
        //Collections.sort(foundList);
        return foundList;
    }

    @Override
    public void create(ToolBetsEntity toolBets) {
        super.create(toolBets);
    }

    public byte[] getBets(){
        ToolBetsEntity locEnt = super.find(27);
        System.out.println(locEnt.getName());
        return locEnt.getBets();
    }
}
