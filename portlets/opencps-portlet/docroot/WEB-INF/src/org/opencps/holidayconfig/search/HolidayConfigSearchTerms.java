/*******************************************************************************
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
 *******************************************************************************/

package org.opencps.holidayconfig.search;

import java.util.Date;

import javax.portlet.PortletRequest;

import org.opencps.util.DateTimeUtil;

import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author nhanhoang
 */
public class HolidayConfigSearchTerms extends HolidayConfigDisplayTerms {

	public HolidayConfigSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		createDate = ParamUtil.getDate(portletRequest, CREATE_DATE,
				DateTimeUtil
						.getDateTimeFormat(DateTimeUtil._VN_DATE_TIME_FORMAT));
		modifiedDate = ParamUtil.getDate(portletRequest, MODIFIED_DATE,
				DateTimeUtil
						.getDateTimeFormat(DateTimeUtil._VN_DATE_TIME_FORMAT));
		userId = ParamUtil.getLong(portletRequest, USER_ID);
		holidayId = ParamUtil.getLong(portletRequest, HOLIDAY_ID);
		holidayStatus = ParamUtil.getInteger(portletRequest, HOLIDAY_STATUS);
		holidayDate = ParamUtil.getDate(portletRequest, MODIFIED_DATE,
				DateTimeUtil.getDateTimeFormat(DateTimeUtil._VN_DATE_FORMAT));

	}

	public Date getCreateDate() {

		return createDate;
	}

	public Date getModifiedDate() {

		return modifiedDate;
	}

	public long getUserId() {

		return userId;
	}

	public void setCreateDate(Date createDate) {

		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(long holidayId) {
		this.holidayId = holidayId;
	}

	public Date getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(Date holidayDate) {
		this.holidayDate = holidayDate;
	}

	public long getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(long holidayStatus) {
		this.holidayStatus = holidayStatus;
	}

	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	public void setUserId(long userId) {

		this.userId = userId;
	}

	protected String description;
	protected long holidayId;
	protected Date holidayDate;
	protected Date createDate;
	protected Date modifiedDate;
	protected long userId;
	protected long holidayStatus;
}
