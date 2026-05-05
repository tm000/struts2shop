package seamshop.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import seamshop.model.Product;
import seamshop.model.Shop;
import seamshop.util.CollectionUtils;

@Component
public class ShopDao extends GenericDao<Shop>
{
	@Autowired
	protected ProductDao productDao;

	public Shop getMyShopById(Long shopId)
	{
		Shop shop = null;
		if (!hasCurrentUser())
		{
			return shop;
		}
		else if (shopId == null)
		{
			log.warn("Input parameter 'shopId' is null.");
			return shop;
		}

		String hql =
			"from " + Shop.class.getName() + " s " +
			"where (s.user.id = :userId) and (s.id = :shopId)";

		shop = createQuery(hql)
			.setParameter("userId", getCurrentUserId())
			.setParameter("shopId", shopId)
			.uniqueResult();

		initProductCountOfShop(shop);

		return shop;
	}

	public Long countMyShops()
	{
		Long count = 0L;
		if (!hasCurrentUser())
		{
			return count;
		}

		String hql =
			"select count(distinct s.id) " +
			"from " + Shop.class.getName() + " s " +
			"where s.user.id = :userId";

		count = createQuery(hql, Long.class)
			.setParameter("userId", getCurrentUserId())
			.getSingleResult();

		return count;
	}

	/**
	 * Used by to print list of shops for "New product" use-case.
	 */
	public List<Shop> getAllMyShops()
	{
		if (!hasCurrentUser())
		{
			return new ArrayList<Shop>();
		}

		String hql =
			"from " + Shop.class.getName() + " s " +
			"where s.user.id = :userId " +
			"order by s.name";

		List<Shop> shops = createQuery(hql)
			.setParameter("userId", getCurrentUserId())
			.list();

		// TODO: Delete: unneeded? (y)
//		initProductCountsOfShops(shops);

		return shops;
	}

	public List<Shop> getPageOfMyShops()
	{
		if (!hasCurrentUser())
		{
			return new ArrayList<Shop>();
		}

		String hql =
			"from " + Shop.class.getName() + " s " +
			"where s.user.id = :userId " +
			"order by s.created desc";

		List<Shop> shops = createPagedQuery(hql)
			.setParameter("userId", getCurrentUserId())
			.list();

		initProductCountsOfShops(shops);

		return shops;
	}

	/**
	 * Returns page of shops with set product count for every shop.
	 */
	@Override
	public List<Shop> getPage()
	{
		List<Shop> shops = super.getPage();
		initProductCountsOfShops(shops);

		return shops;
	}

	private void initProductCountOfShop(Shop shop)
	{
		if (shop != null)
		{
			initProductCountsOfShops(Arrays.asList(shop));
		}
	}

	private void initProductCountsOfShops(List<Shop> shops)
	{
		if (CollectionUtils.isNullOrEmpty(shops))
		{
			log.warn("Provided shops are null or empty");
			return;
		}

		List<Long> shopIds = DaoUtils.getIdsOfEntities(shops);
		Map<Long, Long> shopIdsAndProductCounts =
			productDao.getProductCountsByShopIds(shopIds);

		if (!CollectionUtils.isNullOrEmpty(shopIdsAndProductCounts))
		{
			for (Shop shop : shops)
			{
				Long productCount = shopIdsAndProductCounts.get(shop.getId());
				if (productCount == null)
				{
					productCount = 0L;
				}
				shop.setProductCount(productCount);
			}
		}
	}

	public Shop getShopByProductId(Long productId)
	{
		Shop shop = null;

		if (productId == null)
		{
			log.warn("Input parameter 'productId' is null.");
			return shop;
		}

		String hql =
			"select p.shop " +
			"from " + Product.class.getName() + " p " +
			"where p.id = :productId";

		return createQuery(hql)
			.setParameter("productId", productId)
			.uniqueResult();
	}

	// TODO: Normal: Optimize: Limit count of IDs to GenericDao.MAX_RESULTS_LIMIT.
	public Map<Long, Shop> getShopsByProductIds(Collection<Long> productIds)
	{
		Map<Long, Shop> shops = new HashMap<Long, Shop>();
		if (CollectionUtils.isNullOrEmpty(productIds))
		{
			return shops;
		}

		String hql =
			"select p.id, p.shop " +
			"from " + Product.class.getName() + " p " +
			"where p.id in (:productIds)";

		// TODO: Test. bug: NullPointerException
		log.debug("before getSession()");
		getSession();
		log.debug("after getSession()");


		List<Object[]> productIdAndShops = createQuery(hql, Object[].class)
			.setParameterList("productIds", productIds)
			.list();

		for (Object[] productIdAndShop : productIdAndShops)
		{
			Long productId = (Long) productIdAndShop[0];
			Shop shop = (Shop) productIdAndShop[1];
			shops.put(productId, shop);
		}

		return shops;
	}

	// TODO: Low: Duplication: extract getByField(fieldNam, fieldvValue)
	//       or use Criteria API.
	public Shop getByUrlName(String urlName)
	{
		Shop shop = null;

		if (StringUtils.isEmpty(urlName))
		{
			log.warn("Input parameter 'urlName' is empty.");
			return shop;
		}

		String hql =
			"from " + Shop.class.getName() + " s " +
			"where s.urlName = :urlName";

		return createQuery(hql)
			.setParameter("urlName", urlName)
			.uniqueResult();
	}

	public boolean isUniqueByUrlName(String urlName, Long shopId)
	{
		log.debug("isUniqueByUrlName(String urlName, Long shopId)");
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<Shop> root = cq.from(getEntityClass());
		cq.where(builder.equal(root.get("urlName"), urlName));
		if (shopId != null)
		{
			cq.where(builder.notEqual(root.get("id"), shopId));
		}
		cq.select(builder.count(root));
		return 1 > getSession().createQuery(cq).getSingleResult();
		// return isUniqueCriteria(restrictions);
	}
}
