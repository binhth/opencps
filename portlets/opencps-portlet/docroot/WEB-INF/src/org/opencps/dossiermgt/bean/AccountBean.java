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

package org.opencps.dossiermgt.bean;

import java.io.Serializable;
import java.util.ArrayList;

import org.opencps.util.PortletPropsValues;

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;

/**
 * @author trungnt
 */
public class AccountBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * @param accountInstance
	 * @param accountType
	 * @param accountRoles
	 * @param accountOrgs
	 * @param ownerUserId
	 * @param ownerOrganizationId
	 */
	public AccountBean(
		Object accountInstance, String accountType,
		ArrayList<Role> accountRoles, ArrayList<Organization> accountOrgs,
		long ownerUserId, long ownerOrganizationId) {

		this.setAccountInstance(accountInstance);
		this.setAccountOrgs(accountOrgs);
		this.setAccountRoles(accountRoles);
		this.setAccountType(accountType);
		this.setOwnerOrganizationId(ownerOrganizationId);
		this.setOwnerUserId(ownerUserId);
	}

	private Object _accountInstance;

	private String _accountType;

	private ArrayList<Role> _accountRoles;

	private ArrayList<Organization> _accountOrgs;

	private long _ownerUserId;

	private long _ownerOrganizationId;

	public Object getAccountInstance() {

		return _accountInstance;
	}

	public void setAccountInstance(Object accountInstance) {

		this._accountInstance = accountInstance;
	}

	public String getAccountType() {

		return _accountType;
	}

	public void setAccountType(String accountType) {

		this._accountType = accountType;
	}

	public ArrayList<Role> getAccountRoles() {

		return _accountRoles;
	}

	public void setAccountRoles(ArrayList<Role> accountRoles) {

		this._accountRoles = accountRoles;
	}

	public ArrayList<Organization> getAccountOrgs() {

		return _accountOrgs;
	}

	public void setAccountOrgs(ArrayList<Organization> accountOrgs) {

		this._accountOrgs = accountOrgs;
	}

	public long getOwnerUserId() {

		return _ownerUserId;
	}

	public void setOwnerUserId(long ownerUserId) {

		this._ownerUserId = ownerUserId;
	}

	public long getOwnerOrganizationId() {

		return _ownerOrganizationId;
	}

	public void setOwnerOrganizationId(long ownerOrganizationId) {

		this._ownerOrganizationId = ownerOrganizationId;
	}

	public boolean isCitizen() {

		return _accountType.equals(PortletPropsValues.USERMGT_USERGROUP_NAME_CITIZEN)
			? true : false;
	}

	public boolean isBusiness() {

		return _accountType.equals(PortletPropsValues.USERMGT_USERGROUP_NAME_BUSINESS)
			? true : false;
	}

	public boolean isEmployee() {

		return _accountType.equals(PortletPropsValues.USERMGT_USERGROUP_NAME_EMPLOYEE)
			? true : false;
	}

}
