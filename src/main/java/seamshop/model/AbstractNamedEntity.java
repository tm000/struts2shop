package seamshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import org.apache.struts2.interceptor.parameter.StrutsParameter;
// import org.hibernate.search.annotations.Boost;
// import org.hibernate.search.annotations.Field;
// import org.hibernate.search.annotations.Fields;
// import org.hibernate.search.annotations.Index;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

/**
 * @see AbstractHtmlDescribedEntity
 *
 * @author Alex Siman 2009-12-30
 */
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractNamedEntity extends AbstractIdBasedEntity
{
	public static final int NAME_LENGTH = 255;

	public static final String[] INDEXED_FIELDS = new String[] {"name"};

	@Column(length = NAME_LENGTH, nullable = false)
	// @Fields({
		@FullTextField/*,
		@Field(name = "nameForSort", index = Index.UN_TOKENIZED)*/
	// })
	// "@Boost" annotation means that "name" must be more important than
	// other entity fields in full text search.
	// @Boost(2)
	private String name;

	@Override
	public String[] getIndexedFields()
	{
		return INDEXED_FIELDS;
	}

	@Override
	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getSimpleName())
			.append("{")
				.append("id=").append(getId()).append("; ")
				.append("name=").append(getName())
			.append("}")
			.toString();
	}

	public String getName()
	{
		return name;
	}

	@StrutsParameter
	public void setName(String name)
	{
		this.name = name;
	}
}
