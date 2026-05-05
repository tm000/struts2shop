package seamshop.interceptor.transaction;


import org.apache.struts2.interceptor.validation.AnnotationValidationInterceptor;
import org.springframework.stereotype.Component;

import seamshop.interceptor.AbstractInterceptor;
import seamshop.util.Command;
import seamshop.util.FinalObjectWrapper;

import org.apache.struts2.action.Action;
import org.apache.struts2.ActionInvocation;

/**
 * Interceptor that invoke action within JPA transaction.
 * The type of transaction: read or write - is determined by {@link Transactional}
 * annotation on action method.
 * <p/>
 * Code based on (mainly copied from) {@link AnnotationValidationInterceptor}.
 *
 * @see Transactional
 * @see MyTransactionType
 * @see AnnotationValidationInterceptor
 *
 * @author Alex Siman 2009-06-08
 */
@Component
@SuppressWarnings("serial")
public class TransactionInterceptor extends AbstractInterceptor
{
	protected static final MyTransactionType DEFAULT_TRANSACTION_TYPE = MyTransactionType.READ;

	// TODO: Refactor: duplicated code.
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		log.debug("Begin of interception.");

		Transactional transAnnotation = findMethodAnnotation(Transactional.class);
		MyTransactionType transType = DEFAULT_TRANSACTION_TYPE;
		if (transAnnotation != null)
		{
			transType = transAnnotation.value();
		}

		String actionResult = invokeWithinTransaction(transType);
		log.debug("End of interception. Action result: " + actionResult);

		return actionResult;
	}

	protected String invokeWithinTransaction(MyTransactionType transType) throws Exception
	{
		final ActionInvocation actionInvocation = getActionInvocation();
		if (transactionContext == null)
		{
			throw new NullPointerException("The 'transactionContext' must not be null.");
		}

		final FinalObjectWrapper<String> actionResultWrapper =
			new FinalObjectWrapper<String>(Action.ERROR);

		Command transCommand = new Command()
		{
			@Override
			public void execute() throws Exception
			{
				String actionResult = actionInvocation.invoke();
				actionResultWrapper.setFinalObject(actionResult);
				if (getAbstractAction().hasErrors())
				{
					// Cancel any changes such as action has errors right before commit.
					log.debug("Action has errors. Clearing session.");
					entityManager.clear();
				}
				else
				{
					log.debug("Committing transaction.");
				}
			}
		};

		String actionResult = Action.ERROR;
		if (transType == MyTransactionType.READ)
		{
			log.debug("Invoking action within read-transaction");
			transactionContext.doInReadTransaction(transCommand);
			actionResult = actionResultWrapper.getFinalObject();
		}
		else if (transType == MyTransactionType.WRITE)
		{
			log.debug("Invoking action within write-transaction");
			transactionContext.doInWriteTransaction(transCommand);
			actionResult = actionResultWrapper.getFinalObject();
		}
		// Also if transType == TransactionType.NONE or null.
		else
		{
			log.debug("Invoking action without transaction");
			actionResult = actionInvocation.invoke();
		}

		return actionResult;
	}
}
