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

package org.opencps.dossiermgt.service.persistence;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.opencps.dossiermgt.NoSuchDossierFileLogException;
import org.opencps.dossiermgt.model.DossierFileLog;
import org.opencps.dossiermgt.model.impl.DossierFileLogImpl;
import org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the dossier file log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author trungnt
 * @see DossierFileLogPersistence
 * @see DossierFileLogUtil
 * @generated
 */
public class DossierFileLogPersistenceImpl extends BasePersistenceImpl<DossierFileLog>
	implements DossierFileLogPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DossierFileLogUtil} to access the dossier file log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DossierFileLogImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByD_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByD_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DossierFileLogModelImpl.DOSSIERID_COLUMN_BITMASK |
			DossierFileLogModelImpl.FILEGROUPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.STEPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F_S = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F_S",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @return the matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S(long dossierId, long fileGroupId,
		long stepId) throws SystemException {
		return findByD_F_S(dossierId, fileGroupId, stepId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @return the range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S(long dossierId, long fileGroupId,
		long stepId, int start, int end) throws SystemException {
		return findByD_F_S(dossierId, fileGroupId, stepId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S(long dossierId, long fileGroupId,
		long stepId, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S;
			finderArgs = new Object[] { dossierId, fileGroupId, stepId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S;
			finderArgs = new Object[] {
					dossierId, fileGroupId, stepId,
					
					start, end, orderByComparator
				};
		}

		List<DossierFileLog> list = (List<DossierFileLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DossierFileLog dossierFileLog : list) {
				if ((dossierId != dossierFileLog.getDossierId()) ||
						(fileGroupId != dossierFileLog.getFileGroupId()) ||
						(stepId != dossierFileLog.getStepId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_STEPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				if (!pagination) {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<DossierFileLog>(list);
				}
				else {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_First(long dossierId, long fileGroupId,
		long stepId, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_First(dossierId,
				fileGroupId, stepId, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_First(long dossierId, long fileGroupId,
		long stepId, OrderByComparator orderByComparator)
		throws SystemException {
		List<DossierFileLog> list = findByD_F_S(dossierId, fileGroupId, stepId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_Last(long dossierId, long fileGroupId,
		long stepId, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_Last(dossierId,
				fileGroupId, stepId, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_Last(long dossierId, long fileGroupId,
		long stepId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByD_F_S(dossierId, fileGroupId, stepId);

		if (count == 0) {
			return null;
		}

		List<DossierFileLog> list = findByD_F_S(dossierId, fileGroupId, stepId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dossier file logs before and after the current dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierFileLogId the primary key of the current dossier file log
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog[] findByD_F_S_PrevAndNext(long dossierFileLogId,
		long dossierId, long fileGroupId, long stepId,
		OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = findByPrimaryKey(dossierFileLogId);

		Session session = null;

		try {
			session = openSession();

			DossierFileLog[] array = new DossierFileLogImpl[3];

			array[0] = getByD_F_S_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, orderByComparator, true);

			array[1] = dossierFileLog;

			array[2] = getByD_F_S_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DossierFileLog getByD_F_S_PrevAndNext(Session session,
		DossierFileLog dossierFileLog, long dossierId, long fileGroupId,
		long stepId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

		query.append(_FINDER_COLUMN_D_F_S_DOSSIERID_2);

		query.append(_FINDER_COLUMN_D_F_S_FILEGROUPID_2);

		query.append(_FINDER_COLUMN_D_F_S_STEPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(dossierId);

		qPos.add(fileGroupId);

		qPos.add(stepId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dossierFileLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DossierFileLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; from the database.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByD_F_S(long dossierId, long fileGroupId, long stepId)
		throws SystemException {
		for (DossierFileLog dossierFileLog : findByD_F_S(dossierId,
				fileGroupId, stepId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dossierFileLog);
		}
	}

	/**
	 * Returns the number of dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @return the number of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByD_F_S(long dossierId, long fileGroupId, long stepId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_D_F_S;

		Object[] finderArgs = new Object[] { dossierId, fileGroupId, stepId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_STEPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_D_F_S_DOSSIERID_2 = "dossierFileLog.dossierId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_FILEGROUPID_2 = "dossierFileLog.fileGroupId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_STEPID_2 = "dossierFileLog.stepId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S_U = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByD_F_S_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U =
		new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByD_F_S_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			DossierFileLogModelImpl.DOSSIERID_COLUMN_BITMASK |
			DossierFileLogModelImpl.FILEGROUPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.STEPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.ISUPDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F_S_U = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F_S_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @return the matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_U(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate) throws SystemException {
		return findByD_F_S_U(dossierId, fileGroupId, stepId, isUpdate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @return the range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_U(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, int start, int end)
		throws SystemException {
		return findByD_F_S_U(dossierId, fileGroupId, stepId, isUpdate, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_U(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U;
			finderArgs = new Object[] { dossierId, fileGroupId, stepId, isUpdate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S_U;
			finderArgs = new Object[] {
					dossierId, fileGroupId, stepId, isUpdate,
					
					start, end, orderByComparator
				};
		}

		List<DossierFileLog> list = (List<DossierFileLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DossierFileLog dossierFileLog : list) {
				if ((dossierId != dossierFileLog.getDossierId()) ||
						(fileGroupId != dossierFileLog.getFileGroupId()) ||
						(stepId != dossierFileLog.getStepId()) ||
						(isUpdate != dossierFileLog.getIsUpdate())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_U_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_STEPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_ISUPDATE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				qPos.add(isUpdate);

				if (!pagination) {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<DossierFileLog>(list);
				}
				else {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_U_First(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_U_First(dossierId,
				fileGroupId, stepId, isUpdate, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(", isUpdate=");
		msg.append(isUpdate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_U_First(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate,
		OrderByComparator orderByComparator) throws SystemException {
		List<DossierFileLog> list = findByD_F_S_U(dossierId, fileGroupId,
				stepId, isUpdate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_U_Last(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_U_Last(dossierId,
				fileGroupId, stepId, isUpdate, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(", isUpdate=");
		msg.append(isUpdate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_U_Last(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByD_F_S_U(dossierId, fileGroupId, stepId, isUpdate);

		if (count == 0) {
			return null;
		}

		List<DossierFileLog> list = findByD_F_S_U(dossierId, fileGroupId,
				stepId, isUpdate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dossier file logs before and after the current dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierFileLogId the primary key of the current dossier file log
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog[] findByD_F_S_U_PrevAndNext(long dossierFileLogId,
		long dossierId, long fileGroupId, long stepId, boolean isUpdate,
		OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = findByPrimaryKey(dossierFileLogId);

		Session session = null;

		try {
			session = openSession();

			DossierFileLog[] array = new DossierFileLogImpl[3];

			array[0] = getByD_F_S_U_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, isUpdate,
					orderByComparator, true);

			array[1] = dossierFileLog;

			array[2] = getByD_F_S_U_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, isUpdate,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DossierFileLog getByD_F_S_U_PrevAndNext(Session session,
		DossierFileLog dossierFileLog, long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

		query.append(_FINDER_COLUMN_D_F_S_U_DOSSIERID_2);

		query.append(_FINDER_COLUMN_D_F_S_U_FILEGROUPID_2);

		query.append(_FINDER_COLUMN_D_F_S_U_STEPID_2);

		query.append(_FINDER_COLUMN_D_F_S_U_ISUPDATE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(dossierId);

		qPos.add(fileGroupId);

		qPos.add(stepId);

		qPos.add(isUpdate);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dossierFileLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DossierFileLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; from the database.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByD_F_S_U(long dossierId, long fileGroupId, long stepId,
		boolean isUpdate) throws SystemException {
		for (DossierFileLog dossierFileLog : findByD_F_S_U(dossierId,
				fileGroupId, stepId, isUpdate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(dossierFileLog);
		}
	}

	/**
	 * Returns the number of dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @return the number of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByD_F_S_U(long dossierId, long fileGroupId, long stepId,
		boolean isUpdate) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_D_F_S_U;

		Object[] finderArgs = new Object[] {
				dossierId, fileGroupId, stepId, isUpdate
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_U_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_STEPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_ISUPDATE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				qPos.add(isUpdate);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_D_F_S_U_DOSSIERID_2 = "dossierFileLog.dossierId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_FILEGROUPID_2 = "dossierFileLog.fileGroupId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_STEPID_2 = "dossierFileLog.stepId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_ISUPDATE_2 = "dossierFileLog.isUpdate = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S_U_C =
		new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByD_F_S_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U_C =
		new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByD_F_S_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			DossierFileLogModelImpl.DOSSIERID_COLUMN_BITMASK |
			DossierFileLogModelImpl.FILEGROUPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.STEPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.ISUPDATE_COLUMN_BITMASK |
			DossierFileLogModelImpl.COUNT__COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F_S_U_C = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F_S_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @return the matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_U_C(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_)
		throws SystemException {
		return findByD_F_S_U_C(dossierId, fileGroupId, stepId, isUpdate,
			count_, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @return the range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_U_C(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_, int start,
		int end) throws SystemException {
		return findByD_F_S_U_C(dossierId, fileGroupId, stepId, isUpdate,
			count_, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_U_C(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U_C;
			finderArgs = new Object[] {
					dossierId, fileGroupId, stepId, isUpdate, count_
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S_U_C;
			finderArgs = new Object[] {
					dossierId, fileGroupId, stepId, isUpdate, count_,
					
					start, end, orderByComparator
				};
		}

		List<DossierFileLog> list = (List<DossierFileLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DossierFileLog dossierFileLog : list) {
				if ((dossierId != dossierFileLog.getDossierId()) ||
						(fileGroupId != dossierFileLog.getFileGroupId()) ||
						(stepId != dossierFileLog.getStepId()) ||
						(isUpdate != dossierFileLog.getIsUpdate()) ||
						(count_ != dossierFileLog.getCount_())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(7 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_U_C_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_STEPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_ISUPDATE_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_COUNT__2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				qPos.add(isUpdate);

				qPos.add(count_);

				if (!pagination) {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<DossierFileLog>(list);
				}
				else {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_U_C_First(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_,
		OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_U_C_First(dossierId,
				fileGroupId, stepId, isUpdate, count_, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(", isUpdate=");
		msg.append(isUpdate);

		msg.append(", count_=");
		msg.append(count_);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_U_C_First(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_,
		OrderByComparator orderByComparator) throws SystemException {
		List<DossierFileLog> list = findByD_F_S_U_C(dossierId, fileGroupId,
				stepId, isUpdate, count_, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_U_C_Last(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_,
		OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_U_C_Last(dossierId,
				fileGroupId, stepId, isUpdate, count_, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(", isUpdate=");
		msg.append(isUpdate);

		msg.append(", count_=");
		msg.append(count_);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_U_C_Last(long dossierId,
		long fileGroupId, long stepId, boolean isUpdate, int count_,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByD_F_S_U_C(dossierId, fileGroupId, stepId, isUpdate,
				count_);

		if (count == 0) {
			return null;
		}

		List<DossierFileLog> list = findByD_F_S_U_C(dossierId, fileGroupId,
				stepId, isUpdate, count_, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dossier file logs before and after the current dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierFileLogId the primary key of the current dossier file log
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog[] findByD_F_S_U_C_PrevAndNext(long dossierFileLogId,
		long dossierId, long fileGroupId, long stepId, boolean isUpdate,
		int count_, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = findByPrimaryKey(dossierFileLogId);

		Session session = null;

		try {
			session = openSession();

			DossierFileLog[] array = new DossierFileLogImpl[3];

			array[0] = getByD_F_S_U_C_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, isUpdate, count_,
					orderByComparator, true);

			array[1] = dossierFileLog;

			array[2] = getByD_F_S_U_C_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, isUpdate, count_,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DossierFileLog getByD_F_S_U_C_PrevAndNext(Session session,
		DossierFileLog dossierFileLog, long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, int count_,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

		query.append(_FINDER_COLUMN_D_F_S_U_C_DOSSIERID_2);

		query.append(_FINDER_COLUMN_D_F_S_U_C_FILEGROUPID_2);

		query.append(_FINDER_COLUMN_D_F_S_U_C_STEPID_2);

		query.append(_FINDER_COLUMN_D_F_S_U_C_ISUPDATE_2);

		query.append(_FINDER_COLUMN_D_F_S_U_C_COUNT__2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(dossierId);

		qPos.add(fileGroupId);

		qPos.add(stepId);

		qPos.add(isUpdate);

		qPos.add(count_);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dossierFileLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DossierFileLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63; from the database.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByD_F_S_U_C(long dossierId, long fileGroupId,
		long stepId, boolean isUpdate, int count_) throws SystemException {
		for (DossierFileLog dossierFileLog : findByD_F_S_U_C(dossierId,
				fileGroupId, stepId, isUpdate, count_, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(dossierFileLog);
		}
	}

	/**
	 * Returns the number of dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and isUpdate = &#63; and count_ = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param isUpdate the is update
	 * @param count_ the count_
	 * @return the number of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByD_F_S_U_C(long dossierId, long fileGroupId, long stepId,
		boolean isUpdate, int count_) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_D_F_S_U_C;

		Object[] finderArgs = new Object[] {
				dossierId, fileGroupId, stepId, isUpdate, count_
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_U_C_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_STEPID_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_ISUPDATE_2);

			query.append(_FINDER_COLUMN_D_F_S_U_C_COUNT__2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				qPos.add(isUpdate);

				qPos.add(count_);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_D_F_S_U_C_DOSSIERID_2 = "dossierFileLog.dossierId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_C_FILEGROUPID_2 = "dossierFileLog.fileGroupId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_C_STEPID_2 = "dossierFileLog.stepId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_C_ISUPDATE_2 = "dossierFileLog.isUpdate = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_U_C_COUNT__2 = "dossierFileLog.count_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S_A = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByD_F_S_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_A =
		new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED,
			DossierFileLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByD_F_S_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			DossierFileLogModelImpl.DOSSIERID_COLUMN_BITMASK |
			DossierFileLogModelImpl.FILEGROUPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.STEPID_COLUMN_BITMASK |
			DossierFileLogModelImpl.ACTIONCODE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_D_F_S_A = new FinderPath(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_F_S_A",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @return the matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_A(long dossierId, long fileGroupId,
		long stepId, int actionCode) throws SystemException {
		return findByD_F_S_A(dossierId, fileGroupId, stepId, actionCode,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @return the range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_A(long dossierId, long fileGroupId,
		long stepId, int actionCode, int start, int end)
		throws SystemException {
		return findByD_F_S_A(dossierId, fileGroupId, stepId, actionCode, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findByD_F_S_A(long dossierId, long fileGroupId,
		long stepId, int actionCode, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_A;
			finderArgs = new Object[] { dossierId, fileGroupId, stepId, actionCode };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_D_F_S_A;
			finderArgs = new Object[] {
					dossierId, fileGroupId, stepId, actionCode,
					
					start, end, orderByComparator
				};
		}

		List<DossierFileLog> list = (List<DossierFileLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DossierFileLog dossierFileLog : list) {
				if ((dossierId != dossierFileLog.getDossierId()) ||
						(fileGroupId != dossierFileLog.getFileGroupId()) ||
						(stepId != dossierFileLog.getStepId()) ||
						(actionCode != dossierFileLog.getActionCode())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_A_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_A_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_A_STEPID_2);

			query.append(_FINDER_COLUMN_D_F_S_A_ACTIONCODE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				qPos.add(actionCode);

				if (!pagination) {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<DossierFileLog>(list);
				}
				else {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_A_First(long dossierId, long fileGroupId,
		long stepId, int actionCode, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_A_First(dossierId,
				fileGroupId, stepId, actionCode, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(", actionCode=");
		msg.append(actionCode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the first dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_A_First(long dossierId,
		long fileGroupId, long stepId, int actionCode,
		OrderByComparator orderByComparator) throws SystemException {
		List<DossierFileLog> list = findByD_F_S_A(dossierId, fileGroupId,
				stepId, actionCode, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByD_F_S_A_Last(long dossierId, long fileGroupId,
		long stepId, int actionCode, OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByD_F_S_A_Last(dossierId,
				fileGroupId, stepId, actionCode, orderByComparator);

		if (dossierFileLog != null) {
			return dossierFileLog;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("dossierId=");
		msg.append(dossierId);

		msg.append(", fileGroupId=");
		msg.append(fileGroupId);

		msg.append(", stepId=");
		msg.append(stepId);

		msg.append(", actionCode=");
		msg.append(actionCode);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchDossierFileLogException(msg.toString());
	}

	/**
	 * Returns the last dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dossier file log, or <code>null</code> if a matching dossier file log could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByD_F_S_A_Last(long dossierId, long fileGroupId,
		long stepId, int actionCode, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByD_F_S_A(dossierId, fileGroupId, stepId, actionCode);

		if (count == 0) {
			return null;
		}

		List<DossierFileLog> list = findByD_F_S_A(dossierId, fileGroupId,
				stepId, actionCode, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dossier file logs before and after the current dossier file log in the ordered set where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierFileLogId the primary key of the current dossier file log
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog[] findByD_F_S_A_PrevAndNext(long dossierFileLogId,
		long dossierId, long fileGroupId, long stepId, int actionCode,
		OrderByComparator orderByComparator)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = findByPrimaryKey(dossierFileLogId);

		Session session = null;

		try {
			session = openSession();

			DossierFileLog[] array = new DossierFileLogImpl[3];

			array[0] = getByD_F_S_A_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, actionCode,
					orderByComparator, true);

			array[1] = dossierFileLog;

			array[2] = getByD_F_S_A_PrevAndNext(session, dossierFileLog,
					dossierId, fileGroupId, stepId, actionCode,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DossierFileLog getByD_F_S_A_PrevAndNext(Session session,
		DossierFileLog dossierFileLog, long dossierId, long fileGroupId,
		long stepId, int actionCode, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DOSSIERFILELOG_WHERE);

		query.append(_FINDER_COLUMN_D_F_S_A_DOSSIERID_2);

		query.append(_FINDER_COLUMN_D_F_S_A_FILEGROUPID_2);

		query.append(_FINDER_COLUMN_D_F_S_A_STEPID_2);

		query.append(_FINDER_COLUMN_D_F_S_A_ACTIONCODE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DossierFileLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(dossierId);

		qPos.add(fileGroupId);

		qPos.add(stepId);

		qPos.add(actionCode);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dossierFileLog);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DossierFileLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63; from the database.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByD_F_S_A(long dossierId, long fileGroupId, long stepId,
		int actionCode) throws SystemException {
		for (DossierFileLog dossierFileLog : findByD_F_S_A(dossierId,
				fileGroupId, stepId, actionCode, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(dossierFileLog);
		}
	}

	/**
	 * Returns the number of dossier file logs where dossierId = &#63; and fileGroupId = &#63; and stepId = &#63; and actionCode = &#63;.
	 *
	 * @param dossierId the dossier ID
	 * @param fileGroupId the file group ID
	 * @param stepId the step ID
	 * @param actionCode the action code
	 * @return the number of matching dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByD_F_S_A(long dossierId, long fileGroupId, long stepId,
		int actionCode) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_D_F_S_A;

		Object[] finderArgs = new Object[] {
				dossierId, fileGroupId, stepId, actionCode
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DOSSIERFILELOG_WHERE);

			query.append(_FINDER_COLUMN_D_F_S_A_DOSSIERID_2);

			query.append(_FINDER_COLUMN_D_F_S_A_FILEGROUPID_2);

			query.append(_FINDER_COLUMN_D_F_S_A_STEPID_2);

			query.append(_FINDER_COLUMN_D_F_S_A_ACTIONCODE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(dossierId);

				qPos.add(fileGroupId);

				qPos.add(stepId);

				qPos.add(actionCode);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_D_F_S_A_DOSSIERID_2 = "dossierFileLog.dossierId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_A_FILEGROUPID_2 = "dossierFileLog.fileGroupId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_A_STEPID_2 = "dossierFileLog.stepId = ? AND ";
	private static final String _FINDER_COLUMN_D_F_S_A_ACTIONCODE_2 = "dossierFileLog.actionCode = ?";

	public DossierFileLogPersistenceImpl() {
		setModelClass(DossierFileLog.class);
	}

	/**
	 * Caches the dossier file log in the entity cache if it is enabled.
	 *
	 * @param dossierFileLog the dossier file log
	 */
	@Override
	public void cacheResult(DossierFileLog dossierFileLog) {
		EntityCacheUtil.putResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogImpl.class, dossierFileLog.getPrimaryKey(),
			dossierFileLog);

		dossierFileLog.resetOriginalValues();
	}

	/**
	 * Caches the dossier file logs in the entity cache if it is enabled.
	 *
	 * @param dossierFileLogs the dossier file logs
	 */
	@Override
	public void cacheResult(List<DossierFileLog> dossierFileLogs) {
		for (DossierFileLog dossierFileLog : dossierFileLogs) {
			if (EntityCacheUtil.getResult(
						DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
						DossierFileLogImpl.class, dossierFileLog.getPrimaryKey()) == null) {
				cacheResult(dossierFileLog);
			}
			else {
				dossierFileLog.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all dossier file logs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DossierFileLogImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DossierFileLogImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the dossier file log.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DossierFileLog dossierFileLog) {
		EntityCacheUtil.removeResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogImpl.class, dossierFileLog.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<DossierFileLog> dossierFileLogs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DossierFileLog dossierFileLog : dossierFileLogs) {
			EntityCacheUtil.removeResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
				DossierFileLogImpl.class, dossierFileLog.getPrimaryKey());
		}
	}

	/**
	 * Creates a new dossier file log with the primary key. Does not add the dossier file log to the database.
	 *
	 * @param dossierFileLogId the primary key for the new dossier file log
	 * @return the new dossier file log
	 */
	@Override
	public DossierFileLog create(long dossierFileLogId) {
		DossierFileLog dossierFileLog = new DossierFileLogImpl();

		dossierFileLog.setNew(true);
		dossierFileLog.setPrimaryKey(dossierFileLogId);

		return dossierFileLog;
	}

	/**
	 * Removes the dossier file log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dossierFileLogId the primary key of the dossier file log
	 * @return the dossier file log that was removed
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog remove(long dossierFileLogId)
		throws NoSuchDossierFileLogException, SystemException {
		return remove((Serializable)dossierFileLogId);
	}

	/**
	 * Removes the dossier file log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dossier file log
	 * @return the dossier file log that was removed
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog remove(Serializable primaryKey)
		throws NoSuchDossierFileLogException, SystemException {
		Session session = null;

		try {
			session = openSession();

			DossierFileLog dossierFileLog = (DossierFileLog)session.get(DossierFileLogImpl.class,
					primaryKey);

			if (dossierFileLog == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDossierFileLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dossierFileLog);
		}
		catch (NoSuchDossierFileLogException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected DossierFileLog removeImpl(DossierFileLog dossierFileLog)
		throws SystemException {
		dossierFileLog = toUnwrappedModel(dossierFileLog);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dossierFileLog)) {
				dossierFileLog = (DossierFileLog)session.get(DossierFileLogImpl.class,
						dossierFileLog.getPrimaryKeyObj());
			}

			if (dossierFileLog != null) {
				session.delete(dossierFileLog);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dossierFileLog != null) {
			clearCache(dossierFileLog);
		}

		return dossierFileLog;
	}

	@Override
	public DossierFileLog updateImpl(
		org.opencps.dossiermgt.model.DossierFileLog dossierFileLog)
		throws SystemException {
		dossierFileLog = toUnwrappedModel(dossierFileLog);

		boolean isNew = dossierFileLog.isNew();

		DossierFileLogModelImpl dossierFileLogModelImpl = (DossierFileLogModelImpl)dossierFileLog;

		Session session = null;

		try {
			session = openSession();

			if (dossierFileLog.isNew()) {
				session.save(dossierFileLog);

				dossierFileLog.setNew(false);
			}
			else {
				session.merge(dossierFileLog);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DossierFileLogModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dossierFileLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dossierFileLogModelImpl.getOriginalDossierId(),
						dossierFileLogModelImpl.getOriginalFileGroupId(),
						dossierFileLogModelImpl.getOriginalStepId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S,
					args);

				args = new Object[] {
						dossierFileLogModelImpl.getDossierId(),
						dossierFileLogModelImpl.getFileGroupId(),
						dossierFileLogModelImpl.getStepId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S,
					args);
			}

			if ((dossierFileLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dossierFileLogModelImpl.getOriginalDossierId(),
						dossierFileLogModelImpl.getOriginalFileGroupId(),
						dossierFileLogModelImpl.getOriginalStepId(),
						dossierFileLogModelImpl.getOriginalIsUpdate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U,
					args);

				args = new Object[] {
						dossierFileLogModelImpl.getDossierId(),
						dossierFileLogModelImpl.getFileGroupId(),
						dossierFileLogModelImpl.getStepId(),
						dossierFileLogModelImpl.getIsUpdate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S_U, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U,
					args);
			}

			if ((dossierFileLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dossierFileLogModelImpl.getOriginalDossierId(),
						dossierFileLogModelImpl.getOriginalFileGroupId(),
						dossierFileLogModelImpl.getOriginalStepId(),
						dossierFileLogModelImpl.getOriginalIsUpdate(),
						dossierFileLogModelImpl.getOriginalCount_()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S_U_C,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U_C,
					args);

				args = new Object[] {
						dossierFileLogModelImpl.getDossierId(),
						dossierFileLogModelImpl.getFileGroupId(),
						dossierFileLogModelImpl.getStepId(),
						dossierFileLogModelImpl.getIsUpdate(),
						dossierFileLogModelImpl.getCount_()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S_U_C,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_U_C,
					args);
			}

			if ((dossierFileLogModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dossierFileLogModelImpl.getOriginalDossierId(),
						dossierFileLogModelImpl.getOriginalFileGroupId(),
						dossierFileLogModelImpl.getOriginalStepId(),
						dossierFileLogModelImpl.getOriginalActionCode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_A,
					args);

				args = new Object[] {
						dossierFileLogModelImpl.getDossierId(),
						dossierFileLogModelImpl.getFileGroupId(),
						dossierFileLogModelImpl.getStepId(),
						dossierFileLogModelImpl.getActionCode()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_D_F_S_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_D_F_S_A,
					args);
			}
		}

		EntityCacheUtil.putResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
			DossierFileLogImpl.class, dossierFileLog.getPrimaryKey(),
			dossierFileLog);

		return dossierFileLog;
	}

	protected DossierFileLog toUnwrappedModel(DossierFileLog dossierFileLog) {
		if (dossierFileLog instanceof DossierFileLogImpl) {
			return dossierFileLog;
		}

		DossierFileLogImpl dossierFileLogImpl = new DossierFileLogImpl();

		dossierFileLogImpl.setNew(dossierFileLog.isNew());
		dossierFileLogImpl.setPrimaryKey(dossierFileLog.getPrimaryKey());

		dossierFileLogImpl.setDossierFileLogId(dossierFileLog.getDossierFileLogId());
		dossierFileLogImpl.setModifiedDate(dossierFileLog.getModifiedDate());
		dossierFileLogImpl.setUserId(dossierFileLog.getUserId());
		dossierFileLogImpl.setUserName(dossierFileLog.getUserName());
		dossierFileLogImpl.setDossierId(dossierFileLog.getDossierId());
		dossierFileLogImpl.setFileGroupId(dossierFileLog.getFileGroupId());
		dossierFileLogImpl.setStepId(dossierFileLog.getStepId());
		dossierFileLogImpl.setIsUpdate(dossierFileLog.isIsUpdate());
		dossierFileLogImpl.setFileName(dossierFileLog.getFileName());
		dossierFileLogImpl.setFileVersion(dossierFileLog.getFileVersion());
		dossierFileLogImpl.setFileLink(dossierFileLog.getFileLink());
		dossierFileLogImpl.setActionCode(dossierFileLog.getActionCode());
		dossierFileLogImpl.setCount_(dossierFileLog.getCount_());
		dossierFileLogImpl.setOId(dossierFileLog.getOId());
		dossierFileLogImpl.setFileEntryId(dossierFileLog.getFileEntryId());

		return dossierFileLogImpl;
	}

	/**
	 * Returns the dossier file log with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the dossier file log
	 * @return the dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDossierFileLogException, SystemException {
		DossierFileLog dossierFileLog = fetchByPrimaryKey(primaryKey);

		if (dossierFileLog == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDossierFileLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dossierFileLog;
	}

	/**
	 * Returns the dossier file log with the primary key or throws a {@link org.opencps.dossiermgt.NoSuchDossierFileLogException} if it could not be found.
	 *
	 * @param dossierFileLogId the primary key of the dossier file log
	 * @return the dossier file log
	 * @throws org.opencps.dossiermgt.NoSuchDossierFileLogException if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog findByPrimaryKey(long dossierFileLogId)
		throws NoSuchDossierFileLogException, SystemException {
		return findByPrimaryKey((Serializable)dossierFileLogId);
	}

	/**
	 * Returns the dossier file log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dossier file log
	 * @return the dossier file log, or <code>null</code> if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		DossierFileLog dossierFileLog = (DossierFileLog)EntityCacheUtil.getResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
				DossierFileLogImpl.class, primaryKey);

		if (dossierFileLog == _nullDossierFileLog) {
			return null;
		}

		if (dossierFileLog == null) {
			Session session = null;

			try {
				session = openSession();

				dossierFileLog = (DossierFileLog)session.get(DossierFileLogImpl.class,
						primaryKey);

				if (dossierFileLog != null) {
					cacheResult(dossierFileLog);
				}
				else {
					EntityCacheUtil.putResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
						DossierFileLogImpl.class, primaryKey,
						_nullDossierFileLog);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(DossierFileLogModelImpl.ENTITY_CACHE_ENABLED,
					DossierFileLogImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dossierFileLog;
	}

	/**
	 * Returns the dossier file log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dossierFileLogId the primary key of the dossier file log
	 * @return the dossier file log, or <code>null</code> if a dossier file log with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public DossierFileLog fetchByPrimaryKey(long dossierFileLogId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)dossierFileLogId);
	}

	/**
	 * Returns all the dossier file logs.
	 *
	 * @return the dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dossier file logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @return the range of dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dossier file logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.dossiermgt.model.impl.DossierFileLogModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of dossier file logs
	 * @param end the upper bound of the range of dossier file logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<DossierFileLog> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DossierFileLog> list = (List<DossierFileLog>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DOSSIERFILELOG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DOSSIERFILELOG;

				if (pagination) {
					sql = sql.concat(DossierFileLogModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<DossierFileLog>(list);
				}
				else {
					list = (List<DossierFileLog>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the dossier file logs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (DossierFileLog dossierFileLog : findAll()) {
			remove(dossierFileLog);
		}
	}

	/**
	 * Returns the number of dossier file logs.
	 *
	 * @return the number of dossier file logs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DOSSIERFILELOG);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the dossier file log persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.org.opencps.dossiermgt.model.DossierFileLog")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<DossierFileLog>> listenersList = new ArrayList<ModelListener<DossierFileLog>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<DossierFileLog>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DossierFileLogImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_DOSSIERFILELOG = "SELECT dossierFileLog FROM DossierFileLog dossierFileLog";
	private static final String _SQL_SELECT_DOSSIERFILELOG_WHERE = "SELECT dossierFileLog FROM DossierFileLog dossierFileLog WHERE ";
	private static final String _SQL_COUNT_DOSSIERFILELOG = "SELECT COUNT(dossierFileLog) FROM DossierFileLog dossierFileLog";
	private static final String _SQL_COUNT_DOSSIERFILELOG_WHERE = "SELECT COUNT(dossierFileLog) FROM DossierFileLog dossierFileLog WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dossierFileLog.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DossierFileLog exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DossierFileLog exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(DossierFileLogPersistenceImpl.class);
	private static DossierFileLog _nullDossierFileLog = new DossierFileLogImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DossierFileLog> toCacheModel() {
				return _nullDossierFileLogCacheModel;
			}
		};

	private static CacheModel<DossierFileLog> _nullDossierFileLogCacheModel = new CacheModel<DossierFileLog>() {
			@Override
			public DossierFileLog toEntityModel() {
				return _nullDossierFileLog;
			}
		};
}