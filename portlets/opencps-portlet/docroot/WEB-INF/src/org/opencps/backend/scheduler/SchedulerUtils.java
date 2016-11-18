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

package org.opencps.backend.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opencps.backend.message.SendToEngineMsg;
import org.opencps.backend.util.BackendUtils;
import org.opencps.backend.util.PaymentRequestGenerator;
import org.opencps.dossiermgt.NoSuchDossierException;
import org.opencps.dossiermgt.RequiredDossierPartException;
import org.opencps.dossiermgt.model.Dossier;
import org.opencps.dossiermgt.model.DossierFile;
import org.opencps.dossiermgt.model.DossierPart;
import org.opencps.dossiermgt.model.impl.DossierImpl;
import org.opencps.dossiermgt.service.DossierFileLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierLocalServiceUtil;
import org.opencps.dossiermgt.service.DossierPartLocalServiceUtil;
import org.opencps.processmgt.NoSuchProcessStepException;
import org.opencps.processmgt.NoSuchWorkflowOutputException;
import org.opencps.processmgt.model.ProcessOrder;
import org.opencps.processmgt.model.ProcessStepDossierPart;
import org.opencps.processmgt.model.ProcessWorkflow;
import org.opencps.processmgt.model.SchedulerJobs;
import org.opencps.processmgt.model.WorkflowOutput;
import org.opencps.processmgt.model.impl.ProcessOrderImpl;
import org.opencps.processmgt.model.impl.ProcessWorkflowImpl;
import org.opencps.processmgt.service.ProcessOrderLocalServiceUtil;
import org.opencps.processmgt.service.ProcessWorkflowLocalServiceUtil;
import org.opencps.processmgt.service.SchedulerJobsLocalServiceUtil;
import org.opencps.processmgt.service.WorkflowOutputLocalServiceUtil;
import org.opencps.processmgt.util.ProcessUtils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;


/**
 * @author khoavd
 *
 */
public class SchedulerUtils {

	/**
	 * @param SchedulerJobs schJob
	 */
	public static void doScheduler(SchedulerJobs schJob)
	    throws PortalException, SystemException {

		boolean conditionCheck =
		    BackendUtils.checkPreCondition(
		        schJob.getShedulerPattern(), schJob.getDossierId());

		if (conditionCheck) {

			SchedulerJobsLocalServiceUtil.updateScheduler(
			    schJob.getSchedulerJobsId(), 1);

			Date now = new Date();

			Dossier dossier = new DossierImpl();
			ProcessWorkflow processWorkflow = new ProcessWorkflowImpl();
			ProcessOrder processOrder = new ProcessOrderImpl();

			dossier = DossierLocalServiceUtil.getDossier(schJob.getDossierId());
			processWorkflow =
			    ProcessWorkflowLocalServiceUtil.fetchProcessWorkflow(schJob.getProcessWorkflowId());
			processOrder =
			    ProcessOrderLocalServiceUtil.getProcessOrder(
			        schJob.getDossierId(), schJob.getFileGroupId());

			SendToEngineMsg engineMsg = new SendToEngineMsg();

			engineMsg.setUserId(dossier.getUserId());
			engineMsg.setGroupId(dossier.getGroupId());
			engineMsg.setCompanyId(dossier.getCompanyId());
			engineMsg.setActionDatetime(now);
			engineMsg.setAction(processWorkflow.getActionName());
			engineMsg.setSignature(0);
			engineMsg.setPaymentValue(PaymentRequestGenerator.getTotalPayment(processWorkflow.getPaymentFee(), dossier.getDossierId()));
			engineMsg.setEstimateDatetime(now);
			engineMsg.setReceptionNo(dossier.getReceptionNo());
			engineMsg.setAssignToUserId(processWorkflow.getActionUserId());
			engineMsg.setActionNote(BackendUtils.buildActionName(schJob.getShedulerPattern()));
			engineMsg.setActionUserId(processWorkflow.getActionUserId());
			engineMsg.setProcessWorkflowId(processWorkflow.getProcessWorkflowId());
			engineMsg.setProcessOrderId(processOrder.getProcessOrderId());
			engineMsg.setDossierId(schJob.getDossierId());
			engineMsg.setFileGroupId(schJob.getFileGroupId());

			Message msg = new Message();

			msg.put("msgToEngine", engineMsg);

			MessageBusUtil.sendMessage(
			    "opencps/backoffice/engine/destination", msg);

		}

	}
	
	public void validateAssignTask(
		long dossierId, long processWorkflowId, long processStepId)
		throws NoSuchDossierException, NoSuchWorkflowOutputException,
		RequiredDossierPartException, NoSuchProcessStepException {

		boolean requiredFlag = false;

		if (processWorkflowId <= 0) {
			throw new NoSuchWorkflowOutputException();
		}

		if (dossierId <= 0) {
			throw new NoSuchDossierException();
		}

		/*
		 * if (processStepId <= 0) { throw new NoSuchProcessStepException(); }
		 */
		List<WorkflowOutput> workflowOutputs = new ArrayList<WorkflowOutput>();

		List<ProcessStepDossierPart> processStepDossierParts =
			new ArrayList<ProcessStepDossierPart>();

		if (processStepId > 0) {
			processStepDossierParts =
				ProcessUtils.getDossierPartByStep(processStepId);
		}

		if (processStepDossierParts != null) {
			for (ProcessStepDossierPart processStepDossierPart : processStepDossierParts) {

				if (processStepDossierPart.getDossierPartId() > 0) {
					try {

						List<WorkflowOutput> workflowOutputsTemp =
							WorkflowOutputLocalServiceUtil.getByProcessByPWID_DPID(
								processWorkflowId,
								processStepDossierPart.getDossierPartId());

						if (workflowOutputsTemp != null) {
							workflowOutputs.addAll(workflowOutputsTemp);
						}
					}
					catch (Exception e) {
					}
				}

			}
		}

		if (workflowOutputs != null && !workflowOutputs.isEmpty()) {
			for (WorkflowOutput workflowOutput : workflowOutputs) {
				if (workflowOutput.getRequired()) {

					DossierFile dossierFile = null;
					try {
						DossierPart dossierPart =
							DossierPartLocalServiceUtil.getDossierPart(workflowOutput.getDossierPartId());
						dossierFile =
							DossierFileLocalServiceUtil.getDossierFileInUse(
								dossierId, dossierPart.getDossierpartId());
					}
					catch (Exception e) {
						// TODO: handle exception
					}

					if (dossierFile == null) {
						requiredFlag = true;
						break;
					}

				}
			}
		}

		if (requiredFlag) {
			throw new RequiredDossierPartException();
		}
	}

}
