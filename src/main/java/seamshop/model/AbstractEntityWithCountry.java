package seamshop.model;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Alex Siman 2009-12-24
 */
// TODO: Low: SQL: Performance: Create "Country" entity and use "Country.id"
//       in this entity instead of countryCode? (y)
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractEntityWithCountry extends AbstractIdBasedEntity
{
	@Column(nullable = false, length = 2)
	private String countryCode;

	public String getCountryCode()
	{
		return countryCode;
	}

	@StrutsParameter
	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}
}
