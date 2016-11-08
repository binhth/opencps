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

package org.opencps.notificationmgt.utils;

import java.util.Date;
import java.util.Locale;

import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.model.impl.DossierImpl;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.notificationmgt.engine.UserNotificationHandler;
import org.opencps.notificationmgt.message.SendNotificationMessage;
import org.opencps.processmgt.model.ProcessOrder;
import org.opencps.processmgt.service.ProcessOrderLocalServiceUtil;
import org.opencps.util.PortletPropsValues;
import org.opencps.util.SendMailUtils;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;

/**
 * @author nhanhoang
 */

public class NotificationUtils {

	private static Log _log = LogFactoryUtil.getLog(NotificationUtils.class);

	public static void addUserNotificationEvent(
		SendNotificationMessage message, JSONObject payloadJSON, long userIdDelivery) {

		try {

			ServiceContext serviceContext = new ServiceContext();

			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
				userIdDelivery, UserNotificationHandler.PORTLET_ID, (new Date()).getTime(), 0,
				payloadJSON.toString(), false, serviceContext);

		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public static JSONObject createNotification(
		SendNotificationMessage message, String event, String group, long userIdDelivery,
		boolean privatePage, long groupId) {

		JSONObject payloadJSONObject = JSONFactoryUtil.createJSONObject();
		Locale locale = new Locale("vi", "VN");

		try {

			ProcessOrder processOrder =
				ProcessOrderLocalServiceUtil.getProcessOrder(message.getProcessOrderId());

			String title = StringPool.BLANK;
			title = LanguageUtil.get(locale, event) + "[" + processOrder.getDossierId() + "]";

			long plId = 0;

			Layout layOut = null;

			if (group.equals(NotificationEventKeys.GROUP1)) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}

				payloadJSONObject.put("processOrderId", message.getProcessOrderId());
			}
			else if (group.equals(NotificationEventKeys.GROUP2)) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, false, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}

				payloadJSONObject.put("dossierId", message.getDossierId());
			}
			else if (group.equals(NotificationEventKeys.GROUP3)) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}

				payloadJSONObject.put("paymentFileId", message.getPaymentFileId());
			}
			else if (group.equals(NotificationEventKeys.GROUP4)) {

				layOut = LayoutLocalServiceUtil.getFriendlyURLLayout(groupId, true, group);

				if (Validator.isNotNull(layOut)) {
					plId = layOut.getPlid();
				}

				payloadJSONObject.put("paymentFileId", message.getPaymentFileId());
			}
			payloadJSONObject.put("userIdDelivery", userIdDelivery);
			payloadJSONObject.put("title", title);
			payloadJSONObject.put("notificationText", message.getNotificationContent());
			payloadJSONObject.put("plId", plId);
			payloadJSONObject.put("friendlyUrl", group);
			payloadJSONObject.put("groupId", groupId);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return payloadJSONObject;
	}

	public static void sendEmailNotification(
		SendNotificationMessage message, String email, long dossierId) {

		String from = StringPool.BLANK;
		String to = StringPool.BLANK;
		String subject = StringPool.BLANK;
		String body = StringPool.BLANK;
		boolean htmlFormat = true;

		Locale locale = new Locale("vi", "VN");

		try {

			Dossier dossier = new DossierImpl();

			if (dossierId > 0) {
				dossier = DossierLocalServiceUtil.getDossier(dossierId);
			}

			from =
				Validator.isNotNull(dossier) ? PrefsPropsUtil.getString(
					dossier.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS) : StringPool.BLANK;
			to = "hltn.works@gmail.com";
			subject = PortletPropsValues.SUBJECT_TO_CUSTOMER;
			body = PortletPropsValues.CONTENT_TO_CUSTOMER;

			body =
				StringUtil.replace(body, "{receptionNo}", String.valueOf(message.getDossierId()));
			body =
				StringUtil.replace(
					body, "{event}", LanguageUtil.get(locale, message.getNotificationEventName()));

			SendMailUtils.sendEmail(from, to, subject, body, htmlFormat);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

}
