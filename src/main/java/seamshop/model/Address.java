package seamshop.model;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static seamshop.util.StringBuilderUtils.removeCharsFromEnd;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.ForeignKey;

import seamshop.messages.CountryCollection;

/*
 * TODO: Separate into 2 types?:
 *
 * - Contact: firstName, lastName, gender, birthday, company, email,
 *   telephone (home, work, mobile, fax...), IM (ICQ, Jabber, Skype...);
 *
 * - Address: street, city, state... latitude, longitude.
 *
 * Or make this class as combined of previous two:
 * Contact + Address = ?, etc.
 */
/**
 * Represents geographical address of something: shop, user, etc.
 *
 * @author Alex Siman 2008-09-06
 */
@Entity
@SuppressWarnings("serial")
public class Address extends AbstractIdBasedEntity
{
	/**
	 * Full name of shipping receiver or alias name of address for quicker remembering.
	 */
	@Column(nullable = true)
	private String name;

	@Column(nullable = false)
	private String street;

	private String street2;

	private String city;

	private String state;

	// TODO: Use enum? See Seam wiki example.
	// TODO: Rename to "countryCode"? (y)
	/**
	 * 2-letter country code (ISO 3166). String length = 2 chars. Can be <code>null</code>.
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2">ISO 3166-1 alpha-2</a>
	 * @see <a href="http://en.wikipedia.org/wiki/ISO_3166">ISO 3166</a>
	 * @see <a href="http://www.iso.org/iso/english_country_names_and_code_elements">ISO 3166</a>
	 * @see <a href="http://www.maxmind.com/app/iso3166">ISO 3166</a>
	 */
	@Column(name = "country_code", nullable = false)
	private String country;

	// TODO: Rename to "zip" (no)
	@Column(name = "postal_code")
	private String postalCode;

	private String phone;

	// TODO: Remove: Need this field in address? (n)
	private String email;

	// TODO: Low: Add field? (mb)
//	private String notes;

	private Double latitude;
	private Double longitude;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true, updatable = true, foreignKey = @ForeignKey(name = "fk_address_user_id"))
	private User user;

	public String getName()
	{
		return name;
	}

	@StrutsParameter
	public void setName(String name)
	{
		this.name = name;
	}

	public String getStreet()
	{
		return street;
	}

	@StrutsParameter
	public void setStreet(String street)
	{
		this.street = street;
	}

	public String getStreet2()
	{
		return street2;
	}

	@StrutsParameter
	public void setStreet2(String street2)
	{
		this.street2 = street2;
	}

	public String getCity()
	{
		return city;
	}

	@StrutsParameter
	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	@StrutsParameter
	public void setState(String state)
	{
		this.state = state;
	}

	public String getCountry()
	{
		return country;
	}

	@StrutsParameter
	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	@StrutsParameter
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getPhone()
	{
		return phone;
	}

	@StrutsParameter
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	@StrutsParameter
	public void setEmail(String email)
	{
		this.email = email;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	@StrutsParameter
	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	@StrutsParameter
	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	// TODO: Low: Rename to "getCountry()" if "country" field renamed to "countryCode".
	public String getCountryName()
	{
		return CountryCollection.getInstance().getNameByCode(country);
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	/**
	 * @return Summary of address fields to be printed inline.
	 */
	public String getSummary()
	{
		String[] fieldsToPrint = {name, street, city, getCountryName()};
		String fieldDelimiter = ", ";
		StringBuilder builder = new StringBuilder();
		for (String field : fieldsToPrint)
		{
			if (!isEmpty(field))
			{
				builder.append(field).append(fieldDelimiter);
			}
		}
		removeCharsFromEnd(builder, fieldDelimiter.length());
		return builder.toString();
	}
}
