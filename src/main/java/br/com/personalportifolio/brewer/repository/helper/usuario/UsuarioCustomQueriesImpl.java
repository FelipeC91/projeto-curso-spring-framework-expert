package br.com.personalportifolio.brewer.repository.helper.usuario;

import br.com.personalportifolio.brewer.model.Grupo;
import br.com.personalportifolio.brewer.model.Usuario;
import br.com.personalportifolio.brewer.model.UsuarioGrupo;
import br.com.personalportifolio.brewer.repository.filter.UsuarioFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public class UsuarioCustomQueriesImpl implements UsuarioCustomQueries{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
        System.out.println("select do usuario");
        var builder = entityManager.getCriteriaBuilder();
        var criteriaQuery = builder.createQuery(Usuario.class);
        var root = criteriaQuery.from(Usuario.class);
        Join<Usuario, Grupo> usuarioGrupoJoin = root.join("grupos", JoinType.LEFT);

        Predicate[] predicates = resolveCriteriaFilter(usuarioFilter, root, builder, criteriaQuery);

        criteriaQuery.select(root).distinct(true).where(predicates);

        var query= entityManager.createQuery(criteriaQuery);

//        var sort = pageable.getSort();
//
//        if (sort != null && sort.iterator().hasNext()) {
//            Sort.Order order = sort.iterator().next();
//            var field = order.getProperty();
//            criteriaQuery.orderBy( order.isAscending() ? builder.asc( root.get(field) ) : builder.desc( root.get(field) ) );
//
//        }
//
//        var firstRegister = pageable.getPageNumber() * pageable.getPageSize();
//
//        if (firstRegister == 0) {
//            var query = entityManager.createQuery(criteriaQuery)
//                    .setFirstResult(pageable.getPageNumber());
//        }
//
//        var query = entityManager.createQuery(criteriaQuery)
//                .setFirstResult(firstRegister)
//                .setMaxResults(pageable.getPageSize());

        long totalResult;
        if (usuarioFilter.getGrupos().size() == 1 ) {
           var filteredList = query.getResultList().stream().filter( u -> u.getGrupos().size() == 1).collect(Collectors.toList());
           totalResult = filteredList.size();
           return new PageImpl<Usuario>(filteredList, pageable, totalResult);

        } else {
            totalResult = query.getResultList().stream().count();
            System.out.println("total result >> " + totalResult);

            return new PageImpl<Usuario>(query.getResultList(), pageable, totalResult);

        }
    }

    private Predicate[] resolveCriteriaFilter(UsuarioFilter usuarioFilter, Root<Usuario> root, CriteriaBuilder builder, CriteriaQuery criteriaQuery) {
        var predicates = new ArrayList<Predicate>();

        if (usuarioFilter != null) {

            if (usuarioFilter.getNome() != null && !usuarioFilter.getNome().isBlank()) {
                var searchKey = '%' + usuarioFilter.getNome() + '%';
                predicates.add(builder.like(root.get("nome"), searchKey));
            }

            if (usuarioFilter.getEmail() != null && !usuarioFilter.getEmail().isBlank()) {
                var searchKey = '%' + usuarioFilter.getEmail() + '%';
                predicates.add(builder.like(root.get("email"), searchKey));
            }

            if (usuarioFilter.getGrupos() != null && !usuarioFilter.getGrupos().isEmpty()) {
                if (usuarioFilter.getGrupos().size() == 1) {

                }
                for (Long grupoCodigo : usuarioFilter.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
                    var subQuery = criteriaQuery.subquery(UsuarioGrupo.class);
                    var subRoot = subQuery.from(UsuarioGrupo.class);
                    CriteriaBuilder.In<UsuarioGrupo> in = builder.in(root.get("codigo"));
                    subQuery.select(subRoot.join("id").join("usuario").get("codigo"));

                    subQuery.where(builder.equal(subRoot.join("id").join("grupo").get("codigo"), grupoCodigo));

                    in.value(subQuery);
                    predicates.add(in);
                }
            }

            return predicates.toArray(new Predicate[predicates.size()]);
        }
        return null;
    }
}
