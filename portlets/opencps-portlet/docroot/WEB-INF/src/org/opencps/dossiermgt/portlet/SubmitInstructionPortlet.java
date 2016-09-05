package org.opencps.dossiermgt.portlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.opencps.datamgt.model.DictItem;
import org.opencps.datamgt.search.DictItemDisplayTerms;
import org.opencps.datamgt.service.DictItemLocalServiceUtil;
import org.opencps.servicemgt.model.ServiceInfo;
import org.opencps.servicemgt.model.TemplateFile;
import org.opencps.servicemgt.service.ServiceInfoLocalServiceUtil;
import org.opencps.servicemgt.service.TemplateFileLocalServiceUtil;
import org.opencps.util.WebKeys;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class SubmitInstructionPortlet
 */
public class SubmitInstructionPortlet extends MVCPortlet {
 
	@Override
	public void render(
	    RenderRequest renderRequest, RenderResponse renderResponse)
	    throws PortletException, IOException {

		long serviceinfoId = ParamUtil.getLong(renderRequest, "serviceinfoId");
		
		long dictItemId = ParamUtil.getLong(renderRequest, DictItemDisplayTerms.DICTITEM_ID);

		long templatefileId =
		    ParamUtil.getLong(renderRequest, "templateFileId");

		TemplateFile templateFile = null;

		ServiceInfo serviceInfo = null;
		
		DictItem dictItem = null;

		try {
			
			if(dictItemId > 0) {
				dictItem = DictItemLocalServiceUtil.getDictItem(dictItemId);
				renderRequest.setAttribute(WebKeys.DICT_ITEM_ENTRY, dictItem);
			}
			
			templateFile =
			    TemplateFileLocalServiceUtil.fetchTemplateFile(templatefileId);

			serviceInfo =
			    ServiceInfoLocalServiceUtil.fetchServiceInfo(serviceinfoId);
		}
		catch (Exception e) {
			
		}

		renderRequest.setAttribute(WebKeys.SERVICE_ENTRY, serviceInfo);
		renderRequest.setAttribute(WebKeys.SERVICE_TEMPLATE_ENTRY, templateFile);

		super.render(renderRequest, renderResponse);

	}
}
