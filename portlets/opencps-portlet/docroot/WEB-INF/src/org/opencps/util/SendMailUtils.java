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
package org.opencps.util;

import javax.mail.internet.InternetAddress;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
/**
 * @author nhanhoang
 */

public class SendMailUtils {
	
	private static Log _log = LogFactoryUtil.getLog(SendMailUtils.class);

	/**
	 * @param from
	 * @param to
	 * @param subject
	 * @param body
	 * @param htmlFormat
	 * @return
	 */
	public static boolean sendEmail(
		String fromAddress,String fromName, String to,String cc, String subject, String body, boolean htmlFormat) {

		try {
			if (fromAddress.trim().length() > 0 && to.trim().length() > 0) {
				
				InternetAddress CC = new InternetAddress();
				
				InternetAddress[] CCS = CC.parse(cc);

				MailMessage mailMessage = new MailMessage();
				mailMessage.setFrom(new InternetAddress(fromAddress,fromName));
				mailMessage.setTo(new InternetAddress(to));
				mailMessage.setCC(CCS);
				mailMessage.setSubject(subject);
				mailMessage.setBody(body);
				mailMessage.setHTMLFormat(htmlFormat);
				MailServiceUtil.sendEmail(mailMessage);
				return true;
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
		return false;
	}
}
