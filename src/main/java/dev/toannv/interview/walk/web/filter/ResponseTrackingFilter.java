package dev.toannv.interview.walk.web.filter;

import brave.Span;
import brave.Tracer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static dev.toannv.interview.walk.utils.Constants.TRACKING_HEADER;


@SuppressWarnings({ "NullableProblems", "ConstantConditions" })
public class ResponseTrackingFilter extends OncePerRequestFilter {

    private final BeanFactory beanFactory;

    public ResponseTrackingFilter(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        final Tracer tracer = beanFactory.getBean(Tracer.class);
        if (Objects.nonNull(tracer)) {
            final Span currentSpan = tracer.currentSpan();
            if (null != currentSpan && StringUtils.isEmpty(response.getHeader(TRACKING_HEADER))) {
                final String traceId = currentSpan.context().traceIdString();
                response.setHeader(TRACKING_HEADER, traceId);
            }
        }

        filterChain.doFilter(request, response);
    }

}
