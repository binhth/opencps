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

package org.opencps.jms.context;

import java.util.Enumeration;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hornetq.jms.client.HornetQJMSConnectionFactory;
import org.opencps.util.PortletConstants;
import org.opencps.util.PortletUtil;
import org.opencps.util.WebKeys;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author trungnt
 */
public class JMSLocalContext {

	
	/**
	 * @param companyId
	 * @param channelName
	 * @param queueName
	 * @param lookup
	 * @param mom
	 * @throws NamingException
	 * @throws SystemException
	 * @throws Exception
	 */
	public JMSLocalContext(
		long companyId, String channelName, String queueName, String lookup,
		String mom)
		throws NamingException, SystemException, Exception {

		init(companyId, channelName, queueName, lookup, mom);
	}

	/**
	 * @return
	 * @throws JMSException
	 */
	public int countMessageInQueue()
		throws JMSException {

		int count = 0;
		Enumeration<?> messages = _queueBrowser.getEnumeration();
		while (messages.hasMoreElements()) {
			messages.nextElement();
			count++;
		}
		return count;
	}

	/**
	 * @throws JMSException
	 * @throws Exception
	 */
	public void createByteMessage()
		throws Exception {

		BytesMessage bytesMessage = _queueSession.createBytesMessage();
		setBytesMessage(bytesMessage);
	}

	/**
	 * @throws Exception
	 */
	public void createMapMessage()
		throws Exception {

		MapMessage mapMessage = _queueSession.createMapMessage();
		setMapMessage(mapMessage);
	}

	/**
	 * @throws Exception
	 */
	public void createObjectMessage()
		throws Exception {

		ObjectMessage objectMessage = _queueSession.createObjectMessage();
		setObjectMessage(objectMessage);
	}

	public void createQueueReceiver()
		throws JMSException {

		QueueReceiver queueReceiver = _queueSession.createReceiver(_queue);
		setQueueReceiver(queueReceiver);
	}

	/**
	 * @throws Exception
	 */
	public void createQueue()
		throws Exception {

		Queue queue =
			(Queue) _context.lookup(_properties.getProperty(WebKeys.JMS_DESTINATION));
		setQueue(queue);
	}

	/**
	 * @throws Exception
	 */
	public void createQueueBrowser()
		throws Exception {

		createQueue();
		QueueBrowser queueBrowser = _queueSession.createBrowser(_queue);

		setQueueBrowser(queueBrowser);
	}

	/**
	 * @throws Exception
	 */
	public void createStreamMessage()
		throws Exception {

		StreamMessage streamMessage = _queueSession.createStreamMessage();
		setStreamMessage(streamMessage);
	}

	/**
	 * @throws Exception
	 */
	public void createTextMessage()
		throws Exception {

		TextMessage textMessage = _queueSession.createTextMessage();
		setTextMessage(textMessage);
	}

	/**
	 * @throws JMSException
	 * @throws NamingException
	 */
	public void destroy()
		throws JMSException, NamingException {

		stop();

		if (_connection != null) {
			_connection.close();;
		}

		if (_context != null) {
			_context.close();;
		}

		if (_messageConsumer != null) {
			_messageConsumer.close();
		}

		if (_messageProducer != null) {
			_messageProducer.close();
		}

		if (_queueBrowser != null) {
			_queueBrowser.close();
		}

		if (_queueSession != null) {
			_queueSession.close();
		}

		if (_queueConnectionFactory != null) {
			_queueConnectionFactory = null;
		}

		if (_hornetQJMSConnectionFactory != null) {
			_hornetQJMSConnectionFactory.close();

		}

		_bytesMessage = null;

		_objectMessage = null;

		_streamMessage = null;

		_textMessage = null;

		_properties = null;

		_queue = null;

		_mapMessage = null;

	}

	/**
	 * @param companyId
	 * @param channelName
	 * @throws SystemException
	 * @throws NamingException
	 * @throws JMSException
	 */
	protected void init(
		long companyId, String channelName, String queueName, String lookup,
		String mom)
		throws SystemException, NamingException, JMSException {

		Properties properties =
			PortletUtil.getJMSContextProperties(
				companyId, StringPool.BLANK, false, channelName, queueName,
				lookup, mom);

		Context context = new InitialContext();

		HornetQJMSConnectionFactory factory =
			(HornetQJMSConnectionFactory) context.lookup(PortletConstants.JMS_CONNECTION_FACTORY);

		/*
		 * QueueConnectionFactory queueConnectionFactory =
		 * (QueueConnectionFactory)
		 * context.lookup(PortletConstants.JMS_CONNECTION_FACTORY);
		 */

		/*
		 * QueueConnection connection =
		 * queueConnectionFactory.createQueueConnection();
		 */

		QueueConnection connection = factory.createQueueConnection();

		QueueSession session =
			connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

		Queue queue =
			(Queue) context.lookup(properties.getProperty(WebKeys.JMS_DESTINATION));
		QueueBrowser queueBrowser = session.createBrowser(queue);

		this.setQueueSession(session);
		this.setContext(context);
		// this.setQueueConnectionFactory(queueConnectionFactory);
		this.setProperties(properties);
		this.setConnection(connection);
		this.setQueue(queue);
		this.setQueueBrowser(queueBrowser);
		this.setHornetQJMSConnectionFactory(factory);
	}

	/**
	 * @throws JMSException
	 */
	public void start()
		throws JMSException {

		if (_connection != null) {
			_connection.start();
		}

	}

	/**
	 * @throws JMSException
	 */
	protected void stop()
		throws JMSException {

		if (_connection != null) {
			_connection.stop();
		}

	}

	public BytesMessage getBytesMessage() {

		return _bytesMessage;
	}

	public Connection getConnection() {

		return _connection;
	}

	public Context getContext() {

		return _context;
	}

	public MapMessage getMapMessage() {

		return _mapMessage;
	}

	public MessageConsumer getMessageConsumer() {

		return _messageConsumer;
	}

	public MessageProducer getMessageProducer() {

		return _messageProducer;
	}

	public ObjectMessage getObjectMessage() {

		return _objectMessage;
	}

	public Properties getProperties() {

		return _properties;
	}

	public Queue getQueue() {

		return _queue;
	}

	public QueueBrowser getQueueBrowser() {

		return _queueBrowser;
	}

	public void setBytesMessage(BytesMessage bytesMessage) {

		this._bytesMessage = bytesMessage;
	}

	public void setConnection(QueueConnection connection) {

		this._connection = connection;
	}

	public void setContext(Context context) {

		this._context = context;
	}

	public void setMapMessage(MapMessage mapMessage) {

		this._mapMessage = mapMessage;
	}

	public void setMessageConsumer(MessageConsumer messageConsumer) {

		this._messageConsumer = messageConsumer;
	}

	public void setMessageProducer(MessageProducer messageProducer) {

		this._messageProducer = messageProducer;
	}

	public void setObjectMessage(ObjectMessage objectMessage) {

		this._objectMessage = objectMessage;
	}

	public void setProperties(Properties properties) {

		this._properties = properties;
	}

	public void setQueue(Queue queue) {

		this._queue = queue;
	}

	public void setQueueBrowser(QueueBrowser queueBrowser) {

		this._queueBrowser = queueBrowser;
	}

	public StreamMessage getStreamMessage() {

		return _streamMessage;
	}

	public void setStreamMessage(StreamMessage streamMessage) {

		this._streamMessage = streamMessage;
	}

	public TextMessage getTextMessage() {

		return _textMessage;
	}

	public void setTextMessage(TextMessage textMessage) {

		this._textMessage = textMessage;
	}

	public QueueConnectionFactory getQueueConnectionFactory() {

		return _queueConnectionFactory;
	}

	public void setQueueConnectionFactory(
		QueueConnectionFactory queueConnectionFactory) {

		this._queueConnectionFactory = queueConnectionFactory;
	}

	public QueueSession getQueueSession() {

		return _queueSession;
	}

	public void setQueueSession(QueueSession queueSession) {

		this._queueSession = queueSession;
	}

	public QueueReceiver getQueueReceiver() {

		return _queueReceiver;
	}

	public void setQueueReceiver(QueueReceiver queueReceiver) {

		this._queueReceiver = queueReceiver;
	}

	public HornetQJMSConnectionFactory getHornetQJMSConnectionFactory() {

		return _hornetQJMSConnectionFactory;
	}

	public void setHornetQJMSConnectionFactory(
		HornetQJMSConnectionFactory hornetQJMSConnectionFactory) {

		this._hornetQJMSConnectionFactory = hornetQJMSConnectionFactory;
	}

	private QueueConnectionFactory _queueConnectionFactory;

	private QueueReceiver _queueReceiver;

	private QueueSession _queueSession;

	private BytesMessage _bytesMessage;

	private QueueConnection _connection;

	private Context _context;

	private MapMessage _mapMessage;

	private MessageConsumer _messageConsumer;

	private MessageProducer _messageProducer;

	private ObjectMessage _objectMessage;

	private Properties _properties;

	private Queue _queue;

	private QueueBrowser _queueBrowser;

	private StreamMessage _streamMessage;

	private TextMessage _textMessage;

	private HornetQJMSConnectionFactory _hornetQJMSConnectionFactory;
}
