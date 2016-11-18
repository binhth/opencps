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

package org.opencps.notificationmgt.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.opencps.util.SendMailUtils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
/**
 * @author nhanhoang
 */
public class NotificationPortlet extends MVCPortlet {

	private static Log _log = LogFactoryUtil.getLog(NotificationPortlet.class);

	public void sendUserNotification(ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException, PortalException, SystemException {
		

		//SendMailUtils.sendEmail("no-reply@fds.vn", "hltn.works@gmail.com", "skynetx001@gmail.com,nhanhlt@fds.vn", "subject", "body", true);
	}

}
