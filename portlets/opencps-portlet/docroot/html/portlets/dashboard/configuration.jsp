
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="org.opencps.util.WebKeys"%>
<%@page import="org.opencps.dossiermgt.search.DossierFileDisplayTerms"%>
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

<%@ include file="init.jsp" %>

<%@page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.portal.model.Layout"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>

<liferay-ui:success key="potlet-config-saved" message="portlet-configuration-have-been-successfully-saved" />


<%
	boolean privateLayout = true;
	
	List<Layout> privLayouts = LayoutLocalServiceUtil.getLayouts(scopeGroupId, true);
	List<Layout> pubLayouts = LayoutLocalServiceUtil.getLayouts(scopeGroupId, false);
	
	List<Layout> allLayout = new ArrayList<Layout>();
	
	for (Layout privLayout : privLayouts) {
		allLayout.add(privLayout);
	}

	for (Layout pubLayout : pubLayouts) {
		allLayout.add(pubLayout);
	}
	
	int itemsToDisplay_cfg = GetterUtil.getInteger(portletPreferences.getValue("itemsToDisplay", "2"));
	
	String templatesToDisplay_cfg = GetterUtil.getString(portletPreferences.getValue("templatesToDisplay", "default"));
	
	int timeToReLoad_cfg = GetterUtil.getInteger(portletPreferences.getValue("timeToReLoad", "0"));
	
%>

<liferay-portlet:actionURL var="configurationActionURL" portletConfiguration="true"/>

<aui:form action="<%= configurationActionURL %>" method="post" name="configurationForm">
	
	<aui:select name="dashBoardCFGType" id="dashBoardCFGType" onChange='<%="javavscript:" + renderResponse.getNamespace() + "DashBoardPickType(this)" %>'>
		<aui:option value=""></aui:option>
		<aui:option value="linh_vuc_thu_tuc">linh_vuc_thu_tuc</aui:option>
		<aui:option value="home_linh_vuc">home_linh_vuc</aui:option>
	</aui:select>
	
	<div id="<portlet:namespace />is-hidden-cfg">
	
	</div>
	
<aui:button type="submit" name="Save" value="save"></aui:button>


</aui:form>

<aui:script use = "aui-base,liferay-portlet-url,aui-node">
	
var A = AUI();
		
		Liferay.provide(window, '<portlet:namespace />DashBoardPickType', function(e) {
			var portletURL = Liferay.PortletURL.createURL('<%= PortletURLFactoryUtil.create(request, WebKeys.DASHBOARD_PORTLET, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE) %>');
			portletURL.setParameter("mvcPath", "/html/portlets/dashboard/"+$(e).val()+"/view_cfg.jsp");
			portletURL.setWindowState('<%=LiferayWindowState.EXCLUSIVE.toString() %>'); 
			portletURL.setPortletMode('view');
			
			$("#<portlet:namespace />is-hidden-cfg").load( portletURL.toString(), function () {
				
				selector: '#<portlet:namespace />is-hidden-cfg > .lfr-search-container'
				
			});
		});
		
	
</aui:script>
