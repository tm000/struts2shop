package seamshop.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import seamshop.model.UserShoppingCountry;

/**
 * @author Alex Siman 2009-12-29
 */
@Component
public class UserShoppingCountryDao extends GenericDao<UserShoppingCountry>
{
	public List<UserShoppingCountry> getAllByCurrentUser()
	{
		List<UserShoppingCountry> result = new ArrayList<UserShoppingCountry>();
		if (hasCurrentUser())
		{
			CriteriaBuilder builder = getSession().getCriteriaBuilder();
			CriteriaQuery<UserShoppingCountry> cq = builder.createQuery(getEntityClass());
			Root<UserShoppingCountry> root = cq.from(getEntityClass());
			cq.select(root).where(builder.equal(root.get("user.id"), getCurrentUserId()));
			result = getSession().createQuery(cq).getResultList();
		}
		return result;
	}
}
