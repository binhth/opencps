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

package org.opencps.processmgt.service.persistence;

import java.util.List;

import org.opencps.processmgt.model.ProcessWorkflow;
import org.opencps.processmgt.model.impl.ProcessWorkflowImpl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;


/**
 * @author khoavd
 *
 */
public class ProcessWorkflowFinderImpl extends BasePersistenceImpl<ProcessWorkflow> implements ProcessWorkflowFinder{
	public static final String SQL_FINDER_SHCHEDULER = ProcessWorkflowFinder.class.getName() + ".findInScheduler";
	
	/**
	 * @param preProcessStep
	 * @param serviceProcessId
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public List<ProcessWorkflow> findInShcheduler(
	    long preProcessStep, long serviceProcessId)
	    throws PortalException, SystemException {

		Session session = null;
		try {
			session = openSession();

			String sql = CustomSQLUtil.get(SQL_FINDER_SHCHEDULER);

			SQLQuery q = session.createSQLQuery(sql);

			q.setCacheable(false);

			q.addEntity("ProcessWorkflow", ProcessWorkflowImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(preProcessStep);
			qPos.add(serviceProcessId);

			return (List<ProcessWorkflow>) QueryUtil.list(
			    q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			try {
				throw new SystemException(e);
			}
			catch (SystemException se) {
				se.printStackTrace();
			}
		}
		finally {
			closeSession(session);
		}

		return null;

	}

}
