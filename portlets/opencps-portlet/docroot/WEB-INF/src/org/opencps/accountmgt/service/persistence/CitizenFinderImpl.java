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
/**
 * 
 */
package org.opencps.accountmgt.service.persistence;

import java.util.Iterator;
import java.util.List;

import org.opencps.accountmgt.model.Citizen;
import org.opencps.accountmgt.model.impl.CitizenImpl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

/**
 * @author dunglt
 *
 */

public class CitizenFinderImpl extends BasePersistenceImpl<Citizen> implements
		CitizenFinder {

	public static final String SEARCH_CITIZEN = CitizenFinder.class.getName()
			+ ".searchCitizen";
	public static final String COUNT_CITIZEN = CitizenFinder.class.getName()
			+ ".countCitizen";

	public List<Citizen> searchCitizen(long groupId, String keywords,
			int accountStatus, int start, int end) 
		throws SystemException {

		return _searchCitizen(groupId, keywords, accountStatus,
				start, end);
	}

	/**
	 * @param groupId
	 * @param keywords
	 * @param accountStatus
	 * @param andOperator
	 * @param start
	 * @param end
	 * @return
	 */
	private List<Citizen> _searchCitizen(long groupId, String keywords,
			int accountStatus, int start, int end) 
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(SEARCH_CITIZEN);
			
			if (Validator.isNull(groupId)){
				sql = sql.replace("AND (opencps_acc_citizen.groupId = ?)"
						, StringPool.BLANK);
			}
			if (accountStatus == -1){
				sql = sql.replace("AND (opencps_acc_citizen.accountStatus = ?)"
						, StringPool.BLANK);
			}
			if (Validator.isNull(keywords)){
				sql = sql.replace("AND ((lower(opencps_acc_citizen.fullName) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_citizen.email) LIKE ?))",
						StringPool.BLANK);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Citizen", CitizenImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			
			if (Validator.isNotNull(groupId)){
				qPos.add(groupId);
			}
			if (accountStatus != -1){
				qPos.add(accountStatus);
			}
			if (Validator.isNotNull(keywords)){
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
			}
			
			return (List<Citizen>) QueryUtil.list(q, getDialect(), start, end);
		} catch (Exception e) {
			throw new SystemException();
		} finally {
			session.close();
		}
	}

	public int countCitizen(long groupId, String keywords, int accountStatus) 
			throws SystemException {

		return _countCitizen(groupId, keywords, accountStatus);
	}

	/**
	 * @param groupId
	 * @param keywords
	 * @param accountStatus
	 * @param andOperator
	 * @return
	 */
	private int _countCitizen(long groupId, String keywords,
			int accountStatus) 
		throws SystemException {

		Session session = null;
		
		try {
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_CITIZEN);
			
			if (Validator.isNull(groupId)){
				sql = sql.replace("AND (opencps_acc_citizen.groupId = ?)"
						, StringPool.BLANK);
			}
			if (accountStatus == -1){
				sql = sql.replace("AND (opencps_acc_citizen.accountStatus = ?)"
						, StringPool.BLANK);
			}
			if (Validator.isNull(keywords)){
				sql = sql.replace("AND ((lower(opencps_acc_citizen.fullName) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_citizen.email) LIKE ?))",
						StringPool.BLANK);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);

			q.addScalar(COUNT_COLUMN_NAME, Type.INTEGER);

			QueryPos qPos = QueryPos.getInstance(q);
			
			if (Validator.isNotNull(groupId)){
				qPos.add(groupId);
			}
			if (accountStatus != -1){
				qPos.add(accountStatus);
			}
			if (Validator.isNotNull(keywords)){
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
			}

			Iterator<Integer> itr = q.iterate();

			if (itr.hasNext()) {
				Integer count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}
			
			return 0;
		} catch (Exception e) {
			throw new SystemException();
		} finally {
			session.close();
		}
	}
}
