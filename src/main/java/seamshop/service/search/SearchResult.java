package seamshop.service.search;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

/**
 * @author Alex Siman 2009-12-31
 */
public class SearchResult<R>
{
	private R result;

	/** Size of all results. */
	private long resultSize = 0;

	public R getResult()
	{
		return result;
	}

	public void setResult(R result)
	{
		this.result = result;
	}

	public long getResultSize()
	{
		return resultSize;
	}

	@StrutsParameter
	public void setResultSize(long resultSize)
	{
		this.resultSize = resultSize;
	}
}
