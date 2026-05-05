package seamshop.service.search;

//import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

// import org.hibernate.search.jpa.FullTextQuery;

import seamshop.model.AbstractEntity;
import seamshop.service.search.filter.AbstractCountryFilterFactory;

/**
 * @author Alex Siman 2009-12-30
 */
public abstract class AbstractSearchServiceWithCountryCodes
	<E extends AbstractEntity<?>, SP extends AbstractSearchParamsWithCountryCodes>
	extends AbstractSearchService<E>
{
	protected abstract String getCountryFilterName();

	private final AbstractCountryFilterFactory countryFilterFactory;

	public AbstractSearchServiceWithCountryCodes()
	{
		this.countryFilterFactory = null;
	}

	public AbstractSearchServiceWithCountryCodes(AbstractCountryFilterFactory countryFilterFactory)
	{
		this.countryFilterFactory = countryFilterFactory;
	}

	public SearchResult<List<E>> searchFor(String searchQuery, SP params)
	{
		/*FullTextQuery*/Query fullTextQuery = createFullTextQuery(searchQuery);

		// List<String> countryCodes = params.getCountryCodes();
		// if (!/*isEmpty(countryCodes)*/countryCodes.isEmpty())
		if (this.countryFilterFactory != null)
		{
		// 	// fullTextQuery.enableFullTextFilter(getCountryFilterName())
		// 	// 	.setParameter(AbstractCountryFilterFactory.PARAM_COUNTRY_CODES, countryCodes);
		// 	Query newCondition = new TermQuery(new Term("category", "books"));
		// 	BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
		// 	booleanQueryBuilder.add(fullTextQuery, BooleanClause.Occur.MUST);
		// 	booleanQueryBuilder.add(newCondition, BooleanClause.Occur.MUST);
		// 	fullTextQuery = booleanQueryBuilder.build();
			return getSearchResult(fullTextQuery, this.countryFilterFactory);
		}

		return getSearchResult(fullTextQuery, null);
	}
}
