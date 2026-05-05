package seamshop.service.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

/**
 * @author Alex Siman 2009-12-31
 */
public abstract class AbstractSearchParamsWithCountryCodes
{
	private List<String> countryCodes = new ArrayList<String>();

	public List<String> getCountryCodes()
	{
		return countryCodes;
	}

	@StrutsParameter
	public void setCountryCodes(List<String> countryCodes)
	{
		this.countryCodes = countryCodes;
	}
}
