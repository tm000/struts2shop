package seamshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.ForeignKey;

/**
 * Used to filter shops by country for user's shopping.
 *
 * @author Alex Siman 2009-12-24
 */
@Entity
@SuppressWarnings("serial")
public class UserShoppingCountry extends AbstractEntityWithCountry
{
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_shopping_country_user_id"))
	private User user;

	public UserShoppingCountry()
	{}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}
