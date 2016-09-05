
<%@page import="org.opencps.processmgt.service.ProcessWorkflowLocalServiceUtil"%>
<%@page import="org.opencps.processmgt.model.ProcessWorkflow"%>
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 */
%>

<%@page import="org.opencps.processmgt.util.ProcessUtils"%>
<%@ include file="../init.jsp" %>

<%
	long processStepId = ParamUtil.getLong(request, "processStepId");
	long workflowId = ParamUtil.getLong(request, "workflowId"); 
	
	ProcessWorkflow workflow = null;
	try {
		workflow = ProcessWorkflowLocalServiceUtil.getProcessWorkflow(workflowId);
	} catch(Exception e) {
		//nothing to do
	}
	
%>
<aui:model-context bean="<%= workflow %>" model="<%= ProcessWorkflow.class %>" />
<aui:select name="actionUserId" showEmptyOption="true" label="">
	<%
		List<User> assignUsers = ProcessUtils.getAssignUsers(processStepId, 0);
		for (User userSel : assignUsers) {
		%>	
			<aui:option value="<%= userSel.getUserId() %>"><%= userSel.getFullName() %></aui:option>
		<%
		}
	%>
</aui:select>

