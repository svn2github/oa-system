/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.dao.business.pr;

import java.util.List;

import net.sourceforge.model.business.pr.YearlyBudget;

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
