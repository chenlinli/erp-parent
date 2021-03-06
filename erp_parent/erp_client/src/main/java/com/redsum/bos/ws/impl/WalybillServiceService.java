
package com.redsum.bos.ws.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "WalybillServiceService", targetNamespace = "http://impl.ws.bos.redsum.com/", wsdlLocation = "http://localhost:9090/redsun/ws/waybillws?wsdl")
public class WalybillServiceService
    extends Service
{

    private final static URL WALYBILLSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException WALYBILLSERVICESERVICE_EXCEPTION;
    private final static QName WALYBILLSERVICESERVICE_QNAME = new QName("http://impl.ws.bos.redsum.com/", "WalybillServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:9090/redsun/ws/waybillws?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WALYBILLSERVICESERVICE_WSDL_LOCATION = url;
        WALYBILLSERVICESERVICE_EXCEPTION = e;
    }

    public WalybillServiceService() {
        super(__getWsdlLocation(), WALYBILLSERVICESERVICE_QNAME);
    }

    public WalybillServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WALYBILLSERVICESERVICE_QNAME, features);
    }

    public WalybillServiceService(URL wsdlLocation) {
        super(wsdlLocation, WALYBILLSERVICESERVICE_QNAME);
    }

    public WalybillServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WALYBILLSERVICESERVICE_QNAME, features);
    }

    public WalybillServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WalybillServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IWaybillService
     */
    @WebEndpoint(name = "WalybillServicePort")
    public IWaybillService getWalybillServicePort() {
        return super.getPort(new QName("http://impl.ws.bos.redsum.com/", "WalybillServicePort"), IWaybillService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IWaybillService
     */
    @WebEndpoint(name = "WalybillServicePort")
    public IWaybillService getWalybillServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://impl.ws.bos.redsum.com/", "WalybillServicePort"), IWaybillService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WALYBILLSERVICESERVICE_EXCEPTION!= null) {
            throw WALYBILLSERVICESERVICE_EXCEPTION;
        }
        return WALYBILLSERVICESERVICE_WSDL_LOCATION;
    }

}
