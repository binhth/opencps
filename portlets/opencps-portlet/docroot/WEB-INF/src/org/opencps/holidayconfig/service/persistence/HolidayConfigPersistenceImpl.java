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

package org.opencps.holidayconfig.service.persistence;

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
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.opencps.holidayconfig.NoSuchHolidayConfigException;
import org.opencps.holidayconfig.model.HolidayConfig;
import org.opencps.holidayconfig.model.impl.HolidayConfigImpl;
import org.opencps.holidayconfig.model.impl.HolidayConfigModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The persistence implementation for the holiday config service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author nhanhoang
 * @see HolidayConfigPersistence
 * @see HolidayConfigUtil
 * @generated
 */
public class HolidayConfigPersistenceImpl extends BasePersistenceImpl<HolidayConfig>
	implements HolidayConfigPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link HolidayConfigUtil} to access the holiday config persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = HolidayConfigImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigModelImpl.FINDER_CACHE_ENABLED,
			HolidayConfigImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigModelImpl.FINDER_CACHE_ENABLED,
			HolidayConfigImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_HOLIDAY = new FinderPath(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigModelImpl.FINDER_CACHE_ENABLED,
			HolidayConfigImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByholiday",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_HOLIDAY =
		new FinderPath(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigModelImpl.FINDER_CACHE_ENABLED,
			HolidayConfigImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByholiday", new String[] { Date.class.getName() },
			HolidayConfigModelImpl.HOLIDAY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_HOLIDAY = new FinderPath(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByholiday",
			new String[] { Date.class.getName() });

	/**
	 * Returns all the holiday configs where holiday = &#63;.
	 *
	 * @param holiday the holiday
	 * @return the matching holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfig> findByholiday(Date holiday)
		throws SystemException {
		return findByholiday(holiday, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the holiday configs where holiday = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.holidayconfig.model.impl.HolidayConfigModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param holiday the holiday
	 * @param start the lower bound of the range of holiday configs
	 * @param end the upper bound of the range of holiday configs (not inclusive)
	 * @return the range of matching holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfig> findByholiday(Date holiday, int start, int end)
		throws SystemException {
		return findByholiday(holiday, start, end, null);
	}

	/**
	 * Returns an ordered range of all the holiday configs where holiday = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.holidayconfig.model.impl.HolidayConfigModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param holiday the holiday
	 * @param start the lower bound of the range of holiday configs
	 * @param end the upper bound of the range of holiday configs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfig> findByholiday(Date holiday, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_HOLIDAY;
			finderArgs = new Object[] { holiday };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_HOLIDAY;
			finderArgs = new Object[] { holiday, start, end, orderByComparator };
		}

		List<HolidayConfig> list = (List<HolidayConfig>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (HolidayConfig holidayConfig : list) {
				if (!Validator.equals(holiday, holidayConfig.getHoliday())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_HOLIDAYCONFIG_WHERE);

			boolean bindHoliday = false;

			if (holiday == null) {
				query.append(_FINDER_COLUMN_HOLIDAY_HOLIDAY_1);
			}
			else {
				bindHoliday = true;

				query.append(_FINDER_COLUMN_HOLIDAY_HOLIDAY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(HolidayConfigModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindHoliday) {
					qPos.add(CalendarUtil.getTimestamp(holiday));
				}

				if (!pagination) {
					list = (List<HolidayConfig>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<HolidayConfig>(list);
				}
				else {
					list = (List<HolidayConfig>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first holiday config in the ordered set where holiday = &#63;.
	 *
	 * @param holiday the holiday
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching holiday config
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a matching holiday config could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig findByholiday_First(Date holiday,
		OrderByComparator orderByComparator)
		throws NoSuchHolidayConfigException, SystemException {
		HolidayConfig holidayConfig = fetchByholiday_First(holiday,
				orderByComparator);

		if (holidayConfig != null) {
			return holidayConfig;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("holiday=");
		msg.append(holiday);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchHolidayConfigException(msg.toString());
	}

	/**
	 * Returns the first holiday config in the ordered set where holiday = &#63;.
	 *
	 * @param holiday the holiday
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching holiday config, or <code>null</code> if a matching holiday config could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig fetchByholiday_First(Date holiday,
		OrderByComparator orderByComparator) throws SystemException {
		List<HolidayConfig> list = findByholiday(holiday, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last holiday config in the ordered set where holiday = &#63;.
	 *
	 * @param holiday the holiday
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching holiday config
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a matching holiday config could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig findByholiday_Last(Date holiday,
		OrderByComparator orderByComparator)
		throws NoSuchHolidayConfigException, SystemException {
		HolidayConfig holidayConfig = fetchByholiday_Last(holiday,
				orderByComparator);

		if (holidayConfig != null) {
			return holidayConfig;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("holiday=");
		msg.append(holiday);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchHolidayConfigException(msg.toString());
	}

	/**
	 * Returns the last holiday config in the ordered set where holiday = &#63;.
	 *
	 * @param holiday the holiday
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching holiday config, or <code>null</code> if a matching holiday config could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig fetchByholiday_Last(Date holiday,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByholiday(holiday);

		if (count == 0) {
			return null;
		}

		List<HolidayConfig> list = findByholiday(holiday, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the holiday configs before and after the current holiday config in the ordered set where holiday = &#63;.
	 *
	 * @param holidayId the primary key of the current holiday config
	 * @param holiday the holiday
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next holiday config
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig[] findByholiday_PrevAndNext(long holidayId,
		Date holiday, OrderByComparator orderByComparator)
		throws NoSuchHolidayConfigException, SystemException {
		HolidayConfig holidayConfig = findByPrimaryKey(holidayId);

		Session session = null;

		try {
			session = openSession();

			HolidayConfig[] array = new HolidayConfigImpl[3];

			array[0] = getByholiday_PrevAndNext(session, holidayConfig,
					holiday, orderByComparator, true);

			array[1] = holidayConfig;

			array[2] = getByholiday_PrevAndNext(session, holidayConfig,
					holiday, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected HolidayConfig getByholiday_PrevAndNext(Session session,
		HolidayConfig holidayConfig, Date holiday,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_HOLIDAYCONFIG_WHERE);

		boolean bindHoliday = false;

		if (holiday == null) {
			query.append(_FINDER_COLUMN_HOLIDAY_HOLIDAY_1);
		}
		else {
			bindHoliday = true;

			query.append(_FINDER_COLUMN_HOLIDAY_HOLIDAY_2);
		}

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
			query.append(HolidayConfigModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindHoliday) {
			qPos.add(CalendarUtil.getTimestamp(holiday));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(holidayConfig);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<HolidayConfig> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the holiday configs where holiday = &#63; from the database.
	 *
	 * @param holiday the holiday
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByholiday(Date holiday) throws SystemException {
		for (HolidayConfig holidayConfig : findByholiday(holiday,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(holidayConfig);
		}
	}

	/**
	 * Returns the number of holiday configs where holiday = &#63;.
	 *
	 * @param holiday the holiday
	 * @return the number of matching holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByholiday(Date holiday) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_HOLIDAY;

		Object[] finderArgs = new Object[] { holiday };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_HOLIDAYCONFIG_WHERE);

			boolean bindHoliday = false;

			if (holiday == null) {
				query.append(_FINDER_COLUMN_HOLIDAY_HOLIDAY_1);
			}
			else {
				bindHoliday = true;

				query.append(_FINDER_COLUMN_HOLIDAY_HOLIDAY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindHoliday) {
					qPos.add(CalendarUtil.getTimestamp(holiday));
				}

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

	private static final String _FINDER_COLUMN_HOLIDAY_HOLIDAY_1 = "holidayConfig.holiday IS NULL";
	private static final String _FINDER_COLUMN_HOLIDAY_HOLIDAY_2 = "holidayConfig.holiday = ?";

	public HolidayConfigPersistenceImpl() {
		setModelClass(HolidayConfig.class);
	}

	/**
	 * Caches the holiday config in the entity cache if it is enabled.
	 *
	 * @param holidayConfig the holiday config
	 */
	@Override
	public void cacheResult(HolidayConfig holidayConfig) {
		EntityCacheUtil.putResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigImpl.class, holidayConfig.getPrimaryKey(),
			holidayConfig);

		holidayConfig.resetOriginalValues();
	}

	/**
	 * Caches the holiday configs in the entity cache if it is enabled.
	 *
	 * @param holidayConfigs the holiday configs
	 */
	@Override
	public void cacheResult(List<HolidayConfig> holidayConfigs) {
		for (HolidayConfig holidayConfig : holidayConfigs) {
			if (EntityCacheUtil.getResult(
						HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
						HolidayConfigImpl.class, holidayConfig.getPrimaryKey()) == null) {
				cacheResult(holidayConfig);
			}
			else {
				holidayConfig.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all holiday configs.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(HolidayConfigImpl.class.getName());
		}

		EntityCacheUtil.clearCache(HolidayConfigImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the holiday config.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(HolidayConfig holidayConfig) {
		EntityCacheUtil.removeResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigImpl.class, holidayConfig.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<HolidayConfig> holidayConfigs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (HolidayConfig holidayConfig : holidayConfigs) {
			EntityCacheUtil.removeResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
				HolidayConfigImpl.class, holidayConfig.getPrimaryKey());
		}
	}

	/**
	 * Creates a new holiday config with the primary key. Does not add the holiday config to the database.
	 *
	 * @param holidayId the primary key for the new holiday config
	 * @return the new holiday config
	 */
	@Override
	public HolidayConfig create(long holidayId) {
		HolidayConfig holidayConfig = new HolidayConfigImpl();

		holidayConfig.setNew(true);
		holidayConfig.setPrimaryKey(holidayId);

		return holidayConfig;
	}

	/**
	 * Removes the holiday config with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param holidayId the primary key of the holiday config
	 * @return the holiday config that was removed
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig remove(long holidayId)
		throws NoSuchHolidayConfigException, SystemException {
		return remove((Serializable)holidayId);
	}

	/**
	 * Removes the holiday config with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the holiday config
	 * @return the holiday config that was removed
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig remove(Serializable primaryKey)
		throws NoSuchHolidayConfigException, SystemException {
		Session session = null;

		try {
			session = openSession();

			HolidayConfig holidayConfig = (HolidayConfig)session.get(HolidayConfigImpl.class,
					primaryKey);

			if (holidayConfig == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchHolidayConfigException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(holidayConfig);
		}
		catch (NoSuchHolidayConfigException nsee) {
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
	protected HolidayConfig removeImpl(HolidayConfig holidayConfig)
		throws SystemException {
		holidayConfig = toUnwrappedModel(holidayConfig);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(holidayConfig)) {
				holidayConfig = (HolidayConfig)session.get(HolidayConfigImpl.class,
						holidayConfig.getPrimaryKeyObj());
			}

			if (holidayConfig != null) {
				session.delete(holidayConfig);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (holidayConfig != null) {
			clearCache(holidayConfig);
		}

		return holidayConfig;
	}

	@Override
	public HolidayConfig updateImpl(
		org.opencps.holidayconfig.model.HolidayConfig holidayConfig)
		throws SystemException {
		holidayConfig = toUnwrappedModel(holidayConfig);

		boolean isNew = holidayConfig.isNew();

		HolidayConfigModelImpl holidayConfigModelImpl = (HolidayConfigModelImpl)holidayConfig;

		Session session = null;

		try {
			session = openSession();

			if (holidayConfig.isNew()) {
				session.save(holidayConfig);

				holidayConfig.setNew(false);
			}
			else {
				session.merge(holidayConfig);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !HolidayConfigModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((holidayConfigModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_HOLIDAY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						holidayConfigModelImpl.getOriginalHoliday()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_HOLIDAY, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_HOLIDAY,
					args);

				args = new Object[] { holidayConfigModelImpl.getHoliday() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_HOLIDAY, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_HOLIDAY,
					args);
			}
		}

		EntityCacheUtil.putResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigImpl.class, holidayConfig.getPrimaryKey(),
			holidayConfig);

		return holidayConfig;
	}

	protected HolidayConfig toUnwrappedModel(HolidayConfig holidayConfig) {
		if (holidayConfig instanceof HolidayConfigImpl) {
			return holidayConfig;
		}

		HolidayConfigImpl holidayConfigImpl = new HolidayConfigImpl();

		holidayConfigImpl.setNew(holidayConfig.isNew());
		holidayConfigImpl.setPrimaryKey(holidayConfig.getPrimaryKey());

		holidayConfigImpl.setHolidayId(holidayConfig.getHolidayId());
		holidayConfigImpl.setHoliday(holidayConfig.getHoliday());
<<<<<<< HEAD
		holidayConfigImpl.setDescription(holidayConfig.getDescription());
		holidayConfigImpl.setCreatedDate(holidayConfig.getCreatedDate());
		holidayConfigImpl.setModifiedDate(holidayConfig.getModifiedDate());
		holidayConfigImpl.setRemove(holidayConfig.getRemove());
=======
		holidayConfigImpl.setCompanyId(holidayConfig.getCompanyId());
		holidayConfigImpl.setGroupId(holidayConfig.getGroupId());
		holidayConfigImpl.setUserId(holidayConfig.getUserId());
		holidayConfigImpl.setDescription(holidayConfig.getDescription());
		holidayConfigImpl.setCreatedDate(holidayConfig.getCreatedDate());
		holidayConfigImpl.setModifiedDate(holidayConfig.getModifiedDate());
		holidayConfigImpl.setStatus(holidayConfig.getStatus());
>>>>>>> refs/heads/congbogiaothong-#2016-Feature-Dasboard

		return holidayConfigImpl;
	}

	/**
	 * Returns the holiday config with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the holiday config
	 * @return the holiday config
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig findByPrimaryKey(Serializable primaryKey)
		throws NoSuchHolidayConfigException, SystemException {
		HolidayConfig holidayConfig = fetchByPrimaryKey(primaryKey);

		if (holidayConfig == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchHolidayConfigException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return holidayConfig;
	}

	/**
	 * Returns the holiday config with the primary key or throws a {@link org.opencps.holidayconfig.NoSuchHolidayConfigException} if it could not be found.
	 *
	 * @param holidayId the primary key of the holiday config
	 * @return the holiday config
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigException if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig findByPrimaryKey(long holidayId)
		throws NoSuchHolidayConfigException, SystemException {
		return findByPrimaryKey((Serializable)holidayId);
	}

	/**
	 * Returns the holiday config with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the holiday config
	 * @return the holiday config, or <code>null</code> if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		HolidayConfig holidayConfig = (HolidayConfig)EntityCacheUtil.getResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
				HolidayConfigImpl.class, primaryKey);

		if (holidayConfig == _nullHolidayConfig) {
			return null;
		}

		if (holidayConfig == null) {
			Session session = null;

			try {
				session = openSession();

				holidayConfig = (HolidayConfig)session.get(HolidayConfigImpl.class,
						primaryKey);

				if (holidayConfig != null) {
					cacheResult(holidayConfig);
				}
				else {
					EntityCacheUtil.putResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
						HolidayConfigImpl.class, primaryKey, _nullHolidayConfig);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(HolidayConfigModelImpl.ENTITY_CACHE_ENABLED,
					HolidayConfigImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return holidayConfig;
	}

	/**
	 * Returns the holiday config with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param holidayId the primary key of the holiday config
	 * @return the holiday config, or <code>null</code> if a holiday config with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfig fetchByPrimaryKey(long holidayId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)holidayId);
	}

	/**
	 * Returns all the holiday configs.
	 *
	 * @return the holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfig> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the holiday configs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.holidayconfig.model.impl.HolidayConfigModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of holiday configs
	 * @param end the upper bound of the range of holiday configs (not inclusive)
	 * @return the range of holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfig> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the holiday configs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.holidayconfig.model.impl.HolidayConfigModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of holiday configs
	 * @param end the upper bound of the range of holiday configs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of holiday configs
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfig> findAll(int start, int end,
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

		List<HolidayConfig> list = (List<HolidayConfig>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_HOLIDAYCONFIG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_HOLIDAYCONFIG;

				if (pagination) {
					sql = sql.concat(HolidayConfigModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<HolidayConfig>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<HolidayConfig>(list);
				}
				else {
					list = (List<HolidayConfig>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the holiday configs from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (HolidayConfig holidayConfig : findAll()) {
			remove(holidayConfig);
		}
	}

	/**
	 * Returns the number of holiday configs.
	 *
	 * @return the number of holiday configs
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

				Query q = session.createQuery(_SQL_COUNT_HOLIDAYCONFIG);

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
	 * Initializes the holiday config persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.org.opencps.holidayconfig.model.HolidayConfig")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<HolidayConfig>> listenersList = new ArrayList<ModelListener<HolidayConfig>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<HolidayConfig>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(HolidayConfigImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_HOLIDAYCONFIG = "SELECT holidayConfig FROM HolidayConfig holidayConfig";
	private static final String _SQL_SELECT_HOLIDAYCONFIG_WHERE = "SELECT holidayConfig FROM HolidayConfig holidayConfig WHERE ";
	private static final String _SQL_COUNT_HOLIDAYCONFIG = "SELECT COUNT(holidayConfig) FROM HolidayConfig holidayConfig";
	private static final String _SQL_COUNT_HOLIDAYCONFIG_WHERE = "SELECT COUNT(holidayConfig) FROM HolidayConfig holidayConfig WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "holidayConfig.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No HolidayConfig exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No HolidayConfig exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(HolidayConfigPersistenceImpl.class);
	private static HolidayConfig _nullHolidayConfig = new HolidayConfigImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<HolidayConfig> toCacheModel() {
				return _nullHolidayConfigCacheModel;
			}
		};

	private static CacheModel<HolidayConfig> _nullHolidayConfigCacheModel = new CacheModel<HolidayConfig>() {
			@Override
			public HolidayConfig toEntityModel() {
				return _nullHolidayConfig;
			}
		};
}
