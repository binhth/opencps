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

package org.opencps.dossiermgt.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.util.DateTimeUtil;
import org.opencps.util.PortletConstants;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author trungnt
 */
public class DelayStatus implements MessageListener {

	@Override
	public void receive(Message message)
		throws MessageListenerException {

		try {

			List<Dossier> dossiersUnExpired = DossierLocalServiceUtil
				.getDossierByDelayStatusAndNotDossierStatus(
					PortletConstants.DOSSIER_DELAY_STATUS_UNEXPIRED,
					PortletConstants.DOSSIER_STATUS_NEW);
			List<Dossier> dossiersExpired = DossierLocalServiceUtil
				.getDossierByDelayStatus(
					PortletConstants.DOSSIER_DELAY_STATUS_EXPIRED);

			Date now = new Date();

			int[] dateIgnoreFileds = new int[2];
			dateIgnoreFileds[0] = Calendar.SECOND;
			dateIgnoreFileds[1] = Calendar.MILLISECOND;

			Calendar current = DateTimeUtil
				.getInstance(now, dateIgnoreFileds);

			if (dossiersUnExpired != null) {
				for (Dossier dossier : dossiersUnExpired) {
					if (Validator
						.isNull(dossier
							.getEstimateDatetime()) &&
						Validator
							.isNotNull(dossier
								.getFinishDatetime())) {
						dossier
							.setDelayStatus(
								PortletConstants.DOSSIER_DELAY_STATUS_ONTIME);
						DossierLocalServiceUtil
							.updateDossier(dossier);
					}
					else if (Validator
						.isNotNull(dossier
							.getEstimateDatetime()) &&
						Validator
							.isNull(dossier
								.getFinishDatetime())) {

						Calendar estimateDate = DateTimeUtil
							.getInstance(dossier
								.getEstimateDatetime(), dateIgnoreFileds);

						if (current
							.before(estimateDate)) {
							dossier
								.setDelayStatus(
									PortletConstants.DOSSIER_DELAY_STATUS_EXPIRED);
							DossierLocalServiceUtil
								.updateDossier(dossier);
						}

					}
					else if (Validator
						.isNotNull(dossier
							.getEstimateDatetime()) &&
						Validator
							.isNotNull(dossier
								.getFinishDatetime())) {

						Calendar estimateDate = DateTimeUtil
							.getInstance(dossier
								.getEstimateDatetime(), dateIgnoreFileds);

						Calendar finishDate = DateTimeUtil
							.getInstance(dossier
								.getFinishDatetime(), dateIgnoreFileds);

						if (estimateDate
							.after(finishDate)) {
							dossier
								.setDelayStatus(
									PortletConstants.DOSSIER_DELAY_STATUS_ONTIME);
						}
						else {
							dossier
								.setDelayStatus(
									PortletConstants.DOSSIER_DELAY_STATUS_LATE);
						}

						DossierLocalServiceUtil
							.updateDossier(dossier);

					}
				}
			}

			if (dossiersExpired != null) {
				for (Dossier dossier : dossiersExpired) {
					if (Validator
						.isNotNull(dossier
							.getFinishDatetime())) {
						dossier
							.setDelayStatus(
								PortletConstants.DOSSIER_DELAY_STATUS_LATE);
						DossierLocalServiceUtil
							.updateDossier(dossier);
					}
				}
			}

		}
		catch (Exception e) {
			_log
				.error(e);
		}
	}

	private Log _log = LogFactoryUtil
		.getLog(DelayStatus.class);
}
