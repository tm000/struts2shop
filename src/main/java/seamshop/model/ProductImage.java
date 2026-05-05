package seamshop.model;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.ForeignKey;

/**
 * @author Alex Siman 2009-01-13
 * @see Product
 * @see Image
 */
@Entity
@SuppressWarnings("serial")
public class ProductImage extends AbstractIdBasedEntity
{
	/*
	 * TODO: Refactor: Enhance: Remove "number" field and implement ProductImage as linked list
	 * (with prev and next ProductImage).
	 */
	/**
	 * Serial number of image in product image list. Starts from 0.
	 */
	private Integer number;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_image_product_id"))
	private Product product;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "image_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_image_image_id"))
	private Image image;

	public ProductImage()
	{
		number = 0;
	}

	public Integer getNumber()
	{
		return number;
	}

	@StrutsParameter
	public void setNumber(Integer number)
	{
		this.number = number;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}
}
