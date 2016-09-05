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

package org.opencps.processmgt.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opencps.processmgt.model.SchedulerJobs;
import org.opencps.processmgt.service.base.SchedulerJobsLocalServiceBaseImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * The implementation of the scheduler jobs local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link org.opencps.processmgt.service.SchedulerJobsLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author khoavd
 * @see org.opencps.processmgt.service.base.SchedulerJobsLocalServiceBaseImpl
 * @see org.opencps.processmgt.service.SchedulerJobsLocalServiceUtil
 */
public class SchedulerJobsLocalServiceImpl
	extends SchedulerJobsLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link org.opencps.processmgt.service.SchedulerJobsLocalServiceUtil} to access the scheduler jobs local service.
	 */
	
	/**
	 * @param dossierId
	 * @param fileGroupId
	 * @param processWorkflowId
	 * @param schedulerType
	 * @param status
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public SchedulerJobs addScheduler(
	    long dossierId, long fileGroupId, long processWorkflowId,
	    int schedulerType, int status, String schedulerPattern)
	    throws PortalException, SystemException {

		long schedulerJobId =
		    counterLocalService.increment(SchedulerJobs.class.getName());

		Date now = new Date();

		SchedulerJobs schedulerJob =
		    schedulerJobsPersistence.create(schedulerJobId);

		schedulerJob.setCreateDate(now);
		schedulerJob.setModifiedDate(now);
		schedulerJob.setDossierId(dossierId);
		schedulerJob.setFileGroupId(fileGroupId);
		schedulerJob.setSchedulerType(schedulerType);
		schedulerJob.setProcessWorkflowId(processWorkflowId);
		schedulerJob.setStatus(status);
		schedulerJob.setShedulerPattern(schedulerPattern);

		schedulerJobsPersistence.update(schedulerJob);

		return schedulerJob;

	}

	/**
	 * @param schedulerJobId
	 * @param status
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public SchedulerJobs updateScheduler(long schedulerJobId, int status)
	    throws PortalException, SystemException {

		SchedulerJobs schedulerJob =
		    schedulerJobsPersistence.fetchByPrimaryKey(schedulerJobId);

		schedulerJob.setStatus(status);

		schedulerJobsPersistence.update(schedulerJob);

		return schedulerJob;

	}
	

	
	/**
	 * @param schedulerType
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public List<SchedulerJobs> getSchedulerJobs(int schedulerType)
	    throws PortalException, SystemException {
		List<SchedulerJobs> ls = new ArrayList<SchedulerJobs>();
		
		ls = schedulerJobsPersistence.findByS_T(schedulerType);
		
		return ls;
	}
}