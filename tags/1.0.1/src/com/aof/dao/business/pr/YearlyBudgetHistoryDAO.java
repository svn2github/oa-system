/* =====================================================================
 *
 * Copyright (c) Atos Origin INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package com.aof.dao.business.pr;

import java.util.List;

import com.aof.model.business.pr.YearlyBudget;

/**
 * dao interface for domain model yearlyBudgetHistory
 * @author shilei
 * @version 1.0 (Nov 29, 2005)
 */
public interface YearlyBudgetHistoryDAO {

    /**
     * list history of yearly budget
     * @param yb
     * @return
     */
    List listHistory(final YearlyBudget yb);
}
