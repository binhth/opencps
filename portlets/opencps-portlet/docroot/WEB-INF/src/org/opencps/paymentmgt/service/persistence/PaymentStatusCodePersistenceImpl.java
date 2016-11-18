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

import org.opencps.paymentmgt.NoSuchPaymentStatusCodeException;
import org.opencps.paymentmgt.model.PaymentStatusCode;
import org.opencps.paymentmgt.model.impl.PaymentStatusCodeImpl;
import org.opencps.paymentmgt.model.impl.PaymentStatusCodeModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the PaymentGate StatusCode service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author trungdk
 * @see PaymentStatusCodePersistence
 * @see PaymentStatusCodeUtil
 * @generated
 */
public class PaymentStatusCodePersistenceImpl extends BasePersistenceImpl<PaymentStatusCode>
	implements PaymentStatusCodePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PaymentStatusCodeUtil} to access the PaymentGate StatusCode persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PaymentStatusCodeImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
			PaymentStatusCodeModelImpl.FINDER_CACHE_ENABLED,
			PaymentStatusCodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
			PaymentStatusCodeModelImpl.FINDER_CACHE_ENABLED,
			PaymentStatusCodeImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
			PaymentStatusCodeModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public PaymentStatusCodePersistenceImpl() {
		setModelClass(PaymentStatusCode.class);
	}

	/**
	 * Caches the PaymentGate StatusCode in the entity cache if it is enabled.
	 *
	 * @param paymentStatusCode the PaymentGate StatusCode
	 */
	@Override
	public void cacheResult(PaymentStatusCode paymentStatusCode) {
		EntityCacheUtil.putResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
			PaymentStatusCodeImpl.class, paymentStatusCode.getPrimaryKey(),
			paymentStatusCode);

		paymentStatusCode.resetOriginalValues();
	}

	/**
	 * Caches the PaymentGate StatusCodes in the entity cache if it is enabled.
	 *
	 * @param paymentStatusCodes the PaymentGate StatusCodes
	 */
	@Override
	public void cacheResult(List<PaymentStatusCode> paymentStatusCodes) {
		for (PaymentStatusCode paymentStatusCode : paymentStatusCodes) {
			if (EntityCacheUtil.getResult(
						PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
						PaymentStatusCodeImpl.class,
						paymentStatusCode.getPrimaryKey()) == null) {
				cacheResult(paymentStatusCode);
			}
			else {
				paymentStatusCode.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all PaymentGate StatusCodes.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PaymentStatusCodeImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PaymentStatusCodeImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the PaymentGate StatusCode.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PaymentStatusCode paymentStatusCode) {
		EntityCacheUtil.removeResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
			PaymentStatusCodeImpl.class, paymentStatusCode.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PaymentStatusCode> paymentStatusCodes) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PaymentStatusCode paymentStatusCode : paymentStatusCodes) {
			EntityCacheUtil.removeResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
				PaymentStatusCodeImpl.class, paymentStatusCode.getPrimaryKey());
		}
	}

	/**
	 * Creates a new PaymentGate StatusCode with the primary key. Does not add the PaymentGate StatusCode to the database.
	 *
	 * @param paymentStatusCodeId the primary key for the new PaymentGate StatusCode
	 * @return the new PaymentGate StatusCode
	 */
	@Override
	public PaymentStatusCode create(long paymentStatusCodeId) {
		PaymentStatusCode paymentStatusCode = new PaymentStatusCodeImpl();

		paymentStatusCode.setNew(true);
		paymentStatusCode.setPrimaryKey(paymentStatusCodeId);

		return paymentStatusCode;
	}

	/**
	 * Removes the PaymentGate StatusCode with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param paymentStatusCodeId the primary key of the PaymentGate StatusCode
	 * @return the PaymentGate StatusCode that was removed
	 * @throws org.opencps.paymentmgt.NoSuchPaymentStatusCodeException if a PaymentGate StatusCode with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentStatusCode remove(long paymentStatusCodeId)
		throws NoSuchPaymentStatusCodeException, SystemException {
		return remove((Serializable)paymentStatusCodeId);
	}

	/**
	 * Removes the PaymentGate StatusCode with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the PaymentGate StatusCode
	 * @return the PaymentGate StatusCode that was removed
	 * @throws org.opencps.paymentmgt.NoSuchPaymentStatusCodeException if a PaymentGate StatusCode with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentStatusCode remove(Serializable primaryKey)
		throws NoSuchPaymentStatusCodeException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PaymentStatusCode paymentStatusCode = (PaymentStatusCode)session.get(PaymentStatusCodeImpl.class,
					primaryKey);

			if (paymentStatusCode == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPaymentStatusCodeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(paymentStatusCode);
		}
		catch (NoSuchPaymentStatusCodeException nsee) {
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
	protected PaymentStatusCode removeImpl(PaymentStatusCode paymentStatusCode)
		throws SystemException {
		paymentStatusCode = toUnwrappedModel(paymentStatusCode);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(paymentStatusCode)) {
				paymentStatusCode = (PaymentStatusCode)session.get(PaymentStatusCodeImpl.class,
						paymentStatusCode.getPrimaryKeyObj());
			}

			if (paymentStatusCode != null) {
				session.delete(paymentStatusCode);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (paymentStatusCode != null) {
			clearCache(paymentStatusCode);
		}

		return paymentStatusCode;
	}

	@Override
	public PaymentStatusCode updateImpl(
		org.opencps.paymentmgt.model.PaymentStatusCode paymentStatusCode)
		throws SystemException {
		paymentStatusCode = toUnwrappedModel(paymentStatusCode);

		boolean isNew = paymentStatusCode.isNew();

		Session session = null;

		try {
			session = openSession();

			if (paymentStatusCode.isNew()) {
				session.save(paymentStatusCode);

				paymentStatusCode.setNew(false);
			}
			else {
				session.merge(paymentStatusCode);
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

		EntityCacheUtil.putResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
			PaymentStatusCodeImpl.class, paymentStatusCode.getPrimaryKey(),
			paymentStatusCode);

		return paymentStatusCode;
	}

	protected PaymentStatusCode toUnwrappedModel(
		PaymentStatusCode paymentStatusCode) {
		if (paymentStatusCode instanceof PaymentStatusCodeImpl) {
			return paymentStatusCode;
		}

		PaymentStatusCodeImpl paymentStatusCodeImpl = new PaymentStatusCodeImpl();

		paymentStatusCodeImpl.setNew(paymentStatusCode.isNew());
		paymentStatusCodeImpl.setPrimaryKey(paymentStatusCode.getPrimaryKey());

		paymentStatusCodeImpl.setPaymentStatusCodeId(paymentStatusCode.getPaymentStatusCodeId());
		paymentStatusCodeImpl.setStatusCode(paymentStatusCode.getStatusCode());
		paymentStatusCodeImpl.setStatusCodeMean(paymentStatusCode.getStatusCodeMean());
		paymentStatusCodeImpl.setDescripton(paymentStatusCode.getDescripton());
		paymentStatusCodeImpl.setPaymentGateId(paymentStatusCode.getPaymentGateId());

		return paymentStatusCodeImpl;
	}

	/**
	 * Returns the PaymentGate StatusCode with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the PaymentGate StatusCode
	 * @return the PaymentGate StatusCode
	 * @throws org.opencps.paymentmgt.NoSuchPaymentStatusCodeException if a PaymentGate StatusCode with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentStatusCode findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPaymentStatusCodeException, SystemException {
		PaymentStatusCode paymentStatusCode = fetchByPrimaryKey(primaryKey);

		if (paymentStatusCode == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPaymentStatusCodeException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return paymentStatusCode;
	}

	/**
	 * Returns the PaymentGate StatusCode with the primary key or throws a {@link org.opencps.paymentmgt.NoSuchPaymentStatusCodeException} if it could not be found.
	 *
	 * @param paymentStatusCodeId the primary key of the PaymentGate StatusCode
	 * @return the PaymentGate StatusCode
	 * @throws org.opencps.paymentmgt.NoSuchPaymentStatusCodeException if a PaymentGate StatusCode with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentStatusCode findByPrimaryKey(long paymentStatusCodeId)
		throws NoSuchPaymentStatusCodeException, SystemException {
		return findByPrimaryKey((Serializable)paymentStatusCodeId);
	}

	/**
	 * Returns the PaymentGate StatusCode with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the PaymentGate StatusCode
	 * @return the PaymentGate StatusCode, or <code>null</code> if a PaymentGate StatusCode with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentStatusCode fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PaymentStatusCode paymentStatusCode = (PaymentStatusCode)EntityCacheUtil.getResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
				PaymentStatusCodeImpl.class, primaryKey);

		if (paymentStatusCode == _nullPaymentStatusCode) {
			return null;
		}

		if (paymentStatusCode == null) {
			Session session = null;

			try {
				session = openSession();

				paymentStatusCode = (PaymentStatusCode)session.get(PaymentStatusCodeImpl.class,
						primaryKey);

				if (paymentStatusCode != null) {
					cacheResult(paymentStatusCode);
				}
				else {
					EntityCacheUtil.putResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
						PaymentStatusCodeImpl.class, primaryKey,
						_nullPaymentStatusCode);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PaymentStatusCodeModelImpl.ENTITY_CACHE_ENABLED,
					PaymentStatusCodeImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return paymentStatusCode;
	}

	/**
	 * Returns the PaymentGate StatusCode with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param paymentStatusCodeId the primary key of the PaymentGate StatusCode
	 * @return the PaymentGate StatusCode, or <code>null</code> if a PaymentGate StatusCode with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PaymentStatusCode fetchByPrimaryKey(long paymentStatusCodeId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)paymentStatusCodeId);
	}

	/**
	 * Returns all the PaymentGate StatusCodes.
	 *
	 * @return the PaymentGate StatusCodes
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PaymentStatusCode> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the PaymentGate StatusCodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.paymentmgt.model.impl.PaymentStatusCodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of PaymentGate StatusCodes
	 * @param end the upper bound of the range of PaymentGate StatusCodes (not inclusive)
	 * @return the range of PaymentGate StatusCodes
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PaymentStatusCode> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the PaymentGate StatusCodes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link org.opencps.paymentmgt.model.impl.PaymentStatusCodeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of PaymentGate StatusCodes
	 * @param end the upper bound of the range of PaymentGate StatusCodes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of PaymentGate StatusCodes
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PaymentStatusCode> findAll(int start, int end,
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

		List<PaymentStatusCode> list = (List<PaymentStatusCode>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PAYMENTSTATUSCODE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PAYMENTSTATUSCODE;

				if (pagination) {
					sql = sql.concat(PaymentStatusCodeModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PaymentStatusCode>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PaymentStatusCode>(list);
				}
				else {
					list = (List<PaymentStatusCode>)QueryUtil.list(q,
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
	 * Removes all the PaymentGate StatusCodes from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (PaymentStatusCode paymentStatusCode : findAll()) {
			remove(paymentStatusCode);
		}
	}

	/**
	 * Returns the number of PaymentGate StatusCodes.
	 *
	 * @return the number of PaymentGate StatusCodes
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

				Query q = session.createQuery(_SQL_COUNT_PAYMENTSTATUSCODE);

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
	 * Initializes the PaymentGate StatusCode persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.org.opencps.paymentmgt.model.PaymentStatusCode")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PaymentStatusCode>> listenersList = new ArrayList<ModelListener<PaymentStatusCode>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PaymentStatusCode>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PaymentStatusCodeImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PAYMENTSTATUSCODE = "SELECT paymentStatusCode FROM PaymentStatusCode paymentStatusCode";
	private static final String _SQL_COUNT_PAYMENTSTATUSCODE = "SELECT COUNT(paymentStatusCode) FROM PaymentStatusCode paymentStatusCode";
	private static final String _ORDER_BY_ENTITY_ALIAS = "paymentStatusCode.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PaymentStatusCode exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PaymentStatusCodePersistenceImpl.class);
	private static PaymentStatusCode _nullPaymentStatusCode = new PaymentStatusCodeImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PaymentStatusCode> toCacheModel() {
				return _nullPaymentStatusCodeCacheModel;
			}
		};

	private static CacheModel<PaymentStatusCode> _nullPaymentStatusCodeCacheModel =
		new CacheModel<PaymentStatusCode>() {
			@Override
			public PaymentStatusCode toEntityModel() {
				return _nullPaymentStatusCode;
			}
		};
}