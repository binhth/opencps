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

package org.opencps.taglib.ui.inputsearch;

import javax.servlet.http.HttpServletRequest;

import com.liferay.taglib.util.IncludeTag;

/**
 * @author binhth
 */
public class InputSearchTag extends IncludeTag {

	private static final String _PAGE = "/html/taglib/opencps_ui/input_search/page.jsp";

	private boolean autoFocus;

	private String buttonLabel;

	private String cssClass;

	private String id;

	private String name;

	private String placeholder;

	private boolean showButton;

	private String title;

	private boolean useNamespace;
	
	private String keySelect;
	
	private String urlSelect;
	
	private String currentTab;
	
	@Override
	protected void cleanUp() {

		autoFocus = false;
		buttonLabel = "";
		cssClass = "";
		id = "";
		name = "";
		placeholder = "";
		showButton = true;
		title = "";
		useNamespace = false;
		keySelect = "";
		urlSelect = "";
		currentTab = "";
	}


	@Override
	protected String getPage() {

		return _PAGE;
	}

	
	@Override
	protected void setAttributes(HttpServletRequest request) {

		request
			.setAttribute("liferay-ui:input-search:autoFocus",
					autoFocus);
		request
		.setAttribute("liferay-ui:input-search:buttonLabel",
				buttonLabel);
		request
		.setAttribute("liferay-ui:input-search:cssClass",
				cssClass);
		request
		.setAttribute("liferay-ui:input-search:id",
				id);
		request
		.setAttribute("liferay-ui:input-search:name",
				name);
		request
		.setAttribute("liferay-ui:input-search:placeholder",
				placeholder);
		request
		.setAttribute("liferay-ui:input-search:showButton",
				showButton);
		request
		.setAttribute("liferay-ui:input-search:title",
				title);
		request
		.setAttribute("liferay-ui:input-search:useNamespace",
				useNamespace);
		request
		.setAttribute("liferay-ui:input-search:keySelect",
				keySelect);
		request
		.setAttribute("liferay-ui:input-search:urlSelect",
				urlSelect);
		request
		.setAttribute("liferay-ui:input-search:currentTab",
				currentTab);
	}


	public boolean isAutoFocus() {
		return autoFocus;
	}


	public void setAutoFocus(boolean autoFocus) {
		this.autoFocus = autoFocus;
	}


	public String getButtonLabel() {
		return buttonLabel;
	}


	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}


	public String getCssClass() {
		return cssClass;
	}


	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPlaceholder() {
		return placeholder;
	}


	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}


	public boolean isShowButton() {
		return showButton;
	}


	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public boolean isUseNamespace() {
		return useNamespace;
	}


	public void setUseNamespace(boolean useNamespace) {
		this.useNamespace = useNamespace;
	}


	public String getUrlSelect() {
		return urlSelect;
	}


	public void setUrlSelect(String urlSelect) {
		this.urlSelect = urlSelect;
	}


	public String getKeySelect() {
		return keySelect;
	}


	public void setKeySelect(String keySelect) {
		this.keySelect = keySelect;
	}


	public String getCurrentTab() {
		return currentTab;
	}


	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}


}
