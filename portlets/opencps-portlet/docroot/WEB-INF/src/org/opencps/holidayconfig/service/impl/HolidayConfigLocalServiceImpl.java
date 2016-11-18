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

package org.opencps.holidayconfig.service.impl;

import java.util.Date;
import java.util.List;

import org.opencps.holidayconfig.model.HolidayConfig;
import org.opencps.holidayconfig.service.base.HolidayConfigLocalServiceBaseImpl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

/**
 * The implementation of the holiday config local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link org.opencps.holidayconfig.service.HolidayConfigLocalService}
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author nhanhoang
 * @see org.opencps.holidayconfig.service.base.HolidayConfigLocalServiceBaseImpl
 * @see org.opencps.holidayconfig.service.HolidayConfigLocalServiceUtil
 */
public class HolidayConfigLocalServiceImpl extends
		HolidayConfigLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * org.opencps.holidayconfig.service.HolidayConfigLocalServiceUtil} to
	 * access the holiday config local service.
	 */

	public List<HolidayConfig> getHolidayConfig(int status)
			throws PortalException, SystemException {

		return holidayConfigFinder.getHolidayConfig(status);
	}

	public HolidayConfig addHoliday(Date holidayDate, String description,int status,
			ServiceContext serviceContext) throws PortalException,
			SystemException {

		long holidayId = CounterLocalServiceUtil.increment(HolidayConfig.class
				.getName());

		HolidayConfig holiday = holidayConfigPersistence.create(holidayId);

		Date now = new Date();

		holiday.setHoliday(holidayDate);
		holiday.setUserId(serviceContext.getUserId());
		holiday.setGroupId(serviceContext.getScopeGroupId());
		holiday.setCompanyId(serviceContext.getCompanyId());
		holiday.setCreatedDate(now);
		holiday.setModifiedDate(now);
		holiday.setDescription(description);
		holiday.setStatus(status);
		

		return holidayConfigPersistence.update(holiday);

	}

	public HolidayConfig updateHoliday(long holidayId, Date holidayDate,
			String description, int status) throws PortalException,
			SystemException {

		HolidayConfig holiday = holidayConfigPersistence
				.findByPrimaryKey(holidayId);

		Date now = new Date();

		holiday.setHoliday(holidayDate);
		holiday.setModifiedDate(now);
		holiday.setDescription(description);
		holiday.setStatus(status);

		return holidayConfigPersistence.update(holiday);

	}
}