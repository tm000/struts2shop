package seamshop.service.search;

import org.springframework.stereotype.Component;

import seamshop.model.Shop;
import seamshop.service.search.filter.ShopCountryFilterFactory;

/**
 * @author Alex Siman 2009-12-30
 */
@Component
public class ShopSearchService extends
	AbstractSearchServiceWithCountryCodes<Shop, ShopSearchParams>
{
	public ShopSearchService()
	{
		super(new ShopCountryFilterFactory());
	}

	@Override
	protected String getCountryFilterName()
	{
		return ShopCountryFilterFactory.FILTER_NAME;
	}
}
