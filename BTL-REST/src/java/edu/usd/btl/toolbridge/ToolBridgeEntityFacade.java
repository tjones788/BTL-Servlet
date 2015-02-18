/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.usd.btl.toolbridge;

import edu.usd.btl.toolbets.ToolBetsEntity;
import edu.usd.btl.utility.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import edu.usd.btl.toolbridge.ToolBridgeEntity;

/**
 *
 * @author Tyler_000
 */
@Stateless
public class ToolBridgeEntityFacade extends AbstractFacade<ToolBridgeEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ToolBridgeEntityFacade() {
        super(ToolBridgeEntity.class);
    }

    @Override
    public void create(ToolBridgeEntity toolBridge) {
        super.create(toolBridge);
    }
    
    
}
