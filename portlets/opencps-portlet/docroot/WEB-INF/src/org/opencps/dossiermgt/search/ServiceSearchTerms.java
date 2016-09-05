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

package org.opencps.dossiermgt.search;

import javax.portlet.PortletRequest;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author trungnt
 */
public class ServiceSearchTerms extends ServiceDisplayTerms {

	public ServiceSearchTerms(PortletRequest portletRequest) {
		super(
			portletRequest);

		serviceName = DAOParamUtil
			.getString(portletRequest, SERVICE_NAME);

		serviceConfigId = ParamUtil
			.getLong(portletRequest, SERVICE_CONFIG_ID);

		serviceInfoId = ParamUtil
			.getLong(portletRequest, SERVICE_INFO_ID);

		domainCode = DAOParamUtil
			.getString(portletRequest, DOMAIN_CODE);

		govAgencyName = DAOParamUtil
			.getString(portletRequest, GOVAGENCY_NAME);

		govAgencyCode = DAOParamUtil
			.getString(portletRequest, GOVAGENCY_CODE);

		govAgencyIndex = DAOParamUtil
			.getString(portletRequest, GOVAGENCY_INDEX);

		serviceDomainIndex = DAOParamUtil
			.getString(portletRequest, SERVICE_DOMAIN_INDEX);

	}

	public String getDomainCode() {

		return domainCode;
	}

	public String getGovAgencyCode() {

		return govAgencyCode;
	}

	public String getGovAgencyIndex() {

		return govAgencyIndex;
	}

	public String getGovAgencyName() {

		return govAgencyName;
	}

	public long getServiceConfigId() {

		return serviceConfigId;
	}

	public String getServiceDomainIndex() {

		return serviceDomainIndex;
	}

	public long getServiceInfoId() {

		return serviceInfoId;
	}

	public String getServiceName() {

		return serviceName;
	}

	public void setDomainCode(String domainCode) {

		this.domainCode = domainCode;
	}
	public void setGovAgencyCode(String govAgencyCode) {

		this.govAgencyCode = govAgencyCode;
	}
	public void setGovAgencyIndex(String govAgencyIndex) {

		this.govAgencyIndex = govAgencyIndex;
	}
	public void setGovAgencyName(String govAgencyName) {

		this.govAgencyName = govAgencyName;
	}
	public void setServiceConfigId(long serviceConfigId) {

		this.serviceConfigId = serviceConfigId;
	}
	public void setServiceDomainIndex(String serviceDomainIndex) {

		this.serviceDomainIndex = serviceDomainIndex;
	}

	public void setServiceInfoId(long serviceInfoId) {

		this.serviceInfoId = serviceInfoId;
	}
	public void setServiceName(String serviceName) {

		this.serviceName = serviceName;
	}
	
	protected String domainCode;

	protected String govAgencyCode;

	protected String govAgencyIndex;

	protected String govAgencyName;

	protected long serviceConfigId;

	protected String serviceDomainIndex;

	protected long serviceInfoId;

	protected String serviceName;
}
