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
public class ShopShoppingCountry extends AbstractEntityWithCountry
{
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_shop_shopping_country_shop_id"))
	private Shop shop;

	public ShopShoppingCountry()
	{}

	public Shop getShop()
	{
		return shop;
	}

	public void setShop(Shop shop)
	{
		this.shop = shop;
	}
}
