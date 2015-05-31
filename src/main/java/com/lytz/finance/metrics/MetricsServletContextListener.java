/**
 * 
 */
package com.lytz.finance.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

/**
 * @author cloudlu
 *
 */
public class MetricsServletContextListener extends MetricsServlet.ContextListener {

    public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    
    @Override
    protected MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }

    
}
