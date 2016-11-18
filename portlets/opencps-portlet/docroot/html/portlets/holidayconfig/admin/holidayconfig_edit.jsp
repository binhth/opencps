<%@page import="net.sf.jasperreports.util.NoWriteFieldHandler"%>
<%@page import="org.opencps.util.PortletUtil"%>
<%@page import="java.util.Date"%>
<%@page import="org.opencps.holidayconfig.model.impl.HolidayConfigImpl"%>
<%@page
	import="org.opencps.holidayconfig.service.HolidayConfigLocalServiceUtil"%>
<%@page import="org.opencps.holidayconfig.model.HolidayConfig"%>
<%@page
	import="org.opencps.holidayconfig.search.HolidayConfigDisplayTerms"%>
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
<%@page import="org.opencps.util.MessageKeys"%>
<%@page import="org.opencps.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.log.LogFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.log.Log"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@ include file="../init.jsp"%>

<portlet:actionURL var="updateHolidayConfigURL" name="updateHoliday" />

<%
	long holidayId = ParamUtil.getLong(request,
			HolidayConfigDisplayTerms.HOLIDAY_ID, 0);
	String backURL = ParamUtil.getString(request, WebKeys.BACK_URL);

	HolidayConfig holidayConfig = new HolidayConfigImpl();

	if (holidayId > 0) {

		try {
			holidayConfig = HolidayConfigLocalServiceUtil
					.getHolidayConfig(holidayId);
		} catch (Exception e) {
			_log.error(e);
		}
	}

	Date holiday = Validator.isNotNull(holidayConfig.getHoliday()) ? holidayConfig
			.getHoliday() : new Date();

	PortletUtil.SplitDate spd = null;

	if (Validator.isNotNull(holiday)) {
		spd = new PortletUtil.SplitDate(holiday);
	}

	int day = 0;
	int month = 0;
	int year = 0;

	if (Validator.isNotNull(spd)) {
		day = spd.getDayOfMoth();
		month = spd.getMonth();
		year = spd.getYear();
	}
%>

<liferay-ui:header backURL="<%=backURL%>"
	title='<%=(holidayConfig == null) ? "add-holiday"
					: "update-holiday"%>' />


<div class=" opencps-bound-wrapper pd20 default-box-shadow"">
	<div class="edit-form">

		<liferay-ui:error key="<%= MessageKeys.HOLIDAYCONFIG_SYSTEM_EXCEPTION_OCCURRED%>" 
		message="<%=MessageKeys.HOLIDAYCONFIG_SYSTEM_EXCEPTION_OCCURRED %>" />
		
		<liferay-ui:success key="<%= MessageKeys.HOLIDAYCONFIG_ADD_SUCESS%>" 
		message="<%= MessageKeys.HOLIDAYCONFIG_ADD_SUCESS%>"/>
		
		<liferay-ui:success key="<%= MessageKeys.HOLIDAYCONFIG_UPDATE_SUCESS%>" 
		message="<%= MessageKeys.HOLIDAYCONFIG_UPDATE_SUCESS%>"/>

		<aui:form action="<%=updateHolidayConfigURL.toString()%>"
			method="post" name="fm">

			<aui:model-context bean="<%=holidayConfig%>"
				model="<%=HolidayConfig.class%>" />
			<aui:input name="<%=HolidayConfigDisplayTerms.HOLIDAY_ID%>"
				type="hidden" />
			<aui:input name="<%=WebKeys.REDIRECT_URL%>" type="hidden"
				value="<%=backURL%>" />
			<aui:input name="<%=WebKeys.RETURN_URL%>" type="hidden"
				value="<%=currentURL%>" />
			<aui:fieldset>

				<label class="control-label custom-lebel bold"
					for='<portlet:namespace/><%=HolidayConfigDisplayTerms.HOLIDAY_DATE%>'>
					<liferay-ui:message key="holiday-date" />
				</label>

				<liferay-ui:input-date dayValue="<%=day%>" monthValue="<%=month%>"
					yearValue="<%=year%>"
					name="<%=HolidayConfigDisplayTerms.HOLIDAY_DATE%>" formName="fm"
					autoFocus="<%=true%>" cssClass="input100">
				</liferay-ui:input-date>

				<aui:input name="<%=HolidayConfigDisplayTerms.DESCRIPTION%>"
					type="textarea" label="description"
					value='<%=HtmlUtil.escape(holidayConfig
								.getDescription().length() <= 0 ? "test"
								: holidayConfig.getDescription())%>'>
					<aui:validator name="maxLength">255</aui:validator>
				</aui:input>

				<aui:select name="<%=HolidayConfigDisplayTerms.HOLIDAY_STATUS%>"
					label="status">
					<aui:option value="<%=WebKeys.ACTIVE%>" label="active"
						selected="<%=holidayConfig.getStatus() == WebKeys.ACTIVE%>" />
					<aui:option value="<%=WebKeys.DISABLE%>" label="disable"
						selected="<%=holidayConfig.getStatus() == WebKeys.DISABLE%>" />
				</aui:select>

			</aui:fieldset>

			<aui:fieldset>
				<aui:button type="submit" name="submit" value="submit" />
				<aui:button type="reset" value="clear" />
			</aui:fieldset>
		</aui:form>
	</div>
</div>

<%!private Log _log = LogFactoryUtil
			.getLog("html.portlets.holidayconfig.admin.holidayconfig_edit.jsp");%>
