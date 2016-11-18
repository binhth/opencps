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

package org.opencps.notificationmgt.engine;

import javax.portlet.WindowState;

import org.opencps.dossiermgt.search.DossierDisplayTerms;
import org.opencps.notificationmgt.utils.NotificationEventKeys;
import org.opencps.paymentmgt.search.PaymentFileDisplayTerms;
import org.opencps.processmgt.search.ProcessOrderDisplayTerms;
import org.opencps.util.WebKeys;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

/**
 * @author nhanhoang
 */
public class UserNotificationHandler extends BaseUserNotificationHandler {

	private static Log _log = LogFactoryUtil.getLog(UserNotificationHandler.class);

	public static final String PORTLET_ID = "2_WAR_notificationsportlet";

	public UserNotificationHandler() {

		setPortletId(UserNotificationHandler.PORTLET_ID);

	}

	@Override
	protected String getBody(
		UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject =
			JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		String title = jsonObject.getString("title");
		String bodyText = jsonObject.getString("notificationText");

		String body = StringUtil.replace(getBodyTemplate(), new String[] {
			"[$TITLE$]", "[$BODY_TEXT$]"
		}, new String[] {
			title, bodyText
		});

		return body;
	}

	protected String getBodyTemplate()
		throws Exception {

		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\">[$TITLE$]</div> ");
		sb.append("<div class=\"body\">[$BODY_TEXT$]</div>");
		return sb.toString();
	}

	@Override
	protected String getLink(
		UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
		throws Exception {

		String group = StringPool.BLANK;
		String processOrderId = StringPool.BLANK;
		String dossierId = StringPool.BLANK;
		String paymentFileId = StringPool.BLANK;

		LiferayPortletResponse liferayPortletResponse = serviceContext.getLiferayPortletResponse();

		JSONObject jsonObject =
			JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		group = jsonObject.getString("friendlyUrl");

		dossierId = jsonObject.getString("dossierId");
		paymentFileId = jsonObject.getString("paymentFileId");
		processOrderId = jsonObject.getString("processOrderId");

		long plId =
			jsonObject.getString("plId").trim().length() > 0
				? Long.parseLong(jsonObject.getString("plId")) : 0;
		long groupId =
			jsonObject.getString("groupId").trim().length() > 0
				? Long.parseLong(jsonObject.getString("plId")) : 0;;

		LiferayPortletURL viewURL = null;
		Layout layOut = null;

		if (group.equals(NotificationEventKeys.GROUP1)) {
			
			if (plId <= 0) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}
			}

			viewURL = liferayPortletResponse.createRenderURL(WebKeys.PROCESS_ORDER_PORTLET);

			viewURL.setParameter(
				"mvcPath", "/html/portlets/processmgt/processorder/process_order_detail.jsp");
			viewURL.setParameter(ProcessOrderDisplayTerms.PROCESS_ORDER_ID, processOrderId);
			viewURL.setParameter(WebKeys.BACK_URL, "/group/guest" + group);
			viewURL.setParameter("isEditDossier", String.valueOf(true));
			viewURL.setPlid(plId);
			viewURL.setWindowState(WindowState.NORMAL);

		}
		else if (group.equals(NotificationEventKeys.GROUP2)) {

			if (plId <= 0) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}

			}

			viewURL = liferayPortletResponse.createRenderURL(WebKeys.DOSSIER_MGT_PORTLET);

			viewURL.setParameter(
				"mvcPath", "/html/portlets/dossiermgt/frontoffice/edit_dossier.jsp");
			viewURL.setParameter(DossierDisplayTerms.DOSSIER_ID, dossierId);
			viewURL.setParameter(Constants.CMD, Constants.VIEW);
			viewURL.setParameter(WebKeys.BACK_URL, "/group/guest/");
			viewURL.setParameter(WebKeys.REDIRECT_URL, "/group/guest/");
			viewURL.setParameter("isEditDossier", String.valueOf(false));
			viewURL.setPlid(plId);
			viewURL.setWindowState(WindowState.NORMAL);

		}
		else if (group.equals(NotificationEventKeys.GROUP3)) {

			if (plId <= 0) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}

			}

			viewURL = liferayPortletResponse.createRenderURL(WebKeys.PAYMENT_MGT_PORTLET);

			viewURL.setParameter(
				"mvcPath", "/html/portlets/paymentmgt/frontoffice/frontofficeconfirmbank.jsp");
			viewURL.setParameter(PaymentFileDisplayTerms.PAYMENT_FILE_ID, paymentFileId);
			viewURL.setParameter(Constants.CMD, Constants.VIEW);
			viewURL.setParameter(WebKeys.BACK_URL, "/group/guest" + group);
			viewURL.setParameter(WebKeys.REDIRECT_URL, "/group/guest" + group);
			viewURL.setPlid(plId);
			viewURL.setWindowState(WindowState.NORMAL);

		}
		else if (group.equals(NotificationEventKeys.GROUP4)) {

			if (plId <= 0) {
				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}
			}

			viewURL = liferayPortletResponse.createRenderURL(WebKeys.PAYMENT_MANAGER_PORTLET);

			viewURL.setParameter(
				"mvcPath", "/html/portlets/paymentmgt/backoffice/backofficepaymentdetail.jsp");
			viewURL.setParameter(PaymentFileDisplayTerms.PAYMENT_FILE_ID, paymentFileId);
			viewURL.setParameter(Constants.CMD, Constants.VIEW);
			viewURL.setParameter(WebKeys.REDIRECT_URL, "/group/guest");
			viewURL.setPlid(plId);
			viewURL.setWindowState(WindowState.NORMAL);

		}

		return Validator.isNotNull(viewURL) ? viewURL.toString() : "";
	}
}
