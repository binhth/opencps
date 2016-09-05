
<%@page import="org.opencps.dossiermgt.bean.ProcessOrderBean"%>
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
<%@page import="org.opencps.processmgt.search.ProcessOrderDisplayTerms"%>
<%@page import="org.opencps.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ include file="../init.jsp"%>
<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	ProcessOrderBean processOrder = (ProcessOrderBean) row.getObject();
	
	PortletURL processURL = renderResponse.createRenderURL();
	processURL.setParameter("mvcPath", templatePath + "process_order_detail.jsp");
	processURL.setParameter(ProcessOrderDisplayTerms.PROCESS_ORDER_ID, String.valueOf(processOrder.getProcessOrderId()));
	processURL.setParameter("backURL", currentURL);
	processURL.setParameter("isEditDossier", String.valueOf(false));
%>

<liferay-ui:icon 
	cssClass="search-container-action fa view" 
	image="view" 
	message="view" 
	url="<%=processURL.toString() %>" 
/>