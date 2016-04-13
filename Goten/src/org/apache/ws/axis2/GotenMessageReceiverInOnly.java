/**
 * GotenMessageReceiverInOnly.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:41:27 GMT)
 */
package org.apache.ws.axis2;


/**
 *  GotenMessageReceiverInOnly message receiver
 */
public class GotenMessageReceiverInOnly extends org.apache.axis2.receivers.AbstractMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            GotenSkeleton skel = (GotenSkeleton) obj;

            //Out Envelop
            @SuppressWarnings("unused")
			org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJavaIdentifier(
                            op.getName().getLocalPart())) != null)) {
                if ("partidoFinalizado".equals(methodName)) {
                    //doc style
                    org.apache.ws.axis2.PartidoFinalizado wrappedParam = (org.apache.ws.axis2.PartidoFinalizado) fromOM(msgContext.getEnvelope()
                                                                                                                                  .getBody()
                                                                                                                                  .getFirstElement(),
                            org.apache.ws.axis2.PartidoFinalizado.class);

                    skel.partidoFinalizado(wrappedParam);

                    envelope = getSOAPFactory(msgContext).getDefaultEnvelope();
                } else if ("competicionFinalizada".equals(methodName)) {
                    //doc style
                    org.apache.ws.axis2.CompeticionFinalizada wrappedParam = (org.apache.ws.axis2.CompeticionFinalizada) fromOM(msgContext.getEnvelope()
                                                                                                                                          .getBody()
                                                                                                                                          .getFirstElement(),
                            org.apache.ws.axis2.CompeticionFinalizada.class);

                    skel.competicionFinalizada(wrappedParam);

                    envelope = getSOAPFactory(msgContext).getDefaultEnvelope();
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    @SuppressWarnings("unused")
	private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.RealizarApuestaPartido param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.RealizarApuestaPartido.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
	private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.RealizarApuestaPartidoResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.RealizarApuestaPartidoResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
	private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuestaPichichi param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuestaPichichi.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuestaPichichiResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuestaPichichiResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.PartidoFinalizado param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.PartidoFinalizado.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.RealizarApuestaPichichi param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.RealizarApuestaPichichi.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.RealizarApuestaPichichiResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.RealizarApuestaPichichiResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuestaPartido param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuestaPartido.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuestaPartidoResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuestaPartidoResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.CompeticionFinalizada param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.CompeticionFinalizada.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.RealizarApuestaPartidoResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.RealizarApuestaPartidoResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
	private org.apache.ws.axis2.RealizarApuestaPartidoResponse wraprealizarApuestaPartido() {
        org.apache.ws.axis2.RealizarApuestaPartidoResponse wrappedElement = new org.apache.ws.axis2.RealizarApuestaPartidoResponse();

        return wrappedElement;
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ComprobarApuestaPichichiResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ComprobarApuestaPichichiResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.ws.axis2.ComprobarApuestaPichichiResponse wrapcomprobarApuestaPichichi() {
        org.apache.ws.axis2.ComprobarApuestaPichichiResponse wrappedElement = new org.apache.ws.axis2.ComprobarApuestaPichichiResponse();

        return wrappedElement;
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.RealizarApuestaPichichiResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.RealizarApuestaPichichiResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.ws.axis2.RealizarApuestaPichichiResponse wraprealizarApuestaPichichi() {
        org.apache.ws.axis2.RealizarApuestaPichichiResponse wrappedElement = new org.apache.ws.axis2.RealizarApuestaPichichiResponse();

        return wrappedElement;
    }

    @SuppressWarnings("unused")
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ComprobarApuestaPartidoResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ComprobarApuestaPartidoResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    @SuppressWarnings("unused")
    private org.apache.ws.axis2.ComprobarApuestaPartidoResponse wrapcomprobarApuestaPartido() {
        org.apache.ws.axis2.ComprobarApuestaPartidoResponse wrappedElement = new org.apache.ws.axis2.ComprobarApuestaPartidoResponse();

        return wrappedElement;
    }

    /**
     *  get the default envelope
     */
    @SuppressWarnings("unused")
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        @SuppressWarnings("rawtypes") java.lang.Class type) throws org.apache.axis2.AxisFault {
        try {
            if (org.apache.ws.axis2.CompeticionFinalizada.class.equals(type)) {
                return org.apache.ws.axis2.CompeticionFinalizada.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuestaPartido.class.equals(type)) {
                return org.apache.ws.axis2.ComprobarApuestaPartido.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuestaPartidoResponse.class.equals(
                        type)) {
                return org.apache.ws.axis2.ComprobarApuestaPartidoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuestaPichichi.class.equals(type)) {
                return org.apache.ws.axis2.ComprobarApuestaPichichi.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuestaPichichiResponse.class.equals(
                        type)) {
                return org.apache.ws.axis2.ComprobarApuestaPichichiResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.PartidoFinalizado.class.equals(type)) {
                return org.apache.ws.axis2.PartidoFinalizado.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.RealizarApuestaPartido.class.equals(type)) {
                return org.apache.ws.axis2.RealizarApuestaPartido.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.RealizarApuestaPartidoResponse.class.equals(
                        type)) {
                return org.apache.ws.axis2.RealizarApuestaPartidoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.RealizarApuestaPichichi.class.equals(type)) {
                return org.apache.ws.axis2.RealizarApuestaPichichi.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.RealizarApuestaPichichiResponse.class.equals(
                        type)) {
                return org.apache.ws.axis2.RealizarApuestaPichichiResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    @SuppressWarnings("unused")
    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
