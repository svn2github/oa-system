/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.service.dataTransfer;

import net.sourceforge.model.admin.DataTransferParameter;

/**
 * 定义ERP数据转换的导出文件接口
 * @author ych
 * @version 1.0 (Dec 22, 2005)
 */
public interface ExportManager {
    
    public void exportFile(DataTransferParameter para) throws Exception ;
    
    
}
