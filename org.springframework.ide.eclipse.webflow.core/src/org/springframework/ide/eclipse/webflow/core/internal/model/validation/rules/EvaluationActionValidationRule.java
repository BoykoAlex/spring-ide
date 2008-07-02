/*******************************************************************************
 * Copyright (c) 2005, 2007 Spring IDE Developers
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Spring IDE Developers - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.webflow.core.internal.model.validation.rules;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IType;
import org.springframework.ide.eclipse.core.MessageUtils;
import org.springframework.ide.eclipse.core.model.IModelElement;
import org.springframework.ide.eclipse.core.model.validation.IValidationContext;
import org.springframework.ide.eclipse.core.model.validation.IValidationRule;
import org.springframework.ide.eclipse.webflow.core.internal.model.EvaluateAction;
import org.springframework.ide.eclipse.webflow.core.internal.model.validation.WebflowValidationContext;
import org.springframework.util.StringUtils;

/**
 * @author Christian Dupuis
 * @since 2.0
 */
public class EvaluationActionValidationRule implements
		IValidationRule<EvaluateAction, WebflowValidationContext> {

	public boolean supports(IModelElement element, IValidationContext context) {
		return element instanceof EvaluateAction
				&& context instanceof WebflowValidationContext;
	}

	public void validate(EvaluateAction action,
			WebflowValidationContext context, IProgressMonitor monitor) {

		if (!StringUtils.hasText(action.getExpression())) {
			context.error(action, "NO_EXPRESSOIN_ATTRIBUTE",
					"Element 'evaluate-action' requires 'expression' attribute");
		}
		
		if (!context.isVersion1()) {
			if (StringUtils.hasText(action.getResultType())) {
				IType type = WebflowValidationRuleUtils.getJavaType(action.getResultType(), context);
				if (type == null) {
					context.error(action, "INVALID_TYPE", MessageUtils.format(
							"Class 'var' \"{0}\" cannot be resolved", action.getResultType()));
				}
			}
		}
		
	}
}
