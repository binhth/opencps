/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package org.opencps.holidayconfig.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import org.opencps.holidayconfig.model.HolidayConfigExtend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing HolidayConfigExtend in entity cache.
 *
 * @author nhanhoang
 * @see HolidayConfigExtend
 * @generated
 */
public class HolidayConfigExtendCacheModel implements CacheModel<HolidayConfigExtend>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{holidayExtendId=");
		sb.append(holidayExtendId);
		sb.append(", key=");
		sb.append(key);
		sb.append(", description=");
		sb.append(description);
		sb.append(", status=");
		sb.append(status);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public HolidayConfigExtend toEntityModel() {
		HolidayConfigExtendImpl holidayConfigExtendImpl = new HolidayConfigExtendImpl();

		holidayConfigExtendImpl.setHolidayExtendId(holidayExtendId);

		if (key == null) {
			holidayConfigExtendImpl.setKey(StringPool.BLANK);
		}
		else {
			holidayConfigExtendImpl.setKey(key);
		}

		if (description == null) {
			holidayConfigExtendImpl.setDescription(StringPool.BLANK);
		}
		else {
			holidayConfigExtendImpl.setDescription(description);
		}

		holidayConfigExtendImpl.setStatus(status);
		holidayConfigExtendImpl.setCompanyId(companyId);
		holidayConfigExtendImpl.setGroupId(groupId);
		holidayConfigExtendImpl.setUserId(userId);

		holidayConfigExtendImpl.resetOriginalValues();

		return holidayConfigExtendImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		holidayExtendId = objectInput.readLong();
		key = objectInput.readUTF();
		description = objectInput.readUTF();
		status = objectInput.readInt();
		companyId = objectInput.readLong();
		groupId = objectInput.readLong();
		userId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(holidayExtendId);

		if (key == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(status);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(userId);
	}

	public long holidayExtendId;
	public String key;
	public String description;
	public int status;
	public long companyId;
	public long groupId;
	public long userId;
}