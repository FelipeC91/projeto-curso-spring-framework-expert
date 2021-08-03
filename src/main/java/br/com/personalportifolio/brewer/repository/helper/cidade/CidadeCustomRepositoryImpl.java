package br.com.personalportifolio.brewer.repository.helper.cidade;

import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.repository.filter.CitySearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class CidadeCustomRepositoryImpl implements CidadeCustomQueries{

    @PersistenceContext
    EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Page<Cidade> filtrar(CitySearchFilter citySearchFilter, Pageable pageable) {
        var builder = entityManager.getCriteriaBuilder();
        var criteriaQuery = builder.createQuery(Cidade.class);
        var root = criteriaQuery.from(Cidade.class);
        root.fetch("estado");

        criteriaQuery.select(root);

        Predicate[] predicates = resolveFilter(root, builder, citySearchFilter);

        if (predicates != null)
            criteriaQuery.where(predicates);

        var sort = pageable.getSort();

        if (sort != null && sort.iterator().hasNext()) {
            Sort.Order order = sort.iterator().next();
            var field = order.getProperty();
            criteriaQuery.orderBy( order.isAscending() ? builder.asc( root.get(field) ) : builder.desc( root.get(field) ) );
        }

        System.out.println("page number " +pageable.getPageNumber());
        var firstRegister = pageable.getPageNumber() * pageable.getPageSize();
        System.out.println("first resulst >> " + firstRegister);

        if (firstRegister == 0) {
            var query = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(pageable.getPageNumber());
            System.out.println("==0 fst resulst >> " + pageable.getPageNumber());

        }

        var query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstRegister)
                .setMaxResults(pageable.getPageSize() + 1);

        var totalResult = query.getResultList().stream().count();
        System.out.println("total resulst >> " + totalResult);
        return new PageImpl<Cidade>(query.getResultList(), pageable, totalResult );


    }

    private Predicate[] resolveFilter(Root<Cidade> root, CriteriaBuilder criteriaBuilder, CitySearchFilter citySearchFilter) {
        var predicates = new ArrayList<Predicate>();

        if (citySearchFilter != null) {
            if(citySearchFilter.getEstado() != null)
                predicates.add(criteriaBuilder.equal(root.get("estado"), citySearchFilter.getEstado()));

            if (citySearchFilter.getNome() != null && !citySearchFilter.getNome().isEmpty()) {
                var searchKey = "%" + citySearchFilter.getNome() + "%";
                predicates.add(criteriaBuilder.like(root.get("nome"), searchKey));
            }

            return predicates.toArray(new Predicate[predicates.size()]);
        }

        return null;
    }
}
