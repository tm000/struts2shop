package seamshop.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import jakarta.persistence.ForeignKey;

@Entity
@SuppressWarnings("serial")
public class Cart extends AbstractIdBasedEntity
{
	@OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<CartItem> cartItems = new HashSet<CartItem>();

	// TODO: Rename to "user", "owner"? (xz, y)
	// TODO: Set customer, if current user is registered.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_cart_user_id"))
	private User customer;

	public Set<CartItem> getCartItems()
	{
		return cartItems;
	}

	@StrutsParameter
	public void setCartItems(Set<CartItem> cartItems)
	{
		this.cartItems = cartItems;
	}

	public User getCustomer()
	{
		return customer;
	}

	public void setCustomer(User customer)
	{
		this.customer = customer;
	}
}
