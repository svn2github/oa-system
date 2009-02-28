<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<script language="javascript" src="includes/tree/tree.js"></script>
<script language="javascript" src="includes/tree/tree_tpl.js"></script>
<script language="javascript">
/*
var TREE_GLOBAL_ADMIN = [
	['Global Administrator Menu', 0,
		['System Control', 0,
			['System Log', 'listSystemLog.do'],	
			['Web Monitor', 'listOnlineUser.do'],	
		],
		['System Security', 0,
			['Security Permission', 'listFunction.do'],
			['Menu', 'listMenu.do'],
			['Role', 'listRole.do'],
		],
		['System Parameter', 0,			
			['Purchasing Category Maintenance', 'listPurchaseCategory.do'],
			['Currency Maintenance', 'listCurrency.do'],
			['User Security Policy Setup', 'securityPolicy/securityPolicy.htm'],
			['Metadata Maintenance', 'listMetadata.do'],
			['Global Parameter Setup', 'editGlobalParameter.do'],,
		],
		['Supplier Maintenance', 0,
			['Supplier Maintenance', 'listSupplier.do'],
			['Supplier Confirmation', 'listConfirmSupplier.do'],
		],
		['City & Hotel Maintenance', 0,
			['City Maintenance', 'listCountryProvinceCity.do'],
			['Hotel Maintenance', 'listHotel.do'],		
		],
		['User Maintenance', 'listGlobalUser.do'],
		['Site Maintenance', 'listSite.do'],
		['Approver Delegate', 'listApprover.do'],
	]		
];	
var TREE_SITE_ADMIN = [
	['Site Administartor Menu', 0,
		['System Control', 0,
			['System Log', 'listSystemLog_site.do'],	
			['Web Monitor', 'listOnlineUser_site.do'],
			['Alert Message', 0,
				['Yearly Budget Filter', 'listYearlyBudgetFilterRule.do'],
				['Capex Filter', 'listCapexFilterRule.do'],
				['PR Filter', 'listPurchaseRequestFilterRule.do'],
				['PO Filter', 'listPurchaseOrderFilterRule.do'],
				['Expense Filter', 'listExpenseFilterRule.do'],
				['TA Filter', 'listTravelApplicationFilterRule.do'],
			]
		],
		['System Parameter', 0,			
			['Purchasing Category Maintenance', 'listPurchaseCategory_site.do'],
			['Expense Category Maintenance', 'listExpenseCategory.do'],
			['Exchange Rate Maintenance', 'listExchangeRate.do'],
			['Site Parameter Setup', 'editSiteParameter.do'],
		],
		['Route Defination', 0,			
			['Purchase Route', 0,
				['Capex Approval Rules', 'listCapexApprovalRule.do'],
				['Capex Approval Flow', 'listCapexApprovalFlow.do'],
				['PR Approval Rules', 'listPRApprovalRule.do'],
				['PR Approval Flow', 'listPRApprovalFlow.do'],
				['Purchasing Rules', 'listPurchasingRule.do'],
				['Purchasing Flow', 'listPurchasingFlow.do'],
				['Purchasing Price Control Rules', 'listPurchasingPriceControlRule.do'],
				['Purchasing Price Control Flow', 'listPurchasingPriceControlFlow.do'],
				['PO Approval Rules', 'listPOApprovalRule.do'],
				['PO Approval Flow', 'listPOApprovalFlow.do'],
			],
			['Expense Route', 0,
				['Expense Approval Rules', 'listExpenseApprovalRule.do'],	
				['Expense Approval Flow', 'listExpenseApprovalFlow.do'],	
				['Expense Claim Control Rules', 'listExpenseClaimRule.do'],	
				['Expense Claim Control Flow', 'listExpenseClaimFlow.do'],	
			],
			['Traveling Route', 0,
				['Traveling Approval Rules', 'listTravelApprovalRule.do'],	
				['Traveling Approval Flow', 'listTravelApprovalFlow.do'],					
			],
		],
		['Supplier Maintenance', 0,
			['Supplier Maintenance', 'listSupplier_site.do'],
			['Supplier Confirmation', 'listConfirmSupplier_site.do'],
		],
		['City & Hotel Maintenance', 0,
			['City Maintenance', 'listCountryProvinceCity_site.do'],
			['Hotel Maintenance', 'listHotel_site.do'],		
		],
		['User Maintenance', 'listUser.do'],
		['Department Maintenance', 'listDepartment.do'],
		['Traveling Group Maintenance', 'listTravelGroup.do'],
	]				
];


var TREE_ENDUSER = [
	['End User Menu', 0,
		['Requestor', 0,
			['My Capex Request', 'listCapexRequest.do'],
			['My Request PR', 'listPurchaseRequest_self.do'],
			['My Delegate PR', 'listPurchaseRequest_other.do'],			
			['My PO Receipt', 'listPurchaseOrderItem_receipt.do'],
			['My Expense Request', 'listExpense_self.do'],
			['My Delegate Expense Request', 'listExpense_other.do'],
			['My Request TA', 'listTravelApplication_self.do'],
			['My Delegate TA', 'listTravelApplication_other.do'],
			
		],
		['Approver', 0,
			['Capex Approval', 'listCapexRequestApproveRequest.do'],
			['Purchase Request Approval', 'listPurchaseRequestApproveRequest.do'],
			['Purchase Order Approval', 'listPurchaseOrderApproveRequest.do'],
			['Expense Approval', 'listExpenseApproveRequest.do'],
			['Traveling Application Approval', 'listTravelApplicationApproveRequest.do'],
			['My Approval Delegate', 'listApproverDelegate_self.do'],
		],
		['Purchaser', 0,
			['My Pending PR', 'listPurchaseRequest_purchase.do'],
			['My Pending TA', 'listTravelApplicationPurchase.do'],
			['My Purchase Order', 'listPurchaseOrder.do'],			
			['My Received Air Ticket', 'listAirTicket.do'],			
		],
		['Finance', 0,
			['Yearly Budget', 'listYearlyBudget.do'],
			['Freeze & Unfreeze Yearly Budget', 'listYearlyBudget_freeze.do'],
			['PO Final Confirm', 'listPurchaseOrder_confirm.do'],
			['PO Cancel', 'listPurchaseOrder_cancel.do'],
			['Expense Final Confirm', 'listExpense_finalConfirm.do'],
		],
		['Supervisor', 0,
			['Approver Delegate', 'listApprover_department.do'],
			['PO Assign', 'listPurchaseOrder_assign.do'],
			['PR Assign', 'listPurchaseRequest_assign.do'],
			['TA Assign', 'listTravelApplication_assign.do'],
		],
		['Reporting', 0,
			['Purchase Request', 'reportPurchaseRequest.do'],
			['Expense', 'reportExpense.do'],
			['Travelling Application', 'reportTravelApplication.do'],
		],
	]
];
*/
</script>

<table cellpadding="5" cellspacing="0" cellpadding="10" border="0" width="100%" height="100%">
<tr>
	<td valign="top">
	<script language="javascript">
<logic:iterate id="m" name="X_MENULIST">
		new tree([${m}], TREE_TPL);
</logic:iterate>
	</script>

	<script language="javascript">
		//new tree(TREE_GLOBAL_ADMIN, TREE_TPL);
		//new tree(TREE_SITE_ADMIN, TREE_TPL);
		//new tree(TREE_ENDUSER, TREE_TPL);
	</script>
	</td>
</tr>
</table>