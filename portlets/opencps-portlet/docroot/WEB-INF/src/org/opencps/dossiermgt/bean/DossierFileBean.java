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

import org.opencps.dossiermgt.model.DossierFile;

/**
 * @author trungnt
 */
public class DossierFileBean {

	private DossierFile _dossierFile;
	private long _dossierFileId;
	private long _dossierId;
	private String _partName;
	private int _partType;
	private String _receptionNo;
	private long _serviceInfoId;

	public long getDossierFileId() {

		return _dossierFileId;
	}

	public void setDossierFileId(long dossierFileId) {

		this._dossierFileId = dossierFileId;
	}

	public DossierFile getDossierFile() {

		return _dossierFile;
	}

	public long getDossierId() {

		return _dossierId;
	}

	public String getPartName() {

		return _partName;
	}

	public int getPartType() {

		return _partType;
	}

	public String getReceptionNo() {

		return _receptionNo;
	}

	public long getServiceInfoId() {

		return _serviceInfoId;
	}

	public void setDossierFile(DossierFile dossierFile) {

		this._dossierFile = dossierFile;
	}

	public void setDossierId(long dossierId) {

		this._dossierId = dossierId;
	}

	public void setPartName(String partName) {

		this._partName = partName;
	}

	public void setPartType(int partType) {

		this._partType = partType;
	}

	public void setReceptionNo(String receptionNo) {

		this._receptionNo = receptionNo;
	}

	public void setServiceInfoId(long serviceInfoId) {

		this._serviceInfoId = serviceInfoId;
	}

}
