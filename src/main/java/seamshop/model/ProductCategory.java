package seamshop.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.ForeignKey;

/**
 * @author Alex Siman 2008-12-24
 */
@Entity
@SuppressWarnings("serial")
public class ProductCategory extends AbstractIdBasedEntity
{
	// TODO: Use productID + categoryID as primary key?

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category_product_id"))
	private Product product;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category_category_id"))
	private Category category;

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}
}
