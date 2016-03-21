/**
 * GokuMessageReceiverInOnly.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.0  Built on : Jan 18, 2016 (09:41:27 GMT)
 */
package org.apache.ws.axis2;


/**
 *  GokuMessageReceiverInOnly message receiver
 */
public class GokuMessageReceiverInOnly extends org.apache.axis2.receivers.AbstractMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            GokuSkeleton skel = (GokuSkeleton) obj;

            //Out Envelop
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
                if ("apuestaFinalizada".equals(methodName)) {
                    //doc style
                    org.apache.ws.axis2.ApuestaFinalizada wrappedParam = (org.apache.ws.axis2.ApuestaFinalizada) fromOM(msgContext.getEnvelope()
                                                                                                                                  .getBody()
                                                                                                                                  .getFirstElement(),
                            org.apache.ws.axis2.ApuestaFinalizada.class);

                    skel.apuestaFinalizada(wrappedParam);

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
    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuesta param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuesta.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ComprobarApuestaResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ComprobarApuestaResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApuestaFinalizada param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApuestaFinalizada.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarPartidos param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarPartidos.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarPartidosResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarPartidosResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPartido param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPartido.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPartidoResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPartidoResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPichichi param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPichichi.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ApostarPichichiResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ApostarPichichiResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarEquipos param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarEquipos.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarEquiposResponse param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarEquiposResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarJugadoresEquipo param, boolean optimizeContent)
        throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarJugadoresEquipo.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        org.apache.ws.axis2.ListarJugadoresEquipoResponse param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(org.apache.ws.axis2.ListarJugadoresEquipoResponse.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ComprobarApuestaResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ComprobarApuestaResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ComprobarApuestaResponse wrapcomprobarApuesta() {
        org.apache.ws.axis2.ComprobarApuestaResponse wrappedElement = new org.apache.ws.axis2.ComprobarApuestaResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ListarPartidosResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ListarPartidosResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ListarPartidosResponse wraplistarPartidos() {
        org.apache.ws.axis2.ListarPartidosResponse wrappedElement = new org.apache.ws.axis2.ListarPartidosResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ApostarPartidoResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ApostarPartidoResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ApostarPartidoResponse wrapapostarPartido() {
        org.apache.ws.axis2.ApostarPartidoResponse wrappedElement = new org.apache.ws.axis2.ApostarPartidoResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ApostarPichichiResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ApostarPichichiResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ApostarPichichiResponse wrapapostarPichichi() {
        org.apache.ws.axis2.ApostarPichichiResponse wrappedElement = new org.apache.ws.axis2.ApostarPichichiResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ListarEquiposResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ListarEquiposResponse.MY_QNAME, factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ListarEquiposResponse wraplistarEquipos() {
        org.apache.ws.axis2.ListarEquiposResponse wrappedElement = new org.apache.ws.axis2.ListarEquiposResponse();

        return wrappedElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        org.apache.ws.axis2.ListarJugadoresEquipoResponse param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();

            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    org.apache.ws.axis2.ListarJugadoresEquipoResponse.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.ws.axis2.ListarJugadoresEquipoResponse wraplistarJugadoresEquipo() {
        org.apache.ws.axis2.ListarJugadoresEquipoResponse wrappedElement = new org.apache.ws.axis2.ListarJugadoresEquipoResponse();

        return wrappedElement;
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private java.lang.Object fromOM(org.apache.axiom.om.OMElement param,
        java.lang.Class type) throws org.apache.axis2.AxisFault {
        try {
            if (org.apache.ws.axis2.ApostarPartido.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPartido.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApostarPartidoResponse.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPartidoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApostarPichichi.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPichichi.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApostarPichichiResponse.class.equals(type)) {
                return org.apache.ws.axis2.ApostarPichichiResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ApuestaFinalizada.class.equals(type)) {
                return org.apache.ws.axis2.ApuestaFinalizada.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuesta.class.equals(type)) {
                return org.apache.ws.axis2.ComprobarApuesta.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ComprobarApuestaResponse.class.equals(type)) {
                return org.apache.ws.axis2.ComprobarApuestaResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarEquipos.class.equals(type)) {
                return org.apache.ws.axis2.ListarEquipos.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarEquiposResponse.class.equals(type)) {
                return org.apache.ws.axis2.ListarEquiposResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarJugadoresEquipo.class.equals(type)) {
                return org.apache.ws.axis2.ListarJugadoresEquipo.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarJugadoresEquipoResponse.class.equals(
                        type)) {
                return org.apache.ws.axis2.ListarJugadoresEquipoResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarPartidos.class.equals(type)) {
                return org.apache.ws.axis2.ListarPartidos.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (org.apache.ws.axis2.ListarPartidosResponse.class.equals(type)) {
                return org.apache.ws.axis2.ListarPartidosResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

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
