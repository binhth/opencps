<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ include file="/html/taglib/init.jsp" %>

<%
boolean autoFocus = GetterUtil.getBoolean(request.getAttribute("liferay-ui:input-search:autoFocus"));
String buttonLabel = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:buttonLabel"));
String cssClass = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:cssClass"));
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:id"));
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:name"));
String placeholder = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:placeholder"));
boolean showButton = GetterUtil.getBoolean(request.getAttribute("liferay-ui:input-search:showButton"));
String title = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:title"));
boolean useNamespace = GetterUtil.getBoolean(request.getAttribute("liferay-ui:input-search:useNamespace"), true);

if (!useNamespace) {
	namespace = StringPool.BLANK;
}

String value = ParamUtil.getString(request, name);
String keySelect = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:keySelect"));
String urlSelect = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:urlSelect"));
String currentTab = GetterUtil.getString((String)request.getAttribute("liferay-ui:input-search:currentTab"));
String[] listKey = null;
String[] listUrl = null;
if(keySelect.contains(",")){
	listKey = keySelect.split(",");
	listUrl = urlSelect.split(",");
	System.out.println(listKey+"/"+listUrl);
}
%>
<div class="<%= cssClass %>">
	<input class="search-query span9" id="<%= namespace + id %>" name="<%= namespace + name %>" placeholder="<%= placeholder %>" title="<%= title %>" type="text" value="<%= HtmlUtil.escapeAttribute(value) %>" />

	<c:if test="<%= Validator.isNotNull(currentTab) %>">
		<div class="btn-group">
	        <button class="btn dropdown-toggle" data-toggle="dropdown">
	             <%= LanguageUtil.get(pageContext, currentTab) %>
	        	<span class="caret"></span>
	        </button>
	        <ul class="dropdown-menu">
	        	<%
	        		int iKey = 0;
	        		for(String strKey: listKey){
	        	%>
	             <li><a href="<%=listUrl[iKey] %>"><%= LanguageUtil.get(pageContext, strKey) %></a></li>
	             <%iKey++;} %>
	        </ul>
	   </div>
	   	<button class="btn" type="submit" >
			<%= buttonLabel %>
		</button>
	</c:if>
</div>

<c:if test="<%= autoFocus %>">
	<aui:script>
		Liferay.Util.focusFormField('#<%= namespace %><%= id %>');
	</aui:script>
</c:if>