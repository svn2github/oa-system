/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */

package net.sourceforge.model.metadata;

import com.shcnc.hibernate.PersistentIntegerEnum;

public class MetadataType extends PersistentIntegerEnum {
    
    public final static MetadataType YES_NO = new MetadataType(100001, "Yes or No");
    public final static MetadataType GENDER = new MetadataType(100002, "Gender");
    public final static MetadataType ENABLED_DISABLED = new MetadataType(100003, "Enabled or Disabled");
    public final static MetadataType ROLE_TYPE = new MetadataType(100101, "Role Type");
    public final static MetadataType ORGANIZATION_TYPE = new MetadataType(100102, "Organization Type");
    public final static MetadataType BUDGET_STATUS = new MetadataType(100103, "Budget Status");
    public final static MetadataType BUDGET_TYPE = new MetadataType(100104, "Budget Type");
    public final static MetadataType EXPENSE_TYPE = new MetadataType(100105, "Expense Type");
    public final static MetadataType EXPORT_STATUS = new MetadataType(100106, "Export Status");
    public final static MetadataType HOTEL_LEVEL = new MetadataType(100107, "Hotel Level");
    public final static MetadataType CUSTOMER_TYPE = new MetadataType(100108, "Customer Type");
    public final static MetadataType EXPORT_FILE_LINE_TYPE = new MetadataType(100109, "Export File Line Type");
    public final static MetadataType RECHARGE_TYPE = new MetadataType(100110, "Recharge Type");
    public final static MetadataType EXPORT_FILE_STATUS = new MetadataType(100111, "Export File Status");
    public final static MetadataType GLOBAL_MAIL_REMINDER_TYPE = new MetadataType(100112, "Global Mail Reminder Type");
    public final static MetadataType SITE_MAIL_REMINDER_TYPE = new MetadataType(100113, "Site Mail Reminder Type");
    public final static MetadataType HOTEL_PROMOTE_STATUS = new MetadataType(100114, "Hotel Promote Status");
    public final static MetadataType SUPPLIER_PROMOTE_STATUS = new MetadataType(100115, "Supplier Promote Status");
    public final static MetadataType CONTRACT_STATUS = new MetadataType(100116, "Contract Status");
    public final static MetadataType CAPEX_REQUEST_TYPE = new MetadataType(100117, "Capex Request Type");
    public final static MetadataType CAPEX_REQUEST_STATUS = new MetadataType(100118, "Capex Request Status");
    public final static MetadataType PURCHASE_REQUEST_STATUS = new MetadataType(100119, "Purchase Request Status");
    public final static MetadataType PURCHASE_ORDER_STATUS = new MetadataType(100120, "Purchase Order Status");
    public final static MetadataType SUPPLIER_CONFIRM_STATUS = new MetadataType(100121, "Supplier Confirm Status");
    public final static MetadataType APPROVE_STATUS = new MetadataType(100122, "Approve Status");
    public final static MetadataType APPROVER_DELEGATE_TYPE = new MetadataType(100123, "Approver Delegate Type");
    public final static MetadataType TRAVEL_APPLICATION_STATUS = new MetadataType(100124, "Travel Application Status");
    public final static MetadataType TRAVELLING_MODE = new MetadataType(100125, "Travelling Mode");
    public final static MetadataType SINGLE_RETURN = new MetadataType(100126, "Single or Return");
    public final static MetadataType FLIGHT_CLASS = new MetadataType(100127, "Flight Class");
    public final static MetadataType AIR_TICKET_STATUS = new MetadataType(100128, "Air Ticket Status");
    public final static MetadataType EXPENSE_STATUS = new MetadataType(100129, "Expense Status");
    public final static MetadataType CONDITION_TYPE = new MetadataType(100130, "Condition Type");
    public final static MetadataType CONDITION_COMPARE_TYPE = new MetadataType(100131, "Condition Compare Type");
    public final static MetadataType RULE_TYPE = new MetadataType(100132, "Rule Type");
	public static final MetadataType TRAVEL_APPLICATION_URGENT = new MetadataType(100133, "Travel Application Urgent");
    public static final MetadataType TRAVEL_APPLICATION_BOOK_STATUS = new MetadataType(100134, "Travel Application Book Status");
    public static final MetadataType TRAVEL_TYPE = new MetadataType(100135, "Travel Type");
    public static final MetadataType CONSEQUENCE_TYPE = new MetadataType(100136, "Consequence Type");
    public static final MetadataType EXPORT_FILE_TYPE = new MetadataType(100137, "Export File Type");
    
    static {
        YES_NO.detailClass = YesNo.class;
        GENDER.detailClass = Gender.class;
        ENABLED_DISABLED.detailClass = EnabledDisabled.class;
        ROLE_TYPE.detailClass = RoleType.class;
        ORGANIZATION_TYPE.detailClass = OrganizationType.class;
        BUDGET_STATUS.detailClass = BudgetStatus.class;
        BUDGET_TYPE.detailClass = BudgetType.class;
        EXPENSE_TYPE.detailClass = ExpenseType.class;
        EXPORT_STATUS.detailClass = ExportStatus.class;
        HOTEL_LEVEL.detailClass = HotelLevel.class;
        CUSTOMER_TYPE.detailClass = CustomerType.class;
        EXPORT_FILE_LINE_TYPE.detailClass = ExportFileLineType.class;
        RECHARGE_TYPE.detailClass = RechargeType.class;
        EXPORT_FILE_STATUS.detailClass = ExportFileStatus.class;
        GLOBAL_MAIL_REMINDER_TYPE.detailClass = GlobalMailReminderType.class;
        SITE_MAIL_REMINDER_TYPE.detailClass = SiteMailReminderType.class;
        HOTEL_PROMOTE_STATUS.detailClass = HotelPromoteStatus.class;
        SUPPLIER_PROMOTE_STATUS.detailClass = SupplierPromoteStatus.class;
        CONTRACT_STATUS.detailClass = ContractStatus.class;
        CAPEX_REQUEST_TYPE.detailClass = CapexRequestType.class;
        CAPEX_REQUEST_STATUS.detailClass = CapexRequestStatus.class;
        PURCHASE_REQUEST_STATUS.detailClass = PurchaseRequestStatus.class;
        PURCHASE_ORDER_STATUS.detailClass = PurchaseOrderStatus.class;
        SUPPLIER_CONFIRM_STATUS.detailClass = SupplierConfirmStatus.class;
        APPROVE_STATUS.detailClass = ApproveStatus.class;
        APPROVER_DELEGATE_TYPE.detailClass = ApproverDelegateType.class;
        TRAVEL_APPLICATION_STATUS.detailClass = TravelApplicationStatus.class;
        TRAVELLING_MODE.detailClass = TravellingMode.class;
        SINGLE_RETURN.detailClass = SingleReturn.class;
        FLIGHT_CLASS.detailClass = FlightClass.class;
        AIR_TICKET_STATUS.detailClass = AirTicketStatus.class;
        EXPENSE_STATUS.detailClass = ExpenseStatus.class;
        CONDITION_TYPE.detailClass = ConditionType.class;
        CONDITION_COMPARE_TYPE.detailClass = ConditionCompareType.class;
        RULE_TYPE.detailClass = RuleType.class;
        TRAVEL_APPLICATION_URGENT.detailClass = TravelApplicationUrgent.class;
        TRAVEL_APPLICATION_BOOK_STATUS.detailClass = TravelApplicationBookStatus.class;
        TRAVEL_TYPE.detailClass = TravelType.class; 
        CONSEQUENCE_TYPE.detailClass = ConsequenceType.class;
        EXPORT_FILE_TYPE.detailClass = ExportFileType.class;
    }

    private String defaultLabel;
    private Class detailClass;
    
    public MetadataType() {
    }

    private MetadataType(int metadataId, String defaultLabel) {
        super(null, metadataId);
        this.defaultLabel = defaultLabel;
    }
    
    public void setLabel(String label) {
        this.name = label;
    }
    
    public String getDefaultLabel() {
        return defaultLabel;
    }
    
    public Class getDetailClass() {
        return detailClass;
    }
}
