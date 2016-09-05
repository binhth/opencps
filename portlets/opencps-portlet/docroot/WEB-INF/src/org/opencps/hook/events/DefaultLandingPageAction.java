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
package org.opencps.hook.events;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opencps.util.PortletPropsValues;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.util.PortalUtil;


/**
 * @author Michael Young
 * @author dunglt
 *
 */
public class DefaultLandingPageAction extends Action{

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.events.Action#run(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			doRun(request, response);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
		
	}
	
	protected void doRun(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute(org.opencps.util.WebKeys.USER);
		
		long companyId = PortalUtil.getCompanyId(request);
		
		String path = StringPool.BLANK;
		
		for(UserGroup userGroup : user.getUserGroups()) {
			if(userGroup.getName().equalsIgnoreCase(PortletPropsValues.USERMGT_USERGROUP_NAME_CITIZEN)) {
				 path = com.liferay.portal.kernel.util.PrefsPropsUtil.getString(
					companyId, "default.landing.page.path.citizen");
			} else if(userGroup.getName().equalsIgnoreCase(PortletPropsValues.USERMGT_USERGROUP_NAME_BUSINESS)) {
				 path = com.liferay.portal.kernel.util.PrefsPropsUtil.getString(
					companyId, "default.landing.page.path.business");
			} else if(userGroup.getName().equalsIgnoreCase(PortletPropsValues.USERMGT_USERGROUP_NAME_EMPLOYEE)) {
				 path = com.liferay.portal.kernel.util.PrefsPropsUtil.getString(
					companyId, "default.landing.page.path.employee");
			} else {
				path = com.liferay.portal.kernel.util.PrefsPropsUtil.getString(
					companyId, PropsKeys.DEFAULT_LANDING_PAGE_PATH);
			}
		}
	
		if (_log.isInfoEnabled()) {
			_log.info("Pathhh" +
				PropsKeys.DEFAULT_LANDING_PAGE_PATH + StringPool.EQUAL + path);
		}
	
		if (Validator.isNull(path)) {
			return;
		}
	
		if (path.contains("${liferay:screenName}") ||
			path.contains("${liferay:userId}")) {
	
			if (user == null) {
				return;
			}
	
			path = StringUtil.replace(
				path,
				new String[] {"${liferay:screenName}", "${liferay:userId}"},
				new String[] {
					HtmlUtil.escapeURL(user.getScreenName()),
					String.valueOf(user.getUserId())
				});
		}
	
		LastPath lastPath = new LastPath(StringPool.BLANK, path);
	
		session.setAttribute(org.opencps.util.WebKeys.LAST_PATH, lastPath);
	}
	
	private static Log _log = LogFactoryUtil.getLog(
		DefaultLandingPageAction.class);
	
}
