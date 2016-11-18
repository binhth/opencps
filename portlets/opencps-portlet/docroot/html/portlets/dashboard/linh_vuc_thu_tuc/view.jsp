<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="org.opencps.util.WebKeys"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="org.opencps.util.PortletPropsValues"%>
<%@page import="org.opencps.util.PortletUtil"%>
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

<%@page import="com.liferay.portal.kernel.dao.search.SearchEntry"%>
<%@page import="com.liferay.portal.kernel.log.Log"%>
<%@page import="com.liferay.portal.kernel.log.LogFactoryUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="org.opencps.datamgt.model.DictItem"%>
<%@page import="org.opencps.datamgt.service.DictItemLocalServiceUtil"%>
<%@page import="org.opencps.dossiermgt.bean.ServiceBean"%>
<%@page import="org.opencps.dossiermgt.model.ServiceConfig"%>
<%@page import="org.opencps.dossiermgt.search.ServiceSearch"%>
<%@page import="org.opencps.dossiermgt.search.ServiceSearchTerms"%>
<%@page import="org.opencps.dossiermgt.service.ServiceConfigLocalServiceUtil"%>
<%@page import="org.opencps.servicemgt.model.ServiceInfo"%>
<%@page import="org.opencps.servicemgt.service.ServiceInfoLocalServiceUtil"%>
<%@page import="org.opencps.servicemgt.util.ServiceUtil"%>
<%@page import="org.opencps.util.PortletConstants"%>

<%@ include file="../init.jsp"%>

<%
	
	String backURL = ParamUtil.getString(request, "backURL");
	
%>

<%
	
	List<DictItem> dictItems = PortletUtil.getDictItemInUseByCode(themeDisplay.getScopeGroupId(), 
		PortletPropsValues.DATAMGT_MASTERDATA_SERVICE_DOMAIN, 
		PortletConstants.TREE_VIEW_DEFAULT_ITEM_CODE);

	List<DictItem> dictItemsResult = new ArrayList<DictItem>();
	for(DictItem dictItem: dictItems){
		boolean isShow_cfg = GetterUtil.getBoolean(portletPreferences.getValue(dictItem.getItemCode()+"_isShow", "false"));
		int isShowOrder_cfg = GetterUtil.getInteger(portletPreferences.getValue(dictItem.getItemCode()+"_isShowOrder", "0"));
		if(isShow_cfg){
			dictItem.setCompanyId(isShowOrder_cfg);
			dictItemsResult.add(dictItem);
		}
	}
	
	Collections.sort(dictItemsResult, new Comparator<DictItem>() {

        public int compare(DictItem o1, DictItem o2) {
        	return String.valueOf(o1.getCompanyId()).compareTo(String.valueOf(o2.getCompanyId()));
        }
    });
%>

<aui:row>
	<aui:col width="100" >

		<ul class="sitemap-class opencps-horizontal">
		
			<%
				for(DictItem dictItem: dictItemsResult){
					int layout_cfg = GetterUtil.getInteger(portletPreferences.getValue(dictItem.getItemCode()+"_plid", ""));
			%>
			<li onclick="window.location.href='<%=PortalUtil.getLayoutFullURL(LayoutLocalServiceUtil.getLayout(layout_cfg), themeDisplay) %>'">
				
				<div class="img-<%=dictItem.getItemCode() %>"> 
					<div> 
						<a href="<%=PortalUtil.getLayoutFullURL(LayoutLocalServiceUtil.getLayout(layout_cfg), themeDisplay) %>"><%=dictItem.getItemName(locale) %></a> 
					</div>
				</div>
				
			</li>
			
			<%
				}
			%>
		
		</ul>
		
	</aui:col>
</aui:row>