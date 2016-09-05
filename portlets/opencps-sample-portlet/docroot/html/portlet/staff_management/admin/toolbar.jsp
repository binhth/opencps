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
<%@page import="org.opencps.sample.staff.permissions.StaffPermission"%>
<%@page import="org.opencps.sample.utils.PortletConstants"%>
<%@page import="org.opencps.sample.department.permissions.DepartmentPermission"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="../../init.jsp" %>

<aui:nav-bar cssClass="custom-toolbar">
	<aui:nav id="toolbarContainer" cssClass="nav-display-style-buttons pull-left" >
		<c:if test="<%=DepartmentPermission.contains(permissionChecker, scopeGroupId, DepartmentPermission.ADD)%>">
			<portlet:renderURL var="updateDepartment" windowState="<%=LiferayWindowState.NORMAL.toString() %>">
				<portlet:param name="mvcPath" value="/html/portlet/staff_management/admin/edit_department.jsp"/>
				<portlet:param name="backURL" value="<%=currentURL %>"/>
				<portlet:param name="departmentId" value="0"/>
				<portlet:param name="<%=PortletConstants.CMD %>" value="<%=PortletConstants.ADD %>"/>
			</portlet:renderURL>
			<aui:nav-item 
				id="addButtonContainer" 
				label="add-department" 
				iconCssClass="icon-plus"  
				href='<%=updateDepartment.toString()%>'
			/>
		</c:if>
		<c:if test="<%=StaffPermission.contains(permissionChecker, scopeGroupId, StaffPermission.ADD)%>">
			<portlet:renderURL var="updateStaff" windowState="<%=LiferayWindowState.NORMAL.toString() %>">
				<portlet:param name="mvcPath" value="/html/portlet/staff_management/admin/edit_department.jsp"/>
				<portlet:param name="backURL" value="<%=currentURL %>"/>
				<portlet:param name="staffId" value="0"/>
				<portlet:param name="<%=PortletConstants.CMD %>" value="<%=PortletConstants.ADD %>"/>
			</portlet:renderURL>
			<aui:nav-item 
				id="addButtonContainer" 
				label="add-staff" 
				iconCssClass="icon-plus"  
				href='<%=updateStaff.toString()%>'
			/>			
		</c:if>	
	</aui:nav>
</aui:nav-bar>
