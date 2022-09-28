package br.com.personalportifolio.brewer.repository.helper.cerveja;

import br.com.personalportifolio.brewer.dto.CervejaDTO;
import br.com.personalportifolio.brewer.model.Cerveja;
import br.com.personalportifolio.brewer.model.Estilo;
import br.com.personalportifolio.brewer.model.Origem;
import br.com.personalportifolio.brewer.model.Sabor;
import br.com.personalportifolio.brewer.repository.filter.CervejaFilter;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CervejaCustomRepositoryImpl implements CervejaCustomQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Page<Cerveja> filtrar(CervejaFilter cervejaFilter, Pageable pageable) {
//        var criteria = entityManager.unwrap(Session.class).createCriteria(Cerveja.class);
//
//        criteria.setMaxResults(pageable.getPageSize());
//        criteria.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
//
//        resolveCriteriaFilter(cervejaFilter, criteria);
//
//        return new PageImpl<Cerveja>(criteria.list(), pageable, getTotal(cervejaFilter));

        var builder = entityManager.getCriteriaBuilder();
        var criteriaQuery = builder.createQuery(Cerveja.class);
        var root = criteriaQuery.from(Cerveja.class);

        var sort =  pageable.getSort();

        Predicate[] predicates = resolveCriteriaFilter(cervejaFilter, builder, root);


        if (sort != null && sort.iterator().hasNext()) {
                Sort.Order order = sort.iterator().next();
                var field = order.getProperty();
                criteriaQuery.where(predicates).orderBy(order.isAscending() ? builder.asc( root.get(field) ) : builder.desc( root.get(field) ) );

        }

        criteriaQuery.where(predicates);

        var firstRegister = pageable.getPageNumber() * pageable.getPageSize();

        if (firstRegister == 0) {
            var query = entityManager.createQuery(criteriaQuery)
                                            .setFirstResult(pageable.getPageNumber());
        }

        var query = entityManager.createQuery(criteriaQuery)
                                        .setFirstResult(firstRegister)
                                        .setMaxResults(pageable.getPageSize());

        var totalResult = query.getResultList().stream().count();

        return new PageImpl<Cerveja>(query.getResultList(), pageable, totalResult );
    }

    private Predicate[] resolveCriteriaFilter(CervejaFilter cervejaFilter, CriteriaBuilder builder, Root itemRoot) {
        var predicates = new ArrayList<Predicate>();
        if (cervejaFilter != null) {
            if (cervejaFilter.getSku() != null && !cervejaFilter.getSku().isEmpty() ) {
                System.out.println(cervejaFilter.getSku());
                predicates.add( builder.equal(itemRoot.<String>get("sku"), cervejaFilter.getSku()) );
            }

            if (cervejaFilter.getNome() != null && !cervejaFilter.getNome().isEmpty()) {
                var searchName = "%" + cervejaFilter.getNome() + "%";
                predicates.add(builder.like(itemRoot.get("nome"), searchName));

            }if (estiloIspresent(cervejaFilter))
                predicates.add(builder.equal(itemRoot.<Estilo>get("estilo"), cervejaFilter.getEstilo()));

            if (cervejaFilter.getSabor() != null)
                predicates.add(builder.equal(itemRoot.<Sabor>get("sabor"), cervejaFilter.getSabor()));

            if (cervejaFilter.getOrigem() != null)
                predicates.add(builder.<Origem>equal(itemRoot.<Origem>get("origem"), cervejaFilter.getOrigem()));

            if (cervejaFilter.getPrecoDe() != null)
                predicates.add(builder.ge( itemRoot.<BigDecimal>get("valor"), cervejaFilter.getPrecoDe()));

            if (cervejaFilter.getPrecoAte() != null) {
                System.out.println(cervejaFilter.getPrecoAte());
                predicates.add(builder.le(itemRoot.<BigDecimal>get("valor"), cervejaFilter.getPrecoAte()));
            }

             return predicates.toArray(new Predicate[predicates.size()]);

        }

        return null;
    }


    //    private void resolveCriteriaFilter(CervejaFilter cervejaFilter, Criteria criteria) {
    //        if (cervejaFilter != null) {
    //            if (cervejaFilter.getSku() != null && !cervejaFilter.getSku().isEmpty() ) {
    //                System.out.println(cervejaFilter.getSku());
    //                criteria.add(Restrictions.eq("sku", cervejaFilter.getSku()));
    //            }
    //
    //            if (cervejaFilter.getNome() != null && !cervejaFilter.getNome().isEmpty())
    //                criteria.add(Restrictions.ilike("nome", cervejaFilter.getNome(), MatchMode.ANYWHERE));
    //
    //            if (estiloIspresent(cervejaFilter))
    //                criteria.add(Restrictions.eq("estilo", cervejaFilter.getEstilo()));
    //
    //            if (cervejaFilter.getSabor() != null)
    //                criteria.add(Restrictions.eq("sabor", cervejaFilter.getSabor()));
    //
    //            if (cervejaFilter.getOrigem() != null)
    //                criteria.add(Restrictions.eq("origem", cervejaFilter.getOrigem()));
    //
    //            if (cervejaFilter.getPrecoDe() != null)
    //                criteria.add(Restrictions.ge("valor", cervejaFilter.getPrecoDe()));
    //
    //            if (cervejaFilter.getPrecoAte() != null) {
    //                System.out.println(cervejaFilter.getPrecoAte());
    //                criteria.add(Restrictions.le("valor", cervejaFilter.getPrecoAte()));
    //            }
    //
    //
    //        }
    //    }

//    private Long getTotal(CervejaFilter cervejaFilter) {
//        var criteria = entityManager.unwrap(Session.class).createCriteria(Cerveja.class);
//        criteria.setProjection(Projections.rowCount());
//
//        return (long) criteria.uniqueResult();
//    }
    private boolean estiloIspresent(CervejaFilter cervejaFilter) {
        return cervejaFilter.getEstilo() != null && cervejaFilter.getEstilo().getCodigo() != null;
    }

}
