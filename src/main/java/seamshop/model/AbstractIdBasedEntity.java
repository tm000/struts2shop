package seamshop.model;

import org.apache.struts2.interceptor.parameter.StrutsParameter;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

// TODO: Rename class: "Abstract(Generated|Native)IdBasedEntity".
@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractIdBasedEntity extends AbstractEntity<Long>
{
	@Id
	@GeneratedValue
	private Long id;

	public Long getId()
	{
		return id;
	}

	@StrutsParameter
	public void setId(Long id)
	{
		this.id = id;
	}

	@Transient
	@Override
	public Long getEntityId()
	{
		return getId();
	}
}
