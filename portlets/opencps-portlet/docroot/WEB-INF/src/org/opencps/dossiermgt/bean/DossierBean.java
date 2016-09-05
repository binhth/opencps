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

import org.opencps.dossiermgt.model.Dossier;

/**
 * @author trungnt
 */
public class DossierBean {
	
	
	public long getDossierId() {
	
		return _dossierId;
	}

	public void setDossierId(long dossierId) {
	
		this._dossierId = dossierId;
	}

	public String getServiceName() {

		return _serviceName;
	}

	public void setServiceName(String serviceName) {

		this._serviceName = serviceName;
	}

	public Dossier getDossier() {
	
		return _dossier;
	}

	
	public void setDossier(Dossier dossier) {
	
		this._dossier = dossier;
	}
	
	private long _dossierId;

	private String _serviceName;


	private Dossier _dossier;

}
