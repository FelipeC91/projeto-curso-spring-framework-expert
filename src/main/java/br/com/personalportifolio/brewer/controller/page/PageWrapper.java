package br.com.personalportifolio.brewer.controller.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

public class PageWrapper<T> {
    private Page<T> page;
    private UriComponentsBuilder uriBuilder;

    private final String SPACE_IN_UTF8 = "%20";

    public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
        this.page = page;
        //this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
        var formattedUrl = httpServletRequest
                                .getRequestURL()
                                .append( httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
                                .toString()
                                .replaceAll("\\+", SPACE_IN_UTF8);
        
        this.uriBuilder = UriComponentsBuilder.fromHttpUrl(formattedUrl);
    }

    public Page<T> getPage() {
        return page;
    }

    public String urlResolverTo(int page) {
        return uriBuilder.replaceQueryParam("page", page).toUriString();
    }

    public String orderedUrl(String property) {
        UriComponentsBuilder uriBuilderOrdered = UriComponentsBuilder.fromUriString(uriBuilder.build(true).encode().toUriString());

        var sortValue = property.concat(",").concat(invertDirection(property));

        return uriBuilderOrdered.replaceQueryParam("sort", sortValue).build(true).encode().toUriString();

    }

    public String invertDirection(String property) {
        var direction = "ASC";

        Sort.Order order = (page.getSort() != null) ? page.getSort().getOrderFor(property) : null;

        if (order != null) {
            direction = Sort.Direction.ASC.equals(order.getDirection()) ? "DESC" : "ASC";
            System.out.println("before >> " + direction);
        }
        System.out.println("after >> " + direction);
        return direction;
    }

    public boolean isAscendent(String property) {
        return invertDirection(property).equals("ASC");
    }

    public boolean isOrdered(String property) {
            return page.getSort() != null && page.getSort().getOrderFor(property) != null ? true : false;
    }
}