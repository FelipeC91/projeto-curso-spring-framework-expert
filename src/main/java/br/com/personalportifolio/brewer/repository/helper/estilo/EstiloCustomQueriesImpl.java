package br.com.personalportifolio.brewer.repository.helper.estilo;

import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.repository.filter.EstiloFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class EstiloCustomQueriesImpl implements EstiloCustomQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Page<Estilo> doFilter(EstiloFilter estiloFilter, Pageable pageable) {

        var builder = entityManager.getCriteriaBuilder();
        var criteriaQuery= builder.createQuery(Estilo.class);
        var root = criteriaQuery.from(Estilo.class);


        criteriaQuery.select(root);

        if (estiloFilter != null && estiloFilter.getNome() != null && !estiloFilter.getNome().isEmpty()) {
            var searchKey = "%" + estiloFilter.getNome() + "%";
            criteriaQuery.where( builder.like(root.get("nome"), searchKey));
        }


        var sort = pageable.getSort();

        if (sort != null && sort.iterator().hasNext()) {
            Sort.Order order = sort.iterator().next();
            var field = order.getProperty();
            criteriaQuery.orderBy( order.isAscending() ? builder.asc( root.get(field) ) : builder.desc( root.get(field) ) );

        }


        var firstRegister = pageable.getPageNumber() * pageable.getPageSize();
        System.out.println("first reg " + firstRegister);
        if (firstRegister == 0) {
            var query = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(pageable.getPageNumber());
        }
        System.out.println("max reg " + pageable.getPageSize());

        var query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstRegister)
                .setMaxResults(pageable.getPageSize());

        var totalResult = query.getResultList().stream().count();

        System.out.println("total r. " + totalResult);
        return new PageImpl<Estilo>(query.getResultList(), pageable, totalResult );
    }
}
