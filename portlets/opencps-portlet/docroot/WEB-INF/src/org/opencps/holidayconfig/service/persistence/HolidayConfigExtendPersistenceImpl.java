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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.opencps.holidayconfig.NoSuchHolidayConfigExtendException;
import org.opencps.holidayconfig.model.HolidayConfigExtend;
import org.opencps.holidayconfig.model.impl.HolidayConfigExtendImpl;
import org.opencps.holidayconfig.model.impl.HolidayConfigExtendModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the holiday config extend service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author nhanhoang
 * @see HolidayConfigExtendPersistence
 * @see HolidayConfigExtendUtil
 * @generated
 */
public class HolidayConfigExtendPersistenceImpl extends BasePersistenceImpl<HolidayConfigExtend>
	implements HolidayConfigExtendPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link HolidayConfigExtendUtil} to access the holiday config extend persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = HolidayConfigExtendImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigExtendModelImpl.FINDER_CACHE_ENABLED,
			HolidayConfigExtendImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigExtendModelImpl.FINDER_CACHE_ENABLED,
			HolidayConfigExtendImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigExtendModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public HolidayConfigExtendPersistenceImpl() {
		setModelClass(HolidayConfigExtend.class);
	}

	/**
	 * Caches the holiday config extend in the entity cache if it is enabled.
	 *
	 * @param holidayConfigExtend the holiday config extend
	 */
	@Override
	public void cacheResult(HolidayConfigExtend holidayConfigExtend) {
		EntityCacheUtil.putResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigExtendImpl.class, holidayConfigExtend.getPrimaryKey(),
			holidayConfigExtend);

		holidayConfigExtend.resetOriginalValues();
	}

	/**
	 * Caches the holiday config extends in the entity cache if it is enabled.
	 *
	 * @param holidayConfigExtends the holiday config extends
	 */
	@Override
	public void cacheResult(List<HolidayConfigExtend> holidayConfigExtends) {
		for (HolidayConfigExtend holidayConfigExtend : holidayConfigExtends) {
			if (EntityCacheUtil.getResult(
						HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
						HolidayConfigExtendImpl.class,
						holidayConfigExtend.getPrimaryKey()) == null) {
				cacheResult(holidayConfigExtend);
			}
			else {
				holidayConfigExtend.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all holiday config extends.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(HolidayConfigExtendImpl.class.getName());
		}

		EntityCacheUtil.clearCache(HolidayConfigExtendImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the holiday config extend.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(HolidayConfigExtend holidayConfigExtend) {
		EntityCacheUtil.removeResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigExtendImpl.class, holidayConfigExtend.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<HolidayConfigExtend> holidayConfigExtends) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (HolidayConfigExtend holidayConfigExtend : holidayConfigExtends) {
			EntityCacheUtil.removeResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
				HolidayConfigExtendImpl.class,
				holidayConfigExtend.getPrimaryKey());
		}
	}

	/**
	 * Creates a new holiday config extend with the primary key. Does not add the holiday config extend to the database.
	 *
	 * @param holidayExtendId the primary key for the new holiday config extend
	 * @return the new holiday config extend
	 */
	@Override
	public HolidayConfigExtend create(long holidayExtendId) {
		HolidayConfigExtend holidayConfigExtend = new HolidayConfigExtendImpl();

		holidayConfigExtend.setNew(true);
		holidayConfigExtend.setPrimaryKey(holidayExtendId);

		return holidayConfigExtend;
	}

	/**
	 * Removes the holiday config extend with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param holidayExtendId the primary key of the holiday config extend
	 * @return the holiday config extend that was removed
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigExtendException if a holiday config extend with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfigExtend remove(long holidayExtendId)
		throws NoSuchHolidayConfigExtendException, SystemException {
		return remove((Serializable)holidayExtendId);
	}

	/**
	 * Removes the holiday config extend with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the holiday config extend
	 * @return the holiday config extend that was removed
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigExtendException if a holiday config extend with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfigExtend remove(Serializable primaryKey)
		throws NoSuchHolidayConfigExtendException, SystemException {
		Session session = null;

		try {
			session = openSession();

			HolidayConfigExtend holidayConfigExtend = (HolidayConfigExtend)session.get(HolidayConfigExtendImpl.class,
					primaryKey);

			if (holidayConfigExtend == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchHolidayConfigExtendException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(holidayConfigExtend);
		}
		catch (NoSuchHolidayConfigExtendException nsee) {
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
	protected HolidayConfigExtend removeImpl(
		HolidayConfigExtend holidayConfigExtend) throws SystemException {
		holidayConfigExtend = toUnwrappedModel(holidayConfigExtend);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(holidayConfigExtend)) {
				holidayConfigExtend = (HolidayConfigExtend)session.get(HolidayConfigExtendImpl.class,
						holidayConfigExtend.getPrimaryKeyObj());
			}

			if (holidayConfigExtend != null) {
				session.delete(holidayConfigExtend);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (holidayConfigExtend != null) {
			clearCache(holidayConfigExtend);
		}

		return holidayConfigExtend;
	}

	@Override
	public HolidayConfigExtend updateImpl(
		org.opencps.holidayconfig.model.HolidayConfigExtend holidayConfigExtend)
		throws SystemException {
		holidayConfigExtend = toUnwrappedModel(holidayConfigExtend);

		boolean isNew = holidayConfigExtend.isNew();

		Session session = null;

		try {
			session = openSession();

			if (holidayConfigExtend.isNew()) {
				session.save(holidayConfigExtend);

				holidayConfigExtend.setNew(false);
			}
			else {
				session.merge(holidayConfigExtend);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
			HolidayConfigExtendImpl.class, holidayConfigExtend.getPrimaryKey(),
			holidayConfigExtend);

		return holidayConfigExtend;
	}

	protected HolidayConfigExtend toUnwrappedModel(
		HolidayConfigExtend holidayConfigExtend) {
		if (holidayConfigExtend instanceof HolidayConfigExtendImpl) {
			return holidayConfigExtend;
		}

		HolidayConfigExtendImpl holidayConfigExtendImpl = new HolidayConfigExtendImpl();

		holidayConfigExtendImpl.setNew(holidayConfigExtend.isNew());
		holidayConfigExtendImpl.setPrimaryKey(holidayConfigExtend.getPrimaryKey());

		holidayConfigExtendImpl.setHolidayExtendId(holidayConfigExtend.getHolidayExtendId());
		holidayConfigExtendImpl.setKey(holidayConfigExtend.getKey());
		holidayConfigExtendImpl.setDescription(holidayConfigExtend.getDescription());
		holidayConfigExtendImpl.setStatus(holidayConfigExtend.getStatus());
		holidayConfigExtendImpl.setCompanyId(holidayConfigExtend.getCompanyId());
		holidayConfigExtendImpl.setGroupId(holidayConfigExtend.getGroupId());
		holidayConfigExtendImpl.setUserId(holidayConfigExtend.getUserId());

		return holidayConfigExtendImpl;
	}

	/**
	 * Returns the holiday config extend with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the holiday config extend
	 * @return the holiday config extend
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigExtendException if a holiday config extend with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfigExtend findByPrimaryKey(Serializable primaryKey)
		throws NoSuchHolidayConfigExtendException, SystemException {
		HolidayConfigExtend holidayConfigExtend = fetchByPrimaryKey(primaryKey);

		if (holidayConfigExtend == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchHolidayConfigExtendException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return holidayConfigExtend;
	}

	/**
	 * Returns the holiday config extend with the primary key or throws a {@link org.opencps.holidayconfig.NoSuchHolidayConfigExtendException} if it could not be found.
	 *
	 * @param holidayExtendId the primary key of the holiday config extend
	 * @return the holiday config extend
	 * @throws org.opencps.holidayconfig.NoSuchHolidayConfigExtendException if a holiday config extend with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfigExtend findByPrimaryKey(long holidayExtendId)
		throws NoSuchHolidayConfigExtendException, SystemException {
		return findByPrimaryKey((Serializable)holidayExtendId);
	}

	/**
	 * Returns the holiday config extend with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the holiday config extend
	 * @return the holiday config extend, or <code>null</code> if a holiday config extend with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfigExtend fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		HolidayConfigExtend holidayConfigExtend = (HolidayConfigExtend)EntityCacheUtil.getResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
				HolidayConfigExtendImpl.class, primaryKey);

		if (holidayConfigExtend == _nullHolidayConfigExtend) {
			return null;
		}

		if (holidayConfigExtend == null) {
			Session session = null;

			try {
				session = openSession();

				holidayConfigExtend = (HolidayConfigExtend)session.get(HolidayConfigExtendImpl.class,
						primaryKey);

				if (holidayConfigExtend != null) {
					cacheResult(holidayConfigExtend);
				}
				else {
					EntityCacheUtil.putResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
						HolidayConfigExtendImpl.class, primaryKey,
						_nullHolidayConfigExtend);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(HolidayConfigExtendModelImpl.ENTITY_CACHE_ENABLED,
					HolidayConfigExtendImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return holidayConfigExtend;
	}

	/**
	 * Returns the holiday config extend with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param holidayExtendId the primary key of the holiday config extend
	 * @return the holiday config extend, or <code>null</code> if a holiday config extend with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public HolidayConfigExtend fetchByPrimaryKey(long holidayExtendId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)holidayExtendId);
	}

	/**
	 * Returns all the holiday config extends.
	 *
	 * @return the holiday config extends
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfigExtend> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the holiday config extends.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.holidayconfig.model.impl.HolidayConfigExtendModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of holiday config extends
	 * @param end the upper bound of the range of holiday config extends (not inclusive)
	 * @return the range of holiday config extends
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfigExtend> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the holiday config extends.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.holidayconfig.model.impl.HolidayConfigExtendModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of holiday config extends
	 * @param end the upper bound of the range of holiday config extends (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of holiday config extends
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<HolidayConfigExtend> findAll(int start, int end,
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

		List<HolidayConfigExtend> list = (List<HolidayConfigExtend>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_HOLIDAYCONFIGEXTEND);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_HOLIDAYCONFIGEXTEND;

				if (pagination) {
					sql = sql.concat(HolidayConfigExtendModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<HolidayConfigExtend>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<HolidayConfigExtend>(list);
				}
				else {
					list = (List<HolidayConfigExtend>)QueryUtil.list(q,
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
	 * Removes all the holiday config extends from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (HolidayConfigExtend holidayConfigExtend : findAll()) {
			remove(holidayConfigExtend);
		}
	}

	/**
	 * Returns the number of holiday config extends.
	 *
	 * @return the number of holiday config extends
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

				Query q = session.createQuery(_SQL_COUNT_HOLIDAYCONFIGEXTEND);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the holiday config extend persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.org.opencps.holidayconfig.model.HolidayConfigExtend")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<HolidayConfigExtend>> listenersList = new ArrayList<ModelListener<HolidayConfigExtend>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<HolidayConfigExtend>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(HolidayConfigExtendImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_HOLIDAYCONFIGEXTEND = "SELECT holidayConfigExtend FROM HolidayConfigExtend holidayConfigExtend";
	private static final String _SQL_COUNT_HOLIDAYCONFIGEXTEND = "SELECT COUNT(holidayConfigExtend) FROM HolidayConfigExtend holidayConfigExtend";
	private static final String _ORDER_BY_ENTITY_ALIAS = "holidayConfigExtend.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No HolidayConfigExtend exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(HolidayConfigExtendPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key"
			});
	private static HolidayConfigExtend _nullHolidayConfigExtend = new HolidayConfigExtendImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<HolidayConfigExtend> toCacheModel() {
				return _nullHolidayConfigExtendCacheModel;
			}
		};

	private static CacheModel<HolidayConfigExtend> _nullHolidayConfigExtendCacheModel =
		new CacheModel<HolidayConfigExtend>() {
			@Override
			public HolidayConfigExtend toEntityModel() {
				return _nullHolidayConfigExtend;
			}
		};
}