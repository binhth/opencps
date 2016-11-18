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

package org.opencps.paymentmgt.service.persistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import org.opencps.paymentmgt.NoSuchPaymentGateConfigException;
import org.opencps.paymentmgt.model.PaymentGateConfig;
import org.opencps.paymentmgt.model.impl.PaymentGateConfigImpl;
import org.opencps.paymentmgt.model.impl.PaymentGateConfigModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the PaymentGate configuration service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author trungdk
 * @see PaymentGateConfigPersistence
 * @see PaymentGateConfigUtil
 * @generated
 */
public class PaymentGateConfigPersistenceImpl extends BasePersistenceImpl<PaymentGateConfig>
	implements PaymentGateConfigPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PaymentGateConfigUtil} to access the PaymentGate configuration persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PaymentGateConfigImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
			PaymentGateConfigModelImpl.FINDER_CACHE_ENABLED,
			PaymentGateConfigImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
			PaymentGateConfigModelImpl.FINDER_CACHE_ENABLED,
			PaymentGateConfigImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
			PaymentGateConfigModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public PaymentGateConfigPersistenceImpl() {
		setModelClass(PaymentGateConfig.class);
	}

	/**
	 * Caches the PaymentGate configuration in the entity cache if it is enabled.
	 *
	 * @param paymentGateConfig the PaymentGate configuration
	 */
	@Override
	public void cacheResult(PaymentGateConfig paymentGateConfig) {
		EntityCacheUtil.putResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
			PaymentGateConfigImpl.class, paymentGateConfig.getPrimaryKey(),
			paymentGateConfig);

		paymentGateConfig.resetOriginalValues();
	}

	/**
	 * Caches the PaymentGate configurations in the entity cache if it is enabled.
	 *
	 * @param paymentGateConfigs the PaymentGate configurations
	 */
	@Override
	public void cacheResult(List<PaymentGateConfig> paymentGateConfigs) {
		for (PaymentGateConfig paymentGateConfig : paymentGateConfigs) {
			if (EntityCacheUtil.getResult(
						PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
						PaymentGateConfigImpl.class,
						paymentGateConfig.getPrimaryKey()) == null) {
				cacheResult(paymentGateConfig);
			}
			else {
				paymentGateConfig.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all PaymentGate configurations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PaymentGateConfigImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PaymentGateConfigImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the PaymentGate configuration.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PaymentGateConfig paymentGateConfig) {
		EntityCacheUtil.removeResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
			PaymentGateConfigImpl.class, paymentGateConfig.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PaymentGateConfig> paymentGateConfigs) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PaymentGateConfig paymentGateConfig : paymentGateConfigs) {
			EntityCacheUtil.removeResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
				PaymentGateConfigImpl.class, paymentGateConfig.getPrimaryKey());
		}
	}

	/**
	 * Creates a new PaymentGate configuration with the primary key. Does not add the PaymentGate configuration to the database.
	 *
	 * @param paymentGateId the primary key for the new PaymentGate configuration
	 * @return the new PaymentGate configuration
	 */
	@Override
	public PaymentGateConfig create(long paymentGateId) {
		PaymentGateConfig paymentGateConfig = new PaymentGateConfigImpl();

		paymentGateConfig.setNew(true);
		paymentGateConfig.setPrimaryKey(paymentGateId);

		return paymentGateConfig;
	}

	/**
	 * Removes the PaymentGate configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param paymentGateId the primary key of the PaymentGate configuration
	 * @return the PaymentGate configuration that was removed
	 * @throws org.opencps.paymentmgt.NoSuchPaymentGateConfigException if a PaymentGate configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentGateConfig remove(long paymentGateId)
		throws NoSuchPaymentGateConfigException, SystemException {
		return remove((Serializable)paymentGateId);
	}

	/**
	 * Removes the PaymentGate configuration with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the PaymentGate configuration
	 * @return the PaymentGate configuration that was removed
	 * @throws org.opencps.paymentmgt.NoSuchPaymentGateConfigException if a PaymentGate configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentGateConfig remove(Serializable primaryKey)
		throws NoSuchPaymentGateConfigException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PaymentGateConfig paymentGateConfig = (PaymentGateConfig)session.get(PaymentGateConfigImpl.class,
					primaryKey);

			if (paymentGateConfig == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPaymentGateConfigException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(paymentGateConfig);
		}
		catch (NoSuchPaymentGateConfigException nsee) {
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
	protected PaymentGateConfig removeImpl(PaymentGateConfig paymentGateConfig)
		throws SystemException {
		paymentGateConfig = toUnwrappedModel(paymentGateConfig);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(paymentGateConfig)) {
				paymentGateConfig = (PaymentGateConfig)session.get(PaymentGateConfigImpl.class,
						paymentGateConfig.getPrimaryKeyObj());
			}

			if (paymentGateConfig != null) {
				session.delete(paymentGateConfig);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (paymentGateConfig != null) {
			clearCache(paymentGateConfig);
		}

		return paymentGateConfig;
	}

	@Override
	public PaymentGateConfig updateImpl(
		org.opencps.paymentmgt.model.PaymentGateConfig paymentGateConfig)
		throws SystemException {
		paymentGateConfig = toUnwrappedModel(paymentGateConfig);

		boolean isNew = paymentGateConfig.isNew();

		Session session = null;

		try {
			session = openSession();

			if (paymentGateConfig.isNew()) {
				session.save(paymentGateConfig);

				paymentGateConfig.setNew(false);
			}
			else {
				session.merge(paymentGateConfig);
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

		EntityCacheUtil.putResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
			PaymentGateConfigImpl.class, paymentGateConfig.getPrimaryKey(),
			paymentGateConfig);

		return paymentGateConfig;
	}

	protected PaymentGateConfig toUnwrappedModel(
		PaymentGateConfig paymentGateConfig) {
		if (paymentGateConfig instanceof PaymentGateConfigImpl) {
			return paymentGateConfig;
		}

		PaymentGateConfigImpl paymentGateConfigImpl = new PaymentGateConfigImpl();

		paymentGateConfigImpl.setNew(paymentGateConfig.isNew());
		paymentGateConfigImpl.setPrimaryKey(paymentGateConfig.getPrimaryKey());

		paymentGateConfigImpl.setPaymentGateId(paymentGateConfig.getPaymentGateId());
		paymentGateConfigImpl.setPaymentGateName(paymentGateConfig.getPaymentGateName());

		return paymentGateConfigImpl;
	}

	/**
	 * Returns the PaymentGate configuration with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the PaymentGate configuration
	 * @return the PaymentGate configuration
	 * @throws org.opencps.paymentmgt.NoSuchPaymentGateConfigException if a PaymentGate configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentGateConfig findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPaymentGateConfigException, SystemException {
		PaymentGateConfig paymentGateConfig = fetchByPrimaryKey(primaryKey);

		if (paymentGateConfig == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPaymentGateConfigException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return paymentGateConfig;
	}

	/**
	 * Returns the PaymentGate configuration with the primary key or throws a {@link org.opencps.paymentmgt.NoSuchPaymentGateConfigException} if it could not be found.
	 *
	 * @param paymentGateId the primary key of the PaymentGate configuration
	 * @return the PaymentGate configuration
	 * @throws org.opencps.paymentmgt.NoSuchPaymentGateConfigException if a PaymentGate configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentGateConfig findByPrimaryKey(long paymentGateId)
		throws NoSuchPaymentGateConfigException, SystemException {
		return findByPrimaryKey((Serializable)paymentGateId);
	}

	/**
	 * Returns the PaymentGate configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the PaymentGate configuration
	 * @return the PaymentGate configuration, or <code>null</code> if a PaymentGate configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentGateConfig fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PaymentGateConfig paymentGateConfig = (PaymentGateConfig)EntityCacheUtil.getResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
				PaymentGateConfigImpl.class, primaryKey);

		if (paymentGateConfig == _nullPaymentGateConfig) {
			return null;
		}

		if (paymentGateConfig == null) {
			Session session = null;

			try {
				session = openSession();

				paymentGateConfig = (PaymentGateConfig)session.get(PaymentGateConfigImpl.class,
						primaryKey);

				if (paymentGateConfig != null) {
					cacheResult(paymentGateConfig);
				}
				else {
					EntityCacheUtil.putResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
						PaymentGateConfigImpl.class, primaryKey,
						_nullPaymentGateConfig);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PaymentGateConfigModelImpl.ENTITY_CACHE_ENABLED,
					PaymentGateConfigImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return paymentGateConfig;
	}

	/**
	 * Returns the PaymentGate configuration with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param paymentGateId the primary key of the PaymentGate configuration
	 * @return the PaymentGate configuration, or <code>null</code> if a PaymentGate configuration with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentGateConfig fetchByPrimaryKey(long paymentGateId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)paymentGateId);
	}

	/**
	 * Returns all the PaymentGate configurations.
	 *
	 * @return the PaymentGate configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PaymentGateConfig> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the PaymentGate configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.paymentmgt.model.impl.PaymentGateConfigModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of PaymentGate configurations
	 * @param end the upper bound of the range of PaymentGate configurations (not inclusive)
	 * @return the range of PaymentGate configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PaymentGateConfig> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the PaymentGate configurations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.paymentmgt.model.impl.PaymentGateConfigModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of PaymentGate configurations
	 * @param end the upper bound of the range of PaymentGate configurations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of PaymentGate configurations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PaymentGateConfig> findAll(int start, int end,
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

		List<PaymentGateConfig> list = (List<PaymentGateConfig>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PAYMENTGATECONFIG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PAYMENTGATECONFIG;

				if (pagination) {
					sql = sql.concat(PaymentGateConfigModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PaymentGateConfig>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PaymentGateConfig>(list);
				}
				else {
					list = (List<PaymentGateConfig>)QueryUtil.list(q,
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
	 * Removes all the PaymentGate configurations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (PaymentGateConfig paymentGateConfig : findAll()) {
			remove(paymentGateConfig);
		}
	}

	/**
	 * Returns the number of PaymentGate configurations.
	 *
	 * @return the number of PaymentGate configurations
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

				Query q = session.createQuery(_SQL_COUNT_PAYMENTGATECONFIG);

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
	 * Initializes the PaymentGate configuration persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.org.opencps.paymentmgt.model.PaymentGateConfig")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PaymentGateConfig>> listenersList = new ArrayList<ModelListener<PaymentGateConfig>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PaymentGateConfig>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PaymentGateConfigImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PAYMENTGATECONFIG = "SELECT paymentGateConfig FROM PaymentGateConfig paymentGateConfig";
	private static final String _SQL_COUNT_PAYMENTGATECONFIG = "SELECT COUNT(paymentGateConfig) FROM PaymentGateConfig paymentGateConfig";
	private static final String _ORDER_BY_ENTITY_ALIAS = "paymentGateConfig.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PaymentGateConfig exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PaymentGateConfigPersistenceImpl.class);
	private static PaymentGateConfig _nullPaymentGateConfig = new PaymentGateConfigImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PaymentGateConfig> toCacheModel() {
				return _nullPaymentGateConfigCacheModel;
			}
		};

	private static CacheModel<PaymentGateConfig> _nullPaymentGateConfigCacheModel =
		new CacheModel<PaymentGateConfig>() {
			@Override
			public PaymentGateConfig toEntityModel() {
				return _nullPaymentGateConfig;
			}
		};
}