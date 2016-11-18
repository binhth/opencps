<%@page import="org.opencps.paymentmgt.search.PaymentConfigDisplayTerms"%>
<%@page import="com.liferay.portal.kernel.exception.SystemException"%>
<%@page import="org.opencps.paymentmgt.NoSuchPaymentConfigException"%>
<%@page import="org.opencps.paymentmgt.service.PaymentConfigLocalServiceUtil"%>
<%@page import="org.opencps.paymentmgt.model.PaymentConfig"%>
<%@page import="org.opencps.paymentmgt.NoSuchPaymentFileException"%>
<%@page import="org.opencps.paymentmgt.service.PaymentFileLocalServiceUtil"%>
<%@page import="org.opencps.paymentmgt.model.PaymentFile"%>
<%@page import="org.opencps.paymentmgt.search.PaymentFileDisplayTerms"%>
<%
/**
 * OpenCPS is the open source Core Public Services software
 * Copyright (C) 2016-present OpenCPS community
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
%>
<%@ include file="../init.jsp"%>
<%
	long paymentFileId = ParamUtil.getLong(request, PaymentFileDisplayTerms.PAYMENT_FILE_ID, 0L);
	PaymentFile paymentFile = null;
	try {
		paymentFile = PaymentFileLocalServiceUtil.getPaymentFile(paymentFileId);
	}
	catch (NoSuchPaymentFileException e) {
		
	}
	PaymentConfig paymentConfig = null;
	try {
		if (paymentFile != null)
			paymentConfig = PaymentConfigLocalServiceUtil.getPaymentConfigByGovAgency(scopeGroupId, paymentFile.getGovAgencyOrganizationId(),true);
	}
	catch (SystemException e) {
		
	}	
%>
<portlet:actionURL var="keypayTransactionUrl" name="keypayTransaction" />

<aui:form action="<%=keypayTransactionUrl.toString() %>" method="post" name="fm">
<aui:input name="<%= PaymentFileDisplayTerms.PAYMENT_FILE_ID %>" type="hidden" value="<%= paymentFileId %>"></aui:input>
<c:choose>
	<c:when test="<%= paymentFile != null %>">
		<aui:row>
			<aui:col width="50">
				<aui:select name="bankCode" style="width: 98%">
					<aui:option value="970423"><liferay-ui:message key="TPB"></liferay-ui:message></aui:option>
					<aui:option value="970416"><liferay-ui:message key="ACB"></liferay-ui:message></aui:option>
					<aui:option value="970431"><liferay-ui:message key="EIB"></liferay-ui:message></aui:option>
					<aui:option value="970438"><liferay-ui:message key="BVB"></liferay-ui:message></aui:option>
					<aui:option value="970437"><liferay-ui:message key="HDB"></liferay-ui:message></aui:option>
					<aui:option value="970434"><liferay-ui:message key="IVB"></liferay-ui:message></aui:option>
					<aui:option value="970426"><liferay-ui:message key="MSB"></liferay-ui:message></aui:option>
					<aui:option value="970419"><liferay-ui:message key="NVB"></liferay-ui:message></aui:option>
					<aui:option value="970417"><liferay-ui:message key="PNB"></liferay-ui:message></aui:option>
					<aui:option value="970443"><liferay-ui:message key="SHB"></liferay-ui:message></aui:option>
					<aui:option value="970468"><liferay-ui:message key="SeAb"></liferay-ui:message></aui:option>
					<aui:option value="970424"><liferay-ui:message key="SVB"></liferay-ui:message></aui:option>
					<aui:option value="970407"><liferay-ui:message key="TCB"></liferay-ui:message></aui:option>
					<aui:option value="970427"><liferay-ui:message key="VAB"></liferay-ui:message></aui:option>
					<aui:option value="970439"><liferay-ui:message key="VID"></liferay-ui:message></aui:option>
					<aui:option value="970432"><liferay-ui:message key="VPB"></liferay-ui:message></aui:option>
					<aui:option value="970409"><liferay-ui:message key="NASB"></liferay-ui:message></aui:option>
					<aui:option value="177777"><liferay-ui:message key="OCB"></liferay-ui:message></aui:option>
					<aui:option value="970406"><liferay-ui:message key="DAB"></liferay-ui:message></aui:option>
					<aui:option value="161087"><liferay-ui:message key="SaigonBank"></liferay-ui:message></aui:option>
					<aui:option value="970408"><liferay-ui:message key="GPBank"></liferay-ui:message></aui:option>
					<aui:option value="970420"><liferay-ui:message key="DaiA-Bank"></liferay-ui:message></aui:option>
					<aui:option value="970414"><liferay-ui:message key="OceanBank"></liferay-ui:message></aui:option>
					<aui:option value="970436"><liferay-ui:message key="VietcomBank"></liferay-ui:message></aui:option>
					<aui:option value="970441"><liferay-ui:message key="VIB"></liferay-ui:message></aui:option>
					<aui:option value="970422"><liferay-ui:message key="MB"></liferay-ui:message></aui:option>
					<aui:option value="970428"><liferay-ui:message key="NamA-Bank"></liferay-ui:message></aui:option>
					<aui:option value="970499"><liferay-ui:message key="Agribank"></liferay-ui:message></aui:option>
					<aui:option value="970489"><liferay-ui:message key="VietinBank"></liferay-ui:message></aui:option>
					<aui:option value="970403"><liferay-ui:message key="SacomBank"></liferay-ui:message></aui:option>
					<aui:option value="970459"><liferay-ui:message key="ABBank"></liferay-ui:message></aui:option>
					<aui:option value="970488"><liferay-ui:message key="BIDV"></liferay-ui:message></aui:option>
					<aui:option value="970430"><liferay-ui:message key="PGBank"></liferay-ui:message></aui:option>
					<aui:option value="666666"><liferay-ui:message key="Master-Card"></liferay-ui:message></aui:option>
					<aui:option value="333333"><liferay-ui:message key="Visa"></liferay-ui:message></aui:option>
				</aui:select>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col>
				<aui:button type="submit" name="submit" value="submit"/>					
			</aui:col>
		</aui:row>
	</c:when>
</c:choose>
</aui:form>