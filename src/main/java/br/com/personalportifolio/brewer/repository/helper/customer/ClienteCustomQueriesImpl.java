package br.com.personalportifolio.brewer.repository.helper.customer;

import br.com.personalportifolio.brewer.model.Cidade;
import br.com.personalportifolio.brewer.model.Cliente;
import br.com.personalportifolio.brewer.model.Endereco;
import br.com.personalportifolio.brewer.model.TipoCliente;
import br.com.personalportifolio.brewer.repository.filter.CustomerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;

public class ClienteCustomQueriesImpl implements ClienteCustomQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> filtrar(CustomerFilter customerFilter, Pageable pageable) {
        var builder = entityManager.getCriteriaBuilder();
        var  criteriaQuery = builder.createQuery(Cliente.class);
        var rootElement = criteriaQuery.from(Cliente.class);
        Fetch<Cliente, Endereco> enderecoFetch = rootElement.fetch("endereco");
        Fetch<Endereco, Cidade> cidadeFetch = enderecoFetch.fetch( "cidade", JoinType.LEFT);


        Predicate[] predicates = resolveCriteriaFilter(customerFilter, rootElement, builder);

        if (predicates.length < 0) {
                criteriaQuery.select(rootElement);
        } else {
                criteriaQuery.select(rootElement).where(predicates);
        }

        var sort = pageable.getSort();

        if (sort != null && sort.iterator().hasNext()) {
            Sort.Order order = sort.iterator().next();
            var field = order.getProperty();
            criteriaQuery.orderBy( order.isAscending() ? builder.asc( rootElement.get(field) ) : builder.desc( rootElement.get(field) ) );

        }

        var firstRegister = pageable.getPageNumber() * pageable.getPageSize();

        if (firstRegister == 0) {
            var query = entityManager.createQuery(criteriaQuery)
                    .setFirstResult(pageable.getPageNumber());
        }

        var query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstRegister)
                .setMaxResults(pageable.getPageSize());

        var totalResult = query.getResultList().stream().count();

        return new PageImpl<Cliente>(query.getResultList(), pageable, totalResult );
    }

    private Predicate[] resolveCriteriaFilter(CustomerFilter customerFilter, Root<Cliente> itemRoot, CriteriaBuilder builder) {
        var predicates = new ArrayList<javax.persistence.criteria.Predicate>();

        if (customerFilter != null) {
            if (customerFilter.getNome() != null && !customerFilter.getNome().isEmpty()) {
                var searchKey = "%" + customerFilter.getNome() + "%";
                System.out.println(searchKey);
                predicates.add( builder.like( itemRoot.<String>get("nome"), searchKey));
            }

            if (customerFilter.getCpfCnpj() != null && !customerFilter.getCpfCnpj().isEmpty()) {
                var rawCpfCnpj = TipoCliente.removeMask(customerFilter.getCpfCnpj());
                System.out.println(rawCpfCnpj);
                predicates.add( builder.equal(itemRoot.get("cpfCnpj"), rawCpfCnpj));
            }

            return predicates.toArray(new Predicate[predicates.size()]);

        }

        return null;
    }
}
