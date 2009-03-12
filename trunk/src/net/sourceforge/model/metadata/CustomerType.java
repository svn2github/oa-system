/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

public class CustomerType extends MetadataDetailEnum {
    public static final CustomerType CUSTOMER = new CustomerType(1, "Customer", MetadataType.CUSTOMER_TYPE);
    public static final CustomerType ENTITY = new CustomerType(2, "Entity", MetadataType.CUSTOMER_TYPE);
    
    static {
        CUSTOMER.rechargeType = RechargeType.CUSTOMER;
        ENTITY.rechargeType = RechargeType.ENTITY;
    }
    
    public CustomerType() {
    }

    private CustomerType(int metadataId, String defaultLabel, MetadataType type) {
        super(metadataId, defaultLabel, type);
    }
    
    private RechargeType rechargeType;

    /**
     * @return Returns the rechargeType.
     */
    public RechargeType getRechargeType() {
        return rechargeType;
    }

}
