/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:42:13 GMT)
 */
package org.apache.ws.axis2;


/**
 *  ExtensionMapper class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ExtensionMapper {
    public static java.lang.Object getTypeObject(
        java.lang.String namespaceURI, java.lang.String typeName,
        javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
        if ("http://ws.apache.org/namespaces/axis2/map".equals(namespaceURI) &&
                "entry1".equals(typeName)) {
            return org.apache.ws.namespaces.axis2.map.Entry1.Factory.parse(reader);
        }

        if ("http://ws.apache.org/namespaces/axis2/map".equals(namespaceURI) &&
                "map1".equals(typeName)) {
            return org.apache.ws.namespaces.axis2.map.Map1.Factory.parse(reader);
        }

        throw new org.apache.axis2.databinding.ADBException("Unsupported type " +
            namespaceURI + " " + typeName);
    }
}
