
package com.redsum.bos.ws.impl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.redsum.bos.ws.ObjectFactory;
import com.redsum.bos.ws.Waybilldetail;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "IWaybillService", targetNamespace = "http://ws.bos.redsum.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IWaybillService {


    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg4
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Long
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "addWaybill", targetNamespace = "http://ws.bos.redsum.com/", className = "com.redsum.bos.ws.AddWaybill")
    @ResponseWrapper(localName = "addWaybillResponse", targetNamespace = "http://ws.bos.redsum.com/", className = "com.redsum.bos.ws.AddWaybillResponse")
    public Long addWaybill(
        @WebParam(name = "arg0", targetNamespace = "")
        Long arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        String arg4);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<com.redsum.bos.ws.Waybilldetail>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "walbilldetailList", targetNamespace = "http://ws.bos.redsum.com/", className = "com.redsum.bos.ws.WalbilldetailList")
    @ResponseWrapper(localName = "walbilldetailListResponse", targetNamespace = "http://ws.bos.redsum.com/", className = "com.redsum.bos.ws.WalbilldetailListResponse")
    public List<Waybilldetail> walbilldetailList(
        @WebParam(name = "arg0", targetNamespace = "")
        Long arg0);

}
