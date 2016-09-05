/**
 * OpenCPS is the open source Core Public Services software
 * Copyright (C) 2016-present OpenCPS community

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 */

package org.opencps.jms.context;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author trungnt
 */
public class JMSContextFactory {

	/**
	 * @param companyId
	 * @param code
	 * @param remote
	 * @param channelName
	 * @return
	 */
	public static JMSContext getInstance(
		long companyId, String code, boolean remote, String channelName,
		String queueName, String lookup, String mom) {

		JMSContext context = null;
		try {
			context =
				new JMSContext(
					companyId, code, remote, channelName, queueName, lookup,
					mom);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return context;

	}

	private static Log _log =
		LogFactoryUtil.getLog(JMSContextFactory.class.getName());
}
