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

import org.opencps.accountmgt.model.Business;
import org.opencps.accountmgt.model.impl.BusinessImpl;

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
public class BusinessFinderImpl extends BasePersistenceImpl<Business> implements
		BusinessFinder {

	public static final String SEARCH_BUSINESS = BusinessFinder.class.getName()
			+ ".searchBusiness";
	public static final String COUNT_BUSINESS = BusinessFinder.class.getName()
			+ ".countBusiness";

	public List<Business> searchBusiness(long groupId, String keywords,
			int accountStatus, String businessDomain, int start, int end) 
		throws SystemException {

		return _searchBusiness(groupId, keywords, accountStatus,
				businessDomain, start, end);
	}
	
	/**
	 * @param groupId
	 * @param keywords
	 * @param accountStatus
	 * @param andOperator
	 * @param businessDomain
	 * @param start
	 * @param end
	 * @return
	 */
	private List<Business> _searchBusiness(long groupId, String keywords,
			int accountStatus, String businessDomain,
			int start, int end) throws SystemException {

		Session session = null;

		try {
			
			session = openSession();

			String sql = CustomSQLUtil.get(SEARCH_BUSINESS);
			
			if (Validator.isNull(groupId)){
				sql = sql.replace("AND (opencps_acc_business.groupId = ?)"
						, StringPool.BLANK);
			}
			if (accountStatus == -1){
				sql = sql.replace("AND (opencps_acc_business.accountStatus = ?)"
						, StringPool.BLANK);
			}
			if (Validator.isNull(keywords)){
				sql = sql.replace("AND ((lower(opencps_acc_business.name) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_business.shortName) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_business.enName) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_business.email) LIKE ?))",
						StringPool.BLANK);
			}
			if (Validator.isNull(businessDomain)){
				sql = sql.replace("INNER JOIN "
									+ "opencps_acc_businessdomain "
								+ "ON "
									+ "opencps_acc_business.businessId = opencps_acc_businessdomain.businessId"
						, StringPool.BLANK);
				
				sql = sql.replace("AND (opencps_acc_businessdomain.businessDomainCode = ?)"
						, StringPool.BLANK);
			}
			
			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Business", BusinessImpl.class);

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
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
			}
			if (Validator.isNotNull(businessDomain)){
				qPos.add(businessDomain);
			}
			
			return (List<Business>) QueryUtil.list(q, getDialect(), start, end);
		} catch (Exception e) {
			throw new SystemException();
		} finally {
			session.close();
		}
	}

	public int countBussiness(long groupId, String keywords, int accountStatus,
			String businessDomain) throws SystemException {

		return _countBussiness(groupId, keywords, accountStatus, businessDomain);
	}

	/**
	 * @param groupId
	 * @param keywords
	 * @param accountStatus
	 * @param businessDomain
	 * @param andOperator
	 * @return
	 */
	private int _countBussiness(long groupId, String keywords, int accountStatus,
			String businessDomain) 
		throws SystemException {

		Session session = null;
		
		try {
			session = openSession();
			
			String sql = CustomSQLUtil.get(COUNT_BUSINESS);
			
			if (Validator.isNull(groupId)){
				sql = sql.replace("AND (opencps_acc_business.groupId = ?)"
						, StringPool.BLANK);
			}
			if (accountStatus == -1){
				sql = sql.replace("AND (opencps_acc_business.accountStatus = ?)"
						, StringPool.BLANK);
			}
			if (Validator.isNull(keywords)){
				sql = sql.replace("AND ((lower(opencps_acc_business.name) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_business.shortName) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_business.enName) LIKE ?)",
						StringPool.BLANK);
				sql = sql.replace("OR (lower(opencps_acc_business.email) LIKE ?))",
						StringPool.BLANK);
			}
			if (Validator.isNull(businessDomain)){
				sql = sql.replace("INNER JOIN "
									+ "opencps_acc_businessdomain "
								+ "ON "
									+ "opencps_acc_business.businessId = opencps_acc_businessdomain.businessId"
						, StringPool.BLANK);
				
				sql = sql.replace("AND (opencps_acc_businessdomain.businessDomainCode = ?)"
						, StringPool.BLANK);
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
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
				qPos.add(StringPool.PERCENT + keywords + StringPool.PERCENT);
			}
			if (Validator.isNotNull(businessDomain)){
				qPos.add(businessDomain);
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