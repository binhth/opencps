
<%@page import="com.liferay.portal.model.Layout"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.persistence.LayoutFriendlyURLUtil"%>
<%@page import="com.liferay.portal.kernel.messaging.MessageBusUtil"%>
<%@page import="com.liferay.portal.kernel.messaging.Message"%>
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

<%@ include file="init.jsp"%>

<h3>Hello World</h3>

<%
// 	Message message = new Message();
// 	message.put("curTime", "test");	
// 	MessageBusUtil.sendMessage(DestinationKeys., message);

String friendlyUrl = "/xu-ly-ho-so";
		
long plId = 0;
layout = LayoutLocalServiceUtil.getFriendlyURLLayout(20182, true, friendlyUrl);
//System.out.println("layout:"+layout);

// plId = LayoutLocalServiceUtil.getFriendlyURLLayout(0, true, friendlyUrl).getPlid();
//System.out.println("plId:"+plId);
%>


<portlet:actionURL var="sendUserNotification" windowState="normal"
	name="sendUserNotification">
</portlet:actionURL>
<form action="<%=sendUserNotification%>" name="userNotificationForm"
	method="POST">
	<h4>Send User Notification</h4>
	<b>Title</b><br />
	<aui:input name="title" ></aui:input>
	<b>Notification Text</b><br />
	<textarea rows="4" cols="100"
		name="<portlet:namespace/>notifciationText" style="width: 300px">
</textarea>
	<br /> <input type="submit" name="sendNotification"
		id="sendNotification" value="Notify" />
</form>