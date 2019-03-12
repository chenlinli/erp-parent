
package com.redsum.bos.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.redsum.bos.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddWaybill_QNAME = new QName("http://ws.bos.redsum.com/", "addWaybill");
    private final static QName _WalbilldetailList_QNAME = new QName("http://ws.bos.redsum.com/", "walbilldetailList");
    private final static QName _WalbilldetailListResponse_QNAME = new QName("http://ws.bos.redsum.com/", "walbilldetailListResponse");
    private final static QName _AddWaybillResponse_QNAME = new QName("http://ws.bos.redsum.com/", "addWaybillResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.redsum.bos.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddWaybill }
     * 
     */
    public AddWaybill createAddWaybill() {
        return new AddWaybill();
    }

    /**
     * Create an instance of {@link AddWaybillResponse }
     * 
     */
    public AddWaybillResponse createAddWaybillResponse() {
        return new AddWaybillResponse();
    }

    /**
     * Create an instance of {@link WalbilldetailList }
     * 
     */
    public WalbilldetailList createWalbilldetailList() {
        return new WalbilldetailList();
    }

    /**
     * Create an instance of {@link WalbilldetailListResponse }
     * 
     */
    public WalbilldetailListResponse createWalbilldetailListResponse() {
        return new WalbilldetailListResponse();
    }

    /**
     * Create an instance of {@link Waybilldetail }
     * 
     */
    public Waybilldetail createWaybilldetail() {
        return new Waybilldetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWaybill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsum.com/", name = "addWaybill")
    public JAXBElement<AddWaybill> createAddWaybill(AddWaybill value) {
        return new JAXBElement<AddWaybill>(_AddWaybill_QNAME, AddWaybill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WalbilldetailList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsum.com/", name = "walbilldetailList")
    public JAXBElement<WalbilldetailList> createWalbilldetailList(WalbilldetailList value) {
        return new JAXBElement<WalbilldetailList>(_WalbilldetailList_QNAME, WalbilldetailList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WalbilldetailListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsum.com/", name = "walbilldetailListResponse")
    public JAXBElement<WalbilldetailListResponse> createWalbilldetailListResponse(WalbilldetailListResponse value) {
        return new JAXBElement<WalbilldetailListResponse>(_WalbilldetailListResponse_QNAME, WalbilldetailListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWaybillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsum.com/", name = "addWaybillResponse")
    public JAXBElement<AddWaybillResponse> createAddWaybillResponse(AddWaybillResponse value) {
        return new JAXBElement<AddWaybillResponse>(_AddWaybillResponse_QNAME, AddWaybillResponse.class, null, value);
    }

}
