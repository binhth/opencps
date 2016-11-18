<%@page
	import="org.opencps.holidayconfig.permission.HolidayConfigPermission"%>
<%@page
	import="org.opencps.holidayconfig.search.HolidayConfigDisplayTerms"%>
<%@page import="org.opencps.holidayconfig.model.HolidayConfig"%>
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
<%@page import="org.opencps.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer"%>
<%@page import="org.opencps.util.ActionKeys"%>
<%@ include file="../init.jsp"%>


<%
	ResultRow row = (ResultRow) request
			.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	HolidayConfig holidayConfig = (HolidayConfig) row.getObject();
	
%>

<c:if
	test="<%=HolidayConfigPermission.contains(permissionChecker,
						scopeGroupId, ActionKeys.ADD_HOLIDAY)%>">
	<portlet:renderURL var="editHolidayConfigURL">
		<portlet:param name="mvcPath"
			value="/html/portlets/holidayconfig/admin/holidayconfig_edit.jsp" />
		<portlet:param name="<%=HolidayConfigDisplayTerms.HOLIDAY_ID%>"
			value="<%=String.valueOf(holidayConfig.getHolidayId())%>" />
		<portlet:param name="backURL" value="<%=currentURL%>" />
	</portlet:renderURL>
	<liferay-ui:icon image="edit"
		cssClass="search-container-action fa edit" message="edit"
		url="<%=editHolidayConfigURL.toString()%>" />
</c:if>

<c:if test="<%=holidayConfig.getStatus() == WebKeys.ACTIVE%>">
	<portlet:actionURL var="deactiveHolidayConfigURL" name="updateHoliday">
		<portlet:param name="<%=HolidayConfigDisplayTerms.HOLIDAY_ID%>"
			value="<%=String.valueOf(holidayConfig.getHolidayId())%>" />
		<portlet:param name="redirectURL" value="<%=currentURL%>" />
		<portlet:param name="<%=HolidayConfigDisplayTerms.HOLIDAY_STATUS%>"
			value="<%=String.valueOf(WebKeys.DISABLE)%>" />
	</portlet:actionURL>

	<liferay-ui:icon
		cssClass="search-container-action fa fa-eye-slash ocps-btn ocps-red"
		image="deactive"
	 	url="<%=deactiveHolidayConfigURL.toString()%>"></liferay-ui:icon>

</c:if>

<c:if test="<%=holidayConfig.getStatus() == WebKeys.DISABLE%>">
	<portlet:actionURL var="activeHolidayConfigURL" name="updateHoliday">
		<portlet:param name="<%=HolidayConfigDisplayTerms.HOLIDAY_ID%>"
			value="<%=String.valueOf(holidayConfig.getHolidayId())%>" />
		<portlet:param name="redirectURL" value="<%=currentURL%>" />
		<portlet:param name="<%=HolidayConfigDisplayTerms.HOLIDAY_STATUS%>"
			value="<%=String.valueOf(WebKeys.ACTIVE)%>" />
	</portlet:actionURL>
	<liferay-ui:icon
		image="active"
		cssClass="search-container-action fa fa-eye ocps-btn ocps-green"
		url="<%=activeHolidayConfigURL.toString()%>" ></liferay-ui:icon>
</c:if>

