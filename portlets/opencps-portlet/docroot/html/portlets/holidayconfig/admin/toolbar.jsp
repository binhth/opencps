<%@page
	import="org.opencps.holidayconfig.search.HolidayConfigDisplayTerms"%>
<%@page
	import="org.opencps.holidayconfig.permission.HolidayConfigPermission"%>
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

<%@page import="com.liferay.portal.kernel.log.LogFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.log.Log"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="org.opencps.util.ActionKeys"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="org.opencps.util.PortletUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@page
	import="org.opencps.processmgt.service.ProcessOrderLocalServiceUtil"%>

<%@ include file="../init.jsp"%>


<aui:nav-bar cssClass="opencps-toolbar custom-toolbar">
	<aui:nav id="toolbarContainer"
		cssClass="nav-button-container  nav-display-style-buttons pull-left">
		<c:if
			test="<%=HolidayConfigPermission.contains(
								permissionChecker, scopeGroupId,
								ActionKeys.ADD_HOLIDAY)%>">

			<portlet:renderURL var="addHolidayConfigURL">
				<portlet:param name="mvcPath"
					value="/html/portlets/holidayconfig/admin/holidayconfig_edit.jsp" />
				<portlet:param name="backURL" value="<%=currentURL.toString()%>" />
			</portlet:renderURL>

			<aui:button icon="icon-plus" href="<%=addHolidayConfigURL%>"
				cssClass="action-button"
				value='<%=String.valueOf(LanguageUtil.get(pageContext,
								"add-holiday"))%>' />
		</c:if>
	</aui:nav>
</aui:nav-bar>



<%!private Log _log = LogFactoryUtil
			.getLog("html.portlets.holidayconfig.admin.toolbar.jsp");%>