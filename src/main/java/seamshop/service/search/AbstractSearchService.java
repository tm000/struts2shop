package seamshop.service.search;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.search.backend.lucene.LuceneExtension;
import org.hibernate.search.engine.search.predicate.SearchPredicate;
// import org.hibernate.search.jpa.FullTextEntityManager;
// import org.hibernate.search.jpa.FullTextQuery;
// import org.hibernate.search.jpa.Search;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Component;

import seamshop.actionutil.Pager;
import seamshop.dao.GenericDao;
import seamshop.model.AbstractEntity;
import seamshop.service.AbstractService;
import seamshop.service.search.filter.AbstractCountryFilterFactory;
import seamshop.util.ClassUtils;

/**
 * @author Alex Siman 2009-09-07
 */
// TODO: Refactor to: "AbstractSearchService" like "GenericDao"? (y)
@Component
public abstract class AbstractSearchService<E extends AbstractEntity>
	extends AbstractService
{
	private MultiFieldQueryParser queryParser = null;

	/**
	 * Object class of an entity in Hibernate Session.
	 */
	private Class<E> entityClass;

	/**
	 * Get the class of the entity being managed. If not explicitly specified,
	 * the generic type of implementation is used.
	 */
	protected Class<E> getEntityClass()
	{
		if (entityClass == null)
		{
			entityClass = ClassUtils.getClassOfFirstTypeArgument(this);
		}
		return entityClass;
	}

	private Pager getPager()
	{
		return requestContext.getPager();
	}

	private int getFirstResult()
	{
		return GenericDao.getFirstResult(getPager());
	}

	private int getMaxResults()
	{
		return GenericDao.getMaxResults(getPager());
	}

	private List<E> getPageOfResults(Query luceneQuery, AbstractCountryFilterFactory predicate)
	{
		SearchSession searchSession = Search.session(entityManager);
		return searchSession.search(getEntityClass())
			.extension(LuceneExtension.get())
			.where(f -> f.bool().with( b -> {
				b.must(f.fromLuceneQuery(luceneQuery));
				if (predicate != null)
				{
					b.must(predicate.create(f));
				}
			}))
			.fetch(getFirstResult(), getMaxResults())
			.hits();
	}

	// This method must be synchronized such as this class is singleton service.
	private synchronized MultiFieldQueryParser getQueryParser()
	{
		if (null == queryParser)
		{
			// TODO: Note: Use the same analyzer as one used for indexing entities.
			Analyzer analyzer = new StandardAnalyzer();
			E entity = ClassUtils.newInstanceOfClass(getEntityClass());
			queryParser = new MultiFieldQueryParser(
				entity.getIndexedFields(), analyzer);
		}

		return queryParser;
	}

	private Query parseQuery(String searchFor) throws ParseException
	{
		return getQueryParser().parse(searchFor);
	}

	protected /*FullTextQuery*/Query createFullTextQuery(String searchQuery)
	{
		/*FullTextQuery fullTextQuery = null;*/
		if (isBlank(searchQuery))
		{
			// Nothing to search for.
			//return fullTextQuery;
			return null;
		}

		// FullTextEntityManager fullTextEntityManager =
		// 	Search.getFullTextEntityManager(entityManager);
		try
		{
			return parseQuery(searchQuery);
			// fullTextQuery = fullTextEntityManager
			// 	.createFullTextQuery(luceneQuery, getEntityClass());
		}
		catch (ParseException pe)
		{
			pe.printStackTrace();
		}

		return null;
	}

	protected SearchResult<List<E>> getSearchResult(/*FullTextQuery*/Query fullTextQuery, AbstractCountryFilterFactory predicate)
	{
		// TODO: Add filter for fields: "hidden".
		// TODO: Fetch product title images.

		List<E> result = getPageOfResults(fullTextQuery, predicate);
		long resultSize = result.size()/*getResultSize()*/;

		SearchResult<List<E>> searchResult = new SearchResult<List<E>>();
		searchResult.setResult(result);
		searchResult.setResultSize(resultSize);

		return searchResult;
	}

	public SearchResult<List<E>> searchFor(String searchQuery, AbstractCountryFilterFactory predicate)
	{
		/*FullTextQuery*/Query fullTextQuery = createFullTextQuery(searchQuery);
		return getSearchResult(fullTextQuery, predicate);
	}
}
