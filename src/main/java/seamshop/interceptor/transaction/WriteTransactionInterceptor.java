package seamshop.interceptor.transaction;

import org.springframework.stereotype.Component;

import org.apache.struts2.ActionInvocation;

@Component
@SuppressWarnings("serial")
public class WriteTransactionInterceptor extends TransactionInterceptor
{
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{
		return invokeWithinTransaction(MyTransactionType.WRITE);
	}
}